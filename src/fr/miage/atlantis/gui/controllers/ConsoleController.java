/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;


public class ConsoleController implements ScreenController {
                
    @Override
    public void bind(Nifty nifty, Screen screen) {

    }
    
    @Override
    public void onStartScreen() {
    }
    
    @Override
    public void onEndScreen() {
    }


    /**
     * Methode appelée pour quitter le jeu.
     */
    public void exitGame(){
        
        //@TODO : Définir et implementer les actions lors de l'arret d'une partie (nottament vis a vis du futur multijoueurs)
        System.exit(0);
    }   
}     