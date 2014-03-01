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

import fr.miage.atlantis.graphics.models.SharkModel;
import fr.miage.atlantis.graphics.models.TileModel;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import fr.miage.atlantis.entities.Shark;

/**
 *
 */
public class Game3DRenderer extends SimpleApplication {
    
    private Node mSceneNode;
    private Environment mEnvironment;

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(100.0f);
        cam.setFrustumFar(1000.0f);
        
        mSceneNode = new Node("Scene");
        mEnvironment = new Environment(rootNode, assetManager, viewPort);
        
        TileModel testTile = new TileModel(1, assetManager);
        //rootNode.attachChild(testTile);
        TileModel testTile2 = new TileModel(2, assetManager);
        testTile2.setLocalTranslation(10, 0, 10);
        //rootNode.attachChild(testTile2);
        TileModel testTile3 = new TileModel(3, assetManager);
        testTile3.setLocalTranslation(20, 0, 20);
        //rootNode.attachChild(testTile3);
        
        SharkModel shark = new SharkModel(assetManager);
        shark.printAnimations();
        rootNode.attachChild(shark);
        shark.playAnimation(SharkModel.ANIMATION_ATTACK_SWIMMER);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}
