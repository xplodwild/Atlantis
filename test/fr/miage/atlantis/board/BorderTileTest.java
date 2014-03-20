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
public class BorderTileTest {
    
    public BorderTileTest() {
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
     * Test of isEscapeBorder method, of class BorderTile.
     */
    @Test
    public void testIsEscapeBorder() {
        System.out.println("isEscapeBorder");
        BorderTile instance = null;
        boolean expResult = false;
        boolean result = instance.isEscapeBorder();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}