/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.controllers;

import com.jme3.audio.AudioRenderer;
import com.jme3.renderer.Camera;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.graphics.CamConstants;
import fr.miage.atlantis.graphics.Game3DRenderer;
import java.util.ArrayList;
import java.util.Random;

public class GuiController implements ScreenController {

    private Game3DRenderer g3rdr;
    private AudioRenderer maudioRenderer;
    private Nifty nifty;
    private Screen screen;
    private ArrayList<String> nameRandomizer;
    private String[] players;
    private static int mScreenType;
    private boolean musicState;
    private boolean soundState;

    public GuiController(Game3DRenderer g3d, AudioRenderer ardr) {
        super();
        this.g3rdr = g3d;
        this.maudioRenderer = ardr;
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

            this.g3rdr.getLogic().prepareGame(players);
            this.g3rdr.getLogic().startGame();

            Camera cam = g3rdr.getCamera();
            CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);
        }

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
            this.nifty.getScreen("start").findElementByName("btnOptions").getNiftyControl(Button.class).setText("Sons on");
            this.nifty.getScreen("inGameMenu").findElementByName("btnOptions").getNiftyControl(Button.class).setText("Sons on");
            this.soundState = false;
        } else {
            // this.maudioRenderer.playSource(null);

            this.nifty.getScreen("start").findElementByName("btnOptions").getNiftyControl(Button.class).setText("Sons off");
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
            this.nifty.getScreen("start").findElementByName("btnOptions2").getNiftyControl(Button.class).setText("Musique on");
            this.nifty.getScreen("inGameMenu").findElementByName("btnOptions2").getNiftyControl(Button.class).setText("Musique on");
            this.musicState = false;
        } else {
            // this.maudioRenderer.playSource(null);

            this.nifty.getScreen("start").findElementByName("btnOptions2").getNiftyControl(Button.class).setText("Musique off");
            this.nifty.getScreen("inGameMenu").findElementByName("btnOptions2").getNiftyControl(Button.class).setText("Musique off");
            this.musicState = true;
        }
    }

    /**
     * Retourne au menu initial du jeu .
     */
    public void backToMenu() {

        //Si le jeu n'est pas finis on sauvegarde par defaut.
        boolean gameOver = this.g3rdr.getLogic().isFinished();
        if (!gameOver) {
            this.save();
        }

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
        boolean gameOver = this.g3rdr.getLogic().isFinished();
        if (!gameOver) {
            this.save();
        }
        System.exit(0);
    }

    /**
     * Met à jour les pseudo joueurs sur le GUI
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
        this.nameRandomizer.add("Parla Jessica");
        this.nameRandomizer.add("Prod-yge");
        this.nameRandomizer.add("Alpa-Tchino");
    }

    public void nickRandomJ1() {
        this.nifty.getScreen("start").findElementByName("btnRandom1").hide();
        TextField fieldJ1 = this.nifty.getScreen("start").findElementByName("inputJ1").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ1.setText(tmp);
    }

    public void nickRandomJ2() {
        this.nifty.getScreen("start").findElementByName("btnRandom2").hide();
        TextField fieldJ2 = this.nifty.getScreen("start").findElementByName("inputJ2").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ2.setText(tmp);
    }

    public void nickRandomJ3() {
        this.nifty.getScreen("start").findElementByName("btnRandom3").hide();
        TextField fieldJ3 = this.nifty.getScreen("start").findElementByName("inputJ3").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ3.setText(tmp);
    }

    public void nickRandomJ4() {
        this.nifty.getScreen("start").findElementByName("btnRandom4").hide();
        TextField fieldJ4 = this.nifty.getScreen("start").findElementByName("inputJ4").getNiftyControl(TextField.class);
        String tmp = this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(tmp);
        fieldJ4.setText(tmp);
    }

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
}
