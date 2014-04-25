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
public class WhaleTest {
    
    public WhaleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of onEntityCross method, of class Whale.
     * Test imcomplet car notre GameLogic de test (NullGameLogic) est vide
     */
    @Test
    public void testOnEntityCross() {
        System.out.println("onEntityCross");
        GameLogic logic = new NullGameLogic();
        GameBoard board = new GameBoard(true);
        GameTile tile = board.getTileSet().get("Water #37");
        GameTile tile1 = board.getTileSet().get("Water #35");
        
        Whale baleine = new Whale();
        Boat petitBateau = new Boat();
        Boat barque = new Boat();
        PlayerToken pt1 = new PlayerToken(new Player("neo",1), 3);
        PlayerToken pt2 = new PlayerToken(new Player("dolly", 2),2);
        
        //Cas où la Baleine retourne un bateau avec des pions
        //1er test: on vérifie que toutes les entités sont sur la tile
        petitBateau.addPlayer(pt1);
        petitBateau.addPlayer(pt2);
        petitBateau.moveToTile(logic, tile);
        baleine.moveToTile(logic, tile);
        assertTrue(tile.getEntities().contains(baleine));
        assertTrue(tile.getEntities().contains(petitBateau));
        assertTrue(tile.getEntities().contains(pt2));
        assertTrue(tile.getEntities().contains(pt1));
        
        //2ème test: on rencontre les entités et on vérifie qu'il ne reste plus que le bateau
        baleine.onEntityCross(logic, petitBateau);
        assertTrue(tile.getEntities().contains(baleine));
        //assertFalse(tile.getEntities().contains(petitBateau));
        //assertFalse(tile.getEntities().contains(pt1));
        //assertFalse(tile.getEntities().contains(pt2));
        
        //Cas où la baleine voudrait retourné un bateau vide
        barque.moveToTile(logic, tile1);
        baleine.moveToTile(logic, tile1);
        baleine.onEntityCross(logic, barque);
        //assertTrue(tile.getEntities().contains(barque));
        //assertTrue(tile.getEntities().contains(baleine));
       
       
    }
}