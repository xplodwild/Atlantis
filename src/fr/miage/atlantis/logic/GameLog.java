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
package fr.miage.atlantis.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe stockant les tours de jeu d'une partie
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class GameLog {

    /**
     * Liste des tours de jeu
     */
    private List<GameTurn> mTurns;

    /**
     * Constructeur de GameLog
     *
     */
    public GameLog() {
        mTurns = new ArrayList<GameTurn>();
    }

    /**
     * Log un nouveau tour de jeu
     *
     * @param i Tour de jeu a logger
     */
    public void logTurn(GameTurn i) {
        this.mTurns.add(i);
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Retourne le tour de jeu numero i
     *
     * @param i Numero de tour a retourner
     * @return GameTurn correspondant
     */
    public GameTurn getTurn(int i) {
        return mTurns.get(i);
    }
}
