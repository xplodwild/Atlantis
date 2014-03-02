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

package fr.miage.atlantis.entities;

import fr.miage.atlantis.board.GameTile;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe generant les bateau
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014  
 */
public class Boat extends GameEntity {
    
    /**
     * Constante maximum de joueurs par bateau
     */
    private static final int MAX_PLAYER_PER_BOAT=3;
    
    /**
     * Liste des tokens present sur le bateau
     */
    private List<PlayerToken> mOnboard;
    
    
    /**
     * Constructeur du bateau
     * 
     * @param tile Tile sur lequel est placé le bateau.
     */
    public Boat(GameTile tile) {
        super("Boat", tile); 
        this.mOnboard=new ArrayList();
    }
    
    
    /**
     * Ajoute un PlayerToken sur le bateau
     * 
     * @param token Token a ajouter
     */
    public void addPlayer(PlayerToken token) {
        if(this.hasRoom()){
            this.mOnboard.add(token);
        }
    }
    
    
    /**
     * Test si il y a toujours de la place dans le bateau
     * 
     * @return true si il y a de la place, false sinon
     */
    public boolean hasRoom() {
        boolean retour=false;
        if(Boat.MAX_PLAYER_PER_BOAT > this.mOnboard.size() ){
            retour=true;
        }
        return retour;
    }
    
    
    
    
    //--------------------------------------------------------------------------
    //GETTERS
    //--------------------------------------------------------------------------
    
    
    public List<PlayerToken> getOnboardTokens() {
        return this.mOnboard;
    }
    //--------------------------------------------------------------------------
}
