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
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
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
import fr.miage.atlantis.graphics.hud.TileActionDisplay;
import fr.miage.atlantis.graphics.models.AbstractTileModel;
import fr.miage.atlantis.graphics.models.AnimatedModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.graphics.models.SeaSerpentModel;
import fr.miage.atlantis.graphics.models.SharkModel;
import fr.miage.atlantis.graphics.models.StaticModel;
import fr.miage.atlantis.gui.console.GuiConsole;
import fr.miage.atlantis.logic.GameLogic;
import fr.miage.atlantis.logic.GameTurn;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Game Engine loop class
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class Game3DLogic extends GameLogic {

    private Game3DRenderer mRenderer;
    private GameEntity mPickedEntity;

    public Game3DLogic() {
        super();
        mRenderer = new GuiConsole(this);
    }

    @Override
    public void boot() {
        mRenderer.start();
        prepareGame(new String[]{"Romain", "Olivier"});
    }

    @Override
    public void startGame() {
        // TEST: On place des tokens
        Player[] plays = getPlayers();
        for (int i = 0; i < plays.length; i++) {
            Player p = plays[i];
            List<PlayerToken> tokens = p.getTokens();

            for (PlayerToken token : tokens) {
                token.moveToTile(this, getBoard().getTileSet().get("Beach #3"));
                mRenderer.getEntitiesRenderer().addEntity(token);
            }
        }

        // TEST: On place des bateaux
        Boat boat1 = new Boat();
        boat1.moveToTile(this, getBoard().getTileSet().get("Water #37"));
        mRenderer.getEntitiesRenderer().addEntity(boat1);

        super.startGame();
    }

    public void onTurnStart(Player p) {
        // TODO: Animations
        System.out.println("Game3DLogic: onTurnStart");
        getCurrentTurn().onTurnStarted();
    }

    public void onPlayTileAction(GameTile tile, TileAction action) {
        action.use(tile, this);
    }

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

        // Callback lorsque l'animation est terminée
        motionEvent.getPath().addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (control.getPath().getNbWayPoints() == wayPointIndex + 1) {
                    System.out.println("Game3DLogic: Waypoint reached, processing events");
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

                    // On notifie le jeu, toutes les actions nécessaires sont faites.
                    if (getCurrentTurn() != null) {
                        getCurrentTurn().onUnitMoveFinished();
                    }
                }
            }
        });

        // On détermine l'animation à jouer
        ((AnimatedModel) entNode).playAnimation(AnimationBrain.getMovementAnimation(ent, dest));

        // On lance le mouvement
        motionEvent.play();
    }

    public void onDiceRoll(int face) {
        // On a besoin de lancer le dé
        System.out.println("Game3DLogic: onDiceRoll");
        mRenderer.rollDiceAnimation(face);
    }

    public void onSinkTile(final GameTile tile) {
        AbstractTileModel tileNode = mRenderer.getBoardRenderer().findTileModel(tile);
        if (tileNode == null) {
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
        motionEvent.getPath().addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (motionEvent.getPath().getNbWayPoints() == wayPointIndex + 1) {
                    // On est à la fin de l'animation. On remplace la tile
                    // coulée par une WaterTile
                    final WaterTile newTile = getBoard().sinkTile(Game3DLogic.this, tile);
                    mRenderer.getBoardRenderer().replaceTile(tile, newTile);

                    // On fait en sorte que les nouveaux nageurs nagent
                    List<GameEntity> newTileEntities = new ArrayList<GameEntity>(newTile.getEntities());
                    for (GameEntity ent : newTileEntities) {
                        if (ent instanceof PlayerToken) {
                            onUnitMove(ent, newTile);
                        }
                    }

                    // D'abord, on affiche la tile "piochée" à l'écran
                    final TileAction action = tile.getAction();
                    final TileActionDisplay tad = TileActionDisplay.getTileForAction(action,
                            mRenderer.getAssetManager());
                    mRenderer.displayHudCenter(tad);
                    mRenderer.getHudAnimator().animateFadeIn(tad);

                    // Ensuite, après l'affichage, on traite l'action
                    mRenderer.getFuture().addFutureTimeCallback(new FutureCallback(2.0f) {
                        @Override
                        public void onFutureHappened() {
                            mRenderer.getHudAnimator().animateFadeOut(tad);

                            // On lance l'action sous la tile, si c'est immédiat
                            if (action.isImmediate()) {
                                onPlayTileAction(newTile, action);
                            } else {
                                System.out.println("TODO: Tile is not immediate: " + action.toString());
                                // TODO: Stocker la tile dans les tiles du joueur
                            }

                            // Fin de l'action, étape suivante
                            getCurrentTurn().onSinkTileFinished();
                        }
                    });

                }
            }
        });

        motionEvent.play();
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
                final PlayerToken token = (PlayerToken) target;

                final SeaSerpentModel ssModel = (SeaSerpentModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(ss);
                final PlayerModel playerModel = (PlayerModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(token);
                ssModel.lookAt(playerModel.getLocalTranslation(), Vector3f.UNIT_Y);
                ssModel.rotate(0, -90, 0);

                ssModel.playAnimation(SeaSerpentModel.ANIMATION_ATTACK_CELL, false, true, new AnimEventListener() {
                    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                        ssModel.playAnimation(AnimationBrain.getIdleAnimation(ss));
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

            case GameEntity.ACTION_WHALE_NUKE: {
                final Whale whale = (Whale) source;
                final Boat boat = (Boat) target;

                // Les baleines nukent les bateaux si il y a au moins un joueur dedans, sinon
                // le bateau reste intact.
                if (boat.getOnboardTokens().size() > 0) {
                    // Et il y a des gens: Ils tombent à l'eau, et le bateau disparait
                    List<PlayerToken> onboard = boat.getOnboardTokens();

                    for (PlayerToken pt : onboard) {
                        pt.setState(pt.getState() & ~PlayerToken.STATE_ON_BOAT);
                    }

                    boat.die(this);
                }
            }
            break;


        }
    }

    public void onEntitySpawn(final GameEntity spawned) {
        System.out.println("Game3DLogic: onEntitySpawn " + spawned);
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
        System.out.println("Game3DLogic: requestPick");
        mRenderer.getInputListener().requestPicking(entRq, tileRq);
    }

    @Override
    public void onEntityPicked(GameEntity ent) {
        System.out.println("Entity picked: " + ent);

        GameTurn currentTurn = mRenderer.getLogic().getCurrentTurn();

        if (currentTurn.getRemainingMoves() > 0) {
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
                // bateau)
                EntityPickRequest entPick = null;
                if (!(mPickedEntity instanceof Boat)) {
                    entPick = new EntityPickRequest();
                    entPick.pickingRestriction = EntityPickRequest.FLAG_PICK_BOAT_WITH_ROOM;
                    entPick.player = null;
                }

                // On lance la requête
                requestPick(entPick, tilePick);
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
        }
    }

    @Override
    public void onTilePicked(GameTile tile) {
        System.out.println("Tile " + tile.getName() + " picked!");

        GameTurn currentTurn = mRenderer.getLogic().getCurrentTurn();
        if (currentTurn.getRemainingMoves() > 0) {
            // On assume que ce picking de tile était pour le déplacement d'unités.
            currentTurn.moveEntity(mPickedEntity, tile);
        } else if (!currentTurn.hasSunkLandTile()) {
            // Il faut couler une tile
            currentTurn.sinkLandTile(tile);
        } else if (currentTurn.hasRolledDice() && currentTurn.getRemainingDiceMoves() > 0) {
            // On bouge une entité suite au lancé de dé
            currentTurn.moveDiceEntity(mPickedEntity, tile);
        }

        mPickedEntity = null;
    }
}
