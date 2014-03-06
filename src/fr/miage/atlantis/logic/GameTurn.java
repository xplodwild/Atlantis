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
 * Classe representant un tour de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class GameTurn implements GameRenderListener {

    private TileAction mTileAction;
    private List<TileAction> mRemoteTiles;
    private List<EntityMove> mMoves;
    private GameTile mSunkenTile;
    private int mDiceAction;
    private Player mPlayer;
    private GameLogic mController;
    private boolean mDiceRolled;
    private boolean mTurnIsOver;
    private int mRemainingMoves;

    /**
     * Constructeur de GameTurn
     *
     * @param controller
     * @param p Joueur jouant le tour
     */
    public GameTurn(GameLogic controller, Player p) {
        mPlayer = p;
        mController = controller;
        mRemainingMoves = 3;
        mDiceRolled = false;
        mSunkenTile = null;
    }

    /**
     * Demarre le début du tour de jeu du joueur courant
     */
    public void startTurn() {

        //Peu jouer une carte TileAction dans son pool de tiles

        //3 Déplacement de pions

        //Coule un Tile

        //Effectue l'action du tile ou le stocke

        //Roll the dice

        //Le cas echeant bouge une entity

        //Le cas echeant execute les actions entity necessaires

        //Met l'attribut mTurnIsOver a true pour boucler le tour

        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Deplace une Entity sur le tile donnée
     *
     * @param ent Entity a deplacer
     * @param dest Tile destination
     */
    public void moveEntity(GameEntity ent, GameTile dest) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int rollDice() {
        mDiceRolled = true;
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean hasSunkLandTile() {
        boolean sunk = false;
        if (mSunkenTile != null) {
            sunk = true;
        }
        return sunk;
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

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    public Player getPlayer() {
        return mPlayer;
    }

    public int getRemainingMoves() {
        return mRemainingMoves;
    }

    public boolean getEndOfTurn() {
        return mTurnIsOver;
    }

    public boolean hasRolledDice() {
        return mDiceRolled;
    }
    //--------------------------------------------------------------------------
}
