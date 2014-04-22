/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.NullGameLogic;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.logic.GameLogic;
import java.util.List;
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
public class BoatTest {
    
    public BoatTest() {
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
     * Test of moveToTile method, of class Boat.
     */
    @Test
    public void testMoveToTile() {
        System.out.println("moveToTile");
        GameBoard gb = new GameBoard();
        GameLogic gl = new NullGameLogic();
        
        // Quelques tiles de test
        GameTile t1 = gb.getTileSet().get("Water #4");
        GameTile t2 = gb.getTileSet().get("Water #5");
        GameTile t3 = gb.getTileSet().get("Water #6");
        
        // On fait un bateau
        PlayerToken pt1 = new PlayerToken(new Player("Leonardo Di Caprio",0), 5);
        PlayerToken pt2 = new PlayerToken(new Player("Lara Fabian",0), 5);
        PlayerToken pt3 = new PlayerToken(new Player("Le capitaine crochet",0), 10);
        Boat titanic = new Boat();
        titanic.addPlayer(pt1);
        titanic.addPlayer(pt2);
        titanic.addPlayer(pt3);
        
        // 1er test, on le met sur une tile
        titanic.moveToTile(gl, t1);
        assertTrue(t1.getEntities().contains(titanic));
        assertTrue(t1.getEntities().contains(pt1));
        assertTrue(t1.getEntities().contains(pt2));
        assertTrue(t1.getEntities().contains(pt3));
        
        // 2nd test, on le met sur une autre tile
        titanic.moveToTile(gl, t2);
        assertTrue(t2.getEntities().contains(titanic));
        assertTrue(t2.getEntities().contains(pt1));
        assertTrue(t2.getEntities().contains(pt2));
        assertTrue(t2.getEntities().contains(pt3));
        
        // On vérifie qu'il n'y a plus rien sur la tile précédente.
        assertFalse(t1.getEntities().contains(titanic));
        assertFalse(t1.getEntities().contains(pt1));
        assertFalse(t1.getEntities().contains(pt2));
        assertFalse(t1.getEntities().contains(pt3));
    }

    /**
     * Test of addPlayer method, of class Boat.
     */
    @Test
    public void testAddPlayer() {
        System.out.println("addPlayer");
        Boat b = new Boat();
        
        //On crée les bateaux vides
        assertTrue(b.getOnboardTokens().isEmpty());
        
        PlayerToken pt1 = new PlayerToken(new Player("P1",0),10);
        b.addPlayer(pt1);
        
        //Un ajout fonctionne
        assertTrue(b.getOnboardTokens().contains(pt1));
        
        PlayerToken pt2 = new PlayerToken(new Player("P2",1),10);
        b.addPlayer(pt2);
        PlayerToken pt3 = new PlayerToken(new Player("P3",2),10);
        b.addPlayer(pt3);
        
        //Un total de 3 ajouts fonctionne
        assertTrue(b.getOnboardTokens().contains(pt2));
        assertTrue(b.getOnboardTokens().contains(pt3));
        
        // On ne peut pas ajouter plus de 4 éléments.
        try{
            PlayerToken pt4 = new PlayerToken(new Player("P3",2),10);
            b.addPlayer(pt4);
            fail("On a pu mettre 4 items sur un bateau");
        }catch(Exception e){
            //tout s'est bien passé
            assertTrue(true);
        }
    }

    /**
     * Test of removePlayer method, of class Boat.
     */
    @Test
    public void testRemovePlayer() {
        System.out.println("removePlayer");
        Boat b = new Boat();
        PlayerToken pt = new PlayerToken(new Player("P1",0), 10);
        b.addPlayer(pt);
        
        // On a bien ajouté l'objet
        assertTrue(b.getOnboardTokens().contains(pt));
        
        //On retire
        b.removePlayer(pt);
        
        //Le bateau est de nouveau vide
        assertFalse(b.getOnboardTokens().contains(pt));
        assertTrue(b.getOnboardTokens().isEmpty());
        
        try{
            // Si on retire un item qui n'y est pas on continue sans soucis
            b.removePlayer(pt);
            assertTrue(true);
            // Si on met null en argument on plante
            b.removePlayer(null);
        }catch(NullPointerException ne){
            assertTrue(true);
        }
    }

    /**
     * Test of getPlayerSlot method, of class Boat.
     *
    @Test
    public void testGetPlayerSlot() {
        System.out.println("getPlayerSlot");
        PlayerToken token = null;
        Boat instance = new Boat();
        int expResult = 0;
        int result = instance.getPlayerSlot(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasRoom method, of class Boat.
     *
    @Test
    public void testHasRoom() {
        System.out.println("hasRoom");
        Boat instance = new Boat();
        boolean expResult = false;
        boolean result = instance.hasRoom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of belongsToPlayer method, of class Boat.
     *
    @Test
    public void testBelongsToPlayer() {
        System.out.println("belongsToPlayer");
        Player p = null;
        Boat instance = new Boat();
        boolean expResult = false;
        boolean result = instance.belongsToPlayer(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onEntityCross method, of class Boat.
     *
    @Test
    public void testOnEntityCross() {
        System.out.println("onEntityCross");
        GameLogic logic = null;
        GameEntity ent = null;
        Boat instance = new Boat();
        boolean expResult = false;
        boolean result = instance.onEntityCross(logic, ent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOnboardTokens method, of class Boat.
     *
    @Test
    public void testGetOnboardTokens() {
        System.out.println("getOnboardTokens");
        Boat instance = new Boat();
        List expResult = null;
        List result = instance.getOnboardTokens();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}