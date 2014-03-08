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
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.GameDice;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.graphics.models.DiceModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    private DiceModel mDiceModel;
    private List<FutureCallback> mFutureCallbacks;
    private List<FutureCallback> mFutureCallbacksDeletion;

    public Game3DRenderer(Game3DLogic parent) {
        mParent = parent;
        mFutureCallbacks = new ArrayList<FutureCallback>();
        mFutureCallbacksDeletion = new ArrayList<FutureCallback>();
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

        // Configuration du dé
        mDiceModel = new DiceModel(assetManager);
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

    public void rollDiceAnimation(final int finalFace) {
        mSceneNode.attachChild(mDiceModel);
        mDiceModel.setLocalTranslation(cam.getLocation().add(cam.getDirection().mult(100.0f)));
        mDiceModel.lookAt(cam.getLocation(), Vector3f.ZERO);

        // On créé le chemin
        final Random rand = new Random();
        final MotionPath path = new MotionPath();
        path.addWayPoint(mDiceModel.getLocalTranslation());
        for (int i = 0; i < 10; i++) {
            path.addWayPoint(mDiceModel.getLocalTranslation()
                    .add(-5.0f + rand.nextFloat() * 10.0f,
                    -5.0f + rand.nextFloat() * 10.0f,
                    -5.0f + rand.nextFloat() * 10.0f));
        }

        path.setPathSplineType(Spline.SplineType.CatmullRom);

        path.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
                if (wayPointIndex == 10) {
                    mDiceModel.setLocalRotation(Quaternion.IDENTITY);

                    if (finalFace == GameDice.FACE_SHARK) {
                        mDiceModel.rotate(0, 90, 0);
                    } else if (finalFace == GameDice.FACE_WHALE) {
                        mDiceModel.rotate(185, 0, 200);
                    } else if (finalFace == GameDice.FACE_SEASERPENT) {
                        mDiceModel.rotate(90, 5, 0);
                    }

                    // On laisse un peu de temps entre l'affichage et l'événement effectif
                    FutureCallback fc = new FutureCallback(speed) {
                        @Override
                        public void onFutureHappened() {
                            mSceneNode.detachChild(mDiceModel);
                            mParent.getCurrentTurn().onDiceRollFinished();
                        }
                    };
                    addFutureTimeCallback(fc, 2.0f);
                }
            }
        });

        // On créé le contrôleur
        final MotionEvent motionControl = new MotionEvent(mDiceModel, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(0, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(1f);

        motionControl.play();
    }

    /**
     * Appelle un FutureCallback après timeFromNow secondes de rendu écoulées
     * @param cb Le callback à appeler
     * @param timeFromNow Le nombre de secondes à attendre
     */
    public void addFutureTimeCallback(FutureCallback cb, float timeFromNow) {
        // Le rendu survient dans un GLThread à part. Du coup, on verouille la liste quand on
        // la modifie.
        synchronized (this) {
            mFutureCallbacks.add(cb);
        }
    }

    int FRAME_COUNT = 0;

    @Override
    public void simpleUpdate(float tpf) {
        FRAME_COUNT++;

        // TEST == Evenements de test
        if (FRAME_COUNT == 10) {
            mParent.startGame();
        }

        // Traitement de la file de FutureCallbacks. On traite d'abord les callbacks, puis on
        // supprime les callbacks qui sont terminés/survenus.
        synchronized (this) {
            for (FutureCallback cb : mFutureCallbacks) {
                if (cb.decreaseTime(tpf)) {
                    mFutureCallbacksDeletion.add(cb);
                }
            }

            for (FutureCallback cb : mFutureCallbacksDeletion) {
                mFutureCallbacks.remove(cb);
            }
            mFutureCallbacksDeletion.clear();
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
}
