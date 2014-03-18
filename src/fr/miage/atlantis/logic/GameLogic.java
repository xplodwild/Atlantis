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
 * Classe représentant toute la partie logique du jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public abstract class GameLogic implements GameTurnListener {

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


    public static class EntityPickRequest {
        public static final int FLAG_PICK_PLAYER_ENTITIES   = (1 << 0);
        public static final int FLAG_PICK_SHARK             = (1 << 1);
        public static final int FLAG_PICK_WHALE             = (1 << 2);
        public static final int FLAG_PICK_SEASERPENT        = (1 << 3);
        public static final int FLAG_PICK_BOAT_WITH_ROOM    = (1 << 4);
        public static final int FLAG_PICK_BOAT_WITHOUT_ROOM = (1 << 5);

        /**
         * Restriction des entités pouvant être pickées
         * La valeur doit être une ou plusieurs de FLAG_PICK_**
         */
        public int pickingRestriction;

        /**
         * En cas de restriction d'entités appartenant au joueur,
         * la restriction s'appliquera au joueur passé ici
         */
        public Player player;
    }

    public static class TilePickRequest {
        /**
         * Si cette variable n'est pas nulle, le picking de tile ne fonctionnera que sur les tiles
         * adjacentes à celle-ci
         */
        public GameTile pickNearTile;

        /**
         * Si waterEdgeOnly vaut true, seulement les tiles au bord de l'eau seront sélectionnables
         */
        public boolean waterEdgeOnly;

        /**
         * Si requiredHeight est supérieur ou égal à zéro, seules les tiles au niveau spécifiées
         * pourront être pickées
         */
        public int requiredHeight = -1;
    }

    /**
     * Constructeur de GameLogic
     *
     */
    public GameLogic() {
        mBoard = new GameBoard();
        mDice = GameDice.createDefault();
        mLog = new GameLog();
    }

    /**
     * Initialise les joueurs du jeu
     *
     * @param players Tableau des pseudo des joueurs
     */
    public void prepareGame(String[] players) {
        // On créé les joueurs
        mPlayers = new Player[players.length];
        for (int i = 0; i < mPlayers.length; i++) {
            mPlayers[i] = new Player(players[i], i + 1);
        }
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

        //Fini si le tile Volcan est sorti , ou si tout les mToken sont sauvés.

        return false;
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

    /**
     * Indique à la logique du jeu qu'on a besoin de sélectionner une entité ou une tile
     * @param entRq Si non null, la requête permettant de filtrer les entités à picker
     * @param tileRq Si non null, la requête permettant de filtrer les tiles à picker
     */
    public abstract void requestPick(EntityPickRequest entRq, TilePickRequest tileRq);

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
    //--------------------------------------------------------------------------
}
