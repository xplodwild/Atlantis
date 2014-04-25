/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.NullGameLogic;
import fr.miage.atlantis.logic.GameLogic;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Loris
 */
public class PlayerTokenTest {
    
    /**
     *
     */
    public PlayerTokenTest() {
    }
    
    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of moveToTile method, of class PlayerToken.
     * Test que l'entité se déplace bien sur les tiles
     */
    @Test
    public void testMoveToTile() {
        System.out.println("moveToTile");
        GameLogic logic = new NullGameLogic();
        GameBoard board = new GameBoard(true);
        
        GameTile tile = board.getTileSet().get("Beach #15");
        GameTile tile1 = board.getTileSet().get("Water #37");
        Player luigi = new Player("Luigi", 1,false);
        PlayerToken pt1 = new PlayerToken(luigi, 5);
        
        //1er test on met sur le pion sur une tile
        pt1.moveToTile(logic, tile);
        assertTrue(tile.getEntities().contains(pt1));
        
        //2ème test : on déplace le pion sur une autre tile
        pt1.moveToTile(logic, tile1);
        assertTrue(tile1.getEntities().contains(pt1));
        
        //3ème test : on vérifie que le pion n'est plus sur la première tile
        assertFalse(tile.getEntities().contains(pt1));

    }

}