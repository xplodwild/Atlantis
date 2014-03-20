/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucie
 */
public class GameLogTest {
    
    public GameLogTest() {
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
     * Test of logTurn method, of class GameLog.
     */
    @Test
    public void testLogTurn() {
        System.out.println("logTurn");
        GameTurn i = null;
        GameLog instance = new GameLog();
        instance.logTurn(i);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTurn method, of class GameLog.
     */
    @Test
    public void testGetTurn() {
        System.out.println("getTurn");
        int i = 0;
        GameLog instance = new GameLog();
        GameTurn expResult = null;
        GameTurn result = instance.getTurn(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}