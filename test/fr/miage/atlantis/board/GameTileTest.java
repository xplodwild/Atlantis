/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.Shark;
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
    
    /**
     *
     */
    public GameTileTest() {
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
     * Test si l'entité ajouté sur la tile, s'ajoute
     * 
     */
    @Test
    public void testAddEntity() {
        System.out.println("addEntity");
        GameBoard board = new GameBoard(true);
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
        GameBoard board = new GameBoard(true);
        GameEntity entity = new Shark();
        GameTile tile = board.getTileSet().get("Water #37");
        
        /** Ajoute l'entité sur la tile  et on vérifie qu'elle est présente**/
        tile.addEntity(entity);
        assertEquals(entity, tile.getEntities().get(0));
        
        /** Suppression de l'entité, et vérification de sa suppression**/
        tile.removeEntity(entity);
        assertTrue(tile.getEntities().isEmpty());
    }

    /**
     * Test si la tile a bien été enlevé de la board
     */
    @Test
    public void testRemoveFromBoard() {
        System.out.println("removeFromBoard");
        GameBoard board = new GameBoard(true);
        GameTile tile = board.getTileSet().get("Beach #1");       
        tile.removeFromBoard();
        assertFalse(tile.isOnBoard());
    }
}