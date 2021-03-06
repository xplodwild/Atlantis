/*
 * Copyright (C) 2014 Loris Durand, Guillaume Lesniak, Cristian Sanna,
 *                    Lucie Wiemert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.miage.atlantis;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.audio.AudioNode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.tools.Color;
import fr.miage.atlantis.audio.AudioConstants;
import fr.miage.atlantis.audio.AudioManager;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.graphics.AnimationBrain;
import fr.miage.atlantis.graphics.FutureCallback;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.graphics.ParticlesFactory;
import fr.miage.atlantis.graphics.Utils;
import fr.miage.atlantis.graphics.hud.TileActionDisplay;
import fr.miage.atlantis.graphics.models.AbstractTileModel;
import fr.miage.atlantis.graphics.models.AnimatedModel;
import fr.miage.atlantis.graphics.models.BoatModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.graphics.models.SeaSerpentModel;
import fr.miage.atlantis.graphics.models.SharkModel;
import fr.miage.atlantis.graphics.models.StaticModel;
import fr.miage.atlantis.logic.GameLogic;
import fr.miage.atlantis.logic.GameTurn;
import fr.miage.atlantis.network.NetworkObserverProxy;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Game Engine loop class
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class Game3DLogic extends GameLogic {

    /**
     * Renderer des objet 3d
     */
    private Game3DRenderer mRenderer;

    /**
     * Entite courante
     */
    private GameEntity mPickedEntity;

    /**
     * Compteur callback
     */
    private int mBypassCallbackCount;

    /**
     * Liste des pick d'entite
     */
    private List<EntityPickRequest> mEntRequestHistory;

    /**
     * Liste des pick de tiles
     */
    private List<TilePickRequest> mTileRequestHistory;

    /**
     * Cancel ou non un pick
     */
    private boolean mCanCancelPickingAction;

    /**
     * Cancel un callback d'une action
     */
    private FutureCallback mCancelActionCallback;

    /**
     * Tile utilisé pour annuler une action
     */
    private TileAction mTileUsedToCancel;

    /**
     * Entité source pouvant etre annulée
     */
    private GameEntity mCancellableSource;

    /**
     * Entité cible qui subit l'annulation de sort
     */
    private GameEntity mCancellableTarget;


    /**
     * Instance du logger Java
     */
    private static final Logger logger = Logger.getGlobal();


    /**
     * Constructeur de Game3DLogic
     *
     */
    public Game3DLogic() {
        super();
        mRenderer = new Game3DRenderer(this);
        mBypassCallbackCount = 0;
        mEntRequestHistory = new ArrayList<EntityPickRequest>();
        mTileRequestHistory = new ArrayList<TilePickRequest>();
        mCanCancelPickingAction = false;
    }

    /**
     * Retourne le renderer 3D
     * @return Renderer 3D
     */
    public Game3DRenderer getRenderer() {
        return mRenderer;
    }

    /**
     * Sert a serialiser les données pour la sauvegarde
     *
     * @param data données a serialiser
     * @throws IOException Exception d'entree sortie
     */
    @Override
    public void serializeEssentialData(DataOutputStream data) throws IOException {
        super.serializeEssentialData(data);
        data.writeBoolean(mPickedEntity != null);
        if (mPickedEntity != null) data.writeUTF(mPickedEntity.getName());

    }

    /**
     * Sert a deserialiser les données en vue du chargement
     *
     * @param data données a deserialiser
     * @throws IOException Exception d'entree sortie
     */
    @Override
    public void deserializeData(DataInputStream data) throws IOException {
        super.deserializeData(data);
        if (data.readBoolean()) {
            mPickedEntity = getBoard().getEntity(data.readUTF());
        }
    }


    /**
     * Demarre le renderer graphique
     */
    @Override
    public void boot() {
        mRenderer.start();
    }

    /**
     * Prépare le jeu
     *
     * @param players joueurs du jeu
     * @param prepareBoard plateau du jeu
     */
    @Override
    public void prepareGame(String[] players, boolean prepareBoard) {
        super.prepareGame(players, prepareBoard);

        // On fait le rendu des tiles
        mRenderer.getBoardRenderer().clearBoard();
        mRenderer.getEntitiesRenderer().clearEntities();

        if (prepareBoard) {
            mRenderer.getBoardRenderer().renderBoard(getBoard());

            // Rendu des entités déjà placées sur le plateau
            Map<String, GameTile> tiles = getBoard().getTileSet();
            for (GameTile tile : tiles.values()) {
                for (GameEntity ent : tile.getEntities()) {
                    mRenderer.getEntitiesRenderer().addEntity(ent);
                }
            }
        }
    }


    /**
     * Demarre une partie
     */
    @Override
    public void startGame() {

        // On joue la musique!
        AudioManager.getDefault().setAmbience(true);

        if (DBG_AUTOPREPARE) {
            // TEST: On place des tokens
            Player[] plays = getPlayers();
            for (int i = 0; i < plays.length; i++) {
                Player p = plays[i];
                List<PlayerToken> tokens = p.getTokens();

                for (PlayerToken token : tokens) {
                    int rand = new Random().nextInt(15) + 1;
                    token.moveToTile(this, getBoard().getTileSet().get("Beach #" + rand));
                    token.setState(PlayerToken.STATE_ON_LAND);

                    mRenderer.getEntitiesRenderer().addEntity(token);
                }
            }
            // TEST: On place des bateaux
            Boat boat1 = new Boat();
            boat1.moveToTile(this, getBoard().getTileSet().get("Water #37"));
            mRenderer.getEntitiesRenderer().addEntity(boat1);
        }

        super.startGame();
    }


    /**
     * Remet à zéro les éléments pickée et relance le dernier picking
     * @return true ou false si reset ou non
     */
    public boolean resetPickingAction() {
        assert mEntRequestHistory.size() == mTileRequestHistory.size();

        if (mCanCancelPickingAction && mEntRequestHistory.size() > 1
                && mTileRequestHistory.size() > 1) {
            // On relance la requête avant la dernière requête
            int reqId = mTileRequestHistory.size() - 2;
            TilePickRequest tileRq = mTileRequestHistory.get(reqId);
            EntityPickRequest entRq = mEntRequestHistory.get(reqId);
            mRenderer.getInputListener().forceResetRequest();
            requestPick(entRq, tileRq);

            // Une seule action peut être annulée.
            mCanCancelPickingAction = false;

            // Les actions annulables sont seulement actives lorsqu'on pick une entité initiale. Il
            // faut donc reset l'entité pickée.
            mPickedEntity = null;

            mRenderer.getHud().getGameHud().hideRightClickHint();
            AudioManager.getDefault().playSound(AudioConstants.Path.WHOOSH);

            return true;
        } else {
            // Rien à relancer ou pas possible
            return false;
        }
    }

    /**
     * Getter de la derniere entité selectionnée
     *
     * @return Derniere entity selectionnée
     */
    public GameEntity getLastPickedEntity() {
        return mPickedEntity;
    }


    /**
     * Methode executée a chaque debut de tours
     *
     * @param p Joueur courant
     */
    public void onTurnStart(Player p) {
        logger.log(Level.FINE, "Game3DLogic: onTurnStart()", new Object[]{});

        AudioManager.getDefault().playSound(AudioConstants.Path.DING);

        mRenderer.getHud().getGameHud().displayPlayerTiles(getCurrentTurn().getPlayer().getActionTiles());

        getCurrentTurn().onTurnStarted();
    }


    /**
     * Methode executé au placement d'un pion
     * @param pt Token placé
     */
    @Override
    public void onInitialTokenPut(PlayerToken pt) {
        mRenderer.getEntitiesRenderer().addEntity(pt);
        getCurrentTurn().onInitialTokenPutDone(pt);
    }


    /**
     * Methode executé au placement dun bateau
     * @param b bateau placé
     */
    @Override
    public void onInitialBoatPut(Boat b) {
        super.onInitialBoatPut(b);
        mRenderer.getEntitiesRenderer().addEntity(b);
        getCurrentTurn().onInitialBoatPutDone(b);
    }


    /**
     * Methode executée au moment ou on joue une tile
     * @param tile tile joué
     * @param action action de tile
     */
    public void onPlayTileAction(GameTile tile, TileAction action) {
        action.use(tile, this);
        mCanCancelPickingAction = false;
    }


    /**
     * Methode executé a chaque depalcement d'unité
     * @param ent entité bougé
     * @param dest destination
     */
    @Override
    public void onUnitMove(final GameEntity ent, final GameTile dest) {
        // On récupère la node 3D de cette entité
        final Node entNode = mRenderer.getEntitiesRenderer().getNodeFromEntity(ent);
        if (entNode == null) {
            throw new IllegalStateException("Aucune node 3D trouvée pour l'unité à déplacer!");
        }

        AbstractTileModel tileNode = mRenderer.getBoardRenderer().findTileModel(dest);
        if (tileNode == null) {
            throw new IllegalStateException("Aucune node 3D trouvée pour la tile " + dest.getName() + "!");
        }

        final GameTile previousTile = ent.getTile();

        // On génére l'animation de déplacement. On gère le cas particulier où c'est un joueur qui
        // monte sur un bateau.
        MotionEvent motionEvent;
        if (ent instanceof PlayerToken) {
            PlayerToken pt = (PlayerToken) ent;
            if (pt.getState() == PlayerToken.STATE_ON_BOAT) {
                motionEvent = generateEntityceptionMotion(entNode,
                        (AnimatedModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(pt.getBoat()));
            } else {
                motionEvent = generateEntityOnTileMotion(entNode, tileNode);
            }
        } else {
            motionEvent = generateEntityOnTileMotion(entNode, tileNode);
        }

        // On gère l'effet sonore
        AudioNode tmpAudioNode = null;
        if (ent instanceof Boat) {
            tmpAudioNode = AudioManager.getDefault().playSound(AudioConstants.Path.MOVE_BOAT, true);
        } else if (dest instanceof WaterTile) {
            tmpAudioNode = AudioManager.getDefault().playSound(AudioConstants.Path.MOVE_SWIM, true);
        }
        final AudioNode audioEvent = tmpAudioNode;

        // Callback lorsque l'animation est terminée
        motionEvent.getPath().addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (control.getPath().getNbWayPoints() == wayPointIndex + 1) {
                    logger.log(Level.FINE, "Game3DLogic: Waypoint reached, processing events", new Object[]{});
                    // On bouge effectivement le joueur de tile. Note: On fait cette action à la
                    // fin de l'animation pour pouvoir proprement enchainer les actions (exemple:
                    // on marche sur un requin ou un bateau). Ainsi, le GameTurn attend avant de
                    // faire la suite des opérations via onUnitMoveFinished.
                    if (!ent.moveToTile(Game3DLogic.this, dest)) {
                        // On est à la fin du chemin, mise à jour de l'animation de l'entité bougée
                        // car aucune action n'a eu lieu lors du déplacement
                        ((AnimatedModel) entNode).playAnimation(AnimationBrain.getIdleAnimation(ent));
                    }

                    // Remise à zéro de l'orientation
                    entNode.setLocalRotation(Quaternion.IDENTITY);

                    // Gestion de la montée sur un bateaux
                    if (entNode instanceof PlayerModel) {
                        PlayerToken pt = (PlayerToken) ent;
                        if (pt.getState() == PlayerToken.STATE_ON_BOAT) {
                            // Tour de magie! On détache en fait le personnage du monde normal, et
                            // on l'attache au bateau directement. Ainsi, le personnage bougera
                            // automatiquement avec le bateau.
                            Boat b = pt.getBoat();
                            BoatModel bm = (BoatModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(b);
                            entNode.getParent().detachChild(entNode);
                            bm.attachChild(entNode);

                            // Du coup, on remet à zéro la position pour qu'elle soit relative cette
                            // fois-ci au bateau et non à l'origine du monde 3D
                            entNode.setLocalTranslation(Vector3f.ZERO);
                            entNode.rotate(0, Utils.degreesToRad(90), 0);

                            switch (b.getPlayerSlot(pt)) {
                                case 1:
                                    entNode.setLocalTranslation(10, 0, 0);
                                    break;

                                case 2:
                                    entNode.setLocalTranslation(-10, 0, 0);
                                    break;
                            }

                            // Et on joue un son lié à cet événement
                            final AudioManager audioMan = AudioManager.getDefault();
                            audioMan.playSound(AudioConstants.Path.GO_IN_BOAT);
                        }
                    }

                    // On vérifie si la tile est une tile d'escape, et on lance l'animation
                    // d'escape si c'est le cas
                    if (dest instanceof WaterTile && ent instanceof PlayerToken) {
                        WaterTile wt = (WaterTile) dest;
                        PlayerToken pt = (PlayerToken) ent;

                        if (!pt.isDead() && wt.isLandingTile()) {
                            // On recherche la tile sur laquelle placer le bonhomme en lieu sûr
                            GameTile escapeBorder = wt.findEscapeBorder();
                            if (escapeBorder != null) {
                                mBypassCallbackCount++;
                                onUnitMove(ent, escapeBorder);
                                pt.setState(PlayerToken.STATE_SAFE);
                            } else {
                                // BEWARE DRAGONS! La tile est flaggée comme étant une tile d'escape,
                                // mais il n'y a aucun border d'escape autour! On ne fait rien et
                                // on prévient Cristian qu'il a merdé
                                Logger.getGlobal().severe("La tile " + wt.getName() + " est marquée"
                                        + " comme étant une tile d'escape, mais il n'y a aucun"
                                        + " border d'escape autour!");
                            }
                        }
                    }

                    // On arrête le son
                    if (audioEvent != null) {
                        AudioManager.getDefault().stopSound(audioEvent);
                    }


                    // On notifie le jeu, toutes les actions nécessaires sont faites (si on
                    // ne saute pas la notification, si c'est appelé d'un autre événement).
                    if (getCurrentTurn() != null && mBypassCallbackCount <= 0) {
                        getCurrentTurn().onUnitMoveFinished();
                    } else if (mBypassCallbackCount > 0) {
                        mBypassCallbackCount--;
                    }
                }
            }
        });

        // On détermine l'animation à jouer
        ((AnimatedModel) entNode).playAnimation(AnimationBrain.getMovementAnimation(ent, dest));

        // On lance le mouvement
        motionEvent.play();

        // On lis un son
    }


    /**
     * Methode execute quand on lance le dé
     * @param face face du dé recue
     */
    public void onDiceRoll(int face) {
        // On a besoin de lancer le dé
        logger.log(Level.FINE, "Game3DLogic: onDiceRoll", new Object[]{});
        AudioManager.getDefault().playSound(AudioConstants.Path.DICE_ROLL);
        mRenderer.rollDiceAnimation(face);
    }


    /**
     * Methode executé quand on coule une tile
     * @param tile Tile coulée
     */
    public void onSinkTile(final GameTile tile) {
        AudioManager.getDefault().playSound(AudioConstants.Path.TILE_SPLASH);

        doTileSinkAnimation(tile, new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (control.getPath().getNbWayPoints() == wayPointIndex + 1) {
                    // On est à la fin de l'animation. On remplace la tile
                    // coulée par une WaterTile
                    final WaterTile newTile = getBoard().sinkTile(Game3DLogic.this, tile);
                    mRenderer.getBoardRenderer().replaceTile(tile, newTile);

                    // On fait en sorte que les nouveaux nageurs nagent
                    List<GameEntity> newTileEntities = new ArrayList<GameEntity>(newTile.getEntities());
                    for (GameEntity ent : newTileEntities) {
                        if (ent instanceof PlayerToken) {
                            mBypassCallbackCount++;
                            onUnitMove(ent, newTile);
                        }
                    }

                    // D'abord, on affiche la tile "piochée" à l'écran, si on doit la voir (ie.
                    // c'est une tile immédiate, ou c'est notre tour)
                    final NetworkObserverProxy nop = NetworkObserverProxy.getDefault();
                    final TileAction action = tile.getAction();

                    if (!nop.isNetworkGame()
                            || nop.getPlayerNumber() == getCurrentTurn().getPlayer().getNumber()
                            || action.isImmediate()) {
                        final TileActionDisplay tad = TileActionDisplay.getTileForAction(action,
                                mRenderer.getAssetManager());
                        mRenderer.getHud().displayCenter(tad);
                        mRenderer.getHud().getAnimator().animateFade(tad, 1.0f);

                        // Ensuite, après l'affichage, on traite l'action
                        mRenderer.getFuture().addFutureTimeCallback(new FutureCallback(2.0f) {
                            @Override
                            public void onFutureHappened() {
                                mRenderer.getHud().getAnimator().animateFade(tad, 0.0f);

                                // On lance l'action sous la tile, si c'est immédiat
                                if (action.isImmediate()) {
                                    onPlayTileAction(newTile, action);
                                } else {
                                    // L'action est pas immédiate, on stock la tile dans la pile du
                                    // joueur.
                                    Player player = getCurrentTurn().getPlayer();
                                    player.addActionTile(action);
                                }

                                // SinkTileFinished lance le dé - le joueur en cours est autoritaire
                                // du dé, donc on le lance que si besoin
                                if (!nop.isNetworkGame() || getCurrentTurn().isMyNetworkTurn()) {
                                    // Fin de l'action, étape suivante
                                    getCurrentTurn().onSinkTileFinished();
                                }
                            }
                        });
                    } else {
                        // On est en réseau, et c'est pas notre tour ou la tile est pas immédiate.
                        // Si elle est effectivement immédiate, on lance l'action (animations, etc)
                        if (action.isImmediate()) {
                            onPlayTileAction(newTile, action);
                        } else {
                            Player player = getCurrentTurn().getPlayer();
                            player.addActionTile(action);
                        }
                    }
                }
            }
        });
    }


    /**
     * Appelé lorsque l'utilisateur appuie sur Espace
     */
    public void onHitSpace() {
        // On a appuyé sur espace: Si on est en train de laisser la possibilité à l'utilisateur
        // d'annuler une action, on le fait.
        if (mCancelActionCallback != null && mTileUsedToCancel != null) {
            getCurrentTurn().useRemoteTile(((PlayerToken)mCancellableTarget).getPlayer(),mTileUsedToCancel);

            mTileUsedToCancel = null;
            mCancelActionCallback = null;

            mRenderer.getHud().getGameHud().hidePromptCancel();
        }
    }

    /**
     * Utilisé quand on annule une action
     */
    public void onCancelAction() {
        if (mTileUsedToCancel != null) {
            if (mTileUsedToCancel.getAction() == TileAction.ACTION_CANCEL_ANIMAL) {
                mCancellableSource.die(this);
            }
            mCancellableSource = null;
            mTileUsedToCancel = null;
            mCancelActionCallback = null;
            mRenderer.getHud().getGameHud().hidePromptCancel();
        }
    }

    /**
     * Cherche une tile action pouvant annuler une entité
     *
     * @param entity entité a annuler
     * @param playTilesList liste des tileaction ou chercher
     * @return la tileaction souhaité si trouvée dans la liste
     */
    private TileAction findCancelAction(int entity, List<TileAction> playTilesList) {
        for (TileAction tile : playTilesList) {
            if (tile.getAction() == TileAction.ACTION_CANCEL_ANIMAL
                    && tile.getEntity() == entity) {
                return tile;
            }
        }

        return null;
    }


    /**
     * Methode executée quand on
     *
     * @param source
     * @param target
     * @param action
     */
    public void onCancellableEntityAction(final GameEntity source, final GameEntity target,
            final int action) {
        final PlayerToken token = (PlayerToken) target;
        final List<TileAction> playTilesList = token.getPlayer().getActionTiles();

        if (mTileUsedToCancel != null) {
            /**
             * TODO: On ne sait pas quel joueur sera touché en premier par le requin dans la tile.
             * Si deux joueurs différents ont un pion dans la tile attaquée par le requin, on ne
             * sait pas lequel sera touché en premier, et si les deux ont une tile permettant
             * d'annuler l'attaque, on ne sait pas laquelle sera utilisée.
             * De même, si le premier pion testé n'a pas de tile d'annulation, le requin lancera
             * quand même son attaque sur lui, même si un autre a une tile permettant d'annuler.
             * Pour l'instant, on laisse comme ça, mais c'est à fixer (transformer cette méthode
             * en boolean, et lancer onEntityAction depuis Shark si tous les onCancellableEntity
             * ont retourné false par exemple). De même, mettre en queue les cancellable au cas où
             * on a plusieurs joueurs permettant d'annuler.
             */
            return;
        }

        // On cherche si le joueur a une tile permettant d'annuler l'action
        switch (action) {
            case GameEntity.ACTION_SHARK_EAT:
                mTileUsedToCancel = findCancelAction(TileAction.ENTITY_SHARK, playTilesList);
                break;

            case GameEntity.ACTION_WHALE_NUKE:
                mTileUsedToCancel = findCancelAction(TileAction.ENTITY_WHALE, playTilesList);
                break;

            case GameEntity.ACTION_SEASERPENT_CRUSH:
                mTileUsedToCancel = findCancelAction(TileAction.ENTITY_SEASERPENT, playTilesList);
                break;

            default:
                mTileUsedToCancel = null;
                break;

        }

        if (mTileUsedToCancel != null) {
            // On a une tile d'annulation pour ça, on poll sur le HUD pendant 3 secondes
            // si l'utilisateur veut jouer sa tile. Si il appuie sur espace, la tile
            // d'annulation est utilisée et la tile est annulée.
            mCancellableSource = source;
            mCancellableTarget = target;
            mRenderer.getHud().getGameHud().promptCancel();
            mCancelActionCallback = new FutureCallback(3.0f) {
                @Override
                public void onFutureHappened() {
                    if (mTileUsedToCancel != null) {
                        // La tile n'a pas été utilisée, donc on lance l'action
                        mTileUsedToCancel = null;
                        mCancelActionCallback = null;
                        mCancellableSource = null;

                        onEntityAction(source, target, action);
                        mRenderer.getHud().getGameHud().hidePromptCancel();
                    }
                }
            };

            mRenderer.getFuture().addFutureTimeCallback(mCancelActionCallback);
        } else {
            // On n'a pas de tile pour annuler, on lance l'action
            onEntityAction(source, target, action);
        }
    }

    public void onEntityAction(GameEntity source, GameEntity target, int action) {
        switch (action) {
            case GameEntity.ACTION_SHARK_EAT: {
                // On lance les animations.
                // Ces deux prochaines lignes sont PARFAITEMENT propres et certifiées sans bug.
                final Shark shark = (Shark) ((source instanceof Shark) ? source : target);
                final PlayerToken token = (PlayerToken) ((target instanceof PlayerToken) ? target : source);

                final SharkModel sharkModel = (SharkModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(shark);
                PlayerModel playerModel = (PlayerModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(token);

                sharkModel.playAnimation(SharkModel.ANIMATION_ATTACK_SWIMMER, false, true, new AnimEventListener() {
                    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                        sharkModel.playAnimation(AnimationBrain.getIdleAnimation(shark));
                        control.removeListener(this);
                    }

                    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                    }
                });
                playerModel.playAnimation(PlayerModel.ANIMATION_EATEN_BY_SHARK, false, true, new AnimEventListener() {
                    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                        token.die(Game3DLogic.this);
                        control.removeListener(this);
                    }

                    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                    }
                });
            }
            break;

            case GameEntity.ACTION_SEASERPENT_CRUSH: {
                final SeaSerpent ss = (SeaSerpent) source;
                final SeaSerpentModel ssModel = (SeaSerpentModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(ss);

                // Un kraken peut soit manger un bateau, soit des joueurs (un bateau contenant des
                // joueurs étant traité séparément). Dans tous les cas, on va lancer l'animation
                // pour lui.
                ssModel.playAnimation(SeaSerpentModel.ANIMATION_ATTACK_CELL, false, true, new AnimEventListener() {
                    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                        ssModel.playAnimation(AnimationBrain.getIdleAnimation(ss));
                        control.removeListener(this);
                    }

                    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                    }
                });

                if (target instanceof PlayerToken) {
                    final PlayerToken token = (PlayerToken) target;
                    final PlayerModel playerModel = (PlayerModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(token);
                    ssModel.lookAt(playerModel.getLocalTranslation(), Vector3f.UNIT_Y);
                    ssModel.rotate(0, -90, 0);

                    playerModel.playAnimation(PlayerModel.ANIMATION_EATEN_BY_SHARK, false, true, new AnimEventListener() {
                        public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                            token.die(Game3DLogic.this);
                            control.removeListener(this);
                        }

                        public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                        }
                    });
                } else if (target instanceof Boat) {
                    final Boat boat = (Boat) target;
                    final BoatModel boatModel = (BoatModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(boat);
                    ssModel.lookAt(boatModel.getLocalTranslation(), Vector3f.UNIT_Y);
                    ssModel.rotate(0, -90, 0);

                    boatModel.playAnimation(BoatModel.ANIMATION_BOAT_SINK, false, true, new AnimEventListener() {
                        public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                            boat.die(Game3DLogic.this);
                            control.removeListener(this);
                        }

                        public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                        }
                    });
                } else {
                    throw new IllegalArgumentException("A Sea Serpent cannot crush anything else than a boat or a player!");
                }
            }
            break;

            case GameEntity.ACTION_WHALE_NUKE: {
                final Whale whale = (Whale) source;
                final Boat boat = (Boat) target;

                // Les baleines nukent les bateaux si il y a au moins un joueur dedans, sinon
                // le bateau reste intact.
                if (boat.getOnboardTokens().size() > 0) {
                    // Et il y a des gens: Ils tombent à l'eau, et le bateau disparait
                    List<PlayerToken> onboard = new ArrayList<PlayerToken>(boat.getOnboardTokens());

                    for (PlayerToken pt : onboard) {
                        boat.removePlayer(pt);
                        onPlayerDismountBoat(pt, boat);
                        pt.setState(PlayerToken.STATE_SWIMMING);
                    }

                    boat.die(this);
                }
            }
            break;


        }
    }

    public void onEntitySpawn(final GameEntity spawned) {
        logger.log(Level.FINE, "Game3DLogic: onEntitySpawn ", new Object[]{});

        final AnimatedModel model = mRenderer.getEntitiesRenderer().addEntity(spawned);
        model.playAnimation(AnimationBrain.getSpawnAnimation(spawned), new AnimEventListener() {
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                model.playAnimation(AnimationBrain.getIdleAnimation(spawned));
                control.removeListener(this);
            }

            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
            }
        });
    }

    private MotionEvent generateTileSinkMotion(Node tileNode) {
        // On créé le chemin
        final MotionPath path = new MotionPath();
        path.addWayPoint(tileNode.getLocalTranslation());
        path.addWayPoint(tileNode.getLocalTranslation().add(0, -20, 0));
        path.setPathSplineType(Spline.SplineType.Linear);

        // On créé le contrôleur
        final MotionEvent motionControl = new MotionEvent(tileNode, path);
        motionControl.setInitialDuration(0.5f);

        return motionControl;
    }

    private MotionEvent generateEntityOnTileMotion(Node entNode, AbstractTileModel tileNode) {
        return generateDestinationMotion(entNode, tileNode.getRandomizedTileTopCenter(), 2.0f);
    }

    private MotionEvent generateEntityceptionMotion(Node entNode, StaticModel destNode) {
        return generateDestinationMotion(entNode, destNode.getLocalTranslation(), 2.0f);
    }

    private MotionEvent generateDestinationMotion(Node entNode, Vector3f target, float duration) {
        // On créé le chemin
        final MotionPath path = new MotionPath();
        path.addWayPoint(entNode.getLocalTranslation());
        path.addWayPoint(target);
        path.setPathSplineType(Spline.SplineType.Linear);

        // On créé le contrôleur
        final MotionEvent motionControl = new MotionEvent(entNode, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(0, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(duration);

        return motionControl;
    }

    @Override
    public void onBoardBoat(final PlayerToken player, Boat b) {
        final PlayerModel model = (PlayerModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(player);
        model.playAnimation(AnimationBrain.getIdleAnimation(player));
    }

    public void onUnitDie(GameEntity zombie) {
        zombie.getTile().removeEntity(zombie);
        mRenderer.getEntitiesRenderer().removeEntity(zombie);
    }

    @Override
    public void requestPick(EntityPickRequest entRq, TilePickRequest tileRq) {
        // On a besoin de picker une entité

        logger.log(Level.FINE, "Game3DLogic: requestPick ", new Object[]{});

        mEntRequestHistory.add(entRq);
        mTileRequestHistory.add(tileRq);

        mRenderer.getInputListener().requestPicking(entRq, tileRq);
    }

    @Override
    public void cancelPick() {
        mRenderer.getInputListener().forceResetRequest();
    }

    @Override
    public void onEntityPicked(GameEntity ent) {
        logger.log(Level.FINE, "Game3DLogic: Entity picked ", new Object[]{ent});

        AudioManager.getDefault().playSound(AudioConstants.Path.SELECT);

        GameTurn currentTurn = mRenderer.getLogic().getCurrentTurn();
        mRenderer.getHud().getGameHud().hidePlayerTiles();

        if (currentTurn.getTileAction() != null && !currentTurn.getTileAction().hasBeenUsed()) {
            // La tile d'action n'a pas fini d'être utilisée
            TileAction ta = currentTurn.getTileAction();

            if (ta.getAction() == TileAction.ACTION_MOVE_ANIMAL) {
                // Téléportation d'animal: On a pické l'animal, il faut maintenant picker la tile
                // d'eau qui va bien (tile water, sans personne dessus)
                mPickedEntity = ent;

                TilePickRequest tileRq = new TilePickRequest();
                tileRq.landTilesOnly = false;
                tileRq.requiredHeight = 0;
                tileRq.noEntitiesOnTile = true;
                tileRq.waterEdgeOnly = false;
                ta.setInitialEntity(mPickedEntity);
                requestPick(null, tileRq);
            } else if (ta.getAction() == TileAction.ACTION_BONUS_BOAT
                    || ta.getAction() == TileAction.ACTION_BONUS_SWIM) {
                // Bonus 3 mouvements de bateau ou nageur. On a pické le bateau ou le nageur, on
                // pick donc ensuite une tile voisine
                mPickedEntity = ent;

                TilePickRequest tileRq = new TilePickRequest();
                tileRq.landTilesOnly = false;
                tileRq.requiredHeight = 0;
                tileRq.noEntitiesOnTile = false;
                if (ta.getAction() == TileAction.ACTION_BONUS_BOAT) {
                    tileRq.noBoatOnTile = true;
                }
                tileRq.waterEdgeOnly = false;
                tileRq.pickNearTile = mPickedEntity.getTile();
                ta.setInitialEntity(mPickedEntity);
                requestPick(null, tileRq);
            }
        } else if (currentTurn.getRemainingMoves() > 0) {
            // Si on a déjà une entité et si on a pické un bateau, c'est qu'on veut déplacer ce
            // bonhomme sur le bateau.
            if (mPickedEntity != null && (mPickedEntity instanceof PlayerToken)
                    && (ent instanceof Boat)) {
                PlayerToken pt = (PlayerToken) mPickedEntity;
                Boat b = (Boat) ent;

                // On place le personnage sur le bateau, et on lui dit qu'il est en bateau
                pt.setState(PlayerToken.STATE_ON_BOAT);
                pt.setBoat(b);
                b.addPlayer(pt);

                // On notifie le tour du mouvement
                currentTurn.moveEntity(mPickedEntity, b);

                // Remise à zéro
                mPickedEntity = null;
                mCanCancelPickingAction = false;
                mRenderer.getHud().getGameHud().hideRightClickHint();
            } else {
                // On assume ici que lorsqu'on picke une entité, on veut picker une tile ou un bateau
                // après, puisqu'on a des mouvements restant (et qu'un tour est forcément séquentiel)
                mPickedEntity = ent;

                // Request pour la tile
                TilePickRequest tilePick = new TilePickRequest();
                tilePick.pickNearTile = ent.getTile();
                tilePick.waterEdgeOnly = false;

                // On reste sur l'eau si on est dans l'eau
                if (ent.getTile().getHeight() == 0) {
                    tilePick.requiredHeight = 0;
                }

                // Request pour les bateaux (on peut bouger un perso sur un bateau ayant de la place)
                // si on a pas déjà cliqué sur un bateau (on ne bouge pas un bateau sur un autre
                // bateau), et on ne peut picker un bateau, étant nageur, que si on est sur la tile.
                EntityPickRequest entPick = null;
                if (!(mPickedEntity instanceof Boat)) {
                    PlayerToken pt = (PlayerToken) mPickedEntity;

                    entPick = new EntityPickRequest();
                    entPick.pickingRestriction = EntityPickRequest.FLAG_PICK_BOAT_WITH_ROOM;
                    entPick.player = null;
                    if (pt.getState() == PlayerToken.STATE_SWIMMING) {
                        // On nage: on est obligé d'être sur la tile du bateau pour monter dessus
                        entPick.pickOnTile = ent.getTile();
                        entPick.pickNearTile = null;
                    } else {
                        // On est sur terre: on peut monter sur les bateaux alentours
                        entPick.pickNearTile = ent.getTile();
                        entPick.pickOnTile = null;
                    }
                    entPick.avoidEntity.add(pt.getBoat());
                    entPick.avoidEntity.addAll(getCurrentTurn().getSwimmersMoved());
                } else {
                    // On ne met pas un bateau sur une tile avec un bateau
                    tilePick.noBoatOnTile = true;
                }

                // On lance la requête
                requestPick(entPick, tilePick);
                mCanCancelPickingAction = true;
                mRenderer.getHud().getGameHud().showRightClickHint();
            }
        } else if (currentTurn.hasRolledDice() && currentTurn.getRemainingDiceMoves() > 0) {
            // Le dé a été lancé, et on a des mouvements de dé restant. La seule chose possible, c'est
            // un déplacement d'entité suite au dé qui vient tout juste d'être lancé.
            mPickedEntity = ent;
            TilePickRequest tilePick = new TilePickRequest();
            tilePick.pickNearTile = ent.getTile();
            tilePick.waterEdgeOnly = false;
            // Toutes les entités du dé ne sont que dans l'eau
            tilePick.requiredHeight = 0;

            requestPick(null, tilePick);
            mCanCancelPickingAction = true;
            mRenderer.getHud().getGameHud().showRightClickHint();
        }
    }

    @Override
    public void onTilePicked(GameTile tile) {
        logger.log(Level.FINE, "Game3DLogic: Tile picked ", new Object[]{tile.getName()});

        boolean dontClearEntity = false;

        GameTurn currentTurn = mRenderer.getLogic().getCurrentTurn();
        if (currentTurn.getTokenToPlace() != null) {
            // On a un token a placer, on a donc pas encore commencé la partie.
            currentTurn.putInitialToken(currentTurn.getTokenToPlace(), tile);
        } else if (getRemainingInitialBoats() > 0) {
            // Il nous reste des bateaux initiaux à placer, on a donc pas encore commencé la partie.
            currentTurn.putInitialBoat(tile);
        } else if (currentTurn.getTileAction() != null && !currentTurn.getTileAction().hasBeenUsed()) {
            // On a une tile d'action qui n'a pas finie d'être utilisée
            TileAction ta = currentTurn.getTileAction();
            switch (ta.getAction()) {
                case TileAction.ACTION_MOVE_ANIMAL:
                    // On déplace l'unité là bas
                    currentTurn.tileActionTeleport(mPickedEntity, tile);
                    break;

                case TileAction.ACTION_BONUS_BOAT:
                case TileAction.ACTION_BONUS_SWIM:
                    currentTurn.tileActionBonusBoatOrSwim(mPickedEntity, tile);
                    if (ta.getMovesRemaining() > 0) {
                        dontClearEntity = true;
                    }
                    break;
            }
        } else if (currentTurn.getRemainingMoves() > 0) {
            // On assume que ce picking de tile était pour le déplacement d'unités.
            currentTurn.moveEntity(mPickedEntity, tile);
        } else if (!currentTurn.hasSunkLandTile()) {
            // Il faut couler une tile
            currentTurn.sinkLandTile(tile);
        } else if (currentTurn.hasRolledDice() && currentTurn.getRemainingDiceMoves() > 0) {
            // On bouge une entité suite au lancé de dé
            currentTurn.moveDiceEntity(mPickedEntity, tile);

            if (currentTurn.getRemainingDiceMoves() > 0) {
                // On bouge forcément la même entité
                dontClearEntity = true;
            }
        }

        if (!dontClearEntity) {
            mPickedEntity = null;
        }
        mCanCancelPickingAction = false;
        mRenderer.getHud().getGameHud().hideRightClickHint();
    }

    @Override
    public void onPlayerDismountBoat(PlayerToken player, Boat b) {
        // On détache le player du bateau, puis on le remet dans le board
        PlayerModel pm = (PlayerModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(player);
        Vector3f existingPos = pm.getWorldTranslation();
        pm.getParent().detachChild(pm);

        Node node = mRenderer.getSceneNode();
        node.attachChild(pm);
        pm.setLocalTranslation(existingPos);
    }

    @Override
    public void onTileWhirl(final GameTile tile) {
        // On coule les unités dans les tiles water alentours
        List<GameTile> tilesToSink = new ArrayList<GameTile>();
        tilesToSink.add(tile);
        if (tile.getLeftBottomTile() != null) {
            tilesToSink.add(tile.getLeftBottomTile());
        }
        if (tile.getLeftTile() != null) {
            tilesToSink.add(tile.getLeftTile());
        }
        if (tile.getLeftUpperTile() != null) {
            tilesToSink.add(tile.getLeftUpperTile());
        }
        if (tile.getRightBottomTile() != null) {
            tilesToSink.add(tile.getRightBottomTile());
        }
        if (tile.getRightTile() != null) {
            tilesToSink.add(tile.getRightTile());
        }
        if (tile.getRightUpperTile() != null) {
            tilesToSink.add(tile.getRightUpperTile());
        }

        for (final GameTile sinking : tilesToSink) {
            if (sinking instanceof WaterTile) {
                // On joue l'animation de coulage sur les entités dessus aussi
                List<GameEntity> entities = sinking.getEntities();
                for (final GameEntity ent : entities) {
                    AnimatedModel am = (AnimatedModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(ent);
                    AnimationBrain.State animation = AnimationBrain.getDrownAnimation(ent);
                    am.playAnimation(animation, new AnimEventListener() {
                        public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                            ent.die(Game3DLogic.this);
                            control.removeListener(this);
                        }

                        public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                        }
                    });
                }
            }
        }
    }

    private void doTileSinkAnimation(GameTile tile, MotionPathListener listener) {
        AbstractTileModel tileNode = mRenderer.getBoardRenderer().findTileModel(tile);
        if (tileNode == null) {
            logger.log(Level.SEVERE, "Aucune node 3D trouvée pour la tile de destination!", new Object[]{});
            throw new IllegalStateException("Aucune node 3D trouvée pour la tile de destination!");
        }

        // Effet visuel de splash d'eau
        Node splashEffect = ParticlesFactory.makeWaterSplash(mRenderer.getAssetManager());
        splashEffect.setLocalTranslation(tileNode.getTileTopCenter());
        mRenderer.getRootNode().attachChild(splashEffect);
        ParticlesFactory.emitAllParticles(splashEffect);

        // Génération du mouvement de la tile
        final MotionEvent motionEvent = generateTileSinkMotion((Node) tileNode);

        // Callback lorsque l'animation est terminée
        motionEvent.getPath().addListener(listener);

        motionEvent.play();
    }

    @Override
    public void onTileVolcano() {
        super.onTileVolcano();
        // TODO: Game Over


    }

    /**
     * Stocke le tour présent, génère le suivant et le démarre via start()
     *
     */
    @Override
    public void nextTurn() {

        super.nextTurn();
        Player p = this.getCurrentTurn().getPlayer();



        String joueurCourant = p.getName();

        Player[] plr = this.getPlayers();

        int lg = plr.length;

        String colorP1 = "#0060ab4d";
        String colorP2 = "#349b144d";
        String colorP3 = "#eda0004d";
        String colorP4 = "#c6000b4d";

        HashMap<String, String> playerAndColor = new HashMap();

        LinkedList<Player> lkl = new LinkedList();
        lkl.add(plr[0]);
        lkl.add(plr[1]);
        if (lg >= 3) {
            lkl.add(plr[2]);
        }
        if (lg == 4) {
            lkl.add(plr[3]);
        }


        Element text, panel;
        Nifty nifty = mRenderer.getNifty();
        switch (lg) {



            //PARTIE A 2 JOUEURS***********************************************/

            case 2:
                //Lie les pseudos a leurs couleurs.
                playerAndColor.put(plr[0].getName(), colorP1);
                playerAndColor.put(plr[1].getName(), colorP2);

                //Defini le panel du bas et de droite à la couleur du joueur courant.
                panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelBottom");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelRight");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                //Defini le nom du joueur courant (zone du bas)
                text = nifty.getScreen("inGameHud2J").findElementByName("nomJ1");
                text.getRenderer(TextRenderer.class).setText(p.getName());

                if (lkl.indexOf(p) == 1) {
                    panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelTop2");
                    panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                    text = nifty.getScreen("inGameHud2J").findElementByName("nomJ2");
                    text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());
                } else {
                    panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelTop2");
                    panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                    text = nifty.getScreen("inGameHud2J").findElementByName("nomJ2");
                    text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());
                }
                break;




            //PARTIE A 3 JOUEURS***********************************************/

            case 3:
                playerAndColor.put(plr[0].getName(), colorP1);
                playerAndColor.put(plr[1].getName(), colorP2);
                playerAndColor.put(plr[2].getName(), colorP3);

                //Defini le panel du bas et de droite à la couleur du joueur courant.
                panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelBottom");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelRight");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                //Defini le nom du joueur courant (zone du bas)
                text = nifty.getScreen("inGameHud3J").findElementByName("nomJ1");
                text.getRenderer(TextRenderer.class).setText(p.getName());


                switch (lkl.indexOf(p)) {
                    case 0:
                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());

                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());
                        break;

                    case 1:
                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());

                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        break;

                    case 2:
                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());
                        break;
                }

                break;




            //PARTIE A 4 JOUEURS***********************************************/

            //PARTIE A 4 JOUEURS***********************************************/

            case 4:
                playerAndColor.put(plr[0].getName(), colorP1);
                playerAndColor.put(plr[1].getName(), colorP2);
                playerAndColor.put(plr[2].getName(), colorP3);
                playerAndColor.put(plr[3].getName(), colorP4);

                //Defini le panel du bas et de droite à la couleur du joueur courant.
                panel = nifty.getScreen("inGameHud").findElementByName("HudPanelBottom");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                panel = nifty.getScreen("inGameHud").findElementByName("HudPanelRight");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                //Defini le nom du joueur courant (zone du bas)
                text = nifty.getScreen("inGameHud").findElementByName("nomJ1");
                text.getRenderer(TextRenderer.class).setText(p.getName());

                switch (lkl.indexOf(p)) {
                    case 0:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(3).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(3).getName());

                        break;

                    case 1:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(3).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(3).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        break;

                    case 2:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(3).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(3).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());
                        break;

                    case 3:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());
                        break;
                }

                break;
        }


    }


    /**
     * Affichage des scores
     */
    @Override
    public void onGameFinished() {
        Nifty nifty = mRenderer.getNifty();

        Element element;
        Player[] p = this.getPlayers();

        element = nifty.getScreen("endGame").findElementByName("nomJ1");
        element.getRenderer(TextRenderer.class).setText(p[0].getName());

        element = nifty.getScreen("endGame").findElementByName("nomJ2");
        element.getRenderer(TextRenderer.class).setText(p[1].getName());

        int scoreJ1=p[0].getScore();
        int scoreJ2=p[1].getScore();

        element = nifty.getScreen("endGame").findElementByName("pointsJ1");
        element.getRenderer(TextRenderer.class).setText(scoreJ1+" points");
        element = nifty.getScreen("endGame").findElementByName("pointsJ2");
        element.getRenderer(TextRenderer.class).setText(scoreJ2+" points");





        if(p.length==2){
            if(scoreJ1>scoreJ2){
                element = nifty.getScreen("endGame").findElementByName("winnerJ1");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }

            if(scoreJ1<scoreJ2){
                element = nifty.getScreen("endGame").findElementByName("winnerJ2");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }

            if(scoreJ1==scoreJ2){
                element = nifty.getScreen("endGame").findElementByName("winnerJ1");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
                element = nifty.getScreen("endGame").findElementByName("winnerJ2");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
            }
            nifty.gotoScreen("endGame");
        }

        if(p.length==3){
            element = nifty.getScreen("endGame").findElementByName("nomJ3");
            element.getRenderer(TextRenderer.class).setText(p[2].getName());
            element = nifty.getScreen("endGame").findElementByName("pointsJ3");
            int scoreJ3=p[2].getScore();
            element.getRenderer(TextRenderer.class).setText(scoreJ3+" points");

            if(scoreJ1>scoreJ2 && scoreJ1>scoreJ3){
                element = nifty.getScreen("endGame").findElementByName("winnerJ1");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }

            if(scoreJ1<scoreJ2 && scoreJ3 < scoreJ2){
                element = nifty.getScreen("endGame").findElementByName("winnerJ2");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }

            if(scoreJ1 < scoreJ3 && scoreJ3 > scoreJ2){
                element = nifty.getScreen("endGame").findElementByName("winnerJ3");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }



            if(scoreJ1==scoreJ2 && scoreJ1==scoreJ3){
                element = nifty.getScreen("endGame").findElementByName("winnerJ1");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
                element = nifty.getScreen("endGame").findElementByName("winnerJ2");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
                element = nifty.getScreen("endGame").findElementByName("winnerJ3");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
            }
            nifty.gotoScreen("endGame");
        }


        if(p.length==4){
            element = nifty.getScreen("endGame").findElementByName("nomJ3");
            element.getRenderer(TextRenderer.class).setText(p[2].getName());
            element = nifty.getScreen("endGame").findElementByName("pointsJ3");
            int scoreJ3=p[2].getScore();
            element.getRenderer(TextRenderer.class).setText(scoreJ3+" points");

            element = nifty.getScreen("endGame").findElementByName("nomJ4");
            element.getRenderer(TextRenderer.class).setText(p[3].getName());
            element = nifty.getScreen("endGame").findElementByName("pointsJ4");
            int scoreJ4=p[3].getScore();
            element.getRenderer(TextRenderer.class).setText(scoreJ3+" points");



            if(scoreJ2 < scoreJ1 && scoreJ3 < scoreJ1 && scoreJ4 < scoreJ1 ){
                element = nifty.getScreen("endGame").findElementByName("winnerJ1");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }

            if(scoreJ1 < scoreJ2 && scoreJ3 < scoreJ2 && scoreJ4 < scoreJ2){
                element = nifty.getScreen("endGame").findElementByName("winnerJ2");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }

            if(scoreJ1 < scoreJ3 && scoreJ2 < scoreJ3 && scoreJ4 < scoreJ3){
                element = nifty.getScreen("endGame").findElementByName("winnerJ3");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }

            if(scoreJ1 < scoreJ4 && scoreJ2 < scoreJ4 && scoreJ3<scoreJ4){
                element = nifty.getScreen("endGame").findElementByName("winnerJ4");
                element.getRenderer(TextRenderer.class).setText("GAGNANT !");
            }



            if(scoreJ1==scoreJ2 && scoreJ1==scoreJ3 && scoreJ1==scoreJ4){
                element = nifty.getScreen("endGame").findElementByName("winnerJ1");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
                element = nifty.getScreen("endGame").findElementByName("winnerJ2");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
                element = nifty.getScreen("endGame").findElementByName("winnerJ3");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
                element = nifty.getScreen("endGame").findElementByName("winnerJ4");
                element.getRenderer(TextRenderer.class).setText("EGALITE !");
            }
            nifty.gotoScreen("endGame");
        }



    }

    @Override
    public void finishCurrentAction() {
        // On indique au tour de continuer
        if (getCurrentTurn() != null && getCurrentTurn().canFinishCurrentAction()) {
            // On reset le picking
            mPickedEntity = null;
            mCanCancelPickingAction = false;
            mRenderer.getInputListener().forceResetRequest();

            getCurrentTurn().finishCurrentAction();
        } else {
            AudioManager.getDefault().playSound(AudioConstants.Path.ERROR);
        }
    }
}
