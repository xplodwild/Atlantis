/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Loris
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({fr.miage.atlantis.board.WaterTileTest.class, fr.miage.atlantis.board.BorderTileTest.class, fr.miage.atlantis.board.BeachTileTest.class, fr.miage.atlantis.board.MountainTileTest.class, fr.miage.atlantis.board.TileActionTest.class, fr.miage.atlantis.board.GameTileTest.class, fr.miage.atlantis.board.ForestTileTest.class, fr.miage.atlantis.board.GameBoardTest.class})
public class BoardSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}