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
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.EntityMove;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
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
    public final static boolean DBG_QUICKTEST = false;

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
    private int mRemainingDiceMoves;

    /**
     * Constructeur de GameTurn
     *
     * @param controller
     * @param p Joueur jouant le tour
     */
    public GameTurn(GameLogic controller, Player p) {
        mPlayer = p;
        mController = controller;
        mRemainingMoves = DBG_QUICKTEST ? 1 : 3;
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

    private void finishTurn() {
        log("GameTurn: finishTurn");
        mTurnIsOver = true;
        mController.nextTurn();
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

    public void moveDiceEntity(GameEntity ent, GameTile dest) {
        log("GameTurn: moveDiceEntity");

        // TODO: Faut-il logger ces mouvements aussi?
        mRemainingDiceMoves--;
        mController.onUnitMove(ent, dest);
    }

    public int rollDice() {
        log("GameTurn: rollDice");

        mDiceRolled = true;
        if (!DBG_QUICKTEST) {
            mDiceAction = mController.getDice().roll();
        } else {
            mDiceAction = GameDice.FACE_WHALE;
        }
        switch (mDiceAction) {
            case GameDice.FACE_SEASERPENT:
                mRemainingDiceMoves = SeaSerpent.MAX_MOVES;
                break;

            case GameDice.FACE_SHARK:
                mRemainingDiceMoves = Shark.MAX_MOVES;
                break;

            case GameDice.FACE_WHALE:
                mRemainingDiceMoves = Whale.MAX_MOVES;
                break;
        }

        mController.onDiceRoll(mDiceAction);

        return mDiceAction;
    }

    public int getRemainingDiceMoves() {
        return mRemainingDiceMoves;
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
        log("GameTurn: onUnitMoveFinished()");
        if (mRemainingMoves > 0) {
            // On a encore des mouvements de ses unités possibles, alors on le fait.
            log("==> Remaining moves: " + mRemainingMoves);
            requestPlayerEntityPicking();
        } else if (mSunkenTile == null) {
            log("==> Tile sinking required!");
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
            request.waterEdgeOnly = mController.getBoard().hasTileAtWaterEdge(level);

            mController.requestTilePick(request);
        } else if (mRemainingDiceMoves > 0) {
            // On a encore des mouvements de l'unité du dé possible
            log("==> Remaining dice moves: " + mRemainingDiceMoves);
            requestDiceEntityPicking();
        } else {
            // On a plus de mouvements d'unités, on a coulé la tile, et on a bougé les unités
            // avec le dé, on a donc fini le tour.
            finishTurn();
        }
    }

    public void onDiceRollFinished() {
        // On vérifie si le board a effectivement une entité du type demandé. Sinon, on passe
        // directement à la suite.
        Class entityType = null;
        switch (mDiceAction) {
            case GameDice.FACE_SEASERPENT:
                entityType = SeaSerpent.class;
                break;

            case GameDice.FACE_SHARK:
                entityType = Shark.class;
                break;

            case GameDice.FACE_WHALE:
                entityType = Whale.class;
                break;
        }

        if (mController.getBoard().hasEntityOfType(entityType)) {
            requestDiceEntityPicking();
        } else {
            // Pas d'entité du type du dé a bouger. Le dé étant la dernière étape d'un tour,
            // on a terminé.
            log("GameTurn: No entity of type " + entityType.toString() + " on the board. Finish turn.");
            finishTurn();
        }
    }

    public void onSinkTileFinished() {
        // Tile coulée et action lancée, on lance le dé
        rollDice();
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

    /**
     * Demande à la logique de jeu de picker l'entité qui a été obtenue via le dé
     */
    private void requestDiceEntityPicking() {
        GameLogic.EntityPickRequest request = new GameLogic.EntityPickRequest();

        switch (mDiceAction) {
            case GameDice.FACE_SEASERPENT:
                request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_SEASERPENT;
                break;

            case GameDice.FACE_SHARK:
                request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_SHARK;
                break;

            case GameDice.FACE_WHALE:
                request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_WHALE;
                break;

            default:
                throw new UnsupportedOperationException("Processing of face " + mDiceAction + " isn't supported yet");
        }

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
