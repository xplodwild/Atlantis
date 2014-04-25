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
import fr.miage.atlantis.logic.GameLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe mère qui gère les entitées de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class GameEntity {

    /**
     * Constantes de jeu
     */
    public final static int ACTION_SHARK_EAT = 0;
    public final static int ACTION_WHALE_NUKE = 1;
    public final static int ACTION_SEASERPENT_CRUSH = 2;
    public final static int ACTION_PLAYER_ESCAPE = 3;
    public final static int TYPE_NULL = 0;
    public final static int TYPE_PLAYERTOKEN = 1;
    public final static int TYPE_BOAT = 2;
    public final static int TYPE_SEASERPENT = 3;
    public final static int TYPE_SHARK = 4;
    public final static int TYPE_WHALE = 5;
    private static int ENTITY_UNIQUE_ID = 0;
    private static final Logger logger = Logger.getGlobal();
    /**
     * Nom de l'entité
     */
    private String mName;
    /**
     * Tile de l'entité
     */
    private GameTile mTile;
    /**
     * Indique si l'entité est morte
     */
    private boolean mIsDead;

    /**
     * Constructeur de l'entité
     *
     * @param name Nom de l'entité
     * @param tile Tile ou se place l'entité
     */
    public GameEntity(final String name) {
        this(name, true);
    }

    /**
     * Constructeur de l'entité
     *
     * @param name Nom de l'entité
     * @param appendUniqueID Si on ajoute ou non
     */
    public GameEntity(final String name, final boolean appendUniqueID) {
        if (appendUniqueID) {
            mName = name + "_" + Integer.toString(ENTITY_UNIQUE_ID);
        } else {
            mName = name;
        }
        mIsDead = false;

        ENTITY_UNIQUE_ID++;
    }

    /**
     * @return true si l'entité est morte (retirée du jeu)
     */
    public boolean isDead() {
        return mIsDead;
    }

    /**
     * Deplace cette entité sur le Tile tile, avec la logique de jeu logic
     *
     * @param logic Logique de jeu à adopter
     * @param tile Tile ou l'on deplace l'entité
     * @return true si une action s'est déroulée via onEntityCross
     */
    public boolean moveToTile(GameLogic logic, GameTile tile) {
        // On se déplace, dans un premier temps
        if (mTile != null) {
            mTile.removeEntity(this);
        }

        // On s'assure qu'on soit bien enregistré sur le board
        if (logic != null && logic.getBoard() != null) {
            logic.getBoard().putEntity(this);
        }

        tile.addEntity(this);
        mTile = tile;

        logger.log(Level.FINE, "MOVE " + mName + " TO " + tile.getName(), new Object[]{this, tile});

        boolean somethingHappened = false;
        if (logic != null) {
            // On trigger les événements des autres entités présentes sur la tile. On fait une copie,
            // les entities pouvant être supprimées pendant certains événements.
            List<GameEntity> entities = new ArrayList<GameEntity>(tile.getEntities());
            for (GameEntity ent : entities) {
                if (ent != this) {
                    somethingHappened |= this.onEntityCross(logic, ent);
                    somethingHappened |= ent.onEntityCross(logic, this);
                }
            }
        }

        return somethingHappened;
    }

    /**
     * Tue l'unité visée
     *
     * @param logic logique de jeu tuant l'unité
     */
    public void die(GameLogic logic) {
        // On supprime le perso du jeu
        logic.onUnitDie(this);
        mIsDead = true;
    }

    /**
     * Actions a effectuer lors d'un croisement de deux Entity this et ent
     *
     * @param logic Logique de jeu a adopter
     * @param ent entity qui croise this
     * @return
     */
    public boolean onEntityCross(GameLogic logic, GameEntity ent) {
        // Par défaut, on ne fait rien
        return false;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Retourne la tile sur laquelle est l'entité
     *
     * @return une GameTile
     */
    public GameTile getTile() {
        return mTile;
    }

    /**
     * Renvoie le nom de l'entité
     *
     * @return Un String, nom de l'entité
     */
    public String getName() {
        return mName;
    }
    //--------------------------------------------------------------------------
}
