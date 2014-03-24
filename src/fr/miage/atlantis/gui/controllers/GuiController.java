/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.controllers;

import com.jme3.renderer.Camera;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import fr.miage.atlantis.graphics.CamConstants;
import fr.miage.atlantis.graphics.Game3DRenderer;
import java.util.ArrayList;
import java.util.Random;


public class GuiController implements ScreenController {
           
    private Game3DRenderer g3rdr;
    private Nifty nifty;
    private Screen screen;
    private ArrayList<String> nameRandomizer;
    
    public GuiController(){
        super();
        this.nameRandomizer=new ArrayList();
        this.fillNameRandomizer();
    }
    
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
        
        
        TextField fieldJ1=this.nifty.getScreen("start").findElementByName("inputJ1").getNiftyControl(TextField.class);

        TextField fieldJ2=this.nifty.getScreen("start").findElementByName("inputJ2").getNiftyControl(TextField.class);        
        
        TextField fieldJ3=this.nifty.getScreen("start").findElementByName("inputJ3").getNiftyControl(TextField.class);
        
        TextField fieldJ4=this.nifty.getScreen("start").findElementByName("inputJ4").getNiftyControl(TextField.class);
        
        System.out.println(fieldJ1.getRealText()+" "+fieldJ2.getRealText()+" "+fieldJ3.getRealText()+" "+fieldJ4.getRealText()+" ");
        
        
        
        
       
      
        
        String[] players=new String[4];
        
       
        players[0]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[0]);
        players[1]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[1]);
        players[2]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[2]);
        players[3]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[3]);
        
        
        this.g3rdr.getLogic().prepareGame(players);
        
        this.g3rdr.getLogic().startGame();
                       
        this.nifty.gotoScreen("inGameHud");
        Camera cam = g3rdr.getCamera();
        CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);
        
        
    }
    
    
    
    
    public void reStartGame(){
        
       
        //TEST SI AU MOINS DEUX DES TEXT FIELDS SONT REMPLIS, SINON Genère deux joueurs avec des noms random.
        
        
        TextField fieldJ1=this.nifty.getScreen("start").findElementByName("inputJ1").getNiftyControl(TextField.class);

        TextField fieldJ2=this.nifty.getScreen("start").findElementByName("inputJ2").getNiftyControl(TextField.class);        
        
        TextField fieldJ3=this.nifty.getScreen("start").findElementByName("inputJ3").getNiftyControl(TextField.class);
        
        TextField fieldJ4=this.nifty.getScreen("start").findElementByName("inputJ4").getNiftyControl(TextField.class);
        
        System.out.println(fieldJ1.getRealText()+" "+fieldJ2.getRealText()+" "+fieldJ3.getRealText()+" "+fieldJ4.getRealText()+" ");
        
         
        
        
       
        
        String[] players=new String[4];
        
        players[0]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[0]);
        players[1]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[1]);
        players[2]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[2]);
        players[3]= this.nameRandomizer.get(new Random().nextInt(this.nameRandomizer.size()));
        this.nameRandomizer.remove(players[3]);
        
        this.g3rdr.getLogic().prepareGame(players);
        
        this.g3rdr.getLogic().startGame(); 
        
        
        this.nifty.gotoScreen("inGameHud");
        Camera cam = g3rdr.getCamera();
        CamConstants.moveAboveBoard(g3rdr.getCameraNode(), cam);
        
    }

    private void fillNameRandomizer() {
 this.nameRandomizer.add("Fee-Lycia");
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
