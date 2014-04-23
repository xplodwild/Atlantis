/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

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
     */
    @Test
    public void testIsDead() {
        System.out.println("isDead");
        GameEntity instance = null;
        boolean expResult = false;
        boolean result = instance.isDead();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveToTile method, of class GameEntity.
     */
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
     */
    @Test
    public void testDie() {
        System.out.println("die");
        GameLogic logic = new NullGameLogic();
        
        // On a un requn vivant
        Shark sk = new Shark();
        assertFalse(sk.isDead());
        
        // On le tue
        sk.die(logic);
        
        //il est mort
        assertTrue(sk.isDead());
        
        // Test de cas improbable
        try{
            sk.die(null);
            fail("On devrait avoir eu un NullPointerException");
        }catch(Exception e){
            assertTrue(true);
        }
        // A cause de l'absence de gamelogic on ne vérifie pas qu'il enleve le
        // requin de la tile.
    }

    /**
     * Test of spawn method, of class GameEntity.
     * Méthode non-implémentée
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
    */
}