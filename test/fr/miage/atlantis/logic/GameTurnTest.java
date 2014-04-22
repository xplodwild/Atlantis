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
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
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
        Player joueur = new Player("Lucie", 1);
        NullGameLogic gl = new NullGameLogic();
        GameTurn instance = new GameTurn(gl, joueur);
        GameBoard board = new GameBoard();
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
        GameTurn instance = new GameTurn(new NullGameLogic(), new Player(null, 0));
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
        GameTurn instance = new GameTurn(gl, new Player("P1", 1));
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
        GameTurn instance = new GameTurn(gl, new Player("P1",0));
        GameTile tile = new BeachTile(new GameBoard(), "Beach #yolo");
        
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
        GameTurn instance = new GameTurn(gl, new Player("P1",0));
        GameTile tile = new BeachTile(new GameBoard(), "Beach #yolo");
        
        // Aucune tuile n'a été coulée
        assertFalse(instance.hasSunkLandTile());
        
        // On coule une tuile
        instance.sinkLandTile(tile);
        
        //On a bien coulé une tuile
        assertTrue(instance.hasSunkLandTile());      
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
        // TODO Méthode non implémentée
        fail("The test case is a prototype.");
    }

    /**
     * Test of useLocalTile method, of class GameTurn.
     */
    @Test
    public void testUseLocalTile() {
        System.out.println("useLocalTile");
        GameBoard gb = new GameBoard();
        TileAction action = gb.getTileSet().get("Beach #1").getAction();
        Player p = new Player("P1", 0);
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
     *  On vérifie que l'entité donné par le dé est présent sur la board
     */
    @Test
    public void testOnDiceRollFinished() {
        System.out.println("onDiceRollFinished");
        NullGameLogic gl = new NullGameLogic();
        GameBoard board = new GameBoard();
        GameTurn instance = new GameTurn(gl, new Player("P1",0)); 
        GameEntity req = new Shark();
        GameEntity ser = new SeaSerpent();
        GameEntity bal = new Whale();
       
    
        GameTile tile = board.getTileSet().get("Water #37");
        GameTile tile1 = board.getTileSet().get("Water #23");
        GameTile tile2 = board.getTileSet().get("Water #40");
        
        //on ajoute les entités sur une tile du jeu
        req.moveToTile(gl, tile);
        //bal.moveToTile(gl, tile1);
        ser.moveToTile(gl, tile2);
       
        
        //int result = gl.getDice().roll();
        int result = 2;
        //on compare que le résultat du dé correspond à une entité déjà présente sur le jeu
        switch(result){
            case 0:
                assertTrue(tile1.getEntities().contains(ser));
                break;
            case 1:
                 assertTrue(tile.getEntities().contains(req));
                break;
            case 2:
                 assertTrue(tile2.getEntities().contains(bal));
                break;
        }
       
                
    
    }

}