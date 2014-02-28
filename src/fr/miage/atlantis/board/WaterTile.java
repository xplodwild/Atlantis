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

package fr.miage.atlantis.board;

/**
 * Tile de type mer
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 28/02/2014 * 
 */
public class WaterTile extends GameTile{

    /**
     * Constructeur de classe
     * 
     * @param board Plateau de jeu auquel appartient le tiles de type mer
     */
    public WaterTile(GameBoard board, int x, int y) { 
        super(board, x, y, 0);
    }
}