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
public class SeaSerpentTest {
    
    public SeaSerpentTest() {
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
     * Test of onEntityCross method, of class SeaSerpent.
     * Test imcomplet car notre GameLogic de test (NullGameLogic) est vide
     */
    @Test
    public void testOnEntityCross() {
        System.out.println("onEntityCross");
        GameLogic logic = new NullGameLogic();
        GameBoard board = new GameBoard(true);
        GameTile tile = board.getTileSet().get("Water #37");
        GameTile tile1 = board.getTileSet().get("Water #36");
        GameTile tile2 = board.getTileSet().get("Water #35");
        
        Player mario = new Player("Mario",1,false);
        PlayerToken pt1 = new PlayerToken(mario, 3);
        PlayerToken pt2 = new PlayerToken(new Player("lu", 1,false), 2);
        SeaSerpent serpent = new SeaSerpent();
        Boat petitBateau = new Boat();
        
        //Cas où le pion n'est pas sur un bateau
        //1er test : on ajoute les entités à la tile et on vérifie qu'elles sont dessus
        pt1.moveToTile(logic, tile);
        serpent.moveToTile(logic, tile);
        assertTrue(tile.getEntities().contains(pt1));
        assertTrue(tile.getEntities().contains(serpent));
        
        //2ème test : le serpent mange le pion, on vérifie que le pion n'est plus sur la tile
        serpent.onEntityCross(logic, pt1);
        assertTrue(tile.getEntities().contains(serpent));
        //assertFalse(tile.getEntities().contains(pt1));
        
        //Cas où le serpent retourne un bateau vide           
        //1er test : on ajoute les entités et on teste si les 2 entités sont sur la même tile
        petitBateau.moveToTile(logic, tile1);
        serpent.moveToTile(logic, tile1);
        assertTrue(tile1.getEntities().contains(serpent));
        assertTrue(tile1.getEntities().contains(petitBateau));
        
        //2ème test : le serpent retourne le bateau, on vérifie qu'il n'y ait plus de bateau
        serpent.onEntityCross(logic, petitBateau);      
        assertTrue(tile1.getEntities().contains(serpent));
        //assertFalse(tile1.getEntities().contains(petitBateau));
        
        //Cas où le serpent retourne un bateau avec des pions
        //1er test : on vérifie que toutes les entités sont sur la tile
        petitBateau.addPlayer(pt1);
        petitBateau.addPlayer(pt2);
        petitBateau.moveToTile(logic, tile2);
        serpent.moveToTile(logic, tile2);
        assertTrue(tile2.getEntities().contains(petitBateau));
        assertTrue(tile2.getEntities().contains(serpent));
        
        //2ème test : on vérifie que le serpent a bien tout mangé (bateau + pion)
        serpent.onEntityCross(logic, petitBateau);
        assertTrue(tile2.getEntities().contains(serpent));
        //assertFalse(tile2.getEntities().contains(petitBateau));
        //assertFalse(tile2.getEntities().contains(pt1));
        //assertFalse(tile2.getEntities().contains(pt2));
        
        
             
    }
}