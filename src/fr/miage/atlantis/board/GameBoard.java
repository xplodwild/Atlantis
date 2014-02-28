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

import fr.miage.atlantis.utils.HexagonTable;

/**
 *
 */
public class GameBoard {

    private HexagonTable<GameTile> mBoard;
    
    public GameBoard() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean isTileAtWaterEdge(GameTile tile) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean hasTileAtLevel(int h) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean sinkTile(GameTile tile) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean canPlaceTileAt(int x, int y) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean placeTileAt(GameTile t) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public GameTile getTileAt(int x, int y) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
