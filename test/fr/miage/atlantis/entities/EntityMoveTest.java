/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

import fr.miage.atlantis.board.GameTile;
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
public class EntityMoveTest {
    
    public EntityMoveTest() {
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
     * Test of getSource method, of class EntityMove.
     */
    @Test
    public void testGetSource() {
        System.out.println("getSource");
        EntityMove instance = null;
        GameTile expResult = null;
        GameTile result = instance.getSource();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDestination method, of class EntityMove.
     */
    @Test
    public void testGetDestination() {
        System.out.println("getDestination");
        EntityMove instance = null;
        GameTile expResult = null;
        GameTile result = instance.getDestination();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntity method, of class EntityMove.
     */
    @Test
    public void testGetEntity() {
        System.out.println("getEntity");
        EntityMove instance = null;
        GameEntity expResult = null;
        GameEntity result = instance.getEntity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTurnNumber method, of class EntityMove.
     */
    @Test
    public void testGetTurnNumber() {
        System.out.println("getTurnNumber");
        EntityMove instance = null;
        int expResult = 0;
        int result = instance.getTurnNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}