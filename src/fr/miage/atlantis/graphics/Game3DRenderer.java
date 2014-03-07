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
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
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
    private InputActionListener mInputListener;

    public Game3DRenderer(Game3DLogic parent) {
        mParent = parent;
    }

    @Override
    public void simpleInitApp() {
        // Pré-configuration
        assetManager.registerLoader(BlenderModelLoader.class, "blend");

        // Configuration camera
        flyCam.setMoveSpeed(200.0f);
        cam.setFrustumFar(4000.0f);
        cam.setLocation(new Vector3f(-398.292f, 572.2102f, 176.78018f));
        cam.setRotation(new Quaternion(0.43458012f, 0.5573096f, -0.4326719f, 0.5597688f));

        inputManager.setCursorVisible(true);
        flyCam.setDragToRotate(true);

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

        // Configuration de l'input (picking souris, clavier)
        mInputListener = new InputActionListener(inputManager, this);
    }

    public BoardRenderer getBoardRenderer() {
        return mBoardRenderer;
    }

    public EntitiesRenderer getEntitiesRenderer() {
        return mEntitiesRenderer;
    }

    public InputActionListener getInputListener() {
        return mInputListener;
    }

    public Game3DLogic getLogic() {
        return mParent;
    }

    int FRAME_COUNT = 0;

    @Override
    public void simpleUpdate(float tpf) {
        FRAME_COUNT++;

        if (FRAME_COUNT == 10) {
            mParent.startGame();
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
}
