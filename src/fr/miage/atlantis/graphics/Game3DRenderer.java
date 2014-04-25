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
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.GameDice;
import fr.miage.atlantis.audio.AudioManager;
import fr.miage.atlantis.graphics.hud.AbstractDisplay;
import fr.miage.atlantis.graphics.hud.HudAnimator;
import fr.miage.atlantis.graphics.hud.HudManager;
import fr.miage.atlantis.graphics.hud.TileActionDisplay;
import fr.miage.atlantis.graphics.models.DiceModel;
import fr.miage.atlantis.gui.Gui;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Renderer 3D pour le jeu
 */
public class Game3DRenderer extends SimpleApplication {

    private boolean mDisplayGraphicalStats = false;
    private Node mSceneNode;
    private CameraNode mCameraNode;
    private Environment mEnvironment;
    private Game3DLogic mParent;
    private BoardRenderer mBoardRenderer;
    private EntitiesRenderer mEntitiesRenderer;
    private InputActionListener mInputListener;
    private DiceModel mDiceModel;
    private FutureUpdater mFutureUpdater;
    private Gui mGui;
    private HudAnimator mHudAnimator;
    private HudManager mHudManager;
    private Nifty mNifty;

    /**
     * Constructeur de Game3DRenderer
     *
     * @param parent Logique 3D qui va avec
     */
    public Game3DRenderer(Game3DLogic parent) {
        super();
        mParent = parent;
        mHudAnimator = new HudAnimator();
        mFutureUpdater = new FutureUpdater();
        setSettings(new AppSettings(true));
        settings.setSettingsDialogImage("Interface/splash.jpg");
        settings.setResolution(1280, 720);
        settings.setTitle("Survive: Escape from Atlantis");
    }

    /**
     * Initialisation des graphiques
     */
    @Override
    public void simpleInitApp() {


        // Pré-configuration
        assetManager.registerLoader(BlenderModelLoader.class, "blend");
        AudioManager.getDefault().initialize(assetManager, rootNode);
        AudioManager.getDefault().setMainMusic(true);

        inputManager.clearMappings();

        setDisplayFps(false);
        setDisplayStatView(false);
        setPauseOnLostFocus(false);

        // Configuration camera
        mCameraNode = new CameraNode("Main Camera", cam);
        mCameraNode.setControlDir(ControlDirection.SpatialToCamera);
        flyCam.setDragToRotate(true);
        flyCam.setEnabled(false);
        flyCam.setMoveSpeed(200.0f);
        cam.setFrustumFar(4000.0f);
        CamConstants.moveMenu(mCameraNode, cam);
        rootNode.attachChild(mCameraNode);

        inputManager.setCursorVisible(true);


        // Configuration des ombres
        rootNode.setShadowMode(ShadowMode.Off);

        mSceneNode = new Node("Scene");
        rootNode.attachChild(mSceneNode);
        mEnvironment = new Environment(rootNode, assetManager, viewPort, cam);

        // Rendu du plateau
        mBoardRenderer = new BoardRenderer(assetManager);
        mSceneNode.attachChild(mBoardRenderer);

        mEntitiesRenderer = new EntitiesRenderer(assetManager, mBoardRenderer);
        mSceneNode.attachChild(mEntitiesRenderer);

        // Configuration de l'input (picking souris, clavier)
        mInputListener = new InputActionListener(inputManager, this);

        // Configuration du dé
        mDiceModel = new DiceModel(assetManager);


        mHudManager = new HudManager(this);

        NiftyJmeDisplay jmdsp = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewPort);
        mNifty = jmdsp.getNifty();
        this.guiViewPort.addProcessor(jmdsp);

