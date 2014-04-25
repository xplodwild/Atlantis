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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Tile de type mer
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 28/02/2014
 */
public class WaterTile extends GameTile {

    /**
     * mIsBeginningWithSeaShark : Defini si le waterTile est un des 5
     * emplacement ou se placent les serpent de mer
     */
    private boolean mIsBeginningWithSeaShark;
    /**
     * mIsLandingBay : Defini si le tile est l'un des 8 tile permettant de
     * sauver un pion
     */
    private boolean mIsLandingTile;

    /**
     * Constructeur de WaterTile
     *
     * @param board Plateau de jeu auquel appartient le tiles de type mer
     * @param name Nom du tile
     */
    public WaterTile(GameBoard board, String name) {
        super(board, name, 0);
        this.mIsLandingTile = false;
        this.mIsBeginningWithSeaShark = false;
    }

    /**
     * Constructeur de WaterTile #2
     *
     * @param board Plateau de jeu auquel appartient le tiles de type mer
     * @param name Nom du tile
     */
    public WaterTile(GameBoard board, String name, boolean escape, boolean seashark) {
        super(board, name, 0);
        this.mIsLandingTile = escape;
        this.mIsBeginningWithSeaShark = seashark;
    }

    /**
     * Constructeur de WaterTile #3
     *
     * @param board Plateau auquel appartient le tile
     * @param hg Tile en haut a gauche du tile courant
     * @param hd Tile en haut a droite du tile courant
     * @param g Tile a gauche du tile courant
     * @param d Tile a droite du tile courant
     * @param bd Tile en bas a droite du tile courant
     * @param bg Tile en bas a gauche du tile courant
     * @param name Nom du tile (placement selon le schema établi (de la forme A1
     * B1 B2 ...)
     * @param height hauteur du tile
     */
    public WaterTile(GameBoard board, GameTile hg, GameTile hd, GameTile g, GameTile d, GameTile bd, GameTile bg, String name, int height) {
        super(board, hg, hd, g, d, bd, bg, name, 0);
    }
    
    /**
     * Constructeur de WaterTile #4
     * 
     * @param board Plateau auquel appartient le tile
     * @param stream chargement du fichier
     * @throws IOException 
     */
    public WaterTile(GameBoard board, DataInputStream stream) throws IOException {
        super(board, stream);
        readSerialized(stream);
    }

    /**
     * ressort une BorderTile d'une sauvegarde 
     * 
     * @param data flux de données
     * @throws IOException 
     */
    @Override
    public final void readSerialized(DataInputStream data) throws IOException {
        super.readSerialized(data);
        mIsLandingTile = data.readBoolean();
        mIsBeginningWithSeaShark = data.readBoolean();
    }

    /**
     * Serialize la tile dans le DataOutputStream indiqué
     * 
     * @param data la cible de serialisation
     * @throws IOException 
     */
    @Override
    public void serializeTo(DataOutputStream data) throws IOException {
        super.serializeTo(data);
        data.writeBoolean(mIsLandingTile);
        data.writeBoolean(mIsBeginningWithSeaShark);
    }
    
    /**
     * Recupère le type de la tile
     * @return le type de la tile (tile water)
     */
    @Override
    public int getType() {
        return TILE_WATER;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
   /**
    * Retourne si le tile commence avec une entité SeaShark
    * @return boolean : true si la tile a un SeaSerpent
    */
    public boolean isBeginningWithSeaShark() {
        return this.mIsBeginningWithSeaShark;
    }

    /**
     * Retourne si la tile est une WaterTile ou une tile où l'on peux sauver un pion
     * @return boolean : true si c'est une tile "ile"
     */
    public boolean isLandingTile() {
        return this.mIsLandingTile;
    }
    

    //--------------------------------------------------------------------------
    //SETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Définit si la tile est une tile avec un SeaSerpent au début du jeu
     * @param mIsBeginningWithSeaShark boolean : true si la tile a un SeaSerpent
     */
    public void setIsBeginningWithSeaShark(boolean mIsBeginningWithSeaShark) {
        this.mIsBeginningWithSeaShark = mIsBeginningWithSeaShark;
    }

    /**
     * Définit si la tile est une tile "ile" ou une waterTile
     * @param mIsLandingTile boolean : true si c'est une tile "ile"
     */
    public void setIsLandingTile(boolean mIsLandingTile) {
        this.mIsLandingTile = mIsLandingTile;
    }
    //--------------------------------------------------------------------------
}