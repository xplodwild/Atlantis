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
 * Classe implementant l'animal Baleine
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class Whale extends Animal {

    /**
     * Nombre maximal de movements
     */
    public final static int MAX_MOVES = 3;


    /**
     * Constructeur de Whale
     *
     * @param tile Tile sur lequel on place le SeaSerpent
     */
    public Whale() {
        super("Whale", MAX_MOVES);
    }


    /**
     * Resultat d'un croisement entres entitées
     *
     * @param logic Logique de jeu à appliquer
     * @param ent Entité qui croise le whale
     */
    @Override
    public boolean onEntityCross(GameLogic logic, GameEntity boat) {    

        if (boat instanceof Boat) {
            Boat bt = (Boat) boat;
            // Les baleines retournent les bateau habités
            logic.onEntityAction(this, bt, GameEntity.ACTION_WHALE_NUKE);
        }
        return false;
    }
}
