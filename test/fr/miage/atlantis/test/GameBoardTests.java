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
        
    }
    
    @Test
    public void testSinkTile() {
       
    }
    
    @Test
    public void testGameTile() {
        
    }
    
}