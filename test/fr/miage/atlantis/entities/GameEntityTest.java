/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

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
public class GameEntityTest {
    
    public GameEntityTest() {
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
     * Test of isDead method, of class GameEntity.
     * On teste que l'entité est bien tuée
     */
    @Test
    public void testIsDead() {
        System.out.println("isDead");
        GameLogic lg = new NullGameLogic();
        GameBoard board = new GameBoard();
        GameTile tile = board.getTileSet().get("Water #37");
        Shark requin = new Shark();
        
        //Cas où l'on tue une entité
        requin.moveToTile(lg, tile);
        assertTrue(tile.getEntities().contains(requin));
        
        requin.die(lg);
        assertTrue(requin.isDead());      
    
    }

    /**
     * Test of moveToTile method, of class GameEntity.
     *
    @Test
    public void testMoveToTile() {
        System.out.println("moveToTile");
        GameLogic logic = null;
        GameTile tile = null;
        GameEntity instance = null;
        boolean expResult = false;
        boolean result = instance.moveToTile(logic, tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of die method, of class GameEntity.
     *
    @Test
    public void testDie() {
        System.out.println("die");
        GameLogic logic = null;
        GameEntity instance = null;
        instance.die(logic);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of spawn method, of class GameEntity.
     *
    @Test
    public void testSpawn() {
        System.out.println("spawn");
        GameLogic logic = null;
        GameEntity instance = null;
        instance.spawn(logic);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onEntityCross method, of class GameEntity.
     *
    @Test
    public void testOnEntityCross() {
        System.out.println("onEntityCross");
        GameLogic logic = null;
        GameEntity ent = null;
        GameEntity instance = null;
        boolean expResult = false;
        boolean result = instance.onEntityCross(logic, ent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

  
}