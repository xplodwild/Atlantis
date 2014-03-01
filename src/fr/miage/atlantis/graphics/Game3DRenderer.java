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

import fr.miage.atlantis.graphics.models.TileModel;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.graphics.models.EmptyTileModel;

/**
 *
 */
public class Game3DRenderer extends SimpleApplication {
    
    private Node mSceneNode;
    private Environment mEnvironment;
    private Game3DLogic mParent;
    private int mTileOffset = 0;
    
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
        GameTile currentTile = mParent.getBoard().getFirstTile();
        GameTile rowHeadTile = currentTile;
        
        int x = 0, y = 0;
        while (true) {
            int initialX = x;
            addTileToRender(currentTile, x, y);
            System.out.println("Adding self tile " + x + "," + y + " (" + currentTile.getName() + ")");
            
            while (currentTile.getRightTile() != null) {
                x++;
                currentTile = currentTile.getRightTile();
                addTileToRender(currentTile, x, y);
                System.out.println("Adding right row tile " + x + "," + y + " (" + currentTile.getName() + ")");
            }
            
            currentTile = rowHeadTile;
            x = initialX;
            
            while (currentTile.getLeftTile() != null) {
                x--;
                currentTile = currentTile.getLeftTile();
                addTileToRender(currentTile, x, y);
                System.out.println("Adding left row tile " + x + "," + y + " (" + currentTile.getName() + ")");
            }
            
            boolean belowFound = false;
            while (!belowFound && currentTile != null) {
                if (currentTile.getLeftBottomTile() != null) {
                    System.out.println("Going below left of " + currentTile.getName());
                    rowHeadTile = currentTile.getLeftBottomTile();
                    currentTile = rowHeadTile;
                    mTileOffset -= 10;
                    
                    y++;
                    belowFound = true;
                } else if (currentTile.getRightBottomTile() != null) {
                    System.out.println("Going below right of " + currentTile.getName());
                    rowHeadTile = currentTile.getRightBottomTile();
                    currentTile = rowHeadTile;
                    mTileOffset += 10;
                    
                    y++;
                    belowFound = true;
                } else {
                    currentTile = currentTile.getRightTile();
                }
            }
            
            if (!belowFound) {
                break;
            }
        }
        
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
        
        /*SeaSerpentModel shark = new SeaSerpentModel(assetManager);
        shark.printAnimations();
        rootNode.attachChild(shark);
        shark.playAnimation(SeaSerpentModel.ANIMATION_IDLE);*/
    }
    
    public Node addTileToRender(GameTile tile, int x, int y) {
        Node output;
        if (tile.getHeight() > 0) {
            output = new TileModel(tile.getHeight(), assetManager);
        } else {
            ColorRGBA color = ColorRGBA.White;
            if (tile.getHeight() < 0) {
                color = ColorRGBA.Red;
            } else if (tile.getHeight() == 0) {
                WaterTile wt = (WaterTile) tile;
                color = wt.isLandingTile() ? ColorRGBA.Blue : ColorRGBA.Cyan;
                color = wt.isBeginningWithSeaShark() ? ColorRGBA.Magenta : color;
            }
            output = new EmptyTileModel(assetManager, color);
        }
        
        output.setLocalTranslation(y * -20, 1, x * 20 + mTileOffset);
        mSceneNode.attachChild(output);
        
        return output;
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}
