/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import fr.miage.atlantis.graphics.Game3DRenderer;
import java.util.ArrayList;
import java.util.Random;


public class StartScreenController implements ScreenController {
           
    private Game3DRenderer g3rdr;
    private Nifty nifty;
    private Screen screen;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty=nifty;
        this.screen=screen;
    }
    
    @Override
    public void onStartScreen() {
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    
    public void set3DRenderer(Game3DRenderer g3d){
        this.g3rdr=g3d;
    }
    
    public void startGame(){
        
       
        //TEST SI AU MOINS DEUX DES TEXT FIELDS SONT REMPLIS, SINON Genère deux joueurs avec des noms random.
        ArrayList<String> noms=new ArrayList();
        
        noms.add("Fee-Lycia");
        noms.add("Marie-Mercredi");
        noms.add("Love-Ly");
        noms.add("Chris-Tough");
        noms.add("G-Rémi");
        noms.add("Gabry-Aile");
        noms.add("Sid-Rick");
        noms.add("The-Eau");
        noms.add("Parla Jessica");
        noms.add("Prod-yge");
        noms.add("Alpa-Tchino");        
        
       
        
        String[] players=new String[4];
        
        players[0]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[0]);
        players[1]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[1]);
        players[2]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[2]);
        players[3]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[3]);
        
        this.g3rdr.getLogic().prepareGame(players);
        
        this.g3rdr.getLogic().startGame();
        
        this.nifty.gotoScreen("console");
        
        
    }
    
    
    
    
    public void reStartGame(){
        
       
        //TEST SI AU MOINS DEUX DES TEXT FIELDS SONT REMPLIS, SINON Genère deux joueurs avec des noms random.
        ArrayList<String> noms=new ArrayList();
        
        noms.add("Fee-Lycia");
        noms.add("Marie-Mercredi");
        noms.add("Love-Ly");
        noms.add("Chris-Tough");
        noms.add("G-Rémi");
        noms.add("Gabry-Aile");
        noms.add("Sid-Rick");
        noms.add("The-Eau");
        noms.add("Parla Jessica");
        noms.add("Prod-yge");
        noms.add("Alpa-Tchino");        
        
       
        
        String[] players=new String[4];
        
        players[0]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[0]);
        players[1]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[1]);
        players[2]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[2]);
        players[3]= noms.get(new Random().nextInt(noms.size()));
        noms.remove(players[3]);
        
        this.g3rdr.getLogic().prepareGame(players);
        
        this.g3rdr.getLogic().startGame();
        
        this.nifty.gotoScreen("console");
        
        
    }
}  