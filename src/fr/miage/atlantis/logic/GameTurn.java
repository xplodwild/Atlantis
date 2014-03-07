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
import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant un tour de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class GameTurn implements GameRenderListener {

    private final static boolean DBG_TRACE = true;

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
        mMoves = new ArrayList<EntityMove>();
    }

    private void log(String str) {
        if (DBG_TRACE) System.out.println(str);
    }

    /**
     * Demarre le début du tour de jeu du joueur courant
     */
    public void startTurn() {
        log("GameTurn: startTurn()");
        mController.onTurnStart(mPlayer);



        //////

        //Peu jouer une carte TileAction dans son pool de tiles

        //3 Déplacement de pions

        //Coule un Tile

        //Effectue l'action du tile ou le stocke

        //Roll the dice

        //Le cas echeant bouge une entity

        //Le cas echeant execute les actions entity necessaires

        //Met l'attribut mTurnIsOver a true pour boucler le tour

    }

    /**
     * Deplace une Entity sur le tile donnée
     *
     * @param ent Entity a deplacer
     * @param dest Tile destination
     */
    public void moveEntity(GameEntity ent, GameTile dest) {
        log("GameTurn: moveEntity");

        // On log le mouvement
        // xplod: Pourquoi stocker le numero du tour ici? Surtout qu'on l'a pas
        EntityMove move = new EntityMove(ent.getTile(), dest, ent, -1);
        mMoves.add(move);
        mRemainingMoves--;

        // On le transmet au controlleur en attendant la suite
        mController.onUnitMove(ent, dest);
    }

    public int rollDice() {
        mDiceRolled = true;
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean hasSunkLandTile() {
        return (mSunkenTile != null);
    }

    public void sinkLandTile(GameTile tile) {
        mSunkenTile = tile;
        mController.onSinkTile(tile);
    }

    public void useRemoteTile(TileAction action) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void useLocalTile(TileAction action) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onTurnStarted() {
        log("GameTurn: onTurnStarted()");
        // Le tour commence : on peut utiliser une tile de notre stock local
        // TODO

        // Sinon, on bouge nos entités. On laisse le joueur choisir que ses entités à lui.
        requestPlayerEntityPicking();
    }

    public void onPlayedTileAction() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onUnitMoveFinished() {
        if (mRemainingMoves > 0) {
            // On a encore des mouvements de ses unités possibles, alors on le fait.
            requestPlayerEntityPicking();
        } else if (mSunkenTile == null) {
            // Il faut sinker une tile!
            GameLogic.TilePickRequest request = new GameLogic.TilePickRequest();
            request.pickNearTile = null;
            int level = 1;
            while (!mController.getBoard().hasTileAtLevel(level)) {
                level++;
                if (level > 3) {
                    throw new IllegalStateException("No sinkable tile available!");
                }
            }
            request.requiredHeight = level;
            request.waterEdgeOnly = true;

            mController.requestTilePick(request);
        }
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

    /**
     * Demande à la logique de jeu de picker des entités appartenant au joueur (PlayerToken, ou
     * bateau ayant un pion du joueur en cours dessus).
     */
    private void requestPlayerEntityPicking() {
        GameLogic.EntityPickRequest request = new GameLogic.EntityPickRequest();
        request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_PLAYER_ENTITIES;
        request.player = mPlayer;
        mController.requestEntityPick(request);
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
