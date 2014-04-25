/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.logic;

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.NullGameLogic;
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
    
    /**
     *
     */
    public GameLogTest() {
    }
    
    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of logTurn method, of class GameLog.
     */
    @Test
    public void testLogTurn() {
        System.out.println("logTurn");
        GameTurn gt = new GameTurn(new NullGameLogic(), new Player("P1",0,false));
        GameLog instance = new GameLog();
        instance.logTurn(gt);
        
        assertEquals(gt,instance.getTurn(0));
        
    }

    /**
     * Test of getTurn method, of class GameLog.
     */
    @Test
    public void testGetTurn() {
        System.out.println("getTurn");
        GameTurn gt = new GameTurn(new NullGameLogic(), new Player("P1",0,false));
        GameLog instance = new GameLog();
        instance.logTurn(gt);
        
        assertEquals(gt,instance.getTurn(0));
        
        try{
            GameTurn gtu = instance.getTurn(1);
            if(gtu==null) throw new Exception();
            fail("On peut getter un tour qui n'existe pas, c'est étrange.");
        }catch(Exception e){
            assertTrue(true);
        }
    }
}