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

import java.util.Random;

/**
 * Classe représentant le dé du jeu
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014  
 */
public class GameDice {
    
    /**
     * Numero de face du serpent de mer
     */
    public final static int FACE_SEASERPENT = 0;
    
    /**
     * Numero de face du requin
     */
    public final static int FACE_SHARK = 1;
    
    /**
     * Numero de face de la baleine
     */
    public final static int FACE_WHALE = 2;
    
    /**
     * Nombre de faces du dé
     */
    private final static int FACE_COUNT = 3;
    
    /**
     * Tableau de probabilités des faces
     */
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
    
    
    /**
     * Lance le dé et retourne la face correspondante
     * 
     * @return Face du dé.
     */
    public int roll() {     
        //Pick un nombre a virgule flotante entre 0.0 et 1.0
        float random=new Random().nextFloat();
        int retour=0;
        for(int i = 0 ; i < this.mProbability.length ; i++){
            if(i>0){
                if(random>this.mProbability[i-1] && random<this.mProbability[i+1]){
                    retour=i;
                }
            }else{
                if(i<this.mProbability.length){
                    if(random>this.mProbability[i-1] && random<this.mProbability[i+1]){
                        retour=i;
                    }
                }else{
                    if(random>this.mProbability[i-1] && random<=1){
                    retour=i;
                }
                }   
            }            
        }        
        return retour;
    }    
}
