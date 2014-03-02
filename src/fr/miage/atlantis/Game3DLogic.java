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

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.graphics.AnimationBrain;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.graphics.models.AbstractTileModel;
import fr.miage.atlantis.graphics.models.AnimatedModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.logic.GameLogic;

/**
 * Main Game Engine loop class
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

    public void onPlayTileAction(TileAction t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onUnitMove(final GameEntity ent, final GameTile dest) {
        // On récupère la node 3D de cette entité
        final Node entNode = mRenderer.getEntitiesRenderer().getNodeFromEntity(ent);
        if (entNode == null) {
            throw new IllegalStateException("Aucune node 3D trouvée pour l'unité à déplacer!");
        }
        
        AbstractTileModel tileNode = mRenderer.getBoardRenderer().findTileModel(dest);
        if (tileNode == null) {
            throw new IllegalStateException("Aucune node 3D trouvée pour la tile de destination!");
        }
        
        final MotionEvent motionEvent = generateTileMotionPath(entNode, tileNode);
        
        // Callback lorsque l'animation est terminée
        motionEvent.getPath().addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (motionEvent.getPath().getNbWayPoints() == wayPointIndex + 1) {
                    // On est à la fin du chemin
                    String animation = AnimationBrain.getIdleAnimation(ent);
                    
                    if (animation != null) {
                        ((AnimatedModel) entNode).playAnimation(animation);
                    }
                    
                    // On notifie le jeu
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

    public void onSinkTile(GameTile tile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEntityAction(GameEntity source, GameEntity target, int action) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private MotionEvent generateTileMotionPath(Node entNode, AbstractTileModel tileNode) {
         // On créé le chemin
        final MotionPath path = new MotionPath();
        path.addWayPoint(entNode.getLocalTranslation());
        path.addWayPoint(tileNode.getTileTopCenter());
        path.setPathSplineType(Spline.SplineType.Linear);
        
        // On créé le contrôleur
        final MotionEvent motionControl = new MotionEvent(entNode, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(0, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        
        return motionControl;
    }
}