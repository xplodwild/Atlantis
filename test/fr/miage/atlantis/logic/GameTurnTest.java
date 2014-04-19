/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.logic;

import fr.miage.atlantis.GameDice;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.NullGameLogic;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
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
     * Test of moveEntity method, of class GameTurn.
     */
    @Test
    public void testMoveEntity_GameEntity_GameTile() {
        System.out.println("moveEntity");
        
        Player joueur = new Player("lu",1);
        NullGameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, joueur);
        GameBoard board = new GameBoard();       
        
        //on ajoute un pion sur une tile et on test qu'elle est dessus.
        GameEntity pion = new PlayerToken(joueur,6);
        GameTile tileDep = board.getTileSet().get("Water #35");
        tileDep.addEntity(pion);       
        assertTrue(tileDep.getEntities().contains(pion));
        
        //on déplace le pion sur une autre tile et on test que le pion s'est déplacé.
        GameTile tile = board.getTileSet().get("Water #37");
        instance.moveEntity(pion, tile);               
        assertTrue(tile.getEntities().contains(pion));
        
       /*on teste si le pion n'est plus sur la tile d'origine (à revoir)
        assertFalse(tileDep.getEntities().contains(pion));
       */        
    }

    /**
     * Test of moveEntity method, of class GameTurn.
     */
    @Test
    public void testMoveEntity_GameEntity_Boat() {
        System.out.println("moveEntity");
        Player joueur = new Player("Lucie", 1);
        NullGameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, joueur);
        GameBoard board = new GameBoard();
        GameTile tile = board.getTileSet().get("Water #37");
        PlayerToken pion = new PlayerToken(joueur,6);
        Boat boat = new Boat();
                
        //on met un bateau sur une tile et on déplace le pion sur un bateau
         tile.addEntity(boat);
         instance.moveEntity(pion, boat);
     
        System.out.println(boat.getOnboardTokens());
        assertTrue(boat.getOnboardTokens().contains(pion));
                  
        
    }

    /**
     * Test of moveDiceEntity method, of class GameTurn.
     */
    @Test
    public void testMoveDiceEntity() {
        System.out.println("moveDiceEntity");
        GameEntity ent = new SeaSerpent();
        GameTile dep = new WaterTile(null, "Depart");
        dep.addEntity(ent);
        GameTile dest = new WaterTile(null, "Arrivee");
        GameTurn instance = new GameTurn(new NullGameLogic(), new Player(null, 0));
        instance.moveDiceEntity(ent, dest);
        
        // L'entité s'est elle bien déplacée ?
        assertTrue(dest.getEntities().contains(ent));
        
        // L'entité n'est plus sur la précedente tuile ?
        assertFalse(dep.getEntities().contains(ent));
        
        //TODO L'entité est au deux endroits ca va pas.
    }

    /**
     * Test of rollDice method, of class GameTurn.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
    @Test
    public void testOnUnitMoveFinished() {
        System.out.println("onUnitMoveFinished");
        GameTurn instance = null;
        instance.onUnitMoveFinished();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *  //On vérifie que l'entité donné par le dé est présent sur la board
     *
    @Test
    public void testOnDiceRollFinished() {
        System.out.println("onDiceRollFinished");
        NullGameLogic instance = new NullGameLogic();
        GameBoard board = new GameBoard();
             
        /*On créé l'entité requin sur la tile Water 37
       instance.getDice().roll();
       GameEntity entity = new Shark();
      GameTile tile =  instance.getTileSet().
      tile.addEntity(entity);*/
       
      
        /*On lance le dé et on récupère le résultat
        System.out.println(instance.onDiceRollFinished());
      System.out.println(result);*/
        
      /*on vérifie qu'il existe cette entité sur le plateau
       assertEquals(entity, result);
    
    }*/

    /**
     * Test of onSinkTileFinished method, of class GameTurn.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
    @Test
    public void testHasRolledDice() {
        System.out.println("hasRolledDice");
        GameTurn instance = null;
        boolean expResult = false;
        boolean result = instance.hasRolledDice();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}