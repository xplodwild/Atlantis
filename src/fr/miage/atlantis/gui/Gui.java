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
package fr.miage.atlantis.gui;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ConsoleCommands;
import de.lessvoid.nifty.controls.console.builder.ConsoleBuilder;
import fr.miage.atlantis.graphics.CamConstants;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.gui.console.commands.BindListCommand;
import fr.miage.atlantis.gui.console.commands.ClearConsoleCommand;
import fr.miage.atlantis.gui.console.commands.HelpCommand;
import fr.miage.atlantis.gui.console.commands.LoggingCommand;
import fr.miage.atlantis.gui.console.commands.QuitCommand;
import fr.miage.atlantis.gui.controllers.GuiController;
import fr.miage.atlantis.logic.GameTurn;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Instancie tout les éléments du GUI
 *
 * @author Krys
 */
public class Gui {

    private static final String STYLE_FILE = "nifty-default-styles.xml";
    private static final String CONTROL_FILE = "nifty-default-controls.xml";
    private AssetManager mAm;
    private ViewPort mViewPort;
    private AudioRenderer maudioRenderer;
    private InputManager minputManager;
    private Game3DRenderer mGame3DRenderer;
    private Nifty mNifty;
    private Console mConsole;

    public Gui(Game3DRenderer g3d, Nifty n) {
        this.mAm = g3d.getAssetManager();
        this.maudioRenderer = g3d.getAudioRenderer();
        this.minputManager = g3d.getInputManager();
        this.mViewPort = g3d.getGuiViewPort();
        this.mGame3DRenderer = g3d;

        //Récupère l'obj nifty de l'ecran courant
        this.mNifty = n;
        
        
        //Charge le thème
        n.loadStyleFile(Gui.STYLE_FILE);
        n.loadControlFile(Gui.CONTROL_FILE);
        
        
        this.instanciateScreens();
        //this.instanciateInGameHUD();
        
      
    }
    
    
    private void instanciateScreens(){
        
        this.mNifty.fromXmlWithoutStartScreen("GUI/Screens.xml");
        //this.mNifty.fromXml("GUI/startScreen.xml", "start");
        //this.mNifty.fromXml("GUI/startScreen.xml","start",new GuiController(this.mGame3DRenderer));
        ((GuiController) this.mNifty.getScreen("start").getScreenController()).set3DRenderer(this.mGame3DRenderer);
    
        this.mNifty.gotoScreen("inGameHud");



        // get the console control (this assumes that there is a console in the current screen with the id="console"
        
        mConsole = this.mNifty.getScreen("inGameHud").findElementByName("console").getNiftyControl(Console.class);
       
        // output hello to the console
        mConsole.output("Demarrage de la console");
        mConsole.output("");
        mConsole.output("Tapez help pour afficher la liste des commandes disponible...");

        // create the console commands class and attach it to the console
        ConsoleCommands consoleCommands = new ConsoleCommands(mNifty, mConsole);



        // create a simple command (see below for implementation) this class will be called when the command is detected
        // and register the command as a command with the console
        /*ConsoleCommands.ConsoleCommand simpleCommand = new SimpleCommand();
        consoleCommands.registerCommand("simple", simpleCommand);*/



        // create another command (this time we can even register arguments with nifty so that the command completion will work with arguments too)


        ConsoleCommands.ConsoleCommand help = new HelpCommand(mConsole);
        ConsoleCommands.ConsoleCommand quit = new QuitCommand();
        ConsoleCommands.ConsoleCommand binds = new BindListCommand(mConsole);
        ConsoleCommands.ConsoleCommand logs = new LoggingCommand(mConsole);
        ConsoleCommands.ConsoleCommand clear = new ClearConsoleCommand(mConsole);

        consoleCommands.registerCommand("help", help);
        consoleCommands.registerCommand("-h", help);
        consoleCommands.registerCommand("quit", quit);
        consoleCommands.registerCommand("clear", clear);
        consoleCommands.registerCommand("bindlist", binds);
        consoleCommands.registerCommand("debug", logs);


        // finally enable command completion
        consoleCommands.enableCommandCompletion(true);

        //Start redirection console
        this.redirectSystemStreams();
        
        mConsole.disable();

        //Genere les keybinding
        this.generateConsoleKeyMap();
        ((GuiController) this.mNifty.getScreen("start").getScreenController()).set3DRenderer(this.mGame3DRenderer);
    }
    
    
    
