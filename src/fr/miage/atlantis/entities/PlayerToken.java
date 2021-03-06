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
package fr.miage.atlantis.entities;

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.logic.GameLogic;

/**
 * Classe representant les pions du jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class PlayerToken extends GameEntity {

    /**
     *
     */
    public final static int STATE_UNDEFINED = -1;
    /**
     *
     */
    public final static int STATE_ON_LAND = 0;
    /**
     *
     */
    public final static int STATE_SWIMMING = 1;
    /**
     *
     */
    public final static int STATE_ON_BOAT = 2;
    /**
     *
     */
    public final static int STATE_SAFE = 3;
    /**
     * Joueur a qui appartient le pion
     */
    private Player mPlayer;
    /**
     * Etat du pion
     */
    private int mState;
    /**
     * Valeur du pion
     */
    private int mPoints;
    /**
     * Bateau (si applicable) sur lequel est ce joueur
     */
    private Boat mBoat;

    /**
     * Constructeur des pions
     *
     * @param p Joueur a qui appartient le pion
     * @param points Valeur du pion
     */
    public PlayerToken(Player p, int points) {
        this("PlayerToken", true, p, points);
    }

    /**
     * Constructeur pes pions
     *
     * @param name Nom du pion
     * @param p Joueur propriétaire du pion
     * @param points Valeur en points
     */
    public PlayerToken(String name, Player p, int points) {
        this(name, false, p, points);
    }

    /**
     * Constructeur des pions
     *
     * @param name
     * @param appendUniqueID
     * @param p
     * @param points
     */
    public PlayerToken(String name, boolean appendUniqueID, Player p, int points) {
        super(name, appendUniqueID);
        mState = STATE_UNDEFINED;
        mPoints = points;
        mPlayer = p;
    }

    /**
     * Déplace le pion vers une autre tile
     *
     * @param logic GameLogic du jeu
     * @param tile Destination
     * @return true si le mouvement s'est bien déroulé
     */
    @Override
    public boolean moveToTile(GameLogic logic, GameTile tile) {
        boolean result = super.moveToTile(logic, tile);

        if ((tile instanceof WaterTile) && mState == STATE_ON_LAND) {
            setState(PlayerToken.STATE_SWIMMING);
        }

        return result;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Retourne la constante state du pion
     *
     * @return un int corrspondant à une constante
     */
    public int getState() {
        return mState;
    }

    /**
     * Renvoie la valeur du pion
     *
     * @return La valeur du pion en points
     */
    public int getPoints() {
        return this.mPoints;
    }

    /**
     * Le propriétaire de ce pion
     *
     * @return Le player propriétaire de ce pion
     */
    public Player getPlayer() {
        return mPlayer;
    }

    /**
     * Renvoie le bateau sur lequel est le pion
     *
     * @return un bateau
     */
    public Boat getBoat() {
        return mBoat;
    }
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //SETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Définit l'état du pion
     *
     * @param state Le nouvel état du pion
     */
    public void setState(int state) {
        if (mState == STATE_ON_BOAT && state != STATE_ON_BOAT) {
            // On n'est plus sur un bateau, donc on l'enlève pour éviter toute confusion
            mBoat = null;
        }
        mState = state;
    }

    /**
     * Définit le bateau sur lequel est le pion
     *
     * @param b un boat sur lequel mettre le pion
     */
    public void setBoat(Boat b) {
        mBoat = b;
    }
    //--------------------------------------------------------------------------
}
