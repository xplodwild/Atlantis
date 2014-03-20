/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

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
public class TileActionTest {
    
    public TileActionTest() {
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
     * Test of generateRandomTileActionBeach method, of class TileAction.
     */
    @Test
    public void testGenerateRandomTileActionBeach() {
        System.out.println("generateRandomTileActionBeach");
        TileAction expResult = null;
        TileAction result = TileAction.generateRandomTileActionBeach();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateRandomTileActionMountain method, of class TileAction.
     */
    @Test
    public void testGenerateRandomTileActionMountain() {
        System.out.println("generateRandomTileActionMountain");
        TileAction expResult = null;
        TileAction result = TileAction.generateRandomTileActionMountain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateRandomTileActionForest method, of class TileAction.
     */
    @Test
    public void testGenerateRandomTileActionForest() {
        System.out.println("generateRandomTileActionForest");
        TileAction expResult = null;
        TileAction result = TileAction.generateRandomTileActionForest();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of use method, of class TileAction.
     */
    @Test
    public void testUse() {
        System.out.println("use");
        GameTile tile = null;
        GameLogic logic = null;
        TileAction instance = null;
        instance.use(tile, logic);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isImmediate method, of class TileAction.
     */
    @Test
    public void testIsImmediate() {
        System.out.println("isImmediate");
        TileAction instance = null;
        boolean expResult = false;
        boolean result = instance.isImmediate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTriggerable method, of class TileAction.
     */
    @Test
    public void testIsTriggerable() {
        System.out.println("isTriggerable");
        TileAction instance = null;
        boolean expResult = false;
        boolean result = instance.isTriggerable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isVolcano method, of class TileAction.
     */
    @Test
    public void testIsVolcano() {
        System.out.println("isVolcano");
        TileAction instance = null;
        boolean expResult = false;
        boolean result = instance.isVolcano();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAction method, of class TileAction.
     */
    @Test
    public void testGetAction() {
        System.out.println("getAction");
        TileAction instance = null;
        int expResult = 0;
        int result = instance.getAction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntity method, of class TileAction.
     */
    @Test
    public void testGetEntity() {
        System.out.println("getEntity");
        TileAction instance = null;
        int expResult = 0;
        int result = instance.getEntity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class TileAction.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        TileAction instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}