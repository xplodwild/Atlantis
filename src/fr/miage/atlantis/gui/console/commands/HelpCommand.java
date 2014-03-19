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

package fr.miage.atlantis.gui.console.commands;

import de.lessvoid.nifty.controls.ConsoleCommands;


/**
 * Help command of the console
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class HelpCommand implements ConsoleCommands.ConsoleCommand {
    
    /**
     * Methode executée lors de l'appel de la commande
     * @param args arguments passés à la commande
     */
    @Override
    public void execute(final String[] args) {
        
        /*
         * Nous redirigeons les sorties System.out.println vers notre console;
         * Il est donc possible de definir nos sortie simplement via sysout.
         */
        
        //On liste ici toutes les commandes executable dans la console 
        //Sys.out.print car la console saute déjà des lignes par défaut.

        System.out.print("");
        System.out.print("____________________________________________________");
        System.out.print("|                                                   |");  
        System.out.print("| bindlist    :   List the key bindings             |");
        System.out.print("| clear       :   Clear the console                 |");
        System.out.print("| help | -h   :   Help contextual menu              |");
        System.out.print("| quit        :   Quit the game properly            |"); 
        System.out.print("|___________________________________________________|");
        System.out.print("");
    }
}