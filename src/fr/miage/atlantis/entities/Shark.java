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

package fr.miage.atlantis.entities;

import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.logic.GameLogic;

/**
 *
 */
public class Shark extends Animal {

    private final static int MAX_MOVES = 2;

    public Shark(GameTile tile) {
        super("Shark", tile, MAX_MOVES);
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public void onEntityCross(GameLogic logic, GameEntity ent) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
}
