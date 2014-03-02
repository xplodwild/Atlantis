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
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.GameEntity;
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
        flyCam.setMoveSpeed(100.0f);
        cam.setFrustumFar(1000.0f);
        
        mSceneNode = new Node("Scene");
        rootNode.attachChild(mSceneNode);
        mEnvironment = new Environment(rootNode, assetManager, viewPort);
        
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
        
        
        /*
        TileModel testTile = new TileModel(1, assetManager);
        //rootNode.attachChild(testTile);
        TileModel testTile2 = new TileModel(2, assetManager);
        testTile2.setLocalTranslation(10, 0, 10);
        //rootNode.attachChild(testTile2);
        TileModel testTile3 = new TileModel(3, assetManager);
        testTile3.setLocalTranslation(20, 0, 20);
        //rootNode.attachChild(testTile3);
        */
        
        /*WhaleModel whale = new WhaleModel(assetManager);
        whale.printAnimations();
        rootNode.attachChild(whale);
        whale.playAnimation(WhaleModel.ANIMATION_ATTACK_BOAT);*/
        
        /*SharkModel shark = new SharkModel(assetManager);
        shark.printAnimations();
        rootNode.attachChild(shark);
        shark.playAnimation(SharkModel.ANIMATION_DIVE);*/
        
        SeaSerpentModel shark = new SeaSerpentModel(assetManager);
        shark.printAnimations();
        rootNode.attachChild(shark);
        shark.playAnimation(SeaSerpentModel.ANIMATION_IDLE);
        
        AbstractTileModel tile = (AbstractTileModel) mBoardRenderer.getTile(126);
        shark.setLocalTranslation(tile.getTileTopCenter());
        
        PlayerModel player = new PlayerModel(assetManager, PlayerModel.COLOR_BLUE);
        player.printAnimations();
        rootNode.attachChild(player);
        player.playAnimation(PlayerModel.ANIMATION_LAND_IDLE_1);
        
        tile = (AbstractTileModel) mBoardRenderer.getTile(127);
        player.setLocalTranslation(tile.getTileTopCenter());
    }
    
    

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}
