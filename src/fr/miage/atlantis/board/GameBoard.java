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
public final class GameBoard {

    
    /**
     * Stock les tiles du board
     */
    private HashMap<String,GameTile> tileSet;
    
    
    /**
     * Constructeur de GameBoard
     * 
     */
    public GameBoard() {
       /*On vas creer et adresser ici les tiles en commencant par la haut gauche du plateau */
       /*Le nommage sera A1 pour le tile Haut Gauche puis on incremente*/
        tileSet = new HashMap<String, GameTile>();
       
       //On defini les deux premiere Tile de frontiere du board
       BorderTile firstTile=new BorderTile(this,"Border #1");  
       BorderTile nextTile=new BorderTile(this,"Border #2");    
       //On les ajoute au tileSet
       this.tileSet.put("Bord1", firstTile);
       this.tileSet.put("Bord2", nextTile);
       //On place le 1er a la droite du second
       this.placeTileAtTheRightOf(firstTile, nextTile);
       
       //Puis les 8 suivants de la meme façon
       for(int i=3;i<9;i++){
           BorderTile tmp=new BorderTile(this,"Border #"+i);
           this.placeTileAtTheRightOf(nextTile,tmp);    
           nextTile=tmp;
       }       
       //Première ligne terminée, Frontière haute du jeu mise en place.
       
       
       //Debut de la ligne 2
       nextTile=new BorderTile(this,"Border #47");
       this.placeTileAtTheBottomLeftOf(firstTile, nextTile);
       
       this.placeTileAtTheLeftOf(nextTile, new BorderTile(this,"Border #46",true));
       
       //Place le 1er Tile Water
       WaterTile nextTile2=new WaterTile(this,"Water #1");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       WaterTile tmp=null;
       //Puis les 8 suivants de la meme façon
       for(int i=2;i<8;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #9");
       this.placeTileAtTheRightOf(tmp, nextTile);
       this.placeTileAtTheRightOf(nextTile, new BorderTile(this,"Border #10",true));
       //Fin de la ligne 2
       
       
       //Debut de la ligne 3
       GameTile n46=this.tileSet.get("Border #46");
       
       nextTile=new BorderTile(this,"Border #45",true);
       this.placeTileAtTheBottomLeftOf(n46, nextTile);
       
       //Place le 8e Tile Water
       nextTile2=new WaterTile(this,"Water #8");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       tmp=null;
       //Puis les 8 suivants de la meme façon
       for(int i=9;i<18;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #11",true);
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //Fin de la ligne 3
       
       //Mise a jour de l'attribut du tile de landing
       WaterTile temp=(WaterTile)this.tileSet.get("Water #17");
       temp.setIsLandingTile(true);
       this.tileSet.put("Water #17", temp);
           
       
       
       //Debut de la ligne 4
       GameTile n45=this.tileSet.get("Border #45");
       
       nextTile=new BorderTile(this,"Border #44",true);
       this.placeTileAtTheBottomLeftOf(n45, nextTile);
       
       //Place le 8e Tile Water
       nextTile2=new WaterTile(this,"Water #18");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       tmp=null;
       //Puis les 8 suivants de la meme façon
       for(int i=19;i<29;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #12",true);
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //Fin de la ligne 4
       
                
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
    
    
    /**
     * Accesseur pour le premier Tile du board
     * 
     * @return Premier Tile de la board.
     */
    public GameTile getFirstTile(){
        return this.tileSet.get("Bord1");
    }
    
    
    /**
     * Génère un tile de type aléatoire
     * 
     * @return un tile generé aléatoirement parmis les 3 types.
     */
    public GameTile generateRandomTile(int forestRemaining,int beachRemaining,int mountainRemaining){
        GameTile retour=null;
        int min=0;
        int max=3;
        int x=-1;
        
        if(forestRemaining==0){
            min++;
        }
        if(beachRemaining==0){
            min++;
        }
        if(mountainRemaining==0){
            min++;
        }
                
        while(x<min && min!=max){
            x=new Random().nextInt(3);         
        }   
        
        switch(x){
            case 0:     retour=new ForestTile(this,"tempName");
            break;   
                
            case 1:     retour=new BeachTile(this,"tempName");
            break; 
                
            case 2:     retour=new MountainTile(this,"tempName");
            break; 
                
            default:    retour=null;
            break;
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
    
    
    /**
     * Permet de place un Tile a la droite d'un autre, tout en updatant les Tile adjacents.
     * 
     * @param base Tile existant
     * @param newTile Nouveau tile a greffer
     */
    public void placeTileAtTheRightOf(GameTile base,GameTile newTile) {        
        //On lie les deux tiles entre elles
        base.setRightTile(newTile);
        newTile.setLeftTile(base);
        
        GameTile baseUpperRightTile=base.getRightUpperTile();
        GameTile baseBottomRightTile=base.getRightBottomTile();
        
        //Puis on recupere les tile adjacent aux deux tile et on les lient a la nouvelle tile fraichement crée.
        newTile.setLeftUpperTile(baseUpperRightTile);
        newTile.setLeftBottomTile(baseBottomRightTile);
        
        //Update le HashMap 
        this.tileSet.put(base.getName(), base);
        this.tileSet.put(newTile.getName(), newTile);
        
        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseBottomRightTile != null){
            baseBottomRightTile.setRightUpperTile(newTile);
            this.tileSet.put(baseBottomRightTile.getName(), baseBottomRightTile);
        }
        if(baseUpperRightTile != null){
            baseUpperRightTile.setRightBottomTile(newTile);
            this.tileSet.put(baseUpperRightTile.getName(), baseUpperRightTile);
        }  
    }
    
    
    /**
     * Permet de place un Tile a la gauche d'un autre, tout en updatant les Tile adjacents.
     * 
     * @param base Tile existant
     * @param newTile Nouveau tile a greffer
     */
    public void placeTileAtTheLeftOf(GameTile base,GameTile newTile) {        
        //On lie les deux tiles entre elles
        base.setLeftTile(newTile);
        newTile.setRightTile(base);
        
        GameTile baseUpperLeftTile=base.getLeftUpperTile();
        GameTile baseBottomLeftTile=base.getLeftBottomTile();
        
        //Puis on recupere les tile adjacent aux deux tile et on les lient a la nouvelle tile fraichement crée.
        newTile.setRightUpperTile(baseUpperLeftTile);
        newTile.setRightBottomTile(baseBottomLeftTile);
        
        //Update le HashMap 
        this.tileSet.put(base.getName(), base);
        this.tileSet.put(newTile.getName(), newTile);
        
        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseBottomLeftTile != null){
            baseBottomLeftTile.setRightUpperTile(newTile);
            this.tileSet.put(baseBottomLeftTile.getName(), baseBottomLeftTile);
        }
        if(baseUpperLeftTile != null){
            baseUpperLeftTile.setRightBottomTile(newTile);
            this.tileSet.put(baseUpperLeftTile.getName(), baseUpperLeftTile);
        }  
    }
       
    
    /**
     * Permet de place un Tile en bas a droite d'un autre, tout en updatant les Tile adjacents.
     * 
     * @param base Tile existant
     * @param newTile Nouveau tile a greffer
     */
    public void placeTileAtTheBottomRightOf(GameTile base,GameTile newTile) {        
        //On lie les deux tiles entre elles
        base.setRightBottomTile(newTile);
        newTile.setLeftUpperTile(base);
        
        GameTile baseLeftBottomTile=base.getLeftBottomTile();
        GameTile baseRightTile=base.getRightTile();
        
        //Puis on recupere les tile adjacent aux deux tile et on les lient a la nouvelle tile fraichement crée.
        newTile.setLeftTile(baseLeftBottomTile);
        newTile.setRightUpperTile(baseRightTile);
        
        //Update le HashMap 
        this.tileSet.put(base.getName(), base);
        this.tileSet.put(newTile.getName(), newTile);
        
        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseLeftBottomTile != null){
            baseLeftBottomTile.setRightUpperTile(newTile);
            this.tileSet.put(baseLeftBottomTile.getName(), baseLeftBottomTile);
        }
        if(baseRightTile != null){
            baseRightTile.setRightBottomTile(newTile);
            this.tileSet.put(baseRightTile.getName(), baseRightTile);
        }        
    }
    
    
    /**
     * Permet de place un Tile en bas a gauche d'un autre, tout en updatant les Tile adjacents.
     * 
     * @param base Tile existant
     * @param newTile Nouveau tile a greffer
     */
    public void placeTileAtTheBottomLeftOf(GameTile base,GameTile newTile) {        
        //On lie les deux tiles entre elles
        base.setLeftBottomTile(newTile);
        newTile.setRightUpperTile(base);
        
        GameTile baseLeftTile=base.getLeftTile();
        GameTile baseRightBottomTile=base.getRightBottomTile();
        
        //Puis on recupere les tile adjacent aux deux tile et on les lient a la nouvelle tile fraichement crée.
        newTile.setRightTile(baseRightBottomTile);
        newTile.setLeftUpperTile(baseLeftTile);
        
        //Update le HashMap 
        this.tileSet.put(base.getName(), base);
        this.tileSet.put(newTile.getName(), newTile);
        
        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseRightBottomTile != null){
            baseRightBottomTile.setLeftTile(newTile);
            this.tileSet.put(baseRightBottomTile.getName(), baseRightBottomTile);
        }
        if(baseLeftTile != null){
            baseLeftTile.setRightBottomTile(newTile);
            this.tileSet.put(baseLeftTile.getName(), baseLeftTile);
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
