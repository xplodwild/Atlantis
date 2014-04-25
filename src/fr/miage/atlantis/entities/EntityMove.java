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

import fr.miage.atlantis.board.GameTile;

/**
 * Classe representant les movements d'entitée sur le plateau de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class EntityMove {

    /**
     * Tile source
     */
    private GameTile mSource;
    /**
     * Tile destination
     */
    private GameTile mDestination;
    /**
     * Entité qui à bougé
     */
    private GameEntity mEntity;
    /**
     * Numero du tour ou le mouvement à eu lieu
     */
    private int mTurnNumber;

    /**
     * Constructeur des EntityMove
     *
     * @param src Source Tile
     * @param dest Destination Tile
     * @param ent Entity to move
     */
    public EntityMove(GameTile src, GameTile dest, GameEntity ent, int turn) {
        this.mSource = src;
        this.mDestination = dest;
        this.mEntity = ent;
        this.mTurnNumber = turn;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Retourne la souce du mouvement
     *
     * @return la tile source du Move
     */
    public GameTile getSource() {
        return mSource;
    }

    /**
     * Récupère la destination
     *
     * @return la tile destination
     */
    public GameTile getDestination() {
        return mDestination;
    }

    /**
     * Récupère l'entity qu'on essaie de déplacer
     *
     * @return l'entity qu'on tente de move
     */
    public GameEntity getEntity() {
        return mEntity;
    }

    /**
     * Recupère le numéro du tour
     *
     * @return le numéro du tour en cours
     */
    public int getTurnNumber() {
        return this.mTurnNumber;
    }
    //--------------------------------------------------------------------------
}
