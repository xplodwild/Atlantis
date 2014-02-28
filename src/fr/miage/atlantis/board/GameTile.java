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
     * mHeight : Coordonné Z du tile (hauteur)
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
    
    GameTile(GameBoard board, int height) {
        this.mBoard=board;
        this.mHeight=height;
        this.mEntities=new ArrayList<GameEntity>();
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
    
    public void addEntity(GameEntity gE){
        this.mEntities.add(gE);
    }
    
    public void removeEntity(GameEntity gE){
        this.mEntities.remove(gE);
    }
    
    public List<GameEntity> getEntities() {
        return mEntities;
    }
}
