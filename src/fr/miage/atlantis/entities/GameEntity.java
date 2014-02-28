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
public class GameEntity {
    
    public final static int ACTION_SHARK_EAT = 0;
    public final static int ACTION_WHALE_NUKE = 1;
    public final static int ACTION_SEASERPENT_CRUSH = 2;
    public final static int ACTION_PLAYER_ESCAPE = 3;
    
    private String mName;
    private GameTile mTile;
    
    public GameEntity(final String name, GameTile tile) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void moveToTile(GameLogic logic, GameTile tile) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void die(GameLogic logic) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void spawn(GameLogic logic) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
