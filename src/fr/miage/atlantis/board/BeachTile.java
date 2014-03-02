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

/**
 * Tile de type plage
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 01/03/2014  
 */
public class BeachTile extends GameTile{

    /**
     * Action a effectuer une fois le tile coulé
     */
    private TileAction action;
    
    /**
     * Constructeur de Beachtile
     * 
     * @param board Plateau de jeu auquel appartient le tiles de type plage
     * @param name Nom du tile
     */
    public BeachTile(GameBoard board,String name){ 
        super(board, name, 1);
    }
    
    /**
     * Constructeur de Beachtile #2
     * 
     * @param board Plateau auquel appartient le tile
     * @param hg Tile en haut a gauche du tile courant
     * @param hd Tile en haut a droite du tile courant
     * @param g Tile a gauche du tile courant
     * @param d Tile a droite du tile courant
     * @param bd Tile en bas a droite du tile courant
     * @param bg Tile en bas a gauche du tile courant
     * @param name Nom du tile (placement selon le schema établi (de la forme A1 B1 B2 ...)
     * @param height hauteur du tile
     */    
    public BeachTile(GameBoard board,GameTile hg,GameTile hd,GameTile g,GameTile d,GameTile bd,GameTile bg,String name, int height) {
        super(board, hg, hd, g, d, bd, bg, name, 1);
    }
    
    
       

    //--------------------------------------------------------------------------
    //SETTERS                                                                 `|
    //--------------------------------------------------------------------------
    
    
    public void setAction(TileAction action) {
        this.action = action;
    }
    //--------------------------------------------------------------------------   
}
