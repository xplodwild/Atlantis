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
import fr.miage.atlantis.board.GameBoard;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void testWaterEdge() {
        // On créé un board avec une seule tile pour le moment
        BeachTile single = new BeachTile(mGameBoard, 5, 5);
        assertTrue(mGameBoard.canPlaceTileAt(5, 5));
        
        // On place la tile
        mGameBoard.placeTileAt(single);
        assertTrue(single.isOnBoard());
        
        // La tile est forcément au bord de l'eau
        assertTrue(mGameBoard.isTileAtWaterEdge(single));
        
        // On ne peut plus placer de tiles à cet endroit
        assertFalse(mGameBoard.canPlaceTileAt(5, 5));
        
        // On a bien une tile au niveau Plage
        assertTrue(mGameBoard.hasTileAtLevel(single.getHeight()));
    }
    
    @Test
    public void testSinkTile() {
        // On créé un board avec une seule tile pour le moment
        BeachTile single = new BeachTile(mGameBoard, 5, 5);
        
        // On place la tile
        mGameBoard.placeTileAt(single);
        assertTrue(single.isOnBoard());
        
        // On fait couler la tile
        mGameBoard.sinkTile(single);
        assertFalse(single.isOnBoard());
        
        // On peut replacer une tile à l'endroit cité
        assertTrue(mGameBoard.canPlaceTileAt(5, 5));
    }
    
}