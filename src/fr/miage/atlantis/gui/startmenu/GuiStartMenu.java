/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.startmenu;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.gui.controllers.StartScreenController;

/**
 *
 * @author Cristian
 */
public class GuiStartMenu {

    private static final String STYLE_FILE = "nifty-default-styles.xml";
    private static final String CONTROL_FILE = "nifty-default-controls.xml";
    private AssetManager mAm;
    private ViewPort mViewPort;
    private AudioRenderer maudioRenderer;
    private InputManager minputManager;
    private Game3DRenderer mGame3DRenderer;
    private Nifty mNifty;
    
    /**
     * Constructeur de GuiConsole
     *
     * @param Am AssestManager
     * @param Vp GuiViewport : Attention a ne pas passer le viewPort mais bien
     * guiViewport
     * @param Ar AudioRenderer
     * @param Im InputManager
     */
    public GuiStartMenu(Game3DRenderer g3dr,Nifty n) {
        this.mAm = g3dr.getAssetManager();
        this.maudioRenderer = g3dr.getAudioRenderer();
        this.minputManager = g3dr.getInputManager();
        this.mViewPort = g3dr.getGuiViewPort();
        this.mGame3DRenderer = g3dr;

        
        //Récupère l'obj nifty de l'ecran courant
        this.mNifty = n;

        this.mNifty.fromXmlWithoutStartScreen("GUI/startScreen.xml");

        ((StartScreenController) this.mNifty.getScreen("start").getScreenController()).set3DRenderer(this.mGame3DRenderer);

        this.mNifty.gotoScreen("start");

    }

    //--------------------------------------------------------------------------
    //GETTERS
    //--------------------------------------------------------------------------
    public Nifty getNifty() {
        return this.mNifty;
    }

    public InputManager getInputManager() {
        return minputManager;
    }

    public Game3DRenderer getGame3DRenderer() {
        return mGame3DRenderer;
    }
}
