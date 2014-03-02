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

import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.PlayerToken;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Player {

    private String mName;
    private List<PlayerToken> mTokens;
    private List<GameTile> mActionTiles;
    private int mNumber;
    
    public Player(final String name, final int number) {
        mName = name;
        mTokens = new ArrayList<PlayerToken>();
        mActionTiles = new ArrayList<GameTile>();
        mNumber = number;
        
        // On génère les 10 tokens par joueur
        mTokens.add(new PlayerToken(null, this, 6));
        mTokens.add(new PlayerToken(null, this, 5));
        mTokens.add(new PlayerToken(null, this, 4));
        mTokens.add(new PlayerToken(null, this, 3));
        mTokens.add(new PlayerToken(null, this, 3));
        mTokens.add(new PlayerToken(null, this, 2));
        mTokens.add(new PlayerToken(null, this, 2));
        mTokens.add(new PlayerToken(null, this, 1));
        mTokens.add(new PlayerToken(null, this, 1));
        mTokens.add(new PlayerToken(null, this, 1));
    }
    
    public int getNumber() {
        return mNumber;
    }
    
    public String getName() {
        return mName;
    }
    
    public List<PlayerToken> getTokens() {
        return mTokens;
    }
    
    public List<GameTile> mActionTiles() {
        return mActionTiles;
    }
    
    public int getScore() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
