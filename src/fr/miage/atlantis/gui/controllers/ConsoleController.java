/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;


public class ConsoleController implements ScreenController {
            
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
}     