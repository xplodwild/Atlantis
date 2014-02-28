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

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.EntityMove;
import fr.miage.atlantis.entities.GameEntity;
import java.util.List;

/**
 *
 */
public class GameTurn implements GameRenderListener {

    private TileAction mTileAction;
    private List<TileAction> mRemoteTiles;
    private List<EntityMove> mMoves;
    private GameTile mSunkenTile;
    private int mDiceAction;
    private Player mPlayer;
    private GameLogic mController;
    
    public GameTurn(GameLogic controller, Player p) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void startTurn() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void moveEntity(GameEntity ent, GameTile dest) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public int getRemainingMoves() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean hasRolledDice() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public int rollDice() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean hasSunkLandTile() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void sinkLandTile(GameTile tile) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void useRemoteTile(TileAction action) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void useLocalTile(TileAction action) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onTurnStarted() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onPlayedTileAction() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onUnitMoveFinished() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onDiceRollFinished() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onSinkTileFinished() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onEntityActionFinished() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
