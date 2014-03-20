/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.Shark;
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
public class GameTileTest {
    
    public GameTileTest() {
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
     * Test si l'entité ajouté sur la tile, s'ajoute
     * 
     */
    @Test
    public void testAddEntity() {
        System.out.println("addEntity");
        GameBoard board = new GameBoard();
        GameEntity gE = new Shark();
        GameTile tile = board.getTileSet().get("Water #37");
        tile.addEntity(gE);
        assertEquals(gE, tile.getEntities().get(0));
    }

    /**
     * Ajoute une entité sur une tuile et la supprime.
     * Test si l'entité sur la tuile a bien été suprimé.
     */
    @Test
    public void testRemoveEntity() {
        System.out.println("removeEntity");
        GameBoard board = new GameBoard();
        GameEntity entity = new Shark();
        GameTile tile = board.getTileSet().get("Water #37");
        tile.addEntity(entity);
        assertEquals(entity, tile.getEntities().get(0));
        tile.removeEntity(entity);
        assertTrue(tile.getEntities().isEmpty());
    }

    /**
     * Test si la tile a bien été enlevé de la board
     */
    @Test
    public void testRemoveFromBoard() {
        System.out.println("removeFromBoard");
        GameBoard board = new GameBoard();
        GameTile tile = board.getTileSet().get("Beach #1");       
        tile.removeFromBoard();
        assertFalse(tile.isOnBoard());
    }
}