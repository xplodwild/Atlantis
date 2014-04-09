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
import fr.miage.atlantis.entities.PlayerToken;
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
public class GameTurnListenerTest {
    
    public GameTurnListenerTest() {
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
     * Test of onTurnStart method, of class GameTurnListener.
     */
    @Test
    public void testOnTurnStart() {
        System.out.println("onTurnStart");
        Player p = null;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onTurnStart(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onPlayTileAction method, of class GameTurnListener.
     */
    @Test
    public void testOnPlayTileAction() {
        System.out.println("onPlayTileAction");
        GameTile tile = null;
        TileAction action = null;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onPlayTileAction(tile, action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onUnitMove method, of class GameTurnListener.
     */
    @Test
    public void testOnUnitMove() {
        System.out.println("onUnitMove");
        GameEntity ent = null;
        GameTile dest = null;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onUnitMove(ent, dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onDiceRoll method, of class GameTurnListener.
     */
    @Test
    public void testOnDiceRoll() {
        System.out.println("onDiceRoll");
        int face = 0;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onDiceRoll(face);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onSinkTile method, of class GameTurnListener.
     */
    @Test
    public void testOnSinkTile() {
        System.out.println("onSinkTile");
        GameTile tile = null;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onSinkTile(tile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onEntityAction method, of class GameTurnListener.
     */
    @Test
    public void testOnEntityAction() {
        System.out.println("onEntityAction");
        GameEntity source = null;
        GameEntity target = null;
        int action = 0;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onEntityAction(source, target, action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onEntitySpawn method, of class GameTurnListener.
     */
    @Test
    public void testOnEntitySpawn() {
        System.out.println("onEntitySpawn");
        GameEntity spawned = null;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onEntitySpawn(spawned);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onBoardBoat method, of class GameTurnListener.
     */
    @Test
    public void testOnBoardBoat() {
        System.out.println("onBoardBoat");
        PlayerToken player = null;
        Boat b = null;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onBoardBoat(player, b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onUnitDie method, of class GameTurnListener.
     */
    @Test
    public void testOnUnitDie() {
        System.out.println("onUnitDie");
        GameEntity zombie = null;
        GameTurnListener instance = new GameTurnListenerImpl();
        instance.onUnitDie(zombie);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class GameTurnListenerImpl implements GameTurnListener {

        public void onTurnStart(Player p) {
        }

        public void onPlayTileAction(GameTile tile, TileAction action) {
        }

        public void onUnitMove(GameEntity ent, GameTile dest) {
        }

        public void onDiceRoll(int face) {
        }

        public void onSinkTile(GameTile tile) {
        }

        public void onEntityAction(GameEntity source, GameEntity target, int action) {
        }

        public void onEntitySpawn(GameEntity spawned) {
        }

        public void onBoardBoat(PlayerToken player, Boat b) {
        }

        public void onUnitDie(GameEntity zombie) {
        }

        public void onInitialTokenPut(PlayerToken pt) {
            
        }

        public void onInitialBoatPut(Boat b) {
            
        }

        public void onPlayerDismountBoat(PlayerToken player, Boat b) {
            
        }

        public void onTileWhirl(GameTile tile) {
           
        }

        public void onTileVolcano() {
            
        }

        public void onCancellableEntityAction(GameEntity source, GameEntity target, int action) {
            
        }

        public void onCancelAction() {
            
        }
    }
}