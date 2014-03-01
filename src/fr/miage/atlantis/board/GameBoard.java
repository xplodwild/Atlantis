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

import fr.miage.atlantis.utils.HexagonTable;

/**
 * Classe représentant le plateau de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 28/02/2014 
 */
public class GameBoard {

    /**
     * mBoard Implementation du plateau
     */
    private HexagonTable<GameTile> mBoard;
    
    /**
     * Constructeur de GameBoard
     * 
     */
    public GameBoard() {
        this.mBoard=new HexagonTable<GameTile>();
    }
    
    /**
     * Teste si le tile donné est entouré d'au moins une tile de type WaterTile (h=0)
     * 
     * @param tile Tile du plateau à tester
     * @return 
     */
    public boolean isTileAtWaterEdge(GameTile tile) {
        boolean isAtWaterEdge=false;
        
        //Si un des tiles adjacent est de type WaterTile (avec une Height=0)
        if( this.getUpperRightCornerTile(tile).getHeight()==0  || this.getUpperLeftCornerTile(tile).getHeight()==0 ||
            this.getBottomLeftCornerTile(tile).getHeight()==0  || this.getBottomRightCornerTile(tile).getHeight()==0){
            isAtWaterEdge=true;
    }
    
        return isAtWaterEdge;
    }
    
    public boolean hasTileAtLevel(int h) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    /**
     * Coule le tile donné et le supprime du plateau, passe tout les player present sur le tile à Swimmer
     * 
     * @param tile tile a couler
     * @return 
     */
    public boolean sinkTile(GameTile tile) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean canPlaceTileAt(int x, int y) {
        boolean canPlace=false;
        
        return canPlace;
    }
    
    public void placeTileAt(GameTile t) {        
        if(this.canPlaceTileAt(t.getX(), t.getY())){
            //TODO : Do the job !!! Inserer le tile dans l'hexagonmachin via une fonction a coder dans hexagonmachin
        }
    }
    
    public GameTile getTileAt(int x, int y) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private GameTile getUpperRightCornerTile(GameTile tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
}

    private GameTile getUpperLeftCornerTile(GameTile tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private GameTile getBottomLeftCornerTile(GameTile tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private GameTile getBottomRightCornerTile(GameTile tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
