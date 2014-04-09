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
public class GameRenderListenerTest {
    
    public GameRenderListenerTest() {
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
     * Test of onTurnStarted method, of class GameRenderListener.
     */
    @Test
    public void testOnTurnStarted() {
        System.out.println("onTurnStarted");
        GameRenderListener instance = new GameRenderListenerImpl();
        instance.onTurnStarted();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onPlayedTileAction method, of class GameRenderListener.
     */
    @Test
    public void testOnPlayedTileAction() {
        System.out.println("onPlayedTileAction");
        GameRenderListener instance = new GameRenderListenerImpl();
        instance.onPlayedTileAction();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onUnitMoveFinished method, of class GameRenderListener.
     */
    @Test
    public void testOnUnitMoveFinished() {
        System.out.println("onUnitMoveFinished");
        GameRenderListener instance = new GameRenderListenerImpl();
        instance.onUnitMoveFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onDiceRollFinished method, of class GameRenderListener.
     */
    @Test
    public void testOnDiceRollFinished() {
        System.out.println("onDiceRollFinished");
        GameRenderListener instance = new GameRenderListenerImpl();
        instance.onDiceRollFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onSinkTileFinished method, of class GameRenderListener.
     */
    @Test
    public void testOnSinkTileFinished() {
        System.out.println("onSinkTileFinished");
        GameRenderListener instance = new GameRenderListenerImpl();
        instance.onSinkTileFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onEntityActionFinished method, of class GameRenderListener.
     */
    @Test
    public void testOnEntityActionFinished() {
        System.out.println("onEntityActionFinished");
        GameRenderListener instance = new GameRenderListenerImpl();
        instance.onEntityActionFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class GameRenderListenerImpl implements GameRenderListener {

        public void onTurnStarted() {
        }

        public void onPlayedTileAction() {
        }

        public void onUnitMoveFinished() {
        }

        public void onDiceRollFinished() {
        }

        public void onSinkTileFinished() {
        }

        public void onEntityActionFinished() {
        }

        public void onInitialTokenPutDone() {
            
        }

        public void onInitialBoatPutDone() {
            
        }
    }
}