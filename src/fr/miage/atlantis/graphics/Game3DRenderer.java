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

package fr.miage.atlantis.graphics;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.shadow.ShadowUtil;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.graphics.models.AbstractTileModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.graphics.models.SeaSerpentModel;
import java.util.Map;

/**
 *
 */
public class Game3DRenderer extends SimpleApplication {
    
    private Node mSceneNode;
    private Environment mEnvironment;
    private Game3DLogic mParent;
    private BoardRenderer mBoardRenderer;
    private EntitiesRenderer mEntitiesRenderer;
    
    public Game3DRenderer(Game3DLogic parent) {
        mParent = parent;
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(200.0f);
        cam.setFrustumFar(4000.0f);
        
        // Configuration des ombres
        rootNode.setShadowMode(ShadowMode.Off);
        
        mSceneNode = new Node("Scene");
        rootNode.attachChild(mSceneNode);
        mEnvironment = new Environment(rootNode, assetManager, viewPort, cam);
        
        // Rendu du plateau
        mBoardRenderer = new BoardRenderer(assetManager);
        mBoardRenderer.renderBoard(mParent.getBoard());
        mSceneNode.attachChild(mBoardRenderer);
        
        mEntitiesRenderer = new EntitiesRenderer(assetManager, mBoardRenderer);

        // Rendu des entités déjà placées sur le plateau
        Map<String, GameTile> tiles = mParent.getBoard().getTileSet();
        for (GameTile tile : tiles.values()) {
            for (GameEntity ent : tile.getEntities()) {
                mEntitiesRenderer.addEntity(ent);
            }
        }
        
        mSceneNode.attachChild(mEntitiesRenderer);
        
        
        
    }
    
    public BoardRenderer getBoardRenderer() {
        return mBoardRenderer;
    }
    
    public EntitiesRenderer getEntitiesRenderer() {
        return mEntitiesRenderer;
    }
    
    boolean DID_TEST = false;

    @Override
    public void simpleUpdate(float tpf) {
        if (!DID_TEST) {
            DID_TEST = true;
            
            // TEST: Ajout d'un player token de test
            Map<String, GameTile> tiles = mParent.getBoard().getTileSet();
            Player p = new Player("Joueur 1", 1);
            PlayerToken pt = new PlayerToken(tiles.get("Beach #3"), p, 6);
            mEntitiesRenderer.addEntity(pt);
        
            mParent.onUnitMove(pt, tiles.get("Beach #4"));
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}
