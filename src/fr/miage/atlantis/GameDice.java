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

package fr.miage.atlantis;

/**
 * Classe représentant le dé du jeu
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014  
 */
public class GameDice {
    
    public final static int FACE_SEASERPENT = 0;
    public final static int FACE_SHARK = 1;
    public final static int FACE_WHALE = 2;
    
    private final static int FACE_COUNT = 3;
    
    public float[] mProbability;
    
    
    /**
     * Constructeur de dés
     * 
     * @param probability Tableau des probabilité de chaques faces de tomber
     */
    public GameDice(float[] probability) {
        if (probability == null || probability.length != FACE_COUNT) {
            throw new IllegalArgumentException("The probability array must have FACE_COUNT values");
        }
        
        mProbability = probability;
    }
    
    /**
     * Créé un dés standard, non pipé 
     * 
     * @return Nouveau dé standard , toutes les faces on la meme probabilité de tomber.
     */
    public static GameDice createDefault() {
        return new GameDice(new float[]{ 0.33333333f, 0.3333333f, 0.3333333f });
    }
    
    public int roll() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    
    
}
