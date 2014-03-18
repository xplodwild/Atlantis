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
package fr.miage.atlantis.test;

import fr.miage.atlantis.board.BeachTile;
import fr.miage.atlantis.board.BorderTile;
import fr.miage.atlantis.board.ForestTile;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.MountainTile;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Guigui
 */
public class GameBoardTests extends TestCase {
    
    private GameBoard mGameBoard;
    
    public GameBoardTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mGameBoard = new GameBoard();
    }
    
    @After
    public void tearDown() {
    }
    
    // ============
    // TEST METHODS
    // ============
    
    @Test
    public void testInstanciation() {
        // On créé un board
        assertTrue(this.mGameBoard.getFirstTile() instanceof BorderTile);
        
        //Teste si la frontière 47 est présente
        assertTrue(this.mGameBoard.getTileSet().get("Border #47").isOnBoard());
        
        // La tile est forcément au bord de l'eau
        assertTrue(this.mGameBoard.getTileSet().get("Random #1").getLeftUpperTile() instanceof WaterTile);
                      
        
    }
    
    @Test
    public void testSinkTile() {
       
    }
    
    @Test
    public void testGameTile() {
        GameTile tiles[] = new GameTile[5];
        
        // Test de position
        tiles[0] = new BeachTile(mGameBoard,"Beach Test #1");
        tiles[1] = new ForestTile(mGameBoard,"Forest Test #1");
        tiles[2] = new MountainTile(mGameBoard,"Mountain Test #1");
        tiles[3] = new WaterTile(mGameBoard, "Water Test #1");
        tiles[4] = new WaterTile(mGameBoard,"Water Test #2");
    
        
        // Test ajout d'entités sur une tile d'eau
        Boat b = new Boat();
        Shark s = new Shark();
        Whale w = new Whale();
        SeaSerpent k = new SeaSerpent();
        tiles[3].addEntity(b);
        
        assertEquals(tiles[3].getEntities().size(), 1);
        
        // On ne peut pas ajouter une entité à une autre tile sans la détacher
        try {
            tiles[4].addEntity(b);
            assertTrue(false);
        } catch (IllegalStateException e) {
            // Cette exception DOIT se déclencher
            assertTrue(true);
        }
        
        tiles[3].removeEntity(b);
        
        // Par contre, on ne peut pas ajouter un bateau/requin/baleine/kraken
        // sur de la terre!
        try {
            tiles[0].addEntity(b);
            assertTrue(false);
        } catch (IllegalStateException e) {
            // Cette exception DOIT se déclencher
            assertTrue(true);
        }
        try {
            tiles[0].addEntity(s);
            assertTrue(false);
        } catch (IllegalStateException e) {
            // Cette exception DOIT se déclencher
            assertTrue(true);
        }
        try {
            tiles[0].addEntity(w);
            assertTrue(false);
        } catch (IllegalStateException e) {
            // Cette exception DOIT se déclencher
            assertTrue(true);
        }
        try {
            tiles[0].addEntity(k);
            assertTrue(false);
        } catch (IllegalStateException e) {
            // Cette exception DOIT se déclencher
            assertTrue(true);
        }
        
        //@Rule
        ExpectedException exception = ExpectedException.none();

        //@Test  
        exception.expect(IndexOutOfBoundsException.class);
        
    }
    
}