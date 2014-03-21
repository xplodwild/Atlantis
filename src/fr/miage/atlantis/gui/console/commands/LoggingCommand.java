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
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * BindList command of the console
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class LoggingCommand implements ConsoleCommands.ConsoleCommand {

    /**
     * Methode executée lors de l'appel de la commande
     *
     * @param args arguments passés à la commande
     */
    @Override
    public void execute(final String[] args) {
        String s1 = args[1];
        int nb1 = -99;

        if ("-all".equals(s1)) {
            nb1 = 1;
        }
        if ("-warning".equals(s1)) {
            nb1 = 2;
        }
        if ("-error".equals(s1)) {
            nb1 = 3;
        }
        if ("-off".equals(s1)) {
            nb1 = 4;
        }
        if ("-h".equals(s1)) {
            nb1 = 5;
        }


        switch (nb1) {

            case 1:

               
                
                Logger.getGlobal().setLevel(Level.ALL);
                
                Logger.getGlobal().info("This is a info-level message");
                Logger.getGlobal().config("This is a config-level message");
                Logger.getGlobal().fine("This is a fine-level message");
                Logger.getGlobal().finer("This is a finer-level message");
                Logger.getGlobal().finest("This is a finest-level message");
                
                Logger.getGlobal().finest(Logger.getGlobal().getLevel().toString());

                break;

            case 2:
                
                Logger.getGlobal().setLevel(Level.WARNING);
                break;

            case 3:
                Logger.getGlobal().setLevel(Level.SEVERE);
                break;

            case 4:
                Logger.getGlobal().setLevel(Level.OFF);
                break;

            case 5:
                System.out.println("");
                System.out.print("Les arguments suivants sont supportes :");
                System.out.print("-all : log tout les messages");
                System.out.print("-warning : log les avertissements et les erreurs");
                System.out.print("-error : log uniquement les erreurs");
                System.out.print("-off : arrete de logger les messages");
                System.out.println("");
                break;

            default:
                System.out.println("");
                System.out.print("L'argument donne n'est pas supporte.");
                System.out.print("Attendu :debug -all | -warning | -error | -off");
                System.out.print("Recu : " + s1);
                System.out.println("");
                break;
        }

    }
}
