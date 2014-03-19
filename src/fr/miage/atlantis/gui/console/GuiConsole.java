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

package fr.miage.atlantis.gui.console;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;

import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ConsoleCommands;
import de.lessvoid.nifty.controls.console.builder.ConsoleBuilder;
import fr.miage.atlantis.gui.console.commands.HelpCommand;
import fr.miage.atlantis.gui.console.commands.QuitCommand;
import fr.miage.atlantis.gui.controllers.ConsoleController;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.SwingUtilities;


/**
 * Implementation Java de la console de l'interface graphique grâce a Nifty GUI
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014  
 */


public final class GuiConsole{
    
    
    private static final String STYLE_FILE="nifty-default-styles.xml";
    private static final String CONTROL_FILE="nifty-default-controls.xml";
    
    private AssetManager mAm;
    private ViewPort mViewPort;
    private AudioRenderer maudioRenderer; 
    private InputManager minputManager;
    
    private static Console mConsole;
    
    private Nifty mNifty;
    
    private NiftyJmeDisplay mNiftyDisplay;

 
    public GuiConsole(AssetManager Am,ViewPort Vp,AudioRenderer Ar,InputManager Im) {        
        this.mAm=Am;
        this.maudioRenderer=Ar;
        this.minputManager=Im;
        this.mViewPort=Vp;
        
        this.mNiftyDisplay = new NiftyJmeDisplay(mAm, minputManager, maudioRenderer, mViewPort);
        
        
        //Récupère l'obj nifty de l'ecran courant
        this.mNifty = this.mNiftyDisplay.getNifty();
        
        //Charge le thème
        mNifty.loadStyleFile(STYLE_FILE);        
        
        mNifty.loadControlFile(CONTROL_FILE);
        
        //Ajoute la surcouche console sur l'ecran courant
        mViewPort.addProcessor(this.mNiftyDisplay);
        
        
        
        mNifty.addScreen("ConsoleHUD", new ScreenBuilder("ConsoleHUD"){{            
                                    
            /**
             * Ajoute un controlleur perso a cet ecran
             */
            controller(new ConsoleController()); 
             
            /**
             * Création du Layer sur cet ecran.
             */
            layer(new LayerBuilder("layer1"){{
                
                this.name("layer10");
                
                /*
                 * Propriétés d'agencement des elements
                 */
                childLayoutVertical(); 
 
                /**
                 * Ajoute mon controlleur personnalisé sur ce panel
                 */
                controller(new ConsoleController());
                
                /*
                 * Création d'un panel dans le layer
                 */
                panel(new PanelBuilder("panel1") {{
                    childLayoutCenter(); // panel properties, add more...               
 
                          
                    /**
                     * Ajoute une console
                     */
                    control(new ConsoleBuilder("console") {{
                         width("50%");
                         lines(15);

                         this.valignTop();
                         this.alignLeft();
                       

                         onStartScreenEffect(new EffectBuilder("move") {{
                           length(150);
                           inherit();
                           neverStopRendering(true);
                           effectParameter("mode", "in");
                           effectParameter("direction", "top");
                         }});
                     }}); 
                    //</control>
                }});
                // </panel>
            }});
            // </layer>
        }}.build(mNifty));   
        // </screen> 
        
        this.mNifty.gotoScreen("ConsoleHUD");
        
        
    
        // get the console control (this assumes that there is a console in the current screen with the id="console"
        mConsole = this.getNifty().getScreen("ConsoleHUD").findElementByName("console").getNiftyControl(Console.class);
        
       
        // output hello to the console
        mConsole.output("Starting console");



        // create the console commands class and attach it to the console
        ConsoleCommands consoleCommands = new ConsoleCommands(mNifty, mConsole);



        // create a simple command (see below for implementation) this class will be called when the command is detected
        // and register the command as a command with the console
        /*ConsoleCommands.ConsoleCommand simpleCommand = new SimpleCommand();
        consoleCommands.registerCommand("simple", simpleCommand);*/



        // create another command (this time we can even register arguments with nifty so that the command completion will work with arguments too)
      
       
        ConsoleCommands.ConsoleCommand help = new HelpCommand();
        ConsoleCommands.ConsoleCommand quit = new QuitCommand();
        
        consoleCommands.registerCommand("help", help);
        consoleCommands.registerCommand("-h", help);
        consoleCommands.registerCommand("quit", quit);


        // finally enable command completion
        consoleCommands.enableCommandCompletion(true);

    
        //Start redirection console
        GuiConsole.redirectSystemStreams();       
    }
    
    

   
    /**
     * Redirection du flux de messages System.out / System.err vers la console     * 
     * 
     */
    
    private static void redirectSystemStreams() {
        OutputStream out = new OutputStream() {

        @Override
        public void write(final int b) throws IOException {
          updateTextPane(String.valueOf((char) b));
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
          updateTextPane(new String(b, off, len));
        }

        @Override
        public void write(byte[] b) throws IOException {
          write(b, 0, b.length);
        }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
    
    
    /**
     * Update affichage de la console avec les flux données
     * 
     * @param text 
     */
    private static void updateTextPane(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mConsole.output(text);
            }
        });
    }
    
    
    
    
    //--------------------------------------------------------------------------
    //GETTERS
    //--------------------------------------------------------------------------
    
    
    public Nifty getNifty(){
        return this.mNifty;        
    } 
}
