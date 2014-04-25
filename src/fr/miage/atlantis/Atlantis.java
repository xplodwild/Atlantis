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
package fr.miage.atlantis;

import fr.miage.atlantis.logic.GameLogic;

/**
 * Classe main
 * @author Atlantis team
 */
public class Atlantis {

    /**
     * Logique du jeu
     */
    private GameLogic mGameLogic;

    /**
     * Constructeur d'atlantis
     */
    public Atlantis() {
        mGameLogic = new Game3DLogic();
    }
    
    /**
     * Methode de demarrage du jeu
     */
    public void start() {
        mGameLogic.boot();
    }
    
    /**
     * Methode main executé
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Atlantis app = new Atlantis();
        app.start();
    }
}
