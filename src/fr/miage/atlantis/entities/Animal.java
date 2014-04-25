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

/**
 * Classe Animal, reprensente les entités Animales que l'on place sur le Plateau
 * de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public abstract class Animal extends GameEntity {

    /**
     * Nombre maximum de movements par tour de l'animal sur le plateau
     */
    private int mMaxMoves;

    /**
     * Constructeur d'animaux
     *
     * @param name Nom de l'animal
     * @param tile Tile sur lequel est placé cet animal
     * @param maxMoves Nombre maximal de movement possible de cet animal
     */
    Animal(String name, int maxMoves, boolean appendUniqueID) {
        super(name, appendUniqueID);
        this.mMaxMoves = maxMoves;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Getter du nombre de mouvement max de l'entité Animal
     *
     * @return le maximum de mouvement de cette antité
     */
    public int getMaxMoves() {
        return mMaxMoves;
    }
    //--------------------------------------------------------------------------
}
