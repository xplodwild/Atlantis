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
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ConsoleCommands;
import de.lessvoid.nifty.controls.console.builder.ConsoleBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.tools.Color;
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
    public GuiStartMenu(AssetManager Am, ViewPort Vp, AudioRenderer Ar, InputManager Im, Game3DRenderer g3dr) {
        this.mAm = Am;
        this.maudioRenderer = Ar;
        this.minputManager = Im;
        this.mViewPort = Vp;
        this.mGame3DRenderer = g3dr;

        this.mNiftyDisplay = new NiftyJmeDisplay(mAm, minputManager, maudioRenderer, mViewPort);


        //Récupère l'obj nifty de l'ecran courant
        this.mNifty = this.mNiftyDisplay.getNifty();

        //Charge le thème
        mNifty.loadStyleFile(STYLE_FILE);

        mNifty.loadControlFile(CONTROL_FILE);

        //Ajoute la surcouche console sur l'ecran courant
        mViewPort.addProcessor(this.mNiftyDisplay);



        mNifty.addScreen("start", new ScreenBuilder("start") {
            {



                /**
                 * Ajoute un controlleur perso a cet ecran
                 */
                controller(new StartScreenController());

                /**
                 * Création du Layer sur cet ecran.
                 */
                layer(new LayerBuilder("background") {
                    {

                        /*
                         * Propriétés d'agencement des elements
                         */
                        this.childLayoutHorizontal();
                        this.backgroundColor(Color.NONE);

                        /**
                         * Ajoute mon controlleur personnalisé sur ce panel
                         */
                        controller(new StartScreenController());

                        /*
                         * Création d'un panel dans le layer
                         */
                        panel(new PanelBuilder("panelGauche") {
                            {
                                childLayoutCenter(); // panel properties, add more...               

                                this.backgroundColor(Color.WHITE);
                                this.alignLeft();
                                this.valign(VAlign.Top);
                                this.width("20%");
                                this.height("100%");



                                //</control>
                            }
                        });
                        // </panel>



                        panel(new PanelBuilder("panelDroit") {
                            {
                                childLayoutCenter(); // panel properties, add more...               

                                this.backgroundColor(Color.NONE);
                                this.alignRight();
                                this.valign(VAlign.Top);
                                this.width("80%");
                                this.height("100%");

                                



                                panel(new PanelBuilder("haut") {
                                    {
                                        childLayoutHorizontal(); // panel properties, add more...               

                                        this.backgroundColor(Color.randomColor());
                                        this.alignRight();
                                        this.valign(VAlign.Top);
                                        this.width("100%");
                                        this.height("15%");

                                        panel(new PanelBuilder("haut_1") {
                                            {
                                                childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                                this.text(new TextBuilder("pseudo_1"){{
                                                    this.font("Fonts/sansitc.fnt");
                                                    this.text("Joueur 1");
                                                    }});
                                                
                                                
                                                control(new TextFieldBuilder("input", "hello textfield") {
                                                    {
                                                        width("80%");

                                                    }
                                                });
                                            }
                                        });
                                        
                                        panel(new PanelBuilder("haut_2") {
                                            {
                                               childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                                this.text(new TextBuilder("pseudo_1"){{
                                                    this.font("Fonts/sansitc.fnt");
                                                    this.text("Joueur 1");
                                                    }});
                                                
                                                
                                                control(new TextFieldBuilder("input", "hello textfield") {
                                                    {
                                                        width("80%");

                                                    }
                                                });
                                            }
                                        });
                                        
                                        panel(new PanelBuilder("haut_3") {
                                            {
                                                 childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                                this.text(new TextBuilder("pseudo_1"){{
                                                    this.font("Fonts/sansitc.fnt");
                                                    this.text("Joueur 1");
                                                    }});
                                                
                                                
                                                control(new TextFieldBuilder("input", "hello textfield") {
                                                    {
                                                        width("80%");

                                                    }
                                                });
                                            }
                                        });
                                        
                                        panel(new PanelBuilder("haut_4") {
                                            {
                                                 childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                                this.text(new TextBuilder("pseudo_1"){{
                                                    this.font("Fonts/sansitc.fnt");
                                                    this.text("Joueur 1");
                                                    }});
                                                
                                                
                                                control(new TextFieldBuilder("input", "hello textfield") {
                                                    {
                                                        width("80%");

                                                    }
                                                });
                                            }
                                        });




                                        //</control>
                                    }
                                });
                                
                                
                                
                                
                                 panel(new PanelBuilder("bas") {
                                    {
                                        childLayoutHorizontal(); // panel properties, add more...               

                                        this.backgroundColor(new Color(0.8f, 0.8f, 0.8f, 0.75f));
                                        this.alignRight();
                                        this.valign(VAlign.Bottom);
                                        this.width("100%");
                                        this.height("85%");

                                        panel(new PanelBuilder("bas_1") {
                                            {
                                                childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                                this.image(new ImageBuilder("imageDeFond") {
                                    {
                                        filename("Interface/photo2.jpg");
                                        this.alignLeft();
                                        this.valignTop();
                                    }

                                    @Override
                                    public void panel(PanelBuilder panelBuilder) {
                                        super.panel(panelBuilder); //To change body of generated methods, choose Tools | Templates.
                                    }
                                });
                                            }
                                        });
                                        
                                        panel(new PanelBuilder("bas_2") {
                                            {
                                               childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                               this.image(new ImageBuilder("imageDeFond") {
                                    {
                                        filename("Interface/photo2.jpg");
                                        this.alignLeft();
                                        this.valignTop();
                                    }

                                    @Override
                                    public void panel(PanelBuilder panelBuilder) {
                                        super.panel(panelBuilder); //To change body of generated methods, choose Tools | Templates.
                                    }
                                });
                                            }
                                        });
                                        
                                        panel(new PanelBuilder("bas_3") {
                                            {
                                                 childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                                this.image(new ImageBuilder("imageDeFond") {
                                    {
                                        filename("Interface/photo2.jpg");
                                        this.alignLeft();
                                        this.valignTop();
                                    }

                                    @Override
                                    public void panel(PanelBuilder panelBuilder) {
                                        super.panel(panelBuilder); //To change body of generated methods, choose Tools | Templates.
                                    }
                                });
                                            }
                                        });
                                        
                                        panel(new PanelBuilder("bas_4") {
                                            {
                                                 childLayoutVertical();
                                                this.width("25%");
                                                this.height("75%");
                                                this.valignCenter();
                                                this.alignCenter();
                                                
                                                this.image(new ImageBuilder("imageDeFond") {
                                    {
                                        filename("Interface/photo2.jpg");
                                        this.alignLeft();
                                        this.valignTop();
                                    }

                                    @Override
                                    public void panel(PanelBuilder panelBuilder) {
                                        super.panel(panelBuilder); //To change body of generated methods, choose Tools | Templates.
                                    }
                                });
                                            }
                                        });




                                        //</control>
                                    }
                                });

                            }
                        });
                        // </panel>


                    }
                });
                // </layer>
            }
        }.build(mNifty));
        // </screen> 

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
