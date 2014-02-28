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

import fr.miage.atlantis.entities.GameEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant les tiles que l'on place sur le plateau de jeu
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 28/02/2014 * 
 */
public abstract class GameTile {

    /**
     * mX : Coordonné X du tile sur le plateau de jeu
     */
    private int mX;
    /**
     * my : Coordonné Y du tile sur le plateau de jeu
     */
    private int mY;
    /**
     * mHeight : Coordonnée hauteur du tile
     */
    private int mHeight;
    /**
     * Entitées de jeu présente sur le tile courant (Animaux/Pions/Bateau...)
     */
    private List<GameEntity> mEntities;
    /**
     * mBoard : Board a laquelle appartient le tile courant
     */
    private GameBoard mBoard;
    /**
     * mIsOnBoard : Indique si la tile est sur le plateau ou retirée
     */
    private boolean mIsOnBoard;
    
    
    /**
     * Constructeur de GameTile
     * 
     * @param board Plateau auquel appartient le tile
     * @param height hauteur du tile
     */
    GameTile(GameBoard board, int x, int y, int height) {
        mBoard = board;
        mHeight = height;
        mX = x;
        mY = y;
        mIsOnBoard = true;
        mEntities = new ArrayList<GameEntity>();
    }
        
          
    /**
     * Ajoute une entité sur le tile courant
     * 
     * @param gE Entité de jeu à ajouter sur le tile
     */
    public void addEntity(GameEntity gE){
        this.mEntities.add(gE);
    }
    
    
    /**
     * Enleve une entité sur le tile courant
     * 
     * @param gE Entité de jeu a supprimer du tile 
     */
    public void removeEntity(GameEntity gE){
        this.mEntities.remove(gE);
    }
    
    /**
     * Indique que cette tile a été supprimée du plateau
     */
    public void removeFromBoard() {
        mIsOnBoard = false;
    }
    
    //---------------------------------------------
    //GETTERS
    //---------------------------------------------
    
    public List<GameEntity> getEntities() {
        return mEntities;
    }
    
    public int getX() {
        return mX;
    }
    
    public int getY() {
        return mY;
    }
    
    public int getHeight() {
        return mHeight;
    }
    
    public GameBoard getBoard() {
        return mBoard;
    }
    
    public boolean isOnBoard() {
        return mIsOnBoard;
    }
}