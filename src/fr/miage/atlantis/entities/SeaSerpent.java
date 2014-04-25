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

import fr.miage.atlantis.logic.GameLogic;

/**
 * Classe implementant l'animal Serpent de mer
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class SeaSerpent extends Animal {

    /**
     * Nombre maximal de movements
     */
    public final static int MAX_MOVES = 1;

    /**
     * Constructeur de SeaSerpent
     *
     * @param tile Tile sur lequel on place le SeaSerpent
     */
    public SeaSerpent() {
        this("Serpent de mer", true);
    }

    /**
     * Constructeur de SeaSerpent
     *
     * @param name Nom du bestiau
     */
    public SeaSerpent(String name) {
        this(name, false);
    }

    /**
     * constructeur de SeaSerpent
     *
     * @param name nom du bestiau
     * @param appendUniqueID true si on veut un ID unique pour cette classe
     */
    public SeaSerpent(String name, boolean appendUniqueID) {
        super(name, MAX_MOVES, appendUniqueID);
    }

    /**
     * Resultat d'un croisement entres entitées
     *
     * @param logic Logique de jeu à appliquer
     * @param ent Entité qui croise le seaserpent
     */
    @Override
    public boolean onEntityCross(GameLogic logic, GameEntity ent) {
        if (ent instanceof PlayerToken) {
            // Les krakens mangent les petits bonhommes
            logic.onEntityAction(this, ent, GameEntity.ACTION_SEASERPENT_CRUSH);
            return true;
        } else if (ent instanceof Boat) {
            // Les krakens nukent les bateaux aussi
            logic.onEntityAction(this, ent, GameEntity.ACTION_SEASERPENT_CRUSH);
            return true;
        }

        return false;
    }
}
