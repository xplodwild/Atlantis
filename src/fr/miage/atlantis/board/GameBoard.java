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
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.logic.GameLogic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


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
    private HashMap<String,GameTile> mTileSet;

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
        mTileSet = new HashMap<String, GameTile>();

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
       this.mTileSet.put("Border #1", firstTile);
       this.mTileSet.put("Border #2", nextTile);
       //On place le 1er a la droite du second
       this.placeTileAtTheRightOf(firstTile, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #2");

       //Puis les 8 suivants de la meme façon
       for(int i=3;i<9;i++){
           BorderTile tmp=new BorderTile(this,"Border #"+i);
           this.placeTileAtTheRightOf(nextTile,tmp);
           nextTile=(BorderTile) this.mTileSet.get("Border #"+i);
       }
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 2                                                               |
       //-----------------------------------------------------------------------


       nextTile=new BorderTile(this,"Border #46");
       this.placeTileAtTheBottomLeftOf(firstTile, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #46");

       this.placeTileAtTheLeftOf(nextTile, new BorderTile(this,"Border #45",true));

       WaterTile nextTile2=new WaterTile(this,"Water #1");
       this.placeTileAtTheRightOf(nextTile, nextTile2);

       nextTile2=(WaterTile) this.mTileSet.get("Water #1");

       WaterTile tmp=null;
       for(int i=2;i<8;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile) this.mTileSet.get("Water #"+i);;
       }

       nextTile=new BorderTile(this,"Border #9");
       this.placeTileAtTheRightOf(tmp, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #9");
       this.placeTileAtTheRightOf(nextTile, new BorderTile(this,"Border #10",true));
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 3                                                               |
       //-----------------------------------------------------------------------


       GameTile n46=this.mTileSet.get("Border #45");
       nextTile=new BorderTile(this,"Border #44",true);
       this.placeTileAtTheBottomLeftOf(n46, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #44");

       nextTile2=new WaterTile(this,"Water #8");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile) this.mTileSet.get("Water #8");

       tmp=null;
       for(int i=9;i<18;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile) this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #11",true);
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------



       //-----------------------------------------------------------------------
       //Ligne 4                                                               |
       //-----------------------------------------------------------------------
       GameTile n45=this.mTileSet.get("Border #44");
       nextTile=new BorderTile(this,"Border #43",true);
       this.placeTileAtTheBottomLeftOf(n45, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #43");


       nextTile2=new WaterTile(this,"Water #18");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #18");


       tmp=null;
       for(int i=19;i<29;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #12",true);
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 5                                                               |
       //-----------------------------------------------------------------------


       GameTile n44=this.mTileSet.get("Border #43");
       nextTile=new BorderTile(this,"Border #42");
       this.placeTileAtTheBottomRightOf(n44, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #42");

       nextTile2=new WaterTile(this,"Water #29");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
        nextTile2=(WaterTile) this.mTileSet.get("Water #29");

       for(int i=30;i<32;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile) this.mTileSet.get("Water #"+i);
       }

       GameTile tmp2;
       GameTile nextTile3=nextTile2;
       for(int i=1;i<5;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile2=new WaterTile(this,"Water #32");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile) this.mTileSet.get("Water #32");

       for(int i=33;i<35;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile) this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #13");
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 6                                                               |
       //-----------------------------------------------------------------------


       GameTile n42=this.mTileSet.get("Border #42");
       nextTile=new BorderTile(this,"Border #41");
       this.placeTileAtTheBottomLeftOf(n42, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #41");

       nextTile2=new WaterTile(this,"Water #35");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #35");

       for(int i=36;i<38;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);
       }

       nextTile3=nextTile2;
       for(int i=5;i<10;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile2=new WaterTile(this,"Water #38");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #38");

       for(int i=39;i<41;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #14");
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 7                                                               |
       //-----------------------------------------------------------------------


       GameTile n41=this.mTileSet.get("Border #41");
       nextTile=new BorderTile(this,"Border #40");
       this.placeTileAtTheBottomLeftOf(n41, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #40");

       nextTile2=new WaterTile(this,"Water #41");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #41");

       nextTile3=new WaterTile(this,"Water #42");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
       nextTile3=(WaterTile)this.mTileSet.get("Water #42");

       for(int i=10;i<18;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile2=new WaterTile(this,"Water #43");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #43");

       nextTile3=new WaterTile(this,"Water #44");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
       nextTile3=(WaterTile)this.mTileSet.get("Water #44");

       nextTile=new BorderTile(this,"Border #15");
       this.placeTileAtTheRightOf(nextTile3, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 8                                                               |
       //-----------------------------------------------------------------------


       GameTile n40=this.mTileSet.get("Border #40");
       nextTile=new BorderTile(this,"Border #39");
       this.placeTileAtTheBottomRightOf(n40, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #39");

       nextTile2=new WaterTile(this,"Water #45");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #45");

       nextTile3=new WaterTile(this,"Water #46");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
       nextTile3=(WaterTile)this.mTileSet.get("Water #46");

       tmp2=null;
       for(int i=18;i<21;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile2=new WaterTile(this,"Water #47",false,true);
       this.placeTileAtTheRightOf(tmp2, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #47");

       nextTile3=nextTile2;
       for(int i=21;i<24;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile3=new WaterTile(this,"Water #48");
       this.placeTileAtTheRightOf(tmp2, nextTile3);
       nextTile3=(WaterTile)this.mTileSet.get("Water #48");

       nextTile2=new WaterTile(this,"Water #49");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #49");

       nextTile=new BorderTile(this,"Border #16");
       this.placeTileAtTheRightOf(nextTile2, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 9                                                               |
       //-----------------------------------------------------------------------


       GameTile n39=this.mTileSet.get("Border #39");
       nextTile=new BorderTile(this,"Border #38");
       this.placeTileAtTheBottomLeftOf(n39, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #38");

       nextTile2=new WaterTile(this,"Water #50");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #50");

       nextTile3=new WaterTile(this,"Water #51");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
       nextTile3=(WaterTile)this.mTileSet.get("Water #51");

       for(int i=24;i<32;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile2=new WaterTile(this,"Water #52");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #52");

       nextTile3=new WaterTile(this,"Water #53");
       this.placeTileAtTheRightOf(nextTile2, nextTile3);
       nextTile3=(WaterTile)this.mTileSet.get("Water #53");

       nextTile=new BorderTile(this,"Border #17");
       this.placeTileAtTheRightOf(nextTile3, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 10                                                              |
       //-----------------------------------------------------------------------


       GameTile n38=this.mTileSet.get("Border #38");
       nextTile=new BorderTile(this,"Border #37");
       this.placeTileAtTheBottomRightOf(n38, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #37");

       nextTile2=new WaterTile(this,"Water #54");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #54");

       for(int i=55;i<57;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);
       }

       nextTile3=nextTile2;
       for(int i=32;i<37;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile2=new WaterTile(this,"Water #55");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #55");

       tmp=null;
       for(int i=56;i<58;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #18");
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 11                                                              |
       //-----------------------------------------------------------------------


       GameTile n37=this.mTileSet.get("Border #37");
       nextTile=new BorderTile(this,"Border #36");
       this.placeTileAtTheBottomRightOf(n37, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #36");

       nextTile2=new WaterTile(this,"Water #58");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #58");

       for(int i=59;i<61;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);;
       }

       nextTile3=nextTile2;

       for(int i=37;i<41;i++){
           tmp2=this.generateRandomTile();
           String tmpname=tmp2.getName();
           tmp2.setAction(TileAction.generateRandomTileAction());
           this.placeTileAtTheRightOf(nextTile3,tmp2);
           nextTile3=this.mTileSet.get(tmpname);
       }

       nextTile2=new WaterTile(this,"Water #61");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #61");

       tmp=null;
       for(int i=62;i<64;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #19");
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 12                                                              |
       //-----------------------------------------------------------------------


       GameTile n36=this.mTileSet.get("Border #36");
       nextTile=new BorderTile(this,"Border #35",true);
       this.placeTileAtTheBottomLeftOf(n36, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #35");

       nextTile2=new WaterTile(this,"Water #64");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile)this.mTileSet.get("Water #64");

       tmp=null;
       for(int i=65;i<75;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile)this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #20",true);
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 13                                                              |
       //-----------------------------------------------------------------------


       GameTile n35=this.mTileSet.get("Border #35");
       nextTile=new BorderTile(this,"Border #34",true);
       this.placeTileAtTheBottomRightOf(n35, nextTile);
       nextTile=(BorderTile)this.mTileSet.get("Border #34");

       nextTile2=new WaterTile(this,"Water #75");
       this.placeTileAtTheRightOf(nextTile, nextTile2);
       nextTile2=(WaterTile) this.mTileSet.get("Water #75");

       tmp=null;
       for(int i=76;i<85;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile) this.mTileSet.get("Water #"+i);;
       }

       nextTile=new BorderTile(this,"Border #21",true);
       this.placeTileAtTheRightOf(tmp, nextTile);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 14                                                              |
       //-----------------------------------------------------------------------


       GameTile n34=this.mTileSet.get("Border #34");
       nextTile=new BorderTile(this,"Border #33",true);
       this.placeTileAtTheBottomRightOf(n34, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #33");

       nextTile3=new BorderTile(this,"Border #32",true);
       this.placeTileAtTheRightOf(nextTile, nextTile3);
       nextTile3=(BorderTile) this.mTileSet.get("Border #32");

       nextTile2=new WaterTile(this,"Water #85");
       this.placeTileAtTheRightOf(nextTile3, nextTile2);
       nextTile2=(WaterTile) this.mTileSet.get("Water #85");

       tmp=null;
       for(int i=86;i<92;i++){
           tmp=new WaterTile(this,"Water #"+i);
           this.placeTileAtTheRightOf(nextTile2,tmp);
           nextTile2=(WaterTile) this.mTileSet.get("Water #"+i);
       }

       nextTile=new BorderTile(this,"Border #23",true);
       this.placeTileAtTheRightOf(tmp, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #23");

       nextTile3=new BorderTile(this,"Border #22",true);
       this.placeTileAtTheRightOf(nextTile, nextTile3);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ligne 15                                                              |
       //-----------------------------------------------------------------------


       GameTile n32=this.mTileSet.get("Border #32");
       nextTile=new BorderTile(this,"Border #31");
       this.placeTileAtTheBottomRightOf(n32, nextTile);
       nextTile=(BorderTile) this.mTileSet.get("Border #31");

       for(int i=30;i>23;i--){
           BorderTile tmps=new BorderTile(this,"Border #"+i);
           this.placeTileAtTheRightOf(nextTile,tmps);
           nextTile=(BorderTile) this.mTileSet.get("Border #"+i);
       }
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Update des attributs des tiles SeaShark & Echappatoires               |
       //-----------------------------------------------------------------------


       WaterTile temp=(WaterTile)this.mTileSet.get("Water #17");
       temp.setIsLandingTile(true);
       this.mTileSet.put("Water #17", temp);
       temp=(WaterTile)this.mTileSet.get("Water #18");
       temp.setIsLandingTile(true);
       this.mTileSet.put("Water #18", temp);
       temp=(WaterTile)this.mTileSet.get("Water #8");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.mTileSet.put("Water #8", temp);
       temp=(WaterTile)this.mTileSet.get("Water #28");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.mTileSet.put("Water #28", temp);
       temp=(WaterTile)this.mTileSet.get("Water #75");
       temp.setIsLandingTile(true);
       this.mTileSet.put("Water #75", temp);
       temp=(WaterTile)this.mTileSet.get("Water #84");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.mTileSet.put("Water #84", temp);
       temp=(WaterTile)this.mTileSet.get("Water #74");
       temp.setIsLandingTile(true);
       this.mTileSet.put("Water #74", temp);
       temp=(WaterTile)this.mTileSet.get("Water #64");
       temp.setIsLandingTile(true);
       temp.setIsBeginningWithSeaShark(true);
       this.mTileSet.put("Water #64", temp);
       //-----------------------------------------------------------------------




       //-----------------------------------------------------------------------
       //Ajout des 5 SeaShark sur les Cases prévues à cet effet                |
       //-----------------------------------------------------------------------

       SeaSerpent ss = null;

       temp=(WaterTile)this.mTileSet.get("Water #64");
       ss = new SeaSerpent();
       ss.moveToTile(null, temp);


       temp=(WaterTile)this.mTileSet.get("Water #84");
       ss = new SeaSerpent();
       ss.moveToTile(null, temp);


       temp=(WaterTile)this.mTileSet.get("Water #28");
       ss = new SeaSerpent();
       ss.moveToTile(null, temp);


       temp=(WaterTile)this.mTileSet.get("Water #8");
       ss = new SeaSerpent();
       ss.moveToTile(null, temp);

       temp=(WaterTile)this.mTileSet.get("Water #47");
       ss = new SeaSerpent();
       ss.moveToTile(null, temp);

       //-----------------------------------------------------------------------

       //Affiche chaques tiles et son detail

       this.printAllTiles();
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
        return mTileSet;
    }

    /**
     * Accesseur pour le premier Tile du board
     *
     * @return Premier Tile de la board.
     */
    public GameTile getFirstTile(){
        return this.mTileSet.get("Border #1");
    }


    /**
     * Génère un tile de type aléatoire
     *
     * @return un tile generé aléatoirement parmis les 3 types.
     */
    public GameTile generateRandomTile(){
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

        Iterator<Map.Entry<String, GameTile>> it=this.mTileSet.entrySet().iterator();
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
     * @return La WaterTile qui a remplacé la tile
     */
    public WaterTile sinkTile(GameLogic logic, GameTile tile) {
        // On remplace la tile par une watertile
        mTileSet.remove(tile.getName());

        WaterTile newTile = new WaterTile(this,tile.getLeftUpperTile(),tile.getRightUpperTile()
                ,tile.getLeftTile(),tile.getRightTile(),tile.getRightBottomTile()
                ,tile.getLeftBottomTile(),"Sunken " + tile.getName(),0);

        // On Modifie les occurences du tile à couler dans les tile adjacents
        if (tile.getLeftTile() != null) {
            GameTile tmp=tile.getLeftTile();
            tmp.setRightTile(newTile);
            //On update le tile dans la hashmap
            mTileSet.put(tmp.getName(), tmp);
        }
        if (tile.getRightTile() != null) {            
            GameTile tmp=tile.getRightTile();
            tmp.setLeftTile(newTile);
            mTileSet.put(tmp.getName(), tmp);
        }
        if (tile.getLeftBottomTile() != null) {
            GameTile tmp=tile.getLeftBottomTile();
            tmp.setRightUpperTile(newTile);
            mTileSet.put(tmp.getName(), tmp);
        }
        if (tile.getRightBottomTile() != null) {
            GameTile tmp=tile.getRightBottomTile();
            tmp.setLeftUpperTile(newTile);
            mTileSet.put(tmp.getName(), tmp);
        }
        if (tile.getLeftUpperTile() != null) {
            GameTile tmp=tile.getLeftUpperTile();
            tmp.setRightBottomTile(newTile);
            mTileSet.put(tmp.getName(), tmp);
        }
        if (tile.getRightUpperTile()!= null) {
            GameTile tmp=tile.getRightUpperTile();
            tmp.setLeftBottomTile(newTile);
            mTileSet.put(tmp.getName(), tmp);
        }

        // On jette les gens à la flotte. On fait une copie de la liste puisqu'elle va être
        // modifiée au fur et à mesure du parcours dans la tile source qui se vide.
        List<GameEntity> entities = new ArrayList<GameEntity>(tile.getEntities());
        for (GameEntity ent : entities) {
            ent.moveToTile(logic, newTile);
            System.out.println("Entity is now in sunken tile " + newTile.getName());
        }

        //On update cette tile dans la hashmap
        mTileSet.put(newTile.getName(), newTile);
        return newTile;
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
        if(baseUpperRightTile != null){
            newTile.setLeftUpperTile(baseUpperRightTile);
        }
        if(baseBottomRightTile != null){
            this.placeTileAtTheBottomLeftOf(newTile, baseBottomRightTile);
        }

        //Update le HashMap
        this.mTileSet.put(base.getName(), base);
        this.mTileSet.put(newTile.getName(), newTile);

        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseBottomRightTile != null){
            baseBottomRightTile.setRightUpperTile(newTile);
            this.mTileSet.put(baseBottomRightTile.getName(), baseBottomRightTile);
        }
        if(baseUpperRightTile != null){
            this.placeTileAtTheBottomRightOf(baseUpperRightTile, newTile);
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
        if(baseUpperLeftTile != null){
            newTile.setRightUpperTile(baseUpperLeftTile);
        }
        if(baseBottomLeftTile != null){
            newTile.setRightBottomTile(baseBottomLeftTile);
        }

        //Update le HashMap
        this.mTileSet.put(base.getName(), base);
        this.mTileSet.put(newTile.getName(), newTile);

        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseBottomLeftTile != null){
            baseBottomLeftTile.setRightUpperTile(newTile);
            this.mTileSet.put(baseBottomLeftTile.getName(), baseBottomLeftTile);
        }
        if(baseUpperLeftTile != null){
            baseUpperLeftTile.setRightBottomTile(newTile);
            this.mTileSet.put(baseUpperLeftTile.getName(), baseUpperLeftTile);
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
        if(baseLeftBottomTile != null){
            newTile.setLeftTile(baseLeftBottomTile);
        }
        if(baseRightTile != null){
            newTile.setRightUpperTile(baseRightTile);
        }

        //Update le HashMap
        this.mTileSet.put(base.getName(), base);
        this.mTileSet.put(newTile.getName(), newTile);

        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseLeftBottomTile != null){
            baseLeftBottomTile.setRightTile(newTile);
            this.mTileSet.put(baseLeftBottomTile.getName(), baseLeftBottomTile);
        }
        if(baseRightTile != null){
            baseRightTile.setLeftBottomTile(newTile);
            this.mTileSet.put(baseRightTile.getName(), baseRightTile);
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
        if(baseRightBottomTile != null){
            newTile.setRightTile(baseRightBottomTile);
        }
        if(baseLeftTile != null){
            newTile.setLeftUpperTile(baseLeftTile);
        }


        //Update le HashMap
        this.mTileSet.put(base.getName(), base);
        this.mTileSet.put(newTile.getName(), newTile);

        //Puis on update les Tiles ajacent pour prendre en compte le nouveau Til ajouté
        if(baseRightBottomTile != null){
            baseRightBottomTile.setLeftTile(newTile);
            this.mTileSet.put(baseRightBottomTile.getName(), baseRightBottomTile);
        }
        if(baseLeftTile != null){
            baseLeftTile.setRightBottomTile(newTile);
            this.mTileSet.put(baseLeftTile.getName(), baseLeftTile);
        }
    }


    public void printAllTiles(){
        Set<String> s=this.mTileSet.keySet();

        for(String key : s){
            System.out.println(this.mTileSet.get(key));
        }
    }

    public boolean hasEntityOfType(Class type) {
        Set<String> s = mTileSet.keySet();

        for (String key : s) {
            GameTile tile = mTileSet.get(key);
            List<GameEntity> ents = tile.getEntities();

            for (GameEntity ent : ents) {
                if (ent.getClass().equals(type)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Retourne si oui ou non il y a une tile au bord de l'eau au niveau spécifié.
     * Exemple, si on a une tile Beach entourée de Mountain, cela renverra false même si
     * une tile au niveau beach existe.
     * @param level Le niveau
     * @return true si il y a une tile au bord de l'eau, false sinon
     */
    public boolean hasTileAtWaterEdge(int level) {
        Iterator<Map.Entry<String, GameTile>> it = this.mTileSet.entrySet().iterator();
        while (it.hasNext()) {
            GameTile tmp = it.next().getValue();
            if (tmp.getHeight() == level) {
                if (isTileAtWaterEdge(tmp)) {
                    return true;
                }
            }
        }

        return false;
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
