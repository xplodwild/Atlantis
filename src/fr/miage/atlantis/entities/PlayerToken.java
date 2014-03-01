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

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;

/**
 *
 */
public class PlayerToken extends GameEntity {

    public final static int STATE_ON_LAND  = 0;
    public final static int STATE_SWIMMING = 1;
    public final static int STATE_ON_BOAT  = 2;
    public final static int STATE_SAFE     = 3;
    
    private Player mPlayer;
    private int mState;
    private int mPoints;
    
    public PlayerToken(GameTile tile, Player p, int points) {
        super("PlayerToken", tile);
        mState = STATE_ON_LAND;
        mPoints = points;
        mPlayer = p;
    }
    
    public int getState() {
        return mState;
    }
    
    public void setState(int state) {
        mState = state;
    }
    
}
