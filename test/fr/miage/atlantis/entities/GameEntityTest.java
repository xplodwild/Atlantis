/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.NullGameLogic;
import fr.miage.atlantis.logic.GameLogic;
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
public class GameEntityTest {
    
    public GameEntityTest() {
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
     * Test of isDead method, of class GameEntity.
     * On teste que l'entité est bien tuée
     */
    @Test
    public void testIsDead() {
        System.out.println("isDead");
        GameLogic lg = new NullGameLogic();
        GameBoard board = new GameBoard();
        GameTile tile = board.getTileSet().get("Water #37");
        Shark requin = new Shark();
        
        //Cas où l'on tue une entité
        requin.moveToTile(lg, tile);
        assertTrue(tile.getEntities().contains(requin));
        
        requin.die(lg);
        assertTrue(requin.isDead());      
    
    }

    /**
     * Test of moveToTile method, of class GameEntity.
     * Test imcomplet car notre GameLogic de test (NullGameLogic) est vide
     * Le test effectué est donc mis en commentaire, afin de poursuivre le test
     */
    @Test
    public void testMoveToTile() {
        System.out.println("moveToTile");
        GameLogic logic = new NullGameLogic();
        GameBoard board = new GameBoard();
        GameTile tile = board.getTileSet().get("Water #37");
        GameTile tile1 = board.getTileSet().get("Water #36");
        SeaSerpent serpent = new SeaSerpent();
        
        //on met l'entité sur une tile. Puis on la déplace et on vérifie qu'elle s'est déplacé
        serpent.moveToTile(logic, tile);
        assertTrue(tile.getEntities().contains(serpent));
        
        serpent.moveToTile(logic, tile1);
        assertTrue(tile1.getEntities().contains(serpent));
        //assertFalse(tile1.getEntities().contains(serpent));
        
        
        
      
    }

    /**
     * Test of die method, of class GameEntity.
     */
    @Test
    public void testDie() {
        System.out.println("die");
        GameLogic logic = new NullGameLogic();
        
        // On a un requn vivant
        Shark sk = new Shark();
        assertFalse(sk.isDead());
        
        // On le tue
        sk.die(logic);
        
        //il est mort
        assertTrue(sk.isDead());
        
        // Test de cas improbable
        try{
            sk.die(null);
            fail("On devrait avoir eu un NullPointerException");
        }catch(Exception e){
            assertTrue(true);
        }
        // A cause de l'absence de gamelogic on ne vérifie pas qu'il enleve le
        // requin de la tile.
    }

    /**
     * Test of spawn method, of class GameEntity.
     * Méthode non-implémentée
     *
    @Test
    public void testSpawn() {
        System.out.println("spawn");
        GameLogic logic = null;
        GameEntity instance = null;
        instance.spawn(logic);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}