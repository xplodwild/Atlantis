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
package fr.miage.atlantis.graphics.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;

/**
 * Modèle du dé extends de la classe de modèle statique
 */
public class DiceModel extends StaticModel {

    /**
     * Constructeur du modèle du dé
     * 
     * @param am AssetManager qui permet d'accéder aux assets
     */
    public DiceModel(AssetManager am) {
        super(am, "Models/cube.mesh.xml", "Textures/dice.png", null);
        getMaterial().setFloat("Shininess", 1.0f);
        getMaterial().setColor("Specular", ColorRGBA.White);
    }
}
