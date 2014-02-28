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

import fr.miage.atlantis.logic.GameLogic;

/**
 *
 */
public class TileAction {

    public final static int NONE = -1;
    public final static int ANIMAL_SHARK = 0;
    public final static int ANIMAL_SEASERPENT = 1;
    public final static int ANIMAL_WHALE = 2;
    
    public final static int ACTION_MOVE_ANIMAL = 0;
    public final static int ACTION_CANCEL_ANIMAL = 1;
    public final static int ACTION_SPAWN_ENTITY = 2;
    public final static int ACTION_BONUS_SWIM = 3;
    public final static int ACTION_BONUS_BOAT = 4;
    
    private boolean mIsImmediate;
    private boolean mIsTriggerable;
    private int mAction;
    private int mAnimal;
    
    private TileAction(int action, int animal, boolean isImmediate,
            boolean isTriggerable) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public final static class Factory {
        public static TileAction createMoveAnimal(int animal) {
            throw new UnsupportedOperationException("Not implemented");
        }
        
        public static TileAction createCancelAnimal(int animal) {
            throw new UnsupportedOperationException("Not implemented");
        }
    }
    
    public void use(GameTile tile, GameLogic logic) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    
}
