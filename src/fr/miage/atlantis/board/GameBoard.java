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

import fr.miage.atlantis.entities.SeaSerpent;
import java.util.ArrayList;
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
     * Tile de foret restante à placer
     */
    private int forestTilesRemaining;
    
    /**
     * Tile de plage restante a placer
     */
    private int beachTilesRemaining;
    
    /**
     * Tile de montagne restante a placer
     */
    private int mountainTilesRemaining;
    
    /**
     * Randomiser de tiles
     */
    private ArrayList<GameTile> randomiser;
    
    
    /**
     * Constructeur de GameBoard
     * 
     */
    public GameBoard() {
        /*On vas creer et adresser ici les tiles en commencant par la haut gauche du plateau */       
        tileSet = new HashMap<String, GameTile>();

        this.beachTilesRemaining=16;
        this.forestTilesRemaining=16;
        this.mountainTilesRemaining=8;

        this.randomiser=new ArrayList();
        
        for(int i=0;i<this.forestTilesRemaining;i++){
            randomiser.add(new ForestTile(this,"Forest #"+i));
        }

        for(int i=0;i<this.mountainTilesRemaining;i++){
            randomiser.add(new MountainTile(this,"Mountain #"+i));
        }

        for(int i=0;i<this.beachTilesRemaining;i++){
            randomiser.add(new BeachTile(this,"Beach #"+i));
        }

       //-----------------------------------------------------------------------
       //Ligne 1                                                               |
       //-----------------------------------------------------------------------
       
       
       //On defini les deux premiere Tile de frontiere du board
       BorderTile firstTile=new BorderTile(this,"Border #1");  
       BorderTile nextTile=new BorderTile(this,"Border #2");    
       //On les ajoute au tileSet
       this.tileSet.put("Border #1", firstTile);
       this.tileSet.put("Border #2", nextTile);
       //On place le 1er a la droite du second
       this.placeTileAtTheRightOf(firstTile, nextTile);
       
       //Puis les 8 suivants de la meme façon
       for(int i=3;i<9;i++){
           BorderTile tmp=new BorderTile(this,"Border #"+i);
           this.placeTileAtTheRightOf(nextTile,tmp);    
           nextTile=tmp;
       }     
       //-----------------------------------------------------------------------
       
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 2                                                               |
       //-----------------------------------------------------------------------
       
       
       nextTile=new BorderTile(this,"Border #47");
       this.placeTileAtTheBottomLeftOf(firstTile, nextTile);       
       this.placeTileAtTheLeftOf(nextTile, new BorderTile(this,"Border #46",true));
            
       WaterTile nextTile2=new WaterTile(this,"Water #1");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       WaterTile tmp=null;     
       for(int i=2;i<8;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #9");
       this.placeTileAtTheRightOf(tmp, nextTile);
       this.placeTileAtTheRightOf(nextTile, new BorderTile(this,"Border #10",true));
       //-----------------------------------------------------------------------
       
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 3                                                               |
       //-----------------------------------------------------------------------
       
       
       GameTile n46=this.tileSet.get("Border #46");       
       nextTile=new BorderTile(this,"Border #45",true);
       this.placeTileAtTheBottomLeftOf(n46, nextTile);
       
       nextTile2=new WaterTile(this,"Water #8");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       tmp=null;       
       for(int i=9;i<18;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #11",true);
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
      
       
       
       //-----------------------------------------------------------------------
       //Ligne 4                                                               |
       //-----------------------------------------------------------------------
       GameTile n45=this.tileSet.get("Border #45");       
       nextTile=new BorderTile(this,"Border #44",true);
       this.placeTileAtTheBottomLeftOf(n45, nextTile);
              
       nextTile2=new WaterTile(this,"Water #18");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       tmp=null;       
       for(int i=19;i<29;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #12",true);
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
     
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 5                                                               |
       //-----------------------------------------------------------------------
       
       
       GameTile n44=this.tileSet.get("Border #44");       
       nextTile=new BorderTile(this,"Border #42");
       this.placeTileAtTheBottomRightOf(n44, nextTile);
             
       nextTile2=new WaterTile(this,"Water #29");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
              
       for(int i=30;i<32;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       GameTile tmp2;
       GameTile nextTile3=nextTile2;
       for(int i=1;i<5;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }
             
       nextTile2=new WaterTile(this,"Water #32");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
                    
       for(int i=33;i<35;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
             
       nextTile=new BorderTile(this,"Border #13");
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
      
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 6                                                               |
       //-----------------------------------------------------------------------
       
       
       GameTile n42=this.tileSet.get("Border #42");       
       nextTile=new BorderTile(this,"Border #41");
       this.placeTileAtTheBottomLeftOf(n42, nextTile);
       
       nextTile2=new WaterTile(this,"Water #35");
       this.placeTileAtTheRightOf(nextTile, nextTile2);       
       
       for(int i=36;i<38;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
              
       nextTile3=nextTile2;
       for(int i=6;i<11;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }
              
       nextTile2=new WaterTile(this,"Water #38");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
                
       for(int i=39;i<41;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
             
       nextTile=new BorderTile(this,"Border #14");
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
       
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 7                                                               |
       //-----------------------------------------------------------------------
       
       
       GameTile n41=this.tileSet.get("Border #41");       
       nextTile=new BorderTile(this,"Border #40");
       this.placeTileAtTheBottomLeftOf(n41, nextTile);
              
       nextTile2=new WaterTile(this,"Water #41");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile3=new WaterTile(this,"Water #42");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
                   
       for(int i=10;i<18;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }
              
       nextTile2=new WaterTile(this,"Water #43");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile3=new WaterTile(this,"Water #44");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);    
       
       nextTile=new BorderTile(this,"Border #15");
       this.placeTileAtTheRightOf(nextTile3, nextTile);      
       //-----------------------------------------------------------------------
       
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 8                                                               |
       //-----------------------------------------------------------------------
       
       
       GameTile n40=this.tileSet.get("Border #40");       
       nextTile=new BorderTile(this,"Border #39");
       this.placeTileAtTheBottomRightOf(n40, nextTile);
             
       nextTile2=new WaterTile(this,"Water #45");
       this.placeTileAtTheRightOf(nextTile, nextTile2);       
       nextTile3=new WaterTile(this,"Water #46");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
       tmp2=null;       
       for(int i=19;i<22;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }
       
       nextTile2=new WaterTile(this,"Water #47",false,true);
       this.placeTileAtTheRightOf(tmp2, nextTile2);
       
       nextTile3=nextTile2;       
       for(int i=23;i<26;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }  
       
       nextTile3=new WaterTile(this,"Water #48");
       this.placeTileAtTheRightOf(tmp2, nextTile3);   
       nextTile2=new WaterTile(this,"Water #49");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);    
       
       nextTile=new BorderTile(this,"Border #16");
       this.placeTileAtTheRightOf(nextTile2, nextTile);      
       //-----------------------------------------------------------------------
    
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 9                                                               |
       //-----------------------------------------------------------------------
       
       
       GameTile n39=this.tileSet.get("Border #39");       
       nextTile=new BorderTile(this,"Border #38");
       this.placeTileAtTheBottomLeftOf(n39, nextTile);       
       
       nextTile2=new WaterTile(this,"Water #50");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile3=new WaterTile(this,"Water #51");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
                    
       for(int i=26;i<34;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }
             
       nextTile2=new WaterTile(this,"Water #52");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile3=new WaterTile(this,"Water #53");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);    
       
       nextTile=new BorderTile(this,"Border #17");
       this.placeTileAtTheRightOf(nextTile3, nextTile);      
       //-----------------------------------------------------------------------
     
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 10                                                              |
       //-----------------------------------------------------------------------
       
       
       GameTile n38=this.tileSet.get("Border #38");       
       nextTile=new BorderTile(this,"Border #37");
       this.placeTileAtTheBottomRightOf(n38, nextTile);     
       nextTile2=new WaterTile(this,"Water #54");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       for(int i=55;i<57;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
              
       nextTile3=nextTile2;
       for(int i=26;i<31;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }
             
       nextTile2=new WaterTile(this,"Water #55");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       
       tmp=null;       
       for(int i=56;i<58;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
             
       nextTile=new BorderTile(this,"Border #18");
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
      
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 11                                                              |
       //-----------------------------------------------------------------------
       
       
       GameTile n37=this.tileSet.get("Border #37");       
       nextTile=new BorderTile(this,"Border #36");
       this.placeTileAtTheBottomRightOf(n37, nextTile);
       nextTile2=new WaterTile(this,"Water #58");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
              
       for(int i=59;i<61;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
             
       nextTile3=nextTile2;
      
       for(int i=31;i<35;i++){           
           tmp2=this.generateRandomTile("Random #"+i);
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);    
           nextTile3=tmp2;
       }
             
       nextTile2=new WaterTile(this,"Water #61");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       
       tmp=null;      
       for(int i=62;i<64;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
             
       nextTile=new BorderTile(this,"Border #19");
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
       
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 12                                                              |
       //-----------------------------------------------------------------------
       
       
       GameTile n36=this.tileSet.get("Border #36");       
       nextTile=new BorderTile(this,"Border #35",true);
       this.placeTileAtTheBottomLeftOf(n36, nextTile);
       
       nextTile2=new WaterTile(this,"Water #64");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       tmp=null;      
       for(int i=65;i<75;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #20",true);
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
    
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 13                                                              |
       //-----------------------------------------------------------------------
       
       
       GameTile n35=this.tileSet.get("Border #35");       
       nextTile=new BorderTile(this,"Border #34",true);
       this.placeTileAtTheBottomRightOf(n35, nextTile);       
       nextTile2=new WaterTile(this,"Water #75");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       
       tmp=null;
       for(int i=76;i<85;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #21",true);
       this.placeTileAtTheRightOf(tmp, nextTile);      
       //-----------------------------------------------------------------------
      
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 14                                                              |
       //-----------------------------------------------------------------------
       
       
       GameTile n34=this.tileSet.get("Border #34");       
       nextTile=new BorderTile(this,"Border #33",true);
       this.placeTileAtTheBottomRightOf(n34, nextTile);
       nextTile3=new BorderTile(this,"Border #32",true);
       this.placeTileAtTheRightOf(nextTile, nextTile3);
       nextTile2=new WaterTile(this,"Water #85");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       
       tmp=null;       
       for(int i=86;i<92;i++){           
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);    
           nextTile2=tmp;
       }
       
       nextTile=new BorderTile(this,"Border #23",true);
       this.placeTileAtTheRightOf(tmp, nextTile);      
       nextTile3=new BorderTile(this,"Border #22",true);
       this.placeTileAtTheRightOf(nextTile, nextTile3);  
       //-----------------------------------------------------------------------
       
       
       
       
       //-----------------------------------------------------------------------
       //Ligne 15                                                              |
       //-----------------------------------------------------------------------
      
       
       GameTile n32=this.tileSet.get("Border #32");
       nextTile=new BorderTile(this,"Border #31");
       this.placeTileAtTheBottomRightOf(n32, nextTile);
                    
       for(int i=30;i>23;i--){
           BorderTile tmps=new BorderTile(this,"Border #"+i);
           this.placeTileAtTheRightOf(nextTile,tmps);    
           nextTile=tmps;
       }       
       //-----------------------------------------------------------------------
      
       
       
       
       //-----------------------------------------------------------------------
       //Update des attributs des tiles SeaShark & Echappatoires               |
       //-----------------------------------------------------------------------
       
       
       WaterTile temp=(WaterTile)this.tileSet.get("Water #17");
       temp.setIsLandingTile(true);
       this.tileSet.put("Water #17", temp);   
       temp=(WaterTile)this.tileSet.get("Water #18");
       temp.setIsLandingTile(true);
       this.tileSet.put("Water #18", temp);       
       temp=(WaterTile)this.tileSet.get("Water #8");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.tileSet.put("Water #8", temp);       
       temp=(WaterTile)this.tileSet.get("Water #28");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.tileSet.put("Water #28", temp);
       temp=(WaterTile)this.tileSet.get("Water #75");
       temp.setIsLandingTile(true);
       this.tileSet.put("Water #75", temp);
       temp=(WaterTile)this.tileSet.get("Water #84");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.tileSet.put("Water #84", temp);
       temp=(WaterTile)this.tileSet.get("Water #74");
       temp.setIsLandingTile(true);       
       this.tileSet.put("Water #74", temp);
       temp=(WaterTile)this.tileSet.get("Water #64");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.tileSet.put("Water #64", temp);
       //-----------------------------------------------------------------------
     
       
       
       
       //-----------------------------------------------------------------------
       //Ajout des 5 SeaShark sur les Cases prévues à cet effet                |
       //-----------------------------------------------------------------------
              
       temp=(WaterTile)this.tileSet.get("Water #64");
       temp.addEntity(new SeaSerpent(temp));
       
       temp=(WaterTile)this.tileSet.get("Water #84");
       temp.addEntity(new SeaSerpent(temp));
       
       temp=(WaterTile)this.tileSet.get("Water #28");
       temp.addEntity(new SeaSerpent(temp));
       
       temp=(WaterTile)this.tileSet.get("Water #8");
       temp.addEntity(new SeaSerpent(temp));
       
       temp=(WaterTile)this.tileSet.get("Water #47");
       temp.addEntity(new SeaSerpent(temp));       
       //-----------------------------------------------------------------------      
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
    
    public Map<String, GameTile> getTileSet() {
        return tileSet;
    }
    
    /**
     * Accesseur pour le premier Tile du board
     * 
     * @return Premier Tile de la board.
     */
    public GameTile getFirstTile(){
        return this.tileSet.get("Border #1");
    }
    
    
    /**
     * Génère un tile de type aléatoire
     * 
     * @return un tile generé aléatoirement parmis les 3 types.
     */
    public GameTile generateRandomTile(String name){
        int random=new Random().nextInt(randomiser.size());        
        GameTile retour=this.randomiser.get(random);
        randomiser.remove(random);
        
        return retour;
    }
    
    
    /**
     * Teste si existance d'un tile d'une hauteur donnée dans les tiles de la board
     * 
     * @param h hauteur du tile dont on teste l'existance
     * @return Si existance d'un tile de cette hauteur
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
     * @TODO : Finir la methode
     * 
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
            baseLeftBottomTile.setRightTile(newTile);
            this.tileSet.put(baseLeftBottomTile.getName(), baseLeftBottomTile);
        }
        if(baseRightTile != null){
            baseRightTile.setLeftBottomTile(newTile);
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
    
    
    
    
    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    
    
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
    //--------------------------------------------------------------------------
    
    
    
    
    //--------------------------------------------------------------------------
    //SETTERS                                                                  |
    //--------------------------------------------------------------------------
    
    
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
    //--------------------------------------------------------------------------
}
