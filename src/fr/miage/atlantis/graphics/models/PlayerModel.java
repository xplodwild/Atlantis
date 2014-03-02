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
public class PlayerModel extends AnimatedModel {
    
    public final static String ANIMATION_CLIMB_BOAT = "Climb_boat";
    public final static String ANIMATION_EATEN_BY_SHARK = "Eaten_by_shark";
    public final static String ANIMATION_SWIM_IDLE = "Swim_Idle";
    public final static String ANIMATION_GET_ON_OFF_BOAT = "Get_on_off_boat";
    public final static String ANIMATION_DROWN = "Drown";
    public final static String ANIMATION_DIVE = "Dive";
    public final static String ANIMATION_WALK_CYCLE = "walk_cycle";
    public final static String ANIMATION_SWIM_CYCLE = "swim_cycle";
    public final static String ANIMATION_JUMP_OFF_BOAT = "Jump_off_boat";
    public final static String ANIMATION_CLIMB_ISLAND = "Climb_island";
    public final static String ANIMATION_LAND_IDLE_1 = "Land_Idle1";
    public final static String ANIMATION_LAND_IDLE_2 = "Land_Idle2";
    public final static String ANIMATION_LAND_IDLE_3 = "Land_Idle3";
    
    
    
    public final static String COLOR_BLUE = "blue";
    public final static String COLOR_GREEN = "green";
    public final static String COLOR_ORANGE = "orange";
    public final static String COLOR_RED = "red";

    public PlayerModel(AssetManager am, final String color) {
        super(am, "Models/Avatar_A.mesh.xml",
                "Textures/player_" + color + ".png", null);
    }
    
    public static String intToColor(int i) {
        switch (i) {
            case 1:
                return COLOR_BLUE;
                
            case 2:
                return COLOR_GREEN;
                
            case 3:
                return COLOR_ORANGE;
                
            case 4:
                return COLOR_RED;
                
            default:
                throw new IllegalStateException("Unknown player number");
        }
    }

}
