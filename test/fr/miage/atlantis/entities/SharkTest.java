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
public class SharkTest {
    
    public SharkTest() {
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
     * Test of onEntityCross method, of class Shark.
     */
    @Test
    public void testOnEntityCross() {
        System.out.println("onEntityCross");
        GameLogic logic = new NullGameLogic();
        GameBoard board = new GameBoard();
        GameTile tile = board.getTileSet().get("Water #37");
        Player winnie = new Player("Winnie", 1);
        PlayerToken pt1 = new PlayerToken(winnie, 3);
        Shark requin = new Shark();
        
        //Cas d'un pion n'étant pas sur un bateau 
        //1er test: on ajoute les entités sur la tile et on vérifie qu'ils sont tous dessus
        pt1.moveToTile(logic, tile);
        requin.moveToTile(logic, tile);
        assertTrue(tile.getEntities().contains(pt1));
        assertTrue(tile.getEntities().contains(requin));
        
        //2ème test : le requin mange le pion et on vérifie, que le pion n'est plus là mais qu'il reste le requin
        requin.onEntityCross(logic, pt1);
        assertTrue(tile.getEntities().contains(requin));
        //assertFalse(tile.getEntities().contains(pt1));
        
       
        //Cas d'un pion sur un bateau
        GameTile tile1 = board.getTileSet().get("Water #35");
        Boat petitBateau = new Boat();
        //1er test : on ajoute les entités sur la tile et on vérifie qu'ils sont dessus
        petitBateau.addPlayer(pt1);
        petitBateau.moveToTile(logic, tile1);
        requin.moveToTile(logic, tile1);
        assertTrue(tile1.getEntities().contains(petitBateau));
        assertTrue(tile1.getEntities().contains(requin));
        
        //2ème test : le requin ne mange pas le pion puisqu'il est sur un bateau, on vérifie qu'il reste toujours le pion, le bateau et le requin
        requin.onEntityCross(logic, petitBateau);
        assertTrue(tile1.getEntities().contains(petitBateau));
        assertTrue(tile1.getEntities().contains(pt1));
        assertTrue(tile1.getEntities().contains(requin));
        
   
    }
}