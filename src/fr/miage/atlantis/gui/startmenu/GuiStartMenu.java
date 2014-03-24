/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.startmenu;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.tools.Color;
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
    private NiftyJmeDisplay mNiftyDisplay;

    /**
     * Constructeur de GuiConsole
     *
     * @param Am AssestManager
     * @param Vp GuiViewport : Attention a ne pas passer le viewPort mais bien
     * guiViewport
     * @param Ar AudioRenderer
     * @param Im InputManager
     */
    public GuiStartMenu(Game3DRenderer g3dr,NiftyJmeDisplay jmdsp) {
        this.mAm = g3dr.getAssetManager();
        this.maudioRenderer = g3dr.getAudioRenderer();
        this.minputManager = g3dr.getInputManager();
        this.mViewPort = g3dr.getGuiViewPort();
        this.mGame3DRenderer = g3dr;

        this.mNiftyDisplay = jmdsp;


        //Récupère l'obj nifty de l'ecran courant
        this.mNifty = this.mNiftyDisplay.getNifty();

        //Charge le thème
        mNifty.loadStyleFile(STYLE_FILE);

        mNifty.loadControlFile(CONTROL_FILE);

        //Ajoute la surcouche console sur l'ecran courant
        mViewPort.addProcessor(this.mNiftyDisplay);



        this.mNifty.fromXmlWithoutStartScreen("GUI/startScreen.xml");
        
        ((StartScreenController)this.mNifty.getScreen("start").getScreenController()).set3DRenderer(this.mGame3DRenderer);

                                               

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
