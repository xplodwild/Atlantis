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

import fr.miage.atlantis.entities.GameEntity;
import java.util.List;

/**
 *
 */
public abstract class GameTile {

    private int mX;
    private int mY;
    private int mHeight;
    private List<GameEntity> mEntities;
    private GameBoard mBoard;
    
    GameTile(GameBoard board, int height) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public int getX() {
        return mX;
    }
    
    public int getY() {
        return mY;
    }
    
    public int getHeight() {
        return mHeight;
    }
    
    public GameBoard getBoard() {
        return mBoard;
    }
    
    public List<GameEntity> getEntities() {
        return mEntities;
    }
}
