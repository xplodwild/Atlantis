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

    public final static int STATE_ON_LAND  = 0;
    public final static int STATE_SWIMMING = 1;
    public final static int STATE_ON_BOAT  = 2;
    public final static int STATE_SAFE     = 3;

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
     * Constructeur des pions
     *
     * @param tile Tile sur lequel le pion est placé
     * @param p Joueur a qui appartient le pion
     * @param points Valeur du pion
     */
    public PlayerToken(Player p, int points) {
        super("PlayerToken");
        mState = STATE_ON_LAND;
        mPoints = points;
        mPlayer = p;
    }

    
    @Override
    public boolean moveToTile(GameLogic logic, GameTile tile) {
        boolean result = super.moveToTile(logic, tile);

        if ((tile instanceof WaterTile) && mState == STATE_ON_LAND) {
            // On était sur terre, et on est passé dans l'eau sans bateau (le cas échéant,
            // moveToTile va trigger le onEntityCross de Boat, qui va placer ce PlayerToken
            // en état ON_BOAT).
            setState(PlayerToken.STATE_SWIMMING);
        }

        return result;
    }


    

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    public int getState() {
        return mState;
    }

    public int getPoints() {
        return this.mPoints;
    }

    public Player getPlayer() {
        return mPlayer;
    }
    //--------------------------------------------------------------------------




    //--------------------------------------------------------------------------
    //SETTERS                                                                  |
    //--------------------------------------------------------------------------


    public void setState(int state) {
        mState = state;
    }
    //--------------------------------------------------------------------------
}
