/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.controllers;

import com.jme3.renderer.Camera;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import fr.miage.atlantis.GameSaver;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.audio.AudioConstants;
import fr.miage.atlantis.audio.AudioManager;


import fr.miage.atlantis.graphics.CamConstants;
import fr.miage.atlantis.graphics.Game3DRenderer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiController implements ScreenController {

    private Game3DRenderer g3rdr;
    private Nifty nifty;
    private Screen screen;
    private ArrayList<String> nameRandomizer;
    private String[] players;
    private static int mScreenType;
    private boolean musicState;
    private boolean soundState;

    public GuiController(Game3DRenderer g3d) {
        super();
        this.g3rdr = g3d;
        this.nameRandomizer = new ArrayList();
        this.fillNameRandomizer();
        this.players = new String[4];
        this.musicState = true;
        this.soundState = true;
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
     * Arrete l'action en cours lors d'une partie 
     */
    public void stopCurrentAction(){
        //@TODO : Gerer les differents cas ou l'on peut arreter une action
        //Ne rien faire si on ne peut pas
    }
    
    
    
    /**
     * Demarre une nouvelle partie avec les pseudos données ou des pseudos
     * aléatoires si non renseigné.
     */
    public void startGame() {

        players = new String[4];

        TextField fieldJ1 = this.nifty.getScreen("start").findElementByName("inputJ1").getNiftyControl(TextField.class);
        TextField fieldJ2 = this.nifty.getScreen("start").findElementByName("inputJ2").getNiftyControl(TextField.class);
        TextField fieldJ3 = this.nifty.getScreen("start").findElementByName("inputJ3").getNiftyControl(TextField.class);
        TextField fieldJ4 = this.nifty.getScreen("start").findElementByName("inputJ4").getNiftyControl(TextField.class);

        int cmpt = 0;

        if (!fieldJ1.getRealText().isEmpty()) {
            cmpt++;
        }
        if (!fieldJ2.getRealText().isEmpty()) {
            cmpt++;
        }
        if (!fieldJ3.getRealText().isEmpty()) {
            cmpt++;
        }
        if (!fieldJ4.getRealText().isEmpty()) {
            cmpt++;
        }

        if (cmpt < 2) {
            Label tips1 = this.nifty.getScreen("start").findElementByName("tips1").getNiftyControl(Label.class);
            Label tips2 = this.nifty.getScreen("start").findElementByName("tips2").getNiftyControl(Label.class);
            tips1.setText("Deux joueurs minimum pour commencer");
            tips2.setText("veuillez entrez au minimum 2 noms de joueurs.");
            AudioManager.getDefault().playSound(AudioConstants.Path.ERROR);
        } else {

            players[0] = fieldJ1.getRealText();
            players[1] = fieldJ2.getRealText();
            players[2] = fieldJ3.getRealText();
            players[3] = fieldJ4.getRealText();

            //Arrange l'ordre des joueurs (si on joue a moins que 4,
            //on replace les joueurs dans les premieres cases du tableau
            this.reArrangePlayers(cmpt);

            switch (cmpt) {
                case 2:
                    GuiController.mScreenType = 2;
                    this.nifty.gotoScreen("inGameHud2J");
                    break;
                case 3:
                    GuiController.mScreenType = 3;
                    this.nifty.gotoScreen("inGameHud3J");
                    break;
                case 4:
                    GuiController.mScreenType = 4;
                    this.nifty.gotoScreen("inGameHud");
                    break;
            }


            this.updatePlayerName();

            this.g3rdr.getLogic().prepareGame(players, true);
            this.g3rdr.getLogic().startGame();

            Camera cam = g3rdr.getCamera();
            CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);
            AudioManager.getDefault().setMainMusic(false);
        }

    }

    
    /**
     * Fonction appelée lorsqu'on hoste une nouvelle partie sur le réseau local
     */
    public void lanHost(){
        /**
         * @TODO : INIT UN SERVEUR ICI
         */
        
        /**
         * @TODO : Se connecte a son propre serveur
         */
        
        
        
        /**
         * @TODO : Faire un écran spécifique
         */ 
        this.nifty.gotoScreen("HostLan");
    }  
    
    
     
    /**
     * Fonction appelée une fois que les champs de l'ecran pour rejoindre une partie lan sont remplis.
     */
    public void lanConnect(){
        
        TextField ip = this.nifty.getScreen("JoinLan").findElementByName("IPServeur").getNiftyControl(TextField.class);
        TextField nick = this.nifty.getScreen("JoinLan").findElementByName("Nick").getNiftyControl(TextField.class);
        
        String ipServ=ip.getRealText();
        String nickname=nick.getRealText();
        
        /**
         * @TODO : Traitement de la connection réseau ici.
         */
                
        this.nifty.gotoScreen("ErrorConnect");
    }
     
    
    /**
     * Redemarre le jeu en cours (avec les memes joueurs
     *
     */
    public void reStartGame() {

        //On garde les mêmes nick donc pas besoin de redefinir.


        this.g3rdr.getLogic().prepareGame(players, true);
        this.g3rdr.getLogic().startGame();

        this.nifty.gotoScreen("inGameHud");

        this.updatePlayerName();

        Camera cam = g3rdr.getCamera();
        CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);

    }

    public void returnToGame() {
        Camera cam = g3rdr.getCamera();
        CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);
        switch (GuiController.mScreenType) {
            case 2:
                this.nifty.gotoScreen("inGameHud2J");
                break;
            case 3:
                this.nifty.gotoScreen("inGameHud3J");
                break;
            case 4:

                this.nifty.gotoScreen("inGameHud");
                break;
        }
    }

    /**
     * Désactive tout les sons du jeu
     *
     */
    public void soundToggle() {
        /**
         * TODO : toggle les sons FX ingame
         */
        if (this.soundState) {
            /**
             * Renseigner la source audio a pause à la place de null
             */
            // this.maudioRenderer.pauseSource(null);
           
            this.nifty.getScreen("inGameMenu").findElementByName("btnOptions").getNiftyControl(Button.class).setText("Sons on");
            this.soundState = false;
        } else {
            // this.maudioRenderer.playSource(null);

           
            this.nifty.getScreen("inGameMenu").findElementByName("btnOptions").getNiftyControl(Button.class).setText("Sons off");
            this.soundState = true;
        }
    }

    /**
     * Met en pause la musique du jeu
     *
     */
    public void musicToggle() {
        /**
         * TODO : toggle la musique du jeu
         */
        if (this.musicState) {
            /**
             * Renseigner la source audio a pause à la place de null
             */
            
            // this.maudioRenderer.pauseSource(null);
           
            this.nifty.getScreen("inGameMenu").findElementByName("btnOptions2").getNiftyControl(Button.class).setText("Musique on");
            this.musicState = false;
        } else {
            // this.maudioRenderer.playSource(null);

          
            this.nifty.getScreen("inGameMenu").findElementByName("btnOptions2").getNiftyControl(Button.class).setText("Musique off");
            this.musicState = true;
        }
    }

    /**
     * Retourne au menu initial du jeu .
     */
    public void backToMenu() {

        //Si le jeu n'est pas finis on sauvegarde par defaut.
        /*
        boolean gameOver = this.g3rdr.getLogic().isFinished();
        if (!gameOver) {
            this.save();
        }
        */
        
        
        TextField fieldJ1 = this.nifty.getScreen("start").findElementByName("inputJ1").getNiftyControl(TextField.class);
        TextField fieldJ2 = this.nifty.getScreen("start").findElementByName("inputJ2").getNiftyControl(TextField.class);
        TextField fieldJ3 = this.nifty.getScreen("start").findElementByName("inputJ3").getNiftyControl(TextField.class);
        TextField fieldJ4 = this.nifty.getScreen("start").findElementByName("inputJ4").getNiftyControl(TextField.class);

        //Efface les champs nicknames.
        fieldJ1.setText("");
        fieldJ2.setText("");
        fieldJ3.setText("");
        fieldJ4.setText("");

        this.players = new String[4];
        this.cleanPlayerName();

        //Réaffiche les boutons Pseudo aleatoires
        this.nifty.getScreen("start").findElementByName("btnRandom1").show();
        this.nifty.getScreen("start").findElementByName("btnRandom2").show();
        this.nifty.getScreen("start").findElementByName("btnRandom3").show();
        this.nifty.getScreen("start").findElementByName("btnRandom4").show();

        this.fillNameRandomizer();

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
        //Si le fichier existe
        //charge
        //sinon 
        //rien ou nouvelle partie ou disable le button(à debattre)
        
        
        //@TODO : Verifier pourquoi ca ne clear pas le board ?!
        this.g3rdr.getLogic().prepareGame(players, true);
        
        
        GameSaver loader = new GameSaver();
        try {
            
            /**
             * @TODO : Clear board & entities
             */
            
            
            loader.loadFromFile(g3rdr.getLogic(), "./save.atlantis");
            
            this.nifty.gotoScreen("inGameHud");

            Player[] logicPlayers = g3rdr.getLogic().getPlayers();
            players = new String[logicPlayers.length];
            int i = 0;
            for (Player p : logicPlayers) {
                players[i] = p.getName();
                i++;
            }
            
            this.cleanPlayerName();
            
            this.updatePlayerName();

            this.initHudAfterGameLoad();
            Camera cam = g3rdr.getCamera();
            CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, "Error while loading game!", ex);
        }
    }

    
    
    /**
     * Sauvegarde la partie en cours
     */
    public void save() {
        GameSaver saver = new GameSaver();
        try {
            saver.saveToFile("./save.atlantis", g3rdr.getLogic());
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, "Error while saving game!", ex);
        }
    }

    
    
    /**
     * Quitte le jeu et sauvegarde si la partie n'est pas terminée.
     *
     */
    public void exit() {
       /*
        boolean gameOver = this.g3rdr.getLogic().isFinished();
        if (!gameOver) {
           
            this.save();
        }        
        */ 
        
        System.exit(0);
    }

    
    
    /**
     * Nettoye les pseudo joueurs sur le GUI
     *
     */
    public void cleanPlayerName() {
        Element niftyElement;

        //Ecran 4 Joueurs
        niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ1");
        niftyElement.getRenderer(TextRenderer.class).setText("");
        niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ2");
        niftyElement.getRenderer(TextRenderer.class).setText("");
        niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ3");
        niftyElement.getRenderer(TextRenderer.class).setText("");
        niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ4");
        niftyElement.getRenderer(TextRenderer.class).setText("");

        //Ecran 3 Joueurs
        niftyElement = nifty.getScreen("inGameHud3J").findElementByName("nomJ1");
        niftyElement.getRenderer(TextRenderer.class).setText("");
        niftyElement = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
        niftyElement.getRenderer(TextRenderer.class).setText("");
        niftyElement = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
        niftyElement.getRenderer(TextRenderer.class).setText("");

        //Ecran 2 Joueurs
        niftyElement = nifty.getScreen("inGameHud2J").findElementByName("nomJ1");
        niftyElement.getRenderer(TextRenderer.class).setText("");
        niftyElement = nifty.getScreen("inGameHud2J").findElementByName("nomJ2");
        niftyElement.getRenderer(TextRenderer.class).setText("");


        //Ecran Accueil
        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ1");
        niftyElement.getRenderer(TextRenderer.class).setText("");
        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ2");
        niftyElement.getRenderer(TextRenderer.class).setText("");

        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ3");
        niftyElement.getRenderer(TextRenderer.class).setText("");

        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ4");
        niftyElement.getRenderer(TextRenderer.class).setText("");

    }

    
    
    /**
     * Met à jour les pseudo joueurs sur le GUI
     *
     */
    public void updatePlayerName() {
        Element niftyElement;
        if (players.length == 4) {
            //Ecran 4 Joueurs
            niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ1");
            niftyElement.getRenderer(TextRenderer.class).setText(players[0]);
            niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ2");
            niftyElement.getRenderer(TextRenderer.class).setText(players[1]);
            niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ3");
            niftyElement.getRenderer(TextRenderer.class).setText(players[2]);
            niftyElement = nifty.getScreen("inGameHud").findElementByName("nomJ4");
            niftyElement.getRenderer(TextRenderer.class).setText(players[3]);
        }


        if (players.length == 3) {
            //Ecran 3 Joueurs
            niftyElement = nifty.getScreen("inGameHud3J").findElementByName("nomJ1");
            niftyElement.getRenderer(TextRenderer.class).setText(players[0]);
            niftyElement = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
            niftyElement.getRenderer(TextRenderer.class).setText(players[1]);
            niftyElement = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
            niftyElement.getRenderer(TextRenderer.class).setText(players[2]);
        }

        if (players.length == 2) {
            //Ecran 2 Joueurs
            niftyElement = nifty.getScreen("inGameHud2J").findElementByName("nomJ1");
            niftyElement.getRenderer(TextRenderer.class).setText(players[0]);
            niftyElement = nifty.getScreen("inGameHud2J").findElementByName("nomJ2");
            niftyElement.getRenderer(TextRenderer.class).setText(players[1]);
        }

        //Ecran Accueil
        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ1");
        niftyElement.getRenderer(TextRenderer.class).setText(players[0]);
        niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ2");
        niftyElement.getRenderer(TextRenderer.class).setText(players[1]);
        if (players.length == 3) {
            niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ3");
            niftyElement.getRenderer(TextRenderer.class).setText(players[2]);
        }
        if (players.length == 4) {
            niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ3");
            niftyElement.getRenderer(TextRenderer.class).setText(players[2]);
            niftyElement = nifty.getScreen("inGameMenu").findElementByName("nomJ4");
            niftyElement.getRenderer(TextRenderer.class).setText(players[3]);
        }
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
        this.nameRandomizer.add("Jessica");
        this.nameRandomizer.add("Prodigy");
        this.nameRandomizer.add("Romer");
        this.nameRandomizer.add("Gab");
        this.nameRandomizer.add("Alphonse");
        this.nameRandomizer.add("Brown");
        this.nameRandomizer.add("Grumpy Cat");
        this.nameRandomizer.add("Scumbag Steve");
        this.nameRandomizer.add("Good Guy Greg");
        this.nameRandomizer.add("Stinky-Winky");
        this.nameRandomizer.add("Dispy");
        this.nameRandomizer.add("Lala");
        this.nameRandomizer.add("Po");
        this.nameRandomizer.add("Swaggy");
        this.nameRandomizer.add("Youlow");
        this.nameRandomizer.add("Alpa-Tchino");
    }

    
    /**
     * Remplis le champ pseudo pour joueur 1 avec un pseudo aléatoire     * 
     */
    public void nickRandomJ1() {
        this.nifty.getScreen("start").findElementByName("btnRandom1").hide();
        TextField fieldJ1 = this.nifty.getScreen("start").findElementByName("inputJ1").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ1.setText(tmp);
        AudioManager.getDefault().playSound(AudioConstants.Path.WHOOSH);
    }

    /**
     * Remplis le champ pseudo pour joueur 2  avec un pseudo aléatoire     * 
     */
    public void nickRandomJ2() {
        this.nifty.getScreen("start").findElementByName("btnRandom2").hide();
        TextField fieldJ2 = this.nifty.getScreen("start").findElementByName("inputJ2").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ2.setText(tmp);
        AudioManager.getDefault().playSound(AudioConstants.Path.WHOOSH);
    }

    /**
     * Remplis le champ pseudo pour joueur 3 avec un pseudo aléatoire     * 
     */
    public void nickRandomJ3() {
        this.nifty.getScreen("start").findElementByName("btnRandom3").hide();
        TextField fieldJ3 = this.nifty.getScreen("start").findElementByName("inputJ3").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ3.setText(tmp);
        AudioManager.getDefault().playSound(AudioConstants.Path.WHOOSH);
    }

    
     /**
     * Remplis le champ pseudo pour joueur 4 avec un pseudo aléatoire     * 
     */
    public void nickRandomJ4() {
        this.nifty.getScreen("start").findElementByName("btnRandom4").hide();
        TextField fieldJ4 = this.nifty.getScreen("start").findElementByName("inputJ4").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ4.setText(tmp);
        AudioManager.getDefault().playSound(AudioConstants.Path.WHOOSH);
    }
    
    
    
    
    
    /**
     * Remplis le champ pseudo pour le multijoueur LAN avec un pseudo aléatoire     * 
     */
    public void nickRandomMulti() {
        this.nifty.getScreen("start").findElementByName("btnRandom4").hide();
        TextField fieldMulti = this.nifty.getScreen("JoinLan").findElementByName("inputNick").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldMulti.setText(tmp);
    }

      
      
    /**
     * Réorganise l'ordre des joueurs lors de la création d'une partie si des trous sont laissés au niveau des joueurs.
     * @param nbPlayers nombre de joueurs
     */
    private void reArrangePlayers(int nbPlayers) {
        switch (nbPlayers) {
            case 2:
                if (players[0].isEmpty()) {
                    if (!players[2].isEmpty()) {
                        players[0] = players[2];
                        players[2] = "";
                    } else {
                        players[0] = players[3];
                        players[3] = "";
                    }
                }

                if (players[1].isEmpty()) {

                    if (players[2].isEmpty()) {
                        players[1] = players[3];
                        players[3] = "";
                    } else {

                        if (players[3].isEmpty()) {
                            players[1] = players[2];
                            players[2] = "";
                        }
                    }
                }

                break;
            case 3:
                if (players[0].isEmpty()) {
                    players[0] = players[3];
                    players[3] = "";
                }

                if (players[1].isEmpty()) {
                    players[1] = players[3];
                    players[3] = "";
                }

                if (players[2].equals("")) {
                    players[2] = players[3];
                    players[3] = "";
                }


                break;
            case 4:
                break;
        }

        if (players[3].isEmpty()) {
            String[] tmp = new String[3];
            tmp[0] = players[0];
            tmp[1] = players[1];
            tmp[2] = players[2];
            this.players = tmp;
        }

        if (players[2].isEmpty()) {
            String[] tmp = new String[2];
            tmp[0] = players[0];
            tmp[1] = players[1];
            this.players = tmp;
        }
    }
            
    
    /**
     * Changement d'ecran vers l'ecran "Rejoindre une partie réseau"
     */
    public void lanJoin(){      
         this.nifty.gotoScreen("JoinLan");
    } 
    
    
    /**
     * Initialise le hud du jeu au joueur courant (utile après le chargement d'une sauvegarde)
     *
     */
    public void initHudAfterGameLoad(){
        Player p =this.g3rdr.getLogic().getCurrentTurn().getPlayer();

        String joueurCourant = p.getName();

        Player[] plr = this.g3rdr.getLogic().getPlayers();

        int lg = plr.length;

        String colorP1 = "#0060ab4d";
        String colorP2 = "#349b144d";
        String colorP3 = "#eda0004d";
        String colorP4 = "#c6000b4d";

        HashMap<String, String> playerAndColor = new HashMap();

        LinkedList<Player> lkl = new LinkedList();
        lkl.add(plr[0]);
        lkl.add(plr[1]);
        if (lg >= 3) {
            lkl.add(plr[2]);
        }
        if (lg == 4) {
            lkl.add(plr[3]);
        }


        Element text, panel;
        
        switch (lg) {



            //PARTIE A 2 JOUEURS***********************************************/ 

            case 2:
                //Lie les pseudos a leurs couleurs.
                playerAndColor.put(plr[0].getName(), colorP1);
                playerAndColor.put(plr[1].getName(), colorP2);

                //Defini le panel du bas et de droite à la couleur du joueur courant.
                panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelBottom");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelRight");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                //Defini le nom du joueur courant (zone du bas)
                text = nifty.getScreen("inGameHud2J").findElementByName("nomJ1");
                text.getRenderer(TextRenderer.class).setText(p.getName());

                if (lkl.indexOf(p) == 1) {
                    panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelTop2");
                    panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                    text = nifty.getScreen("inGameHud2J").findElementByName("nomJ2");
                    text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());
                } else {
                    panel = nifty.getScreen("inGameHud2J").findElementByName("HudPanelTop2");
                    panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                    text = nifty.getScreen("inGameHud2J").findElementByName("nomJ2");
                    text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());
                }
                break;




            //PARTIE A 3 JOUEURS***********************************************/    

            case 3:
                playerAndColor.put(plr[0].getName(), colorP1);
                playerAndColor.put(plr[1].getName(), colorP2);
                playerAndColor.put(plr[2].getName(), colorP3);

                //Defini le panel du bas et de droite à la couleur du joueur courant.
                panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelBottom");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelRight");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                //Defini le nom du joueur courant (zone du bas)
                text = nifty.getScreen("inGameHud3J").findElementByName("nomJ1");
                text.getRenderer(TextRenderer.class).setText(p.getName());


                switch (lkl.indexOf(p)) {
                    case 0:
                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());

                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());
                        break;

                    case 1:
                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());

                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        break;

                    case 2:
                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        panel = nifty.getScreen("inGameHud3J").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud3J").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());
                        break;
                }

                break;




            //PARTIE A 4 JOUEURS***********************************************/ 

            //PARTIE A 4 JOUEURS***********************************************/

            case 4:
                playerAndColor.put(plr[0].getName(), colorP1);
                playerAndColor.put(plr[1].getName(), colorP2);
                playerAndColor.put(plr[2].getName(), colorP3);
                playerAndColor.put(plr[3].getName(), colorP4);

                //Defini le panel du bas et de droite à la couleur du joueur courant.
                panel = nifty.getScreen("inGameHud").findElementByName("HudPanelBottom");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                panel = nifty.getScreen("inGameHud").findElementByName("HudPanelRight");
                panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(p.getName())));
                //Defini le nom du joueur courant (zone du bas)
                text = nifty.getScreen("inGameHud").findElementByName("nomJ1");
                text.getRenderer(TextRenderer.class).setText(p.getName());

                switch (lkl.indexOf(p)) {
                    case 0:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(3).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(3).getName());

                        break;

                    case 1:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(3).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(3).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        break;

                    case 2:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(3).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(3).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());
                        break;

                    case 3:
                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop2");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(0).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ2");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(0).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop3");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(1).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ3");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(1).getName());

                        panel = nifty.getScreen("inGameHud").findElementByName("HudPanelTop4");
                        panel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(playerAndColor.get(lkl.get(2).getName())));
                        text = nifty.getScreen("inGameHud").findElementByName("nomJ4");
                        text.getRenderer(TextRenderer.class).setText(lkl.get(2).getName());
                        break;
                }

                break;
        }
    }
}