    private void generateConsoleKeyMap(){
        //Bind la touche de la console
        this.getInputManager().addMapping("console", new KeyTrigger(KeyInput.KEY_F12));
        this.getInputManager().addListener(this.toggleConsole(), "console");

        //Bind la touche d'activation du quicktest
        this.getInputManager().addMapping("quicktest", new KeyTrigger(KeyInput.KEY_F11));
        this.getInputManager().addListener(this.toggleQuicktest(), "quicktest");

        //Bind la touche d'activation des stats
        this.getInputManager().addMapping("fps", new KeyTrigger(KeyInput.KEY_F10));
        this.getInputManager().addListener(this.toggleFps(), "fps");

        //Bind la touche d'activation des stats
        this.getInputManager().addMapping("menu", new KeyTrigger(KeyInput.KEY_ESCAPE));
        this.getInputManager().addListener(this.toggleMenu(), "menu");
    }


    private ActionListener toggleMenu(){
        return new ActionListener() {

            public void onAction(String name, boolean isPressed, float tpf) {
                if(isPressed){

                    Gui.this.mNifty.gotoScreen("start");
                    ((GuiController) mNifty.getScreen("start").getScreenController()).set3DRenderer(mGame3DRenderer);
                    Camera cam = mGame3DRenderer.getCamera();
                    CamConstants.moveMenu(mGame3DRenderer.getCameraNode(), cam);
                }

            }
        };
    }


    private ActionListener toggleConsole(){
        return new ActionListener() {

            public void onAction(String name, boolean isPressed, float tpf) {
                if(isPressed){
                    if(Gui.this.mConsole.isEnabled()){
                        
                        Gui.this.mConsole.setEnabled(false);
                        Gui.this.mConsole.getElement().setVisible(false);
                    }else{
                        Gui.this.mConsole.setEnabled(true);
                        Gui.this.mConsole.getElement().setVisible(true);
                    }
                }

            }
        };
    }

    private ActionListener toggleQuicktest(){
        return new ActionListener() {

            public void onAction(String name, boolean isPressed, float tpf) {
                 if(isPressed){
                    mConsole.output("");
                    if(GameTurn.DBG_QUICKTEST){
                        mConsole.output("Desactivation du mode QuickTest");
                        GameTurn.DBG_QUICKTEST = false;
                    }else{
                        mConsole.output("Activation du mode QuickTest");
                        GameTurn.DBG_QUICKTEST = true;
                    }
                 }
            }
        };
    }

    private ActionListener toggleFps(){
        return new ActionListener() {

            public void onAction(String name, boolean isPressed, float tpf) {
                 if(isPressed){
                    Gui.this.getGame3DRenderer().toggleGraphicsStats();                    
                 }
            }
        };
    }



    /**
     * Redirection du flux de messages System.out / System.err vers la console     *
     *
     */
    private void redirectSystemStreams() {

        Handler customHandler = new Handler() {

            @Override
            public void close() throws SecurityException {
            }

            @Override
            public void flush() {
            }

            @Override
            public void publish(LogRecord record) {
                String text=record.getMessage();
                System.out.println(text);
                Gui.this.mConsole.output(text);
            }
        };

        customHandler.setLevel(Level.FINEST);
        Logger.getGlobal().addHandler(customHandler);
    }
    
    
    

    //--------------------------------------------------------------------------
    //GETTERS
    //--------------------------------------------------------------------------


    public Nifty getNifty(){
        return this.mNifty;
    }


    public Console getConsole(){
        return mConsole;
    }

    public InputManager getInputManager() {
        return minputManager;
    }

    public Game3DRenderer getGame3DRenderer() {
        return mGame3DRenderer;
    }  
}
