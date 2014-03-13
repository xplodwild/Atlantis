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
 *
 */
public class BoatModel extends AnimatedModel {
    
    public final static String ANIMATION_BOAT_IDLE = "boat_idle";
    public final static String ANIMATION_BOAT_SINK = "boat_sink";
    public final static String ANIMATION_BOAT_ROW = "boat_row";

    public BoatModel(AssetManager am) {
        super(am, "Models/boat.mesh.xml",
                "Textures/boat.png", "Textures/boat_normal.png");
        setLocalTranslation(0, -8.0f, 0);
    }

}
