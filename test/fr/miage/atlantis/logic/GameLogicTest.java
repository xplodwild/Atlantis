/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.logic;

import fr.miage.atlantis.GameDice;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
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
public class GameLogicTest {
    
    public GameLogicTest() {
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
     * Test of prepareGame method, of class GameLogic.
     */
    @Test
    public void testPrepareGame() {
        System.out.println("prepareGame");
        String[] players = null;
        GameLogic instance = new GameLogicImpl();
        instance.prepareGame(players);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startGame method, of class GameLogic.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        GameLogic instance = new GameLogicImpl();
        instance.startGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextTurn method, of class GameLogic.
     */
    @Test
    public void testNextTurn() {
        System.out.println("nextTurn");
        GameLogic instance = new GameLogicImpl();
        instance.nextTurn();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextPlayer method, of class GameLogic.
     */
    @Test
    public void testNextPlayer() {
        System.out.println("nextPlayer");
        Player p = null;
        GameLogic instance = new GameLogicImpl();
        Player expResult = null;
        Player result = instance.nextPlayer(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFinished method, of class GameLogic.
     */
    @Test
    public void testIsFinished() {
        System.out.println("isFinished");
        GameLogic instance = new GameLogicImpl();
        boolean expResult = false;
        boolean result = instance.isFinished();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onUnitMove method, of class GameLogic.
     */
    @Test
    public void testOnUnitMove() {
        System.out.println("onUnitMove");
        GameEntity ent = null;
        GameTile dest = null;
        GameLogic instance = new GameLogicImpl();
        instance.onUnitMove(ent, dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoard method, of class GameLogic.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        GameLogic instance = new GameLogicImpl();
        GameBoard expResult = null;
        GameBoard result = instance.getBoard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentTurn method, of class GameLogic.
     */
    @Test
    public void testGetCurrentTurn() {
        System.out.println("getCurrentTurn");
        GameLogic instance = new GameLogicImpl();
        GameTurn expResult = null;
        GameTurn result = instance.getCurrentTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayers method, of class GameLogic.
     */
    @Test
    public void testGetPlayers() {
        System.out.println("getPlayers");
        GameLogic instance = new GameLogicImpl();
        Player[] expResult = null;
        Player[] result = instance.getPlayers();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDice method, of class GameLogic.
     */
    @Test
    public void testGetDice() {
        System.out.println("getDice");
        GameLogic instance = new GameLogicImpl();
        GameDice expResult = null;
        GameDice result = instance.getDice();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of boot method, of class GameLogic.
     */
    @Test
    public void testBoot() {
        System.out.println("boot");
        GameLogic instance = new GameLogicImpl();
        instance.boot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of requestPick method, of class GameLogic.
     */
    @Test
    public void testRequestPick() {
        System.out.println("requestPick");
        GameLogic.EntityPickRequest entRq = null;
        GameLogic.TilePickRequest tileRq = null;
        GameLogic instance = new GameLogicImpl();
        instance.requestPick(entRq, tileRq);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onEntityPicked method, of class GameLogic.
     */
    @Test
    public void testOnEntityPicked() {
        System.out.println("onEntityPicked");
        GameEntity ent = null;
        GameLogic instance = new GameLogicImpl();
        instance.onEntityPicked(ent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onTilePicked method, of class GameLogic.
     */
    @Test
    public void testOnTilePicked() {
        System.out.println("onTilePicked");
        GameTile tile = null;
        GameLogic instance = new GameLogicImpl();
        instance.onTilePicked(tile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class GameLogicImpl extends GameLogic {

        public void boot() {
        }

        public void requestPick(EntityPickRequest entRq, TilePickRequest tileRq) {
        }

        public void onEntityPicked(GameEntity ent) {
        }

        public void onTilePicked(GameTile tile) {
        }
    }
}