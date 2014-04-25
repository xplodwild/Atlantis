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

/**
 * Modèle du Shark extend de la classe modèle animé
 */
public class SharkModel extends AnimatedModel {

    /**
     *
     */
    public final static String ANIMATION_RISE = "Rise";
    /**
     *
     */
    public final static String ANIMATION_DIVE = "Dive";
    /**
     *
     */
    public final static String ANIMATION_SUCKED_DOWN_WHIRPOOL = "sucked_down_whirpool";
    /**
     *
     */
    public final static String ANIMATION_SWIM_CYCLE = "swim_cycle";
    /**
     *
     */
    public final static String ANIMATION_ATTACK_SWIMMER = "attack_swimmer";

    /**
     * Constructeur du modèle Shark
     * @param am AssetManager qui permet d'accéder aux assets
     */
    public SharkModel(AssetManager am) {
        super(am, "Models/sharkA.mesh.xml", "Textures/shark.png", null);
        getModel().scale(0.9f);
        getModel().setLocalTranslation(0, -3.0f, 0);
    }
}
