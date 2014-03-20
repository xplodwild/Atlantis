/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.logic;

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
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
public class GameTurnTest {
    
    public GameTurnTest() {
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
     * Test of startTurn method, of class GameTurn.
     */
    @Test
    public void testStartTurn() {
        System.out.println("startTurn");
        GameTurn instance = null;
        instance.startTurn();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveEntity method, of class GameTurn.
     */
    @Test
    public void testMoveEntity_GameEntity_GameTile() {
        System.out.println("moveEntity");
        GameEntity ent = null;
        GameTile dest = null;
        GameTurn instance = null;
        instance.moveEntity(ent, dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveEntity method, of class GameTurn.
     */
    @Test
    public void testMoveEntity_GameEntity_Boat() {
        System.out.println("moveEntity");
        GameEntity ent = null;
        Boat dest = null;
        GameTurn instance = null;
        instance.moveEntity(ent, dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveDiceEntity method, of class GameTurn.
     */
    @Test
    public void testMoveDiceEntity() {
        System.out.println("moveDiceEntity");
        GameEntity ent = null;
        GameTile dest = null;
        GameTurn instance = null;
        instance.moveDiceEntity(ent, dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rollDice method, of class GameTurn.
     */
    @Test
    public void testRollDice() {
        System.out.println("rollDice");
        GameTurn instance = null;
        int expResult = 0;
        int result = instance.rollDice();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemainingDiceMoves method, of class GameTurn.
     */
    @Test
    public void testGetRemainingDiceMoves() {
        System.out.println("getRemainingDiceMoves");
        GameTurn instance = null;
        int expResult = 0;
        int result = instance.getRemainingDiceMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasSunkLandTile method, of class GameTurn.
     */
    @Test
    public void testHasSunkLandTile() {
        System.out.println("hasSunkLandTile");
        GameTurn instance = null;
        boolean expResult = false;
        boolean result = instance.hasSunkLandTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sinkLandTile method, of class GameTurn.
     */
    @Test
    public void testSinkLandTile() {
        System.out.println("sinkLandTile");
        GameTile tile = null;
        GameTurn instance = null;
        instance.sinkLandTile(tile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of useRemoteTile method, of class GameTurn.
     */
    @Test
    public void testUseRemoteTile() {
        System.out.println("useRemoteTile");
        TileAction action = null;
        GameTurn instance = null;
        instance.useRemoteTile(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of useLocalTile method, of class GameTurn.
     */
    @Test
    public void testUseLocalTile() {
        System.out.println("useLocalTile");
        TileAction action = null;
        GameTurn instance = null;
        instance.useLocalTile(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onTurnStarted method, of class GameTurn.
     */
    @Test
    public void testOnTurnStarted() {
        System.out.println("onTurnStarted");
        GameTurn instance = null;
        instance.onTurnStarted();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onPlayedTileAction method, of class GameTurn.
     */
    @Test
    public void testOnPlayedTileAction() {
        System.out.println("onPlayedTileAction");
        GameTurn instance = null;
        instance.onPlayedTileAction();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onUnitMoveFinished method, of class GameTurn.
     */
    @Test
    public void testOnUnitMoveFinished() {
        System.out.println("onUnitMoveFinished");
        GameTurn instance = null;
        instance.onUnitMoveFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onDiceRollFinished method, of class GameTurn.
     */
    @Test
    public void testOnDiceRollFinished() {
        System.out.println("onDiceRollFinished");
        GameTurn instance = null;
        instance.onDiceRollFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onSinkTileFinished method, of class GameTurn.
     */
    @Test
    public void testOnSinkTileFinished() {
        System.out.println("onSinkTileFinished");
        GameTurn instance = null;
        instance.onSinkTileFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onEntityActionFinished method, of class GameTurn.
     */
    @Test
    public void testOnEntityActionFinished() {
        System.out.println("onEntityActionFinished");
        GameTurn instance = null;
        instance.onEntityActionFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer method, of class GameTurn.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        GameTurn instance = null;
        Player expResult = null;
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemainingMoves method, of class GameTurn.
     */
    @Test
    public void testGetRemainingMoves() {
        System.out.println("getRemainingMoves");
        GameTurn instance = null;
        int expResult = 0;
        int result = instance.getRemainingMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndOfTurn method, of class GameTurn.
     */
    @Test
    public void testGetEndOfTurn() {
        System.out.println("getEndOfTurn");
        GameTurn instance = null;
        boolean expResult = false;
        boolean result = instance.getEndOfTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasRolledDice method, of class GameTurn.
     */
    @Test
    public void testHasRolledDice() {
        System.out.println("hasRolledDice");
        GameTurn instance = null;
        boolean expResult = false;
        boolean result = instance.hasRolledDice();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}