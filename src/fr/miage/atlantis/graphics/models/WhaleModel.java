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
public class WhaleModel extends AnimatedModel {

    public final static String ANIMATION_IDLE = "Idle";
    public final static String ANIMATION_RISE_IDLE = "Rise_Idle";
    public final static String ANIMATION_SWIM = "Swim";
    public final static String ANIMATION_RISE = "Rise";
    public final static String ANIMATION_DIVE = "Dive";
    public final static String ANIMATION_ATTACK_BOAT = "Attack_Boat";
    public final static String ANIMATION_SUCKED_DOWN = "Sucked_Down";
    
    public WhaleModel(AssetManager am) {
        super(am, "Models/whaleA.mesh.xml", "Textures/whale.png", null);
    }

    
}
