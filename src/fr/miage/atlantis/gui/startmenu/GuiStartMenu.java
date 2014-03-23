/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.startmenu;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
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
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.gui.console.GuiConsole;
import fr.miage.atlantis.gui.console.commands.BindListCommand;
import fr.miage.atlantis.gui.console.commands.ClearConsoleCommand;
import fr.miage.atlantis.gui.console.commands.HelpCommand;
import fr.miage.atlantis.gui.console.commands.LoggingCommand;
import fr.miage.atlantis.gui.console.commands.QuitCommand;
import fr.miage.atlantis.gui.controllers.ConsoleController;
import fr.miage.atlantis.gui.controllers.StartScreenController;
import fr.miage.atlantis.logic.GameTurn;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class GuiStartMenu {
    
    
    private static final String STYLE_FILE="nifty-default-styles.xml";
    private static final String CONTROL_FILE="nifty-default-controls.xml";
    
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
     * @param Vp GuiViewport : Attention a ne pas passer le viewPort mais bien guiViewport
     * @param Ar AudioRenderer
     * @param Im InputManager
     */
    public GuiStartMenu(AssetManager Am,ViewPort Vp,AudioRenderer Ar,InputManager Im,Game3DRenderer g3dr) {        
        this.mAm=Am;
        this.maudioRenderer=Ar;
        this.minputManager=Im;
        this.mViewPort=Vp;
        this.mGame3DRenderer=g3dr;
        
        this.mNiftyDisplay = new NiftyJmeDisplay(mAm, minputManager, maudioRenderer, mViewPort);
        
        
        //Récupère l'obj nifty de l'ecran courant
        this.mNifty = this.mNiftyDisplay.getNifty();
        
        //Charge le thème
        mNifty.loadStyleFile(STYLE_FILE);        
        
        mNifty.loadControlFile(CONTROL_FILE);
        
        //Ajoute la surcouche console sur l'ecran courant
        mViewPort.addProcessor(this.mNiftyDisplay);
        
        
        
        mNifty.addScreen("StartMenu", new ScreenBuilder("StartMenu"){{            
               
            
            
            /**
             * Ajoute un controlleur perso a cet ecran
             */
            controller(new StartScreenController()); 
             
            /**
             * Création du Layer sur cet ecran.
             */
            layer(new LayerBuilder("MainLayer"){{
                                
                /*
                 * Propriétés d'agencement des elements
                 */
                childLayoutVertical(); 
 
                /**
                 * Ajoute mon controlleur personnalisé sur ce panel
                 */
                controller(new StartScreenController());
                
                /*
                 * Création d'un panel dans le layer
                 */
                panel(new PanelBuilder("panelGauche") {{
                    childLayoutCenter(); // panel properties, add more...               
 
                          
                    /**
                     * Ajoute une console
                     */
                    control(new ConsoleBuilder("console") {{
                         width("50%");
                         lines(15);

                         this.valignTop();
                         this.alignLeft();
                         
                         this.visible(false);

                        
                         this.onShowEffect(new EffectBuilder("move") {{
                           length(150);
                           inherit();
                           neverStopRendering(true);
                           effectParameter("mode", "in");
                           effectParameter("direction", "top");
                         }});
                                                  
                         this.onHideEffect(new EffectBuilder("move") {{
                           length(150);                           
                           inherit();                          
                           effectParameter("mode", "out");
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
      
       
        ConsoleCommands.ConsoleCommand help = new HelpCommand();
        ConsoleCommands.ConsoleCommand quit = new QuitCommand();
        ConsoleCommands.ConsoleCommand binds = new BindListCommand();
        ConsoleCommands.ConsoleCommand logs = new LoggingCommand();
        ConsoleCommands.ConsoleCommand clear = new ClearConsoleCommand(GuiConsole.mConsole);
        
        consoleCommands.registerCommand("help", help);
        consoleCommands.registerCommand("-h", help);
        consoleCommands.registerCommand("quit", quit);
        consoleCommands.registerCommand("clear", clear);
        consoleCommands.registerCommand("bindlist", binds);
        consoleCommands.registerCommand("debug", logs);
                

        // finally enable command completion
        consoleCommands.enableCommandCompletion(true);
    
        //Start redirection console
        GuiConsole.redirectSystemStreams();      
        
        
        
        
        
        
        mConsole.disable();        
        
        //Genere les keybinding 
        this.generateConsoleKeyMap();
    }
    
    
    
    private void generateConsoleKeyMap(){
        //Bind la touche de la console
        this.getInputManager().addMapping(INPUT_TOGGLE_CONSOLE, new KeyTrigger(KeyInput.KEY_F12));
        this.getInputManager().addListener(this.toggleConsole(), INPUT_TOGGLE_CONSOLE);
        
        //Bind la touche d'activation du quicktest
        this.getInputManager().addMapping("quicktest", new KeyTrigger(KeyInput.KEY_F11));
        this.getInputManager().addListener(this.toggleQuicktest(), "quicktest");
        
        //Bind la touche d'activation des stats
        this.getInputManager().addMapping("fps", new KeyTrigger(KeyInput.KEY_F10));
        this.getInputManager().addListener(this.toggleFps(), "fps");
    }

    
    
    
    
    private ActionListener toggleConsole(){
        return new ActionListener() {

            public void onAction(String name, boolean isPressed, float tpf) {                
                if(isPressed){
                    if(GuiConsole.mConsole.isEnabled()){
                        GuiConsole.mConsole.setEnabled(false);
                        GuiConsole.mConsole.getElement().setVisible(false);
                    }else{
                        GuiConsole.mConsole.setEnabled(true);
                        GuiConsole.mConsole.getElement().setVisible(true);
                    }
                }
                
            }
        };
    }
    
    private ActionListener toggleQuicktest(){
        return new ActionListener() {

            public void onAction(String name, boolean isPressed, float tpf) { 
                 if(isPressed){
                    GuiConsole.mConsole.output("");
                    if(GameTurn.DBG_QUICKTEST){
                        GuiConsole.mConsole.output("Desactivation du mode QuickTest");
                        GameTurn.DBG_QUICKTEST = false;  
                    }else{
                        GuiConsole.mConsole.output("Activation du mode QuickTest");
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
                    Game3DRenderer g3rdr=GuiConsole.this.getGame3DRenderer();
                    g3rdr.toggleGraphicsStats();                            
                 }
            }
        };
    }
    

   
    /**
     * Redirection du flux de messages System.out / System.err vers la console     * 
     * 
     */    
    private static void redirectSystemStreams() {
      
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
                mConsole.output(text); 
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
    
    
    public static Console getConsole(){
        return GuiConsole.mConsole;
    }
    
    public InputManager getInputManager() {
        return minputManager;
    }
    
    public Game3DRenderer getGame3DRenderer() {
        return mGame3DRenderer;
    }
}
