/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

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
public class WaterTileTest {
    
    public WaterTileTest() {
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
     * Test of isBeginningWithSeaShark method, of class WaterTile.
     */
    @Test
    public void testIsBeginningWithSeaShark() {
        System.out.println("isBeginningWithSeaShark");
        WaterTile instance = null;
        boolean expResult = false;
        boolean result = instance.isBeginningWithSeaShark();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLandingTile method, of class WaterTile.
     */
    @Test
    public void testIsLandingTile() {
        System.out.println("isLandingTile");
        WaterTile instance = null;
        boolean expResult = false;
        boolean result = instance.isLandingTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIsBeginningWithSeaShark method, of class WaterTile.
     */
    @Test
    public void testSetIsBeginningWithSeaShark() {
        System.out.println("setIsBeginningWithSeaShark");
        boolean mIsBeginningWithSeaShark = false;
        WaterTile instance = null;
        instance.setIsBeginningWithSeaShark(mIsBeginningWithSeaShark);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIsLandingTile method, of class WaterTile.
     */
    @Test
    public void testSetIsLandingTile() {
        System.out.println("setIsLandingTile");
        boolean mIsLandingTile = false;
        WaterTile instance = null;
        instance.setIsLandingTile(mIsLandingTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}