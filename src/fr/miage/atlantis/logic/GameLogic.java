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

import fr.miage.atlantis.GameDice;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.GameEntity;

/**
 *
 */
public abstract class GameLogic implements GameTurnListener {
    
    private GameBoard mBoard;
    private GameDice mDice;
    private GameLog mLog;
    private GameTurn mCurrentTurn;
    private Player[] mPlayers;
    
    public GameLogic() {
        mBoard = new GameBoard();
        mDice = GameDice.createDefault();
        mLog = new GameLog();
    }
    
    public void boot() {
        // Afficher un menu pour lancer la partie
    }
    
    public void prepareGame(String[] players) {
        mPlayers = new Player[players.length];
        for (int i = 0; i < mPlayers.length; i++) {
            mPlayers[i] = new Player(players[i], i+1);
        }
    }
    
    public void startGame() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public void nextTurn() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public GameBoard getBoard() {
        return mBoard;
    }
    
    public GameTurn getCurrentTurn() {
        return mCurrentTurn;
    }
    
    public boolean isFinished() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public void onUnitMove(final GameEntity ent, final GameTile dest) {
        ent.moveToTile(this, dest);
    }
}
