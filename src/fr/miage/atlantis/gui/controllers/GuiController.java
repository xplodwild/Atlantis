/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.controllers;

import com.jme3.renderer.Camera;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import fr.miage.atlantis.audio.AudioManager;
import fr.miage.atlantis.graphics.CamConstants;
import fr.miage.atlantis.graphics.Game3DRenderer;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class GuiController implements ScreenController {

    private Game3DRenderer g3rdr;
    private Nifty nifty;
    private Screen screen;
    private ArrayList<String> nameRandomizer;
    private String[] players;

    public GuiController(Game3DRenderer g3d) {
        super();
        this.g3rdr = g3d;
        this.nameRandomizer = new ArrayList();
        this.fillNameRandomizer();
        this.players = new String[4];
    }

    public GuiController() {
        super();
        this.nameRandomizer = new ArrayList();
        this.fillNameRandomizer();
        this.players = new String[4];
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

    public void set3DRenderer(Game3DRenderer g3d) {
        this.g3rdr = g3d;
    }

    
    /**
     * Demarre une nouvelle partie avec les pseudos données ou des pseudos aléatoires si non renseigné.
     */
    public void startGame() {

        players = new String[4];

        TextField fieldJ1 = this.nifty.getScreen("start").findElementByName("inputJ1").getNiftyControl(TextField.class);

        if (!fieldJ1.getRealText().isEmpty() && fieldJ1.getRealText().matches("[.-_'éèà,;:/!<>*+()#`è°^ëäïâêî$€µù a-zA-Z1-9]*")) {
            players[0] = fieldJ1.getRealText();
        } else {
            players[0] = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
            this.nameRandomizer.remove(players[0]);
        }


        TextField fieldJ2 = this.nifty.getScreen("start").findElementByName("inputJ2").getNiftyControl(TextField.class);

        if (!fieldJ2.getRealText().isEmpty() && fieldJ2.getRealText().matches("[.-_'éèà,;:/!<>*+()#`è°^ëäïâêî$€µù a-zA-Z1-9]*")) {
            players[1] = fieldJ2.getRealText();
        } else {
            players[1] = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
            this.nameRandomizer.remove(players[1]);
        }


        TextField fieldJ3 = this.nifty.getScreen("start").findElementByName("inputJ3").getNiftyControl(TextField.class);

        if (!fieldJ3.getRealText().isEmpty() && fieldJ3.getRealText().matches("[.-_'éèà,;:/!<>*+()#`è°^ëäïâêî$€µù a-zA-Z1-9]*")) {
            players[2] = fieldJ3.getRealText();
        } else {
            players[2] = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
            this.nameRandomizer.remove(players[2]);
        }


        TextField fieldJ4 = this.nifty.getScreen("start").findElementByName("inputJ4").getNiftyControl(TextField.class);

        if (!fieldJ4.getRealText().isEmpty() && fieldJ4.getRealText().matches("[.-_'éèà,;:/!<>*+()#`è°^ëäïâêî$€µù a-zA-Z1-9]*")) {
            players[3] = fieldJ4.getRealText();
        } else {
            players[3] = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
            this.nameRandomizer.remove(players[3]);
        }


        this.g3rdr.getLogic().prepareGame(players);
        this.g3rdr.getLogic().startGame();

        this.nifty.gotoScreen("inGameHud");

        this.updatePlayerName();


        Camera cam = g3rdr.getCamera();
        CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);
        
        AudioManager.getDefault().setMainMusic(false);

    }

    
    /**
     * Redemarre le jeu en cours (avec les memes joueurs
     * 
     */
    public void reStartGame() {

        //On garde les mêmes nick donc pas besoin de redefinir.


        this.g3rdr.getLogic().prepareGame(players);
        this.g3rdr.getLogic().startGame();

        this.nifty.gotoScreen("inGameHud");

        this.updatePlayerName();

        Camera cam = g3rdr.getCamera();
        CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);

    }

    
    /**
     * Désactive tout les sons du jeu
     * 
     */
    public void soundToggle() {
        /**
         * TODO : toggle les sons FX ingame
         */
        
    }

    
    /**
     * Met en pause la musique du jeu
     * 
     */
    public void musicToggle() {
        /**
         * TODO : toggle la musique du jeu
         */
    }

    
    /**
     * Retourne au menu initial du jeu
     * .
     */
    public void backToMenu() {
        
        boolean gameOver=this.g3rdr.getLogic().isFinished();        
        if(!gameOver){
            this.save();
        } 
        
        this.nifty.gotoScreen("start");
        AudioManager.getDefault().setMainMusic(true);
        Logger.getGlobal().severe("BLAHH BACKTOMENU");

        /**
         * @TODO : Reinitialiser tout.
         */
    }

    
    /**
     * Charge le dernier fichier de sauvegarde existant
     */
    public void load() {
        /**
         * @TODO : chargement du dernier jeu
         */
        
        //Si le fichier existe
        //charge
        //sinon 
        //rien ou nouvelle partie ou disable le button(à debattre)
    }

    
    /**
     * Sauvegarde la partie en cours
     */
    public void save() {
        /**
         * @TODO : sauvegarde du jeu
         */
    }

    
    /**
     * Quitte le jeu et sauvegarde si la partie n'est pas terminée.
     * 
     */
    public void exit() {        
        boolean gameOver=this.g3rdr.getLogic().isFinished();        
        if(!gameOver){
            this.save();
        }        
        System.exit(0);
    }

    
    /**
     * Met à jour les pseudo joueurs sur le GUI
     * 
     */
    public void updatePlayerName() {

        Element niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ1");
        // swap old with new text
        niftyElement.getRenderer(TextRenderer.class).setText(players[0]);

        niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ2");
        niftyElement.getRenderer(TextRenderer.class).setText(players[1]);
        niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ3");
        niftyElement.getRenderer(TextRenderer.class).setText(players[2]);
        niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ4");
        niftyElement.getRenderer(TextRenderer.class).setText(players[3]);

        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ1");
        niftyElement.getRenderer(TextRenderer.class).setText(players[0]);
        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ2");
        niftyElement.getRenderer(TextRenderer.class).setText(players[1]);
        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ3");
        niftyElement.getRenderer(TextRenderer.class).setText(players[2]);
        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ4");
        niftyElement.getRenderer(TextRenderer.class).setText(players[3]);
    }

    
    /**
     * Ajoute des pseudos dans le randomizer de pseudo.
     * 
     */
    private void fillNameRandomizer() {
        this.nameRandomizer.add("Fee-Lycia");
        this.nameRandomizer.add("Dunnkor-Leone");
        this.nameRandomizer.add("Marie-Mercredi");
        this.nameRandomizer.add("Love-Ly");
        this.nameRandomizer.add("Chris-Tough");
        this.nameRandomizer.add("G-Rémi");
        this.nameRandomizer.add("Gabry-Aile");
        this.nameRandomizer.add("Sid-Rick");
        this.nameRandomizer.add("The-Eau");
        this.nameRandomizer.add("Parla Jessica");
        this.nameRandomizer.add("Prod-yge");
        this.nameRandomizer.add("Alpa-Tchino");
    }
}
