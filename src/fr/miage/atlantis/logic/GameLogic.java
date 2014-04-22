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
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant toute la partie logique du jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public abstract class GameLogic implements GameTurnListener {

    protected static final boolean DBG_AUTOPREPARE = false;
    /**
     * Plateau du jeu
     */
    private GameBoard mBoard;
    /**
     * Dés du jeu
     */
    private GameDice mDice;
    /**
     * Log des tours de jeu
     */
    private GameLog mLog;
    /**
     * Tour de jeu actuel
     */
    private GameTurn mCurrentTurn;
    /**
     * Tableau des joueurs
     */
    private Player[] mPlayers;
    /**
     * Nombre de bateaux placés lors du début d'une partie
     */
    private int mBoatsPlaced;
    /**
     * Indique si le volcan a été tiré
     */
    private boolean mVolcanized;

    public static class EntityPickRequest {

        public static final int FLAG_PICK_PLAYER_ENTITIES = (1 << 0);
        public static final int FLAG_PICK_SHARK = (1 << 1);
        public static final int FLAG_PICK_WHALE = (1 << 2);
        public static final int FLAG_PICK_SEASERPENT = (1 << 3);
        public static final int FLAG_PICK_BOAT_WITH_ROOM = (1 << 4);
        public static final int FLAG_PICK_BOAT_WITHOUT_ROOM = (1 << 5);
        public static final int FLAG_PICK_SWIMMER = (1 << 6);
        /**
         * Restriction des entités pouvant être pickées La valeur doit être une
         * ou plusieurs de FLAG_PICK_**
         */
        public int pickingRestriction;
        /**
         * En cas de restriction d'entités appartenant au joueur, la restriction
         * s'appliquera au joueur passé ici
         */
        public Player player;
        /**
         * Permet de sélectionner uniquement les entités étant dans les tiles
         * autour de la tile indiquée.
         */
        public GameTile pickNearTile;
        /**
         * Permet de sélectionner uniquement les entités étant sur la tile
         * indiquée.
         */
        public GameTile pickOnTile;
        /**
         * Liste d'entités qui seront ignorées par le picking
         */
        public List<GameEntity> avoidEntity = new ArrayList<GameEntity>();

        @Override
        public String toString() {
            return "EntityPickRequest: restriction flags=" + pickingRestriction + "; player=" + player;
        }
    }

    public static class TilePickRequest {

        /**
         * Si cette variable n'est pas nulle, le picking de tile ne fonctionnera
         * que sur les tiles adjacentes à celle-ci
         */
        public GameTile pickNearTile;
        /**
         * Si waterEdgeOnly vaut true, seulement les tiles au bord de l'eau
         * seront sélectionnables
         */
        public boolean waterEdgeOnly;
        /**
         * Si landEdgeOnly vaut true, seulement les tiles au bord d'une tile de
         * terre seront sélectionnables
         */
        public boolean landEdgeOnly;
        /**
         * Si landTilesOnly vaut true, seulement les tiles de terre seront
         * sélectionnables
         */
        public boolean landTilesOnly;
        /**
         * Si noEntitiesOnTile vaut true, seulement les tiles n'ayant pas
         * d'entités dessus seront sélectionnables
         */
        public boolean noEntitiesOnTile;
        /**
         * Si noBoatOnTile vaut true, seulement les tiles n'ayant pas de bateau
         * dessus seront sélectionnables
         */
        public boolean noBoatOnTile;
        /**
         * Si requiredHeight est supérieur ou égal à zéro, seules les tiles au
         * niveau spécifiées pourront être pickées
         */
        public int requiredHeight = -1;

        @Override
        public String toString() {
            return "TilePickRequest: pickNearTile=" + pickNearTile + "; waterEdgeOnly=" + waterEdgeOnly
                    + "; requiredHeight=" + requiredHeight;
        }
    }

    /**
     * Constructeur de GameLogic
     *
     */
    public GameLogic() {
        mDice = GameDice.createDefault();
        mLog = new GameLog();
        mBoatsPlaced = 0;
    }

    /**
     * Initialise les joueurs du jeu
     *
     * @param players Tableau des pseudo des joueurs
     * @param prepareBoard Indique si il faut générer un board ou non
     */
    public void prepareGame(String[] players, boolean prepareBoard) {
        // Création du board
        mBoard = new GameBoard(prepareBoard);
        
        // On créé les joueurs
        mPlayers = new Player[players.length];
        for (int i = 0; i < mPlayers.length; i++) {
            mPlayers[i] = new Player(players[i], i + 1, prepareBoard);
        }

        if (DBG_AUTOPREPARE) {
            // Préparation automatique du board
            mBoatsPlaced = players.length * 2;
        } else {
            // Aucun bateau initialement placé
            mBoatsPlaced = 0;
        }

        mVolcanized = false;
    }
    
    /**
     * Restaure un GameTurn (sauvegardé par exemple)
     * @param turn 
     */
    public void restoreTurn(GameTurn turn) {
        mCurrentTurn = turn;
        mCurrentTurn.startTurn();
    }
    
    public void serializeEssentialData(DataOutputStream data) throws IOException {
        data.writeInt(mBoatsPlaced);
        data.writeBoolean(mVolcanized);
    }
    
    public void deserializeData(DataInputStream data) throws IOException {
        mBoatsPlaced = data.readInt();
        mVolcanized = data.readBoolean();
    }

    /**
     * Initialise le premier tour de jeu
     *
     */
    public void startGame() {
        Player p = mPlayers[0];
        mCurrentTurn = new GameTurn(this, p);
        mCurrentTurn.startTurn();
    }

    /**
     * Stocke le tour présent, génère le suivant et le démarre via start()
     *
     */
    public void nextTurn() {
        mLog.logTurn(mCurrentTurn);
        Player p = mCurrentTurn.getPlayer();


        mCurrentTurn = new GameTurn(this, this.nextPlayer(p));

        // Lance le nouveau tour
        mCurrentTurn.startTurn();
    }

    /**
     * Retourne le joueur suivant
     *
     * @param p Joueur Courant
     * @return Joueur suivant
     */
    public Player nextPlayer(Player p) {
        Player next = null;
        for (int i = 0; i < mPlayers.length; i++) {
            if (p.equals(mPlayers[i])) {
                if (i + 1 == mPlayers.length) {
                    next = mPlayers[0];
                } else {
                    next = mPlayers[i + 1];
                }
            }
        }
        return next;
    }

    /**
     * Retourne true si le jeu est bien terminé
     *
     * @return True si le jeu est fini, false sinon
     */
    public boolean isFinished() {
        if (mVolcanized) {
            // On a pické le volcan, on a terminé
            return true;
        } else {
            // On teste tous les pions: La partie est finie seulement si tous les pions sont soit
            // safe, soit morts.
            for (Player p : mPlayers) {
                List<PlayerToken> tokens = p.getTokens();
                for (PlayerToken token : tokens) {
                    // Si le pionn'est pas mort, ou si le pion n'est pas safe, on a pas fini
                    if (!token.isDead() || token.getState() != PlayerToken.STATE_SAFE) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Retourne le nombre de bateaux initiaux RESTANT à placer
     */
    public int getRemainingInitialBoats() {
        // Chaque joueur a deux bateaux à placer
        return mPlayers.length * 2 - mBoatsPlaced;
    }

    @Override
    public void onInitialBoatPut(Boat b) {
        mBoatsPlaced++;
    }

    /**
     * Actions a effectuer lors du deplacement de ent sur dest.
     *
     * @param ent Entity a deplacer
     * @param dest Tile de destination
     */
    @Override
    public void onUnitMove(final GameEntity ent, final GameTile dest) {
        ent.moveToTile(this, dest);
    }

    /**
     * Actions lors de la tile volcan
     *
     * @param tile
     */
    public void onTileVolcano() {
        mVolcanized = true;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    public GameBoard getBoard() {
        return mBoard;
    }

    public GameTurn getCurrentTurn() {
        return mCurrentTurn;
    }

    public Player[] getPlayers() {
        return mPlayers;
    }

    public GameDice getDice() {
        return mDice;
    }
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //METHODES ABSTRAITES                                                      |
    //--------------------------------------------------------------------------
    /**
     * Methode permettant de lancer une partie
     */
    public abstract void boot();

    public abstract GameEntity getLastPickedEntity();

    /**
     * Indique à la logique du jeu qu'on a besoin de sélectionner une entité ou
     * une tile
     *
     * @param entRq Si non null, la requête permettant de filtrer les entités à
     * picker
     * @param tileRq Si non null, la requête permettant de filtrer les tiles à
     * picker
     */
    public abstract void requestPick(EntityPickRequest entRq, TilePickRequest tileRq);

    /**
     * Annule la requête de picking en cours
     */
    public abstract void cancelPick();

    /**
     * Signale au moteur de logique qu'on a pické une entité
     *
     * @param ent L'entité pickée
     */
    public abstract void onEntityPicked(GameEntity ent);

    /**
     * Signale au moteur de logique qu'on a pické une tile
     *
     * @param tile La tile pickée
     */
    public abstract void onTilePicked(GameTile tile);

    /**
     * Signale au moteur de logique qu'on a pické une tile tourbillon
     *
     * @param tile Le tourbillon pické
     */
    public abstract void onTileWhirl(final GameTile tile);

    public abstract void onHitSpace();
    //--------------------------------------------------------------------------
}
