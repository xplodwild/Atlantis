/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.logic;

import fr.miage.atlantis.GameDice;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.BeachTile;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.NullGameLogic;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
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
    
    /**
     *
     */
    public GameTurnTest() {
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
     * Test of moveEntity method, of class GameTurn.
     */
    @Test
    public void testMoveEntity_GameEntity_GameTile() {
        System.out.println("moveEntity");
        
        Player joueur = new Player("lu",1,false);
        NullGameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, joueur);
        GameBoard board = new GameBoard(true);       
        
        //on ajoute un pion sur une tile et on test qu'elle est dessus.
        GameEntity pion = new PlayerToken(joueur,6);
        GameTile tileDep = board.getTileSet().get("Water #35");
        pion.moveToTile(gl, tileDep);
        assertTrue(tileDep.getEntities().contains(pion));
        
        //on déplace le pion sur une autre tile et on test que le pion s'est déplacé.
        GameTile tile = board.getTileSet().get("Water #37");
        instance.moveEntity(pion, tile);               
        assertTrue(tile.getEntities().contains(pion));
        
       //on teste si le pion n'est plus sur la tile d'origine
        assertFalse(tileDep.getEntities().contains(pion));
        
    }

    /**
     * Test of moveEntity method, of class GameTurn.
     */
    @Test
    public void testMoveEntity_GameEntity_Boat() {
        System.out.println("moveEntity");
        Player joueur = new Player("Lucie", 1,false);
        NullGameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, joueur);
        GameBoard board = new GameBoard(true);
        GameTile tile = board.getTileSet().get("Water #37");
        PlayerToken pion = new PlayerToken(joueur,6);
        Boat boat = new Boat();
                
        //on met un bateau sur une tile et on déplace le pion sur un bateau
boat.moveToTile(gl,tile);
boat.addPlayer(pion);     
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
        ent.moveToTile(new NullGameLogic(), dep);
        GameTile dest = new WaterTile(null, "Arrivee");
        GameTurn instance = new GameTurn(new NullGameLogic(), new Player(null, 0,false));
        instance.moveDiceEntity(ent, dest);
        
        // L'entité s'est elle bien déplacée ?
        assertTrue(dest.getEntities().contains(ent));
        
        // L'entité n'est plus sur la précedente tuile ?
        assertFalse(dep.getEntities().contains(ent));
    }

    /**
     * Test of rollDice method, of class GameTurn.
     */
    @Test
    public void testRollDice() {
        System.out.println("rollDice");
        GameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, new Player("P1", 1,false));
        for(int i=0;i<1000;i++){
            int res = instance.rollDice();
            assertFalse(res<0 || res>=GameDice.FACE_COUNT);
        }
    }

    /**
     * Test of hasSunkLandTile method, of class GameTurn.
     */
    @Test
    public void testHasSunkLandTile() {
        System.out.println("hasSunkLandTile");
        GameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, new Player("P1",0,false));
        GameTile tile = new BeachTile(new GameBoard(true), "Beach #yolo");
        
        // Aucune tuile n'a été coulée
        assertFalse(instance.hasSunkLandTile());
        
        //On coule une tuile
        instance.sinkLandTile(tile);
        
        //On a bien coulé une tuile
        assertTrue(instance.hasSunkLandTile());
    }

    /**
     * Test of sinkLandTile method, of class GameTurn.
     */
    @Test
    public void testSinkLandTile() {
        System.out.println("sinkLandTile");
        GameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, new Player("P1",0,false));
        GameTile tile = new BeachTile(new GameBoard(true), "Beach #yolo");
        
        // Aucune tuile n'a été coulée
        assertFalse(instance.hasSunkLandTile());
        
        // On coule une tuile
        instance.sinkLandTile(tile);
        
        //On a bien coulé une tuile
        assertTrue(instance.hasSunkLandTile());      
    }

    /**
     * Test of useRemoteTile method, of class GameTurn.
     */
    @Test
    public void testUseRemoteTile() {
        System.out.println("useRemoteTile");
        GameBoard gb = new GameBoard(true);
        TileAction action = TileAction.Factory.createCancelAnimal(TileAction.ENTITY_SHARK);
        Player p = new Player("P1", 0,false);
        GameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, p);
        Player p2 = new Player("P2", 1,false);
        p2.addActionTile(action);
        
        // Le joueur use son action pas pdt son tour
        instance.useRemoteTile(p2,action);
        
        assertFalse(p2.getActionTiles().contains(action));
    }

    /**
     * Test of useLocalTile method, of class GameTurn.
     */
    @Test
    public void testUseLocalTile() {
        System.out.println("useLocalTile");
        GameBoard gb = new GameBoard(true);
        TileAction action = TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SEASERPENT);
        Player p = new Player("P1", 0,false);
        p.addActionTile(action);
        GameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, p);
        
        // Le joueur utilise son actiontile
        instance.useLocalTile(action);
        
        assertFalse(p.getActionTiles().contains(action));
       
        try{
            // Le joueur utilise encore son actiontile
            instance.useLocalTile(action);
            fail("L'exception ne s'est pas déclenchée, "
                + "on a utilisé deux fois une tileaction en un tour");
        }catch (Exception e){
            assertTrue(true);
        }
    }

 }