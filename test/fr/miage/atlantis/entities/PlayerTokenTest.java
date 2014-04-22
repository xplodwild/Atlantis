/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
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
    
    public PlayerTokenTest() {
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
     * Test of moveToTile method, of class PlayerToken.
     */
    @Test
    public void testMoveToTile() {
        System.out.println("moveToTile");
        GameLogic logic = null;
        GameTile tile = null;
        PlayerToken instance = null;
        boolean expResult = false;
        boolean result = instance.moveToTile(logic, tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getState method, of class PlayerToken.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        PlayerToken instance = null;
        int expResult = 0;
        int result = instance.getState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPoints method, of class PlayerToken.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        PlayerToken instance = null;
        int expResult = 0;
        int result = instance.getPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer method, of class PlayerToken.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        PlayerToken instance = null;
        Player expResult = null;
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoat method, of class PlayerToken.
     */
    @Test
    public void testGetBoat() {
        System.out.println("getBoat");
        PlayerToken instance = null;
        Boat expResult = null;
        Boat result = instance.getBoat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setState method, of class PlayerToken.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        int state = 0;
        PlayerToken instance = null;
        instance.setState(state);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBoat method, of class PlayerToken.
     */
    @Test
    public void testSetBoat() {
        System.out.println("setBoat");
        Boat b = null;
        PlayerToken instance = null;
        instance.setBoat(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}