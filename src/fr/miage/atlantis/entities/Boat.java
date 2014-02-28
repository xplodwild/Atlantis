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
import java.util.List;

/**
 *
 */
public class Boat extends GameEntity {
    
    private List<PlayerToken> mOnboard;
    
    public Boat(GameTile tile) {
        super("Boat", tile);
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public List<PlayerToken> getOnboardTokens() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void addPlayer(PlayerToken token) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean hasRoom() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    
}
