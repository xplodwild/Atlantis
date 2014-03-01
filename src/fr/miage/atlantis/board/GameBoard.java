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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;



/**
 * Classe représentant le plateau de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 28/02/2014 
 */
public class GameBoard {

    private HashMap<String,GameTile> tileSet;
    
    /**
     * Constructeur de GameBoard
     * 
     */
    public GameBoard() {
       /*On vas creer et adresser ici les tiles en commencant par la haut gauche du plateau */
       /*Le nommage sera A1 pour le tile Haut Gauche puis on incremente*/
       
       //On defini les deux premiere Tile de frontiere du board
       BorderTile firstTile=new BorderTile(this);  
       BorderTile nextTile=new BorderTile(this);    
       //On les ajoute au tileSet
       this.tileSet.put("Bord1", firstTile);
       this.tileSet.put("Bord2", nextTile);
       //On place le 1er a la droite du second
       this.placeTileAtTheRightOf(firstTile, nextTile);
       
       //Puis les 8 suivants de la meme façon
       for(int i=3;i<9;i++){
           this.placeTileAtTheRightOf(nextTile, nextTile);
           this.tileSet.put("Bord"+i, nextTile);
       }
       
       //Puis on passe à la ligne suivante
       //... To be continued
        
        
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
            this.getBottomLeftCornerTile(tile).getHeight()==0  || this.getBottomRightCornerTile(tile).getHeight()==0 ||
            this.getLeftSideTile(tile).getHeight()==0 || this.getRightSideTile(tile).getHeight()==0){
            
            isAtWaterEdge=true;
        }    
        return isAtWaterEdge;
    }
    
    
    public GameTile getFirstTile(){
        return this.tileSet.get("Bord1");
    }
    
    
    /**
     * Génère un tile de type aléatoire
     * 
     * @return 
     */
    public GameTile generateRandomTile(int forestRemaining,int beachRemaining,int mountainRemaining){
        GameTile retour=null;
        int x=new Random().nextInt(3);
        
        if(forestRemaining>0 && beachRemaining>0 && mountainRemaining>0){
            switch(x){
                case 0: retour=new ForestTile(this,"tempName");
                break;   
                case 1: retour=new BeachTile(this,"tempName");
                break;   
                case 2: retour=new MountainTile(this,"tempName");
                break;      
            }  
        }
        
        
        return retour;
    }
    
    
    /**
     * Teste si existance d'un tile d'une hauteur donnée dans les tiles de la board
     * 
     * @param h hauteur du tile dont on teste l'existance
     * @return 
     */
    public boolean hasTileAtLevel(int h) {
        Boolean retour=false;
        
        Iterator<Map.Entry<String, GameTile>> it=this.tileSet.entrySet().iterator();
        while(it.hasNext()){
            GameTile tmp=it.next().getValue();
            if(tmp.getHeight() == h){
                retour=true;
                break;
            }
        }        
        return retour;
    }
    
    /**
     * Coule le tile donné et le supprime du plateau, passe tout les player present sur le tile à Swimmer
     * 
     * @param tile tile a couler
     */
    public void sinkTile(GameTile tile) {
        
        //Reste a couler les entities presente et agir dessus.
        
        this.tileSet.remove(tile.getName());
        throw new UnsupportedOperationException("Not fully implemented yet");
    }
    
    public boolean canPlaceTile() {
        boolean canPlace=false;
        
        return canPlace;
    }
    
    public void placeTileAtTheRightOf(GameTile base,GameTile newTile) {        
        //On lie les deux tiles entre elles
        base.setRightTile(newTile);
        newTile.setLeftTile(newTile);
        
        GameTile baseUpperRightTile=base.getRightUpperTile();
        GameTile baseBottomRightTile=base.getRightBottomTile();
        
        //Puis on recupere les tile adjacent aux deux tile et on les lient a la nouvelle tile fraichement crée.
        newTile.setLeftUpperTile(baseUpperRightTile);
        newTile.setLeftBottomTile(baseBottomRightTile);
        
        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseBottomRightTile != null){
            baseBottomRightTile.setRightUpperTile(newTile);
        }
        if(baseUpperRightTile != null){
            baseUpperRightTile.setRightBottomTile(newTile);
        }        
    }
            
    
    //-----------------------------------------------
    //GETTERS                                       |
    //-----------------------------------------------
    
    /**
     * Retourne le tile de nom donnée appartenant a la board.
     * 
     * @param name Nom du tile a retourner
     * @return Tile de la board de nom 'name' ou null si introuvable
     */
    public GameTile getTileByName(String name) {
        GameTile retour=null;
        
       Iterator<Map.Entry<String, GameTile>> it=this.tileSet.entrySet().iterator();
        while(it.hasNext()){
            GameTile tmp=it.next().getValue();
            if(tmp.getName().equals(name)){
                retour=tmp;
                break;
            }
        }        
        return retour;
    }

    private GameTile getUpperRightCornerTile(GameTile tile) {
        return tile.getRightUpperTile();
    }

    private GameTile getUpperLeftCornerTile(GameTile tile) {
        return tile.getLeftUpperTile();
    }

    private GameTile getBottomLeftCornerTile(GameTile tile) {
       return tile.getLeftBottomTile();
    }

    private GameTile getBottomRightCornerTile(GameTile tile) {
        return tile.getRightBottomTile();
    }
    
    private GameTile getRightSideTile(GameTile tile) {
        return tile.getRightTile();
    }
    
    private GameTile getLeftSideTile(GameTile tile) {
        return tile.getLeftTile();
    }
    
    
    //-----------------------------------------------
    //SETTERS                                       |
    //-----------------------------------------------
    
    
    private void setUpperRightCornerTile(GameTile tile,GameTile toBePlaced) {
        tile.setRightUpperTile(toBePlaced);
    }

    private void setUpperLeftCornerTile(GameTile tile,GameTile toBePlaced) {
        tile.setLeftUpperTile(toBePlaced);
    }

    private void setBottomLeftCornerTile(GameTile tile,GameTile toBePlaced) {
       tile.setLeftBottomTile(toBePlaced);
    }

    private void setBottomRightCornerTile(GameTile tile,GameTile toBePlaced) {
        tile.setRightBottomTile(toBePlaced);
    }
    
    private void setRightSideTile(GameTile tile,GameTile toBePlaced) {
        tile.setRightTile(toBePlaced);
    }
    
    private void setLeftSideTile(GameTile tile,GameTile toBePlaced) {
        tile.setLeftTile(toBePlaced);
    }    
}
