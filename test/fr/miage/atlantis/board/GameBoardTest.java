/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

import fr.miage.atlantis.logic.GameLogic;
import java.util.Map;
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
public class GameBoardTest {
    
    public GameBoardTest() {
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
     * Test of isTileAtWaterEdge method, of class GameBoard.
     */
    @Test
    public void testIsTileAtWaterEdge() {
        System.out.println("isTileAtWaterEdge");
        GameBoard instance = new GameBoard();
        
        // Au bord de l'eau
        GameTile tile = instance.getTileSet().get("Water #37");
        boolean expResult = true;
        boolean result = instance.isTileAtWaterEdge(tile);
        assertEquals(expResult, result);
        
        // Au milieu de l'eau
        tile = instance.getTileSet().get("Water #11");
        expResult = true;
        result = instance.isTileAtWaterEdge(tile);
        assertEquals(expResult, result);
        
        // Sur la côte
        tile = instance.getTileSet().get("Water #37");
        tile = tile.getRightTile();
        expResult = true;
        result = instance.isTileAtWaterEdge(tile);
        assertEquals(expResult, result);
        
        // Test de la tuile enclavée
        tile = tile.getRightBottomTile();
        expResult = false;
        result = instance.isTileAtWaterEdge(tile);
        assertEquals(expResult, result);
        
        //Test de suite proche de la tuile d'eau centrale de l'île.
        tile = tile.getRightBottomTile();
        expResult = true;
        result = instance.isTileAtWaterEdge(tile);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getTileSet method, of class GameBoard.
     *
    @Test
    public void testGetTileSet() {
        System.out.println("getTileSet");
        GameBoard instance = new GameBoard();
        Map expResult = null;
        Map result = instance.getTileSet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstTile method, of class GameBoard.
     *
    @Test
    public void testGetFirstTile() {
        System.out.println("getFirstTile");
        GameBoard instance = new GameBoard();
        GameTile expResult = null;
        GameTile result = instance.getFirstTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateRandomTile method, of class GameBoard.
     *
    @Test
    public void testGenerateRandomTile() {
        System.out.println("generateRandomTile");
        GameBoard instance = new GameBoard();
        GameTile expResult = null;
        GameTile result = instance.generateRandomTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasTileAtLevel method, of class GameBoard.
     *
    @Test
    public void testHasTileAtLevel() {
        System.out.println("hasTileAtLevel");
        int h = 0;
        GameBoard instance = new GameBoard();
        boolean expResult = false;
        boolean result = instance.hasTileAtLevel(h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sinkTile method, of class GameBoard.
     *
    @Test
    public void testSinkTile() {
        System.out.println("sinkTile");
        GameLogic logic = null;
        GameTile tile = null;
        GameBoard instance = new GameBoard();
        WaterTile expResult = null;
        WaterTile result = instance.sinkTile(logic, tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canPlaceTile method, of class GameBoard.
     *
    @Test
    public void testCanPlaceTile() {
        System.out.println("canPlaceTile");
        GameBoard instance = new GameBoard();
        boolean expResult = false;
        boolean result = instance.canPlaceTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeTileAtTheRightOf method, of class GameBoard.
     *
    @Test
    public void testPlaceTileAtTheRightOf() {
        System.out.println("placeTileAtTheRightOf");
        GameTile base = null;
        GameTile newTile = null;
        GameBoard instance = new GameBoard();
        instance.placeTileAtTheRightOf(base, newTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeTileAtTheLeftOf method, of class GameBoard.
     *
    @Test
    public void testPlaceTileAtTheLeftOf() {
        System.out.println("placeTileAtTheLeftOf");
        GameTile base = null;
        GameTile newTile = null;
        GameBoard instance = new GameBoard();
        instance.placeTileAtTheLeftOf(base, newTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeTileAtTheBottomRightOf method, of class GameBoard.
     *
    @Test
    public void testPlaceTileAtTheBottomRightOf() {
        System.out.println("placeTileAtTheBottomRightOf");
        GameTile base = null;
        GameTile newTile = null;
        GameBoard instance = new GameBoard();
        instance.placeTileAtTheBottomRightOf(base, newTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeTileAtTheBottomLeftOf method, of class GameBoard.
     *
    @Test
    public void testPlaceTileAtTheBottomLeftOf() {
        System.out.println("placeTileAtTheBottomLeftOf");
        GameTile base = null;
        GameTile newTile = null;
        GameBoard instance = new GameBoard();
        instance.placeTileAtTheBottomLeftOf(base, newTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printAllTiles method, of class GameBoard.
     *
    @Test
    public void testPrintAllTiles() {
        System.out.println("printAllTiles");
        GameBoard instance = new GameBoard();
        instance.printAllTiles();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasEntityOfType method, of class GameBoard.
     *
    @Test
    public void testHasEntityOfType() {
        System.out.println("hasEntityOfType");
        Class type = null;
        GameBoard instance = new GameBoard();
        boolean expResult = false;
        boolean result = instance.hasEntityOfType(type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasTileAtWaterEdge method, of class GameBoard.
     *
    @Test
    public void testHasTileAtWaterEdge() {
        System.out.println("hasTileAtWaterEdge");
        int level = 0;
        GameBoard instance = new GameBoard();
        boolean expResult = false;
        boolean result = instance.hasTileAtWaterEdge(level);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}