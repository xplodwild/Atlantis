/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.logic.GameLogic;
import java.util.Map;
import java.util.Set;
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
        
        //Test de tuile proche de la tuile d'eau centrale de l'île.
        tile = tile.getRightBottomTile();
        expResult = true;
        result = instance.isTileAtWaterEdge(tile);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getTileSet method, of class GameBoard.
     */
    @Test
    public void testGetTileSet() {
        System.out.println("getTileSet");
        GameBoard instance = new GameBoard();
        
        // On vénifie que le set contient le bon nombre de tuiles.
        int bt = 0;
        int ft = 0;
        int mt = 0;
        Set<String> lks = instance.getTileSet().keySet();
        for(String key : lks){
            if(instance.getTileSet().get(key) instanceof BeachTile){
                bt++;
            }
            if(instance.getTileSet().get(key) instanceof ForestTile){
                ft++;
            }
            if(instance.getTileSet().get(key) instanceof MountainTile){
                mt++;
            }
        }
        assertEquals(16,bt);
        assertEquals(16,ft);
        assertEquals(8,mt);
    }

    /**
     * Test of getFirstTile method, of class GameBoard.
     */
    @Test
    public void testGetFirstTile() {
        System.out.println("getFirstTile");
        GameBoard instance = new GameBoard();
        
        String result = instance.getFirstTile().getName();
        assertEquals("Border #1", result);
        // La première tile du plateau est toujours la tile Boreder #1
    }

    /**
     * Test of generateRandomTile method, of class GameBoard.
     */
    @Test
    public void testGenerateRandomTile() {
        System.out.println("generateRandomTile");
        GameBoard instance = new GameBoard();
        
        // On génère 40 tiles et on vérifie qu'il y a le compte.
        int b = 0;
        int f = 0;
        int m = 0;
        for(int i=0;i<3;i++){
            GameTile result = instance.generateRandomTile();
            if(result instanceof BeachTile) b++;
            if(result instanceof ForestTile) f++;
            if(result instanceof MountainTile) m++;
        }
        assertEquals(b,16);
        assertEquals(f,16);
        assertEquals(m,8);
        
        /* BUG à FIX ici
         * Le randomizer est déjà vidé lorsque je fais new Gameboard
         * Du coup dans ma boucle for ca exceptionne parce que le randomizer est vide.
         * Faudrait le mettre dans une classe à part.
         * 
         */
    }

    /**
     * Test of hasTileAtLevel method, of class GameBoard.
     */
    @Test
    public void testHasTileAtLevel() {
        System.out.println("hasTileAtLevel");
        
        GameBoard instance = new GameBoard();
        
        // Il doit rester des tuiles sable
        assertTrue(instance.hasTileAtLevel(1));
        
        // Il ne doit plus rester de tuiles sable
        for(int i=0;i<16;i++){
            instance.sinkTile(new NullGameLogic() { }, instance.getTileSet().get("Beach #"+i));
        }
        assertFalse(instance.hasTileAtLevel(1));
        
        // Il doit rester des tuiles forêt
        assertTrue(instance.hasTileAtLevel(2));
        
        // Il ne doit plus rester de tuiles foret
        for(int i=0;i<16;i++){
            instance.sinkTile(new NullGameLogic() { }, instance.getTileSet().get("Forest #"+i));
        }
        assertFalse(instance.hasTileAtLevel(2));
        
        // Il doit rester des tuiles montagne
        assertTrue(instance.hasTileAtLevel(3));
        
        // Il ne doit plus rester de tuiles foret
        for(int i=0;i<8;i++){
            instance.sinkTile(new NullGameLogic() { }, instance.getTileSet().get("Mountain #"+i));
        }
        assertFalse(instance.hasTileAtLevel(3));
    }

    /**
     * Test of sinkTile method, of class GameBoard.
     */
    @Test
    public void testSinkTile() {
        System.out.println("sinkTile");
        GameBoard instance = new GameBoard();
        GameTile tile = instance.getTileSet().get("Water #42");
        tile = tile.getRightTile().getRightTile();
        GameTile r = tile.getRightTile();
        GameTile ru = tile.getRightUpperTile();
        GameTile rb = tile.getRightBottomTile();
        GameTile l = tile.getLeftTile();
        GameTile lu = tile.getLeftUpperTile();
        GameTile lb = tile.getLeftBottomTile();
        
        GameTile wat = instance.sinkTile(new NullGameLogic(), tile);
        
        assertEquals(r.getLeftTile(),wat);
        assertEquals(ru.getLeftBottomTile(),wat);
        assertEquals(rb.getLeftUpperTile(),wat);
        assertEquals(l.getRightTile(),wat);
        assertEquals(lu.getRightBottomTile(),wat);
        assertEquals(lb.getRightUpperTile(),wat);
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