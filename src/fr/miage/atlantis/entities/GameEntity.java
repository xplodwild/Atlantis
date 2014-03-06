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

    /**
     * Nom de l'entité
     */
    private String mName;

    /**
     * Tile de l'entité
     */
    private GameTile mTile;


    /**
     * Constructeur de l'entité
     *
     * @param name Nom de l'entité
     * @param tile Tile ou se place l'entité
     */
    public GameEntity(final String name) {
        mName = name;
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

        tile.addEntity(this);
        mTile = tile;

        System.out.println("MOVE " + mName + " TO " + tile.getName());

        boolean somethingHappened = false;
        if (logic != null) {
            // On trigger les événements des autres entités présentes sur la tile
            for (GameEntity ent : tile.getEntities()) {
                if (ent != this) {
                    somethingHappened |= onEntityCross(logic, ent);
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
    }

    
    /**
     * Spawn une unité.
     *
     * @param logic logique de jeu spawnant une unité.
     */
    public void spawn(GameLogic logic) {
        throw new UnsupportedOperationException("Not implemented");
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
    
    
    public GameTile getTile() {
        return mTile;
    }
    //--------------------------------------------------------------------------   
}
