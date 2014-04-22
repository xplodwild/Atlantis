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
import com.jme3.scene.shape.Box;
import fr.miage.atlantis.graphics.Utils;

/**
 *
 */
public class SeaSerpentModel extends AnimatedModel {

    public final static String ANIMATION_IDLE = "Idle";
    public final static String ANIMATION_ATTACK_CELL = "Attack cell";
    public final static String ANIMATION_RISE = "rise";
    public final static String ANIMATION_SUCKED_DOWN = "Sucked Down";
    public final static String ANIMATION_SWIM_CYCLE = "swim cycle";
    public final static String ANIMATION_DIVE = "Dive";
    private final static String COLLISION_BONE_ATTACH = "root";

    public SeaSerpentModel(AssetManager am) {
        super(am, "Models/serpentA.mesh.xml", "Textures/seaserpent.png",
                "Textures/seaserpent_normal.png");

        getModel().setLocalTranslation(-2, -3, 24);

        // On définit la forme spéciale de collision, car sinon le picking est décalé lorsque
        // le personnage joue l'animation de nage
        Box box = new Box(6, 13, 6);
        setupCustomCollisionShape(Utils.generateInvisibleBox(am, box), COLLISION_BONE_ATTACH);
    }
}
