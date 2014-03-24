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
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.EntityMove;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe representant un tour de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class GameTurn implements GameRenderListener {

    public static boolean DBG_QUICKTEST = false;

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
    private PlayerToken mTokenToPlace;

    /**
     * Instance du logger Java
     */
    private static final Logger logger = Logger.getGlobal();




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

    public TileAction getTileAction() {
        return mTileAction;
    }


    /**
     * Demarre le début du tour de jeu du joueur courant
     */
    public void startTurn() {
        logger.log(Level.FINE, "GameTurn: startTurn()", new Object[]{});
        mController.onTurnStart(mPlayer);
    }

    private void finishTurn() {
        logger.log(Level.FINE, "GameTurn: finishTurn()", new Object[]{});
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
        logger.log(Level.FINE, "GameTurn: moveEntity (on tile)", new Object[]{});

        // On log le mouvement
        // xplod: Pourquoi stocker le numero du tour ici? Surtout qu'on l'a pas
        EntityMove move = new EntityMove(ent.getTile(), dest, ent, -1);
        mMoves.add(move);
        mRemainingMoves--;

        // Si on est un joueur et qu'on a pické une tile, on s'enlève du bateau (si on était
        // dessus avant).
        if (ent instanceof PlayerToken) {
            PlayerToken pt = (PlayerToken) ent;
            if (pt.getState() == PlayerToken.STATE_ON_BOAT) {
                Boat b = pt.getBoat();
                b.removePlayer(pt);
                if (dest instanceof WaterTile) {
                    pt.setState(PlayerToken.STATE_SWIMMING);
                } else {
                    pt.setState(PlayerToken.STATE_ON_LAND);
                }
                mController.onPlayerDismountBoat(pt, b);
            }
        }

        // On le transmet au controlleur en attendant la suite
        mController.onUnitMove(ent, dest);
    }

    /**
     * Déplace une entité sur un bateau donné
     * @param ent L'entité qui se déplace
     * @param dest Bateau ciblé
     */
    public void moveEntity(GameEntity ent, Boat dest) {
        logger.log(Level.FINE, "GameTurn: moveEntity (on boat)", new Object[]{});

        // On log le mouvement
        // xplod: Pourquoi stocker le numero du tour ici? Surtout qu'on l'a pas
        EntityMove move = new EntityMove(ent.getTile(), dest.getTile(), ent, -1);
        mMoves.add(move);
        mRemainingMoves--;

        // On le transmet au controlleur en attendant la suite
        mController.onUnitMove(ent, dest.getTile());
    }

    public void moveDiceEntity(GameEntity ent, GameTile dest) {
        logger.log(Level.FINE, "GameTurn: moveDiceEntity ", new Object[]{});

        // TODO: Faut-il logger ces mouvements aussi?
        mRemainingDiceMoves--;
        mController.onUnitMove(ent, dest);
    }

    public void tileActionTeleport(GameEntity ent, GameTile dest) {
        logger.log(Level.FINE, "GameTile: tileActionTeleport");
        mController.onUnitMove(ent, dest);
    }

    public int rollDice() {
        logger.log(Level.FINE, "GameTurn: rollDice ", new Object[]{});

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

    public PlayerToken getTokenToPlace() {
        return mTokenToPlace;
    }

    public void sinkLandTile(GameTile tile) {
        mSunkenTile = tile;
        mController.onSinkTile(tile);
    }

    public void putInitialToken(PlayerToken pt, GameTile tile) {
        pt.moveToTile(null, tile);
        pt.setState(PlayerToken.STATE_ON_LAND);
        mController.onInitialTokenPut(pt);
    }

    public void putInitialBoat(GameTile tile) {
        Boat b = new Boat();
        b.moveToTile(null, tile);
        mController.onInitialBoatPut(b);
    }

    private void fetchTokenToPlace() {
        List<PlayerToken> tokens = mPlayer.getTokens();
        mTokenToPlace = null;

        for (PlayerToken pt : tokens) {
            if (pt.getState() == PlayerToken.STATE_UNDEFINED && pt.getTile() == null) {
                // On a un token non placé, on demande pour le placer
                GameLogic.TilePickRequest request = new GameLogic.TilePickRequest();
                request.landTilesOnly = true;
                request.noEntitiesOnTile = true;

                mTokenToPlace = pt;
                mController.requestPick(null, request);
                break;
            }
        }
    }

    public void useRemoteTile(TileAction action) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void useLocalTile(TileAction action) {
        // On ne peut utiliser qu'une seule tile d'action non immédiate par tour.
        if (mTileAction != null) {
            throw new IllegalStateException("GameTurn: useLocalTile called but we already played a tile action!");
        }

        mTileAction = action;

        // La requête est déléguée à la tile elle-même
        action.use(null, mController);
    }

    public void onTurnStarted() {
        logger.log(Level.FINE, "GameTurn: onTurnStarted() ", new Object[]{});
        // On vérifie d'abord si il nous reste des tokens player a placer
        fetchTokenToPlace();

        if (mTokenToPlace == null) {
            // On vérifie qu'il ne nous reste pas de bateau à placer
            if (mController.getRemainingInitialBoats() > 0) {
                // On a un bateau à placer, on pick une tile d'eau
                GameLogic.TilePickRequest request = new GameLogic.TilePickRequest();
                request.landTilesOnly = false;
                request.noEntitiesOnTile = true;
                request.requiredHeight = 0;

                mController.requestPick(null, request);
            } else {
                // Le tour (normal) du joueur commence. Il peut bouger ses entités comme il le
                // souhaite, ou utiliser une de ses tiles d'action. Les tiles d'actions sont
                // déclenchées via le GUI à partir du moment où aucune entité n'est bougée.
                requestPlayerMovePicking();
            }
        }
    }

    public void onInitialTokenPutDone() {
        // Un pion joueur a été placé. On finit le tour, c'est au suivant même si on a tout placé.
        finishTurn();
    }

    @Override
    public void onInitialBoatPutDone() {
        // Un pion bateau a été placé. On finit le tour, c'est au suivant même si on a tout placé.
        finishTurn();
    }

    public void onPlayedTileAction() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onUnitMoveFinished() {
        logger.log(Level.FINE, "GameTurn: onUnitMoveFinished() ", new Object[]{});

        if (mTileAction != null && !mTileAction.hasBeenUsed()) {
            // On a une tile action pas utilisée, et on a fait un mouvement.
            if (mTileAction.getAction() == TileAction.ACTION_MOVE_ANIMAL) {
                // Téléportation d'animale faite, on est good, on continue le tour
                mTileAction.setUsed();
                requestPlayerMovePicking();
            }
        } else if (mRemainingMoves > 0) {
            // On a encore des mouvements de ses unités possibles, alors on le fait.
            logger.log(Level.FINE, "GameTurn: ==> Remaining moves: " + mRemainingMoves, new Object[]{});
            requestPlayerMovePicking();
        } else if (mSunkenTile == null) {
            logger.log(Level.FINE, "GameTurn: ==> Tile sinking required! ", new Object[]{});

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

            mController.requestPick(null, request);
        } else if (mRemainingDiceMoves > 0) {
            // On a encore des mouvements de l'unité du dé possible
            logger.log(Level.FINE, "GameTurn: ==> Remaining dice moves: " + mRemainingDiceMoves, new Object[]{});
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
            logger.log(Level.FINE, "GameTurn: No entity of type " + entityType.toString() + " on the board. Finish turn.", new Object[]{});
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
    private void requestPlayerMovePicking() {
        GameLogic.EntityPickRequest request = new GameLogic.EntityPickRequest();
        request.pickingRestriction =
                (GameLogic.EntityPickRequest.FLAG_PICK_PLAYER_ENTITIES |
                GameLogic.EntityPickRequest.FLAG_PICK_BOAT_WITH_ROOM |
                GameLogic.EntityPickRequest.FLAG_PICK_BOAT_WITHOUT_ROOM);

        request.player = mPlayer;
        mController.requestPick(request, null);
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

        mController.requestPick(request, null);
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
