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

package fr.miage.atlantis.board;

import fr.miage.atlantis.logic.GameLogic;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe représentant les faces arrières des tiles du plateau de jeu
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014  
 */
public class TileAction {

    
    /**
     * Constantes representant les faces arrières des Tiles du plateau
     */
    public final static int NONE = -1;                    
    public final static int ANIMAL_SHARK = 0;
    public final static int ANIMAL_WHALE = 1;  
    
    public final static int ACTION_MOVE_ANIMAL = 0;
    public final static int ACTION_CANCEL_ANIMAL = 1;
    public final static int ACTION_SPAWN_ENTITY = 2;
    public final static int ACTION_BONUS_SWIM = 3;
    public final static int ACTION_BONUS_BOAT = 4;
    public final static int ACTION_VOLCANO = 5;
    
    /**
     * Nombre de tile action de type Spawn Animal Shark
     */
    public static int nb_Animal_Shark=6;
       
    /**
     * Nombre de tile action de type Spawn Animal Whale 
     */
    public static int nb_Animal_Whale=5;
    
    /**
     * Nombre de tile action de type Move Animal
     */
    private static int nb_Action_MoveAnimal=16;
    
    /**
     * Nombre de tile action de type CancelAnimal
     */
    private static int nb_Action_CancelAnimal=11;
    
    /**
     * Nombre de tile action de type SpawnAnimal
     */
    private static int nb_Action_SpawnAnimal=16;
    
    /**
     * Nombre de tile action de type SpawnBoat
     */
    private static int nb_Action_BonusBoat=2;                                   //@TODO : Trouver le nombre exact de ce type de tile
    
    /**
     * Nombre de tile action de type BonusSwim
     */
    private static int nb_Action_BonusSwim=3;                                   //@TODO : Trouver le nombre exact de ce type de tile
    
    
    /**
     * Defini si l'action est une action a realiser immediatement ou non
     */
    private boolean mIsImmediate;
    
    /**
     * Defini si l'action se declenche lors d'une attaque adverse
     */
    private boolean mIsTriggerable;
    
    /**
     * Defini si l'action est l'explosion du volcan
     */
    private boolean mIsVolcano;
    private int mAction;
    private int mAnimal;
    
    private static ArrayList<TileAction> randomiser;
    
    
    
    private TileAction(int action, int animal, boolean isImmediate,boolean isTriggerable,boolean isVolcano) {
        this.mAction=action;
        this.mAnimal=animal;
        this.mIsImmediate=isImmediate;
        this.mIsTriggerable=isTriggerable;
        this.mIsVolcano=isVolcano;   
    }
    
    
    
    public final static class Factory {
        public static TileAction createMoveAnimal(int animal) {
            return new TileAction(0,animal,false,false,false);
        }
        
        public static TileAction createCancelAnimal(int animal) {
            return new TileAction(1,animal,false,true,false);
        }
        
        public static TileAction createSpawnAnimal(int animal){
            return new TileAction(2,animal,true,false,false);
        }
        
        public static TileAction createBonusBoat(){
            return new TileAction(3,TileAction.NONE,true,false,false);
        }
        
        public static TileAction createBonusSwim(){
            return new TileAction(4,TileAction.NONE,false,false,false);
        }
        
        public static TileAction createVolcano(){
            return new TileAction(5,TileAction.NONE,false,false,true);
        }        
    }
    
    /**
     * Genere une action au hasard à placer sous un tile
     * 
     * @return A random ActionTile
     */
    public static TileAction generateRandomTileAction(){
        if(TileAction.randomiser == null){
            for(int i=0;i<=TileAction.nb_Animal_Shark;i++){
                randomiser.add(TileAction.Factory.createSpawnAnimal(TileAction.ANIMAL_SHARK));
                for(int j=0;j<=TileAction.nb_Action_CancelAnimal;j++){
                    randomiser.add(TileAction.Factory.createCancelAnimal(TileAction.ANIMAL_SHARK));
                }
                for(int k=0;k<=TileAction.nb_Action_MoveAnimal;k++){
                    randomiser.add(TileAction.Factory.createMoveAnimal(TileAction.ANIMAL_SHARK));
                }
            }
            
            for(int i=0;i<=TileAction.nb_Animal_Whale;i++){
                randomiser.add(TileAction.Factory.createSpawnAnimal(TileAction.ANIMAL_WHALE));
                
                for(int j=0;j<=TileAction.nb_Action_CancelAnimal;j++){
                    randomiser.add(TileAction.Factory.createCancelAnimal(TileAction.ANIMAL_WHALE));
                }
                for(int k=0;k<=TileAction.nb_Action_MoveAnimal;k++){
                    randomiser.add(TileAction.Factory.createMoveAnimal(TileAction.ANIMAL_WHALE));
                }
            }
            
            for(int i=0;i<=TileAction.nb_Action_BonusBoat;i++){
                randomiser.add(TileAction.Factory.createBonusBoat());
            }
            
            for(int i=0;i<=TileAction.nb_Action_BonusSwim;i++){
                randomiser.add(TileAction.Factory.createBonusSwim());
            }          
        }
        
        int random=new Random().nextInt(TileAction.randomiser.size());        
        TileAction retour=TileAction.randomiser.get(random);
        TileAction.randomiser.remove(random);
        
        return retour;
    }
    
    
    /**
     * @TODO : Implementer la methode
     * 
     * Defini la logique de jeu à lancer lors de l'utilisation de l'action d'un tile
     * 
     * @param tile Tile d'action
     * @param logic Logique a executer à l'utilisation
     */
    public void use(GameTile tile, GameLogic logic) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    
    
    
    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    
    
    public boolean isImmediate() {
        return mIsImmediate;
    }

    public boolean isTriggerable() {
        return mIsTriggerable;
    }

    public boolean isVolcano() {
        return mIsVolcano;
    } 

    public int getAction() {
        return mAction;
    }

    public int getAnimal() {
        return mAnimal;
    }  
    //--------------------------------------------------------------------------
}