        mGui = new Gui(this, mNifty);
        mNifty.gotoScreen("start");
    }


    /**
     *
     * @param r
     */
    public void runOnMainThread(Callable r) {
        enqueue(r);
    }


    /**
     * Affiche ou non le framerate et d'autres infos dans un coin de l'écran
     */

    public void toggleGraphicsStats() {
        if (!mDisplayGraphicalStats) {
            mDisplayGraphicalStats = true;
            setDisplayFps(true);
            setDisplayStatView(true);
        } else {
            mDisplayGraphicalStats = false;
            setDisplayFps(false);
            setDisplayStatView(false);
        }
    }

    /**
     * Renvoie le Node auquel est rattachée la caméra
     *
     * @return le Node auquel est rattachée la caméra
     */
    public CameraNode getCameraNode() {
        return mCameraNode;
    }

    /**
     * Renvoie le BoardRenderer utilisé pour le rendu de la board
     *
     * @return le BoardRenderer utilisé pour le rendu de la board
     */
    public BoardRenderer getBoardRenderer() {
        return mBoardRenderer;
    }

    /**
     * Renvoie le renderer des entités
     *
     * @return le renderer des entités
     */
    public EntitiesRenderer getEntitiesRenderer() {
        return mEntitiesRenderer;
    }

    /**
     * Renvoie le Listener de rendu
     *
     * @return le Listener de rendu
     */
    public InputActionListener getInputListener() {
        return mInputListener;
    }

    /**
     * Renvoie la logique 3D utilisée dans ce rendu
     *
     * @return la logique 3D utilisée dans ce rendu
     */
    public Game3DLogic getLogic() {
        return mParent;
    }

    /**
     * Renvoie l'animateur des infos de l'interface
     *
     * @return l'animateur des infos de l'interface
     */
    public HudAnimator getHudAnimator() {
        return mHudAnimator;
    }

    /**
     * Affiche le Hud
     *
     * @param disp AbstractDisplay
     */
    public void displayHudCenter(AbstractDisplay disp) {
        disp.setPosition(cam.getWidth() / 2 - TileActionDisplay.IMAGE_WIDTH / 2,
                cam.getHeight() / 2 - TileActionDisplay.IMAGE_HEIGHT / 2);
        guiNode.attachChild(disp);
    }

    /**
     * Renvoie le HudManager
     *
     * @return le HudManager
     */
    public HudManager getHud() {
        return mHudManager;
    }

    /**
     * Renvoie le FuturAdapter
     *
     * @return le FuturAdapter
     */
    public FutureUpdater getFuture() {
        return mFutureUpdater;
    }

    /**
     * Renvoie le noeaud d'attache prinpcipal du Rendrerer
     *
     * @return Renvoie le noeaud d'attache prinpcipal du Rendrerer
     */
    public Node getSceneNode() {
        return mSceneNode;
    }

    /**
     * Animation pour le lancer de dé
     *
     * @param finalFace Face sur laquelle le dé doit terminer son mouvement
     */
    public void rollDiceAnimation(final int finalFace) {
        mSceneNode.attachChild(mDiceModel);
        mDiceModel.setLocalTranslation(cam.getLocation().add(cam.getDirection().mult(150.0f)));
        mDiceModel.lookAt(cam.getLocation(), Vector3f.ZERO);

        // On créé le chemin
        final Random rand = new Random();
        final MotionPath path = new MotionPath();
        path.addWayPoint(mDiceModel.getLocalTranslation());

        for (int i = 0; i < 2 * 3; i++) {
            path.addWayPoint(mDiceModel.getLocalTranslation()
                    .add(-5.0f + rand.nextFloat() * 10.0f,
                    -5.0f + rand.nextFloat() * 10.0f,
                    -5.0f + rand.nextFloat() * 10.0f));
        }

        path.setPathSplineType(Spline.SplineType.Bezier);

        path.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
                if (wayPointIndex + 1 == path.getNbWayPoints()) {
                    mDiceModel.setLocalRotation(Quaternion.IDENTITY);

                    if (finalFace == GameDice.FACE_SHARK) {
                        mDiceModel.rotate(0, 90, 0);
                    } else if (finalFace == GameDice.FACE_WHALE) {
                        mDiceModel.rotate(185, 0, 200);
                    } else if (finalFace == GameDice.FACE_SEASERPENT) {
                        mDiceModel.rotate(90, 5, 0);
                    }

                    // On laisse un peu de temps entre l'affichage et l'événement effectif
                    FutureCallback fc = new FutureCallback(2.0f) {
                        @Override
                        public void onFutureHappened() {
                            mSceneNode.detachChild(mDiceModel);
                            mParent.getCurrentTurn().onDiceRollFinished();
                        }
                    };
                    mFutureUpdater.addFutureTimeCallback(fc);
                }
            }
        });

        // On créé le contrôleur
        final MotionEvent motionControl = new MotionEvent(mDiceModel, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(0, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(2f);

        motionControl.play();
    }

    /**
     * Mise a jour de l'animation
     *
     * @param tpf temps
     */
    @Override
    public void simpleUpdate(float tpf) {

        mHudAnimator.update(tpf);

        // Mise à jour des animations du HUD
        mHudManager.update(tpf);

        // Mise à jour des callbacks temporels
        mFutureUpdater.update(tpf);
    }

    /**
     * Retourne l'objet de framework d'interface: Nifty
     *
     * @return l'objet de framework d'interface: Nifty
     */
    public Nifty getNifty() {
        return mNifty;
    }

    /**
     * Méthode non-implémentée
     *
     * @param rm Gestionnaire de rendu
     */
    @Override
    public void simpleRender(RenderManager rm) {
    }
}
