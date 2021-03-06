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
 * Classe implementant l'animal requin
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class Shark extends Animal {

    /**
     * Nombre maximal de movements
     */
    public final static int MAX_MOVES = 2;

    /**
     * Constructeur de Requin
     *
     */
    public Shark() {
        this("Shark", true);
    }

    /**
     * Constructeur de Requin
     *
     * @param name nom du bestiau
     */
    public Shark(final String name) {
        this(name, false);
    }

    /**
     * Constructeur de Requin
     *
     * @param name nom du bestiau
     * @param appendUniqueID true si on souhaite ajouter un ID unique
     */
    public Shark(final String name, boolean appendUniqueID) {
        super(name, MAX_MOVES, appendUniqueID);
    }

    /**
     * Resultat d'un croisement entres entitées
     *
     * @param logic Logique de jeu à appliquer
     * @param ent Entité qui croise le requin
     */
    @Override
    public boolean onEntityCross(GameLogic logic, GameEntity ent) {
        if (ent instanceof PlayerToken) {
            PlayerToken pt = (PlayerToken) ent;

            if (pt.getState() != PlayerToken.STATE_ON_BOAT) {
                // Les sharks mangent les petits humains n'étant pas sur un bateau
                logic.onCancellableEntityAction(this, ent, GameEntity.ACTION_SHARK_EAT);
                return true;
            }
        }

        return false;
    }
}
