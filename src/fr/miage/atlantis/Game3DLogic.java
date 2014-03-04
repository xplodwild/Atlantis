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
import com.jme3.effect.ParticleEmitter;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Spline;
import com.jme3.scene.Node;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.graphics.AnimationBrain;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.graphics.ParticlesFactory;
import fr.miage.atlantis.graphics.models.AbstractTileModel;
import fr.miage.atlantis.graphics.models.AnimatedModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.graphics.models.SharkModel;
import fr.miage.atlantis.logic.GameLogic;

/**
 * Main Game Engine loop class
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class Game3DLogic extends GameLogic {

    private Game3DRenderer mRenderer;

    public Game3DLogic() {
        super();
        mRenderer = new Game3DRenderer(this);
    }

    @Override
    public void boot() {
        mRenderer.start();
    }

    public void onTurnStart(Player p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onPlayTileAction(GameTile tile, TileAction action) {
        action.use(tile, this);
    }

    @Override
    public void onUnitMove(final GameEntity ent, final GameTile dest) {
        super.onUnitMove(ent, dest);

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
        final MotionEvent motionEvent = generateEntityOnTileMotion(entNode, tileNode);

        // Callback lorsque l'animation est terminée
        motionEvent.getPath().addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (motionEvent.getPath().getNbWayPoints() == wayPointIndex + 1) {
                    // On bouge effectivement le joueur de tile. Note: On fait cette action à la
                    // fin de l'animation pour pouvoir proprement enchainer les actions (exemple:
                    // on marche sur un requin ou un bateau). Ainsi, le GameTurn attend avant de
                    // faire la suite des opérations via onUnitMoveFinished.
                    ent.moveToTile(Game3DLogic.this, dest);

                    // On est à la fin du chemin, mise à jour de l'animation de l'entité bougée
                    String animation = AnimationBrain.getIdleAnimation(ent);

                    if (animation != null) {
                        ((AnimatedModel) entNode).playAnimation(animation);
                    }

                    // On notifie le jeu, toutes les actions nécessaires sont faites.
                    if (getCurrentTurn() != null) {
                        getCurrentTurn().onUnitMoveFinished();
                    }
                }
            }
        });

        // On détermine l'animation à jouer
        String animation = AnimationBrain.getMovementAnimation(ent, dest);
        if (animation != null) {
            ((AnimatedModel) entNode).playAnimation(animation);
        }

        // On lance le mouvement
        motionEvent.play();
    }

    public void onDiceRoll(int face) {
        throw new UnsupportedOperationException("Not supported yet.");
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
                    WaterTile newTile = getBoard().sinkTile(Game3DLogic.this, tile);
                    mRenderer.getBoardRenderer().replaceTile(tile, newTile);

                    // On fait en sorte que les nouveaux nageurs nagent
                    for (GameEntity ent : newTile.getEntities()) {
                        if (ent instanceof PlayerToken) {
                            onUnitMove(ent, newTile);
                        }
                    }

                    // Fin du tour, on notifie le tour si on l'a
                    if (getCurrentTurn() != null) {
                        getCurrentTurn().onSinkTileFinished();
                    }

                    // On lance l'action sous la tile, si c'est immédiat
                    TileAction action = TileAction.Factory.createSpawnEntity(TileAction.ENTITY_SHARK); //tile.getAction();
                    if (action.isImmediate()) {
                        onPlayTileAction(newTile, action);
                    } else {
                        System.out.println("TODO: Tile is not immediate: " + action.toString());
                        // TODO: Stocker la tile dans les tiles du joueur
                    }
                }
            }
        });

        motionEvent.play();
    }

    public void onEntityAction(GameEntity source, GameEntity target, int action) {
        switch (action) {
            case GameEntity.ACTION_SHARK_EAT:
                // On lance les animations.
                // Ces deux prochaines lignes sont PARFAITEMENT propres et certifiées sans bug.
                final Shark shark = (Shark) ((source instanceof Shark) ? source : target);
                final PlayerToken token = (PlayerToken) ((target instanceof PlayerToken) ? target : source);

                final SharkModel sharkModel = (SharkModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(shark);
                PlayerModel playerModel = (PlayerModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(token);

                sharkModel.playAnimation(SharkModel.ANIMATION_ATTACK_SWIMMER, false, new AnimEventListener() {
                    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                        sharkModel.playAnimation(SharkModel.ANIMATION_SWIM_CYCLE);
                        control.removeListener(this);
                    }

                    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                    }
                });
                playerModel.playAnimation(PlayerModel.ANIMATION_EATEN_BY_SHARK, false, new AnimEventListener() {
                    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                        token.die(Game3DLogic.this);
                        control.removeListener(this);
                    }

                    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                    }
                });

                break;
        }
    }

    public void onEntitySpawn(GameEntity spawned) {
        mRenderer.getEntitiesRenderer().addEntity(spawned);
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
         // On créé le chemin
        final MotionPath path = new MotionPath();
        path.addWayPoint(entNode.getLocalTranslation());
        path.addWayPoint(tileNode.getTileTopCenter());
        path.setPathSplineType(Spline.SplineType.Linear);

        // On créé le contrôleur
        final MotionEvent motionControl = new MotionEvent(entNode, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setInitialDuration(2f);

        return motionControl;
    }

    @Override
    public void onBoardBoat(final PlayerToken player, Boat b) {
        final PlayerModel model = (PlayerModel) mRenderer.getEntitiesRenderer().getNodeFromEntity(player);
        String animation = AnimationBrain.getIdleAnimation(player);
        if (animation != null) {
            model.playAnimation(animation);
        }
    }

    public void onUnitDie(GameEntity zombie) {
        zombie.getTile().removeEntity(zombie);
        mRenderer.getEntitiesRenderer().removeEntity(zombie);
    }
}

