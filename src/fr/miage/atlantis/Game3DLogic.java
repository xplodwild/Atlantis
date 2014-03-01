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
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.logic.GameLogic;
import java.util.HashMap;
import java.util.Map;

/**
 * Main Game Engine loop class
 */
public class Game3DLogic extends GameLogic {
    
    private Game3DRenderer mRenderer;
    private Map<GameEntity, Node> mEntityNodes;
    private Map<GameTile, Node> mTileNodes;
    
    public Game3DLogic() {
        super();
        mRenderer = new Game3DRenderer();
        mEntityNodes = new HashMap<GameEntity, Node>();
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

    public void onUnitMove(GameEntity ent, GameTile dest) {
        // On récupère la node 3D de cette entité
        Node entNode = mEntityNodes.get(ent);
        if (entNode == null) {
            throw new IllegalStateException("Aucune node 3D trouvée pour l'unité à déplacer!");
        }
        
        Node tileNode = mTileNodes.get(dest);
        if (tileNode == null) {
            throw new IllegalStateException("Aucune node 3D trouvée pour la tile de destination!");
        }
        
        final MotionPath path = new MotionPath();
        path.addWayPoint(entNode.getLocalTranslation());
        path.addWayPoint(tileNode.getLocalTranslation());
        path.setCurveTension(0.5f);
        path.setCycle(false);
        path.setPathSplineType(Spline.SplineType.Bezier);
        
        MotionEvent motionControl = new MotionEvent(entNode, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(1f);
        motionControl.setSpeed(2f);
        
        path.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    System.out.println(control.getSpatial().getName() + "Finished!!! ");
                } else {
                    System.out.println(control.getSpatial().getName() + " Reached way point " + wayPointIndex);
                }
            }
        });
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
    
}