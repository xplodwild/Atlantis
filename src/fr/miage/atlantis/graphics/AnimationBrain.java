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

package fr.miage.atlantis.graphics;

import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.graphics.models.BoatModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.graphics.models.SeaSerpentModel;
import fr.miage.atlantis.graphics.models.WhaleModel;
import java.util.Random;

/**
 *
 */
public class AnimationBrain {

    public static String getIdleAnimation(GameEntity ent) {
        if (ent instanceof PlayerToken) {
            PlayerToken pt = (PlayerToken) ent;
            
            if (pt.getState() == PlayerToken.STATE_SWIMMING) {
                return PlayerModel.ANIMATION_SWIM_IDLE;
            } else {
                int idle = new Random().nextInt(3);
                switch (idle) {
                    case 0:
                        return PlayerModel.ANIMATION_LAND_IDLE_1;
                        
                    case 1:
                        return PlayerModel.ANIMATION_LAND_IDLE_2;
                        
                    case 2:
                        return PlayerModel.ANIMATION_LAND_IDLE_3;
                }
            }
        } else if (ent instanceof SeaSerpent) {
            return SeaSerpentModel.ANIMATION_IDLE;
        } else if (ent instanceof Shark) {
            // Shark n'a pas d'animation d'Idle oO
            return null;
        } else if (ent instanceof Whale) {
            return WhaleModel.ANIMATION_IDLE;
        } else if (ent instanceof Boat) {
            return BoatModel.ANIMATION_BOAT_IDLE;
        }
        
        return null;
    }
    
    public static String getMovementAnimation(GameEntity ent, GameTile dest) {
        if (ent instanceof PlayerToken) {
            PlayerToken pt = (PlayerToken) ent;
            
            if (dest.getHeight() == 0
                    && pt.getState() != PlayerToken.STATE_ON_BOAT) {
                return PlayerModel.ANIMATION_SWIM_CYCLE;
            } else {
                return PlayerModel.ANIMATION_WALK_CYCLE;
            }
        }
        
        return null;
    }
}
