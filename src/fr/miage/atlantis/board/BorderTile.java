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
 * @date 28/02/2014 * 
 */
public class BorderTile extends GameTile{

    /**
     * Constructeur de BorderTile
     * 
     * @param board Plateau de jeu auquel appartient le tiles de type frontiere
     * @param name Nom du tile
     */
    public BorderTile(GameBoard board){ 
        super(board, "Limites du plateau", -99);
    }
    
    /**
     * Constructeur de BorderTile #2
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
    public BorderTile(GameBoard board,GameTile hg,GameTile hd,GameTile g,GameTile d,GameTile bd,GameTile bg,String name, int height) {
        super(board, hg, hd, g, d, bd, bg, "Limites du plateau", -99);
    }
}
