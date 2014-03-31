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
        instance.fillInRandomizerWithTiles();
        int b = 0;
        int f = 0;
        int m = 0;
        for(int i=0;i<40;i++){
            GameTile result = instance.generateRandomTile();
            if(result instanceof BeachTile) b++;
            if(result instanceof ForestTile) f++;
            if(result instanceof MountainTile) m++;
        }
        assertEquals(16,b);
        assertEquals(16,f);
        assertEquals(8,m);
       
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
     *
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
        
        GameBoard instance = new GameBoard();
        GameTile tile = instance.getTileSet().get("Water #30").getRightTile();   
        GameTile newtile = new WaterTile(instance, "Water #yolo");
        instance.placeTileAtTheRightOf(tile, newtile);
        
        assertEquals(tile.getRightTile(), newtile);
        assertEquals(newtile.getLeftTile(),tile);
        
        tile = tile.getRightUpperTile();
        assertEquals(tile.getRightBottomTile(), newtile);
        assertEquals(newtile.getLeftUpperTile(),tile);
        
        tile = tile.getRightTile();
        assertEquals(tile.getLeftBottomTile(), newtile);
        assertEquals(newtile.getRightUpperTile(),tile);
        
        tile = tile.getRightBottomTile();
        assertEquals(tile.getLeftTile(), newtile);
        assertEquals(newtile.getRightTile(),tile);
        
        tile = tile.getLeftBottomTile();
        assertEquals(tile.getLeftUpperTile(), newtile);
        assertEquals(newtile.getRightBottomTile(),tile);
        
        tile = tile.getLeftTile();
        assertEquals(tile.getRightUpperTile(), newtile);
        assertEquals(newtile.getLeftBottomTile(),tile);
        
    }

    /**
     * Test of placeTileAtTheLeftOf method, of class GameBoard.
     */
    //@Test
    public void testPlaceTileAtTheLeftOf() {
        System.out.println("placeTileAtTheLeftOf");
        GameBoard instance = new GameBoard();
        GameTile tile = instance.getTileSet().get("Water #37").getLeftTile();
        GameTile newtile = new WaterTile(instance, "Water #yo");
        instance.placeTileAtTheLeftOf(tile, newtile);
        
        /**pb sur la newtile qui trouve pas toute sa gauche*/
        assertEquals(tile.getLeftTile(), newtile);
        assertEquals(newtile.getRightTile(),tile);
        
        /*Nouvelle tile : tile en haut à gauche.
        *Vérification : tile en bas à gauche de la tile = à la newtile
        *Vérification : tile en haut à droite de la newtile = à la tile*/
        tile = tile.getLeftUpperTile();  
        assertEquals(tile.getLeftBottomTile(), newtile);
        assertEquals(newtile.getRightUpperTile(),tile);
        
        /*Nouvelle tile : tile à gauche.
        *Vérification : tile en bas à droite de la tile = à la newtile
        *Vérification : tile en haut à gauche de la newtile = à la tile*/
        tile = tile.getLeftTile();
        assertEquals(tile.getRightBottomTile(), newtile);
        assertEquals(newtile.getLeftUpperTile(),tile);
        
        /*Nouvelle tile : tile en bas à gauche.
        *Vérification : tile à droite de la tile = à la newtile
        *Vérification : tile à gauche de la newtile = à la tile*/
        tile = tile.getLeftBottomTile();
        assertEquals(tile.getRightTile(), newtile);
        assertEquals(newtile.getLeftTile(),tile);
        
        /*Nouvelle tile : tile en bas à droite.
        *Vérification : tile en haut à droite de la tile = à la newtile
        *Vérification : tile en bas à gauche de la newtile = à la tile*/
        tile = tile.getRightBottomTile();
        assertEquals(tile.getRightUpperTile(), newtile);
        assertEquals(newtile.getLeftBottomTile(),tile);
        
        /*Nouvelle tile : tile à droite.
        *Vérification : tile en haut à gauche de la tile = à la newtile
        *Vérification : tile en bas à droite de la newtile = à la tile*/
        tile = tile.getRightTile();
        assertEquals(tile.getLeftUpperTile(), newtile);
        assertEquals(newtile.getRightBottomTile(),tile);
      
    }

    /**
     * Test of placeTileAtTheBottomRightOf method, of class GameBoard.
     */
    //@Test
    public void testPlaceTileAtTheBottomRightOf() {
        System.out.println("placeTileAtTheBottomRightOf");
        GameBoard instance = new GameBoard();
        GameTile tile = instance.getTileSet().get("Water #30").getRightBottomTile(); 
        GameTile newtile = new WaterTile(instance, "Water #yolo");
        instance.placeTileAtTheBottomRightOf(tile, newtile);
      
        assertEquals(tile.getRightBottomTile(), newtile);
        assertEquals(newtile.getLeftUpperTile(),tile);
        
        /*Nouvelle tile : tile à droite.
        *Vérification : tile en bas à gauche de la tile = à la newtile
        *Vérification : tile en haut à droite de la newtile = à la tile*/
        tile = tile.getRightTile();
        assertEquals(tile.getLeftBottomTile(), newtile);
        assertEquals(newtile.getRightUpperTile(),tile);
        
        /*Nouvelle tile : tile en bas à droite.
        *Vérification : tile à gauche de la tile = à la newtile
        *Vérification : tile à droite de la newtile = à la tile*/      
        tile = tile.getRightBottomTile();
        assertEquals(tile.getLeftTile(), newtile);
        assertEquals(newtile.getRightTile(),tile);
        
        /*Nouvelle tile : tile en bas à gauche.
        *Vérification : tile en haut à gauche de la tile = à la newtile
        *Vérification : tile en bas à droite de la newtile = à la tile*/     
        tile = tile.getLeftBottomTile();
        assertEquals(tile.getLeftUpperTile(), newtile);
        assertEquals(newtile.getRightBottomTile(),tile);
        
        /*Nouvelle tile : tile à gauche.
        *Vérification : tile en haut à droite de la tile = à la newtile
        *Vérification : tile en bas à gauche de la newtile = à la tile*/    
        tile = tile.getLeftTile();
        assertEquals(tile.getRightUpperTile(), newtile);
        assertEquals(newtile.getLeftBottomTile(),tile);
      
        /*Nouvelle tile : tile en haut à gauche.
        *Vérification : tile à gauche de la tile = à la newtile
        *Vérification : tile à droite de la newtile = à la tile*/      
        tile = tile.getLeftUpperTile();
        assertEquals(tile.getLeftTile(), newtile);
        assertEquals(newtile.getRightTile(), tile);       
    }

    /**
     * Test of placeTileAtTheBottomLeftOf method, of class GameBoard.
     */
    //@Test
    public void testPlaceTileAtTheBottomLeftOf() {
        System.out.println("placeTileAtTheBottomLeftOf");
        GameBoard instance = new GameBoard();
        GameTile tile = instance.getTileSet().get("Water #30").getLeftBottomTile(); 
        GameTile newtile = new WaterTile(instance, "Water #yolo");
        instance.placeTileAtTheBottomLeftOf(tile, newtile);
      
        assertEquals(tile.getLeftBottomTile(), newtile);
        assertEquals(newtile.getRightUpperTile(),tile);
        
        /*Nouvelle tile : tile en bas à droite.
        *Vérification : tile à gauche de la tile = à la newtile
        *Vérification : tile à droite de la newtile = à la tile*/
        tile = tile.getRightBottomTile();
        assertEquals(tile.getLeftTile(), newtile);
        assertEquals(newtile.getRightTile(),tile);
        
        /*Nouvelle tile : tile en bas à gauche.
        *Vérification : tile en haut à gauche de la tile = à la newtile
        *Vérification : tile en bas à droite de la newtile = à la tile*/      
        tile = tile.getLeftBottomTile();
        assertEquals(tile.getLeftUpperTile(), newtile);
        assertEquals(newtile.getRightBottomTile(),tile);
        
        /*Nouvelle tile : tile à gauche.
        *Vérification : tile en haut à droite de la tile = à la newtile
        *Vérification : tile en bas à gauche de la newtile = à la tile*/     
        tile = tile.getLeftTile();
        assertEquals(tile.getRightUpperTile(), newtile);
        assertEquals(newtile.getLeftBottomTile(),tile);
        
        /*Nouvelle tile : tile en haut à gauche.
        *Vérification : tile à droite de la tile = à la newtile
        *Vérification : tile à gauche de la newtile = à la tile*/    
        tile = tile.getLeftUpperTile();
        assertEquals(tile.getRightTile(), newtile);
        assertEquals(newtile.getLeftTile(),tile);
      
        /*Nouvelle tile : tile en haut à droite.
        *Vérification : tile en bas à droite de la tile = à la newtile
        *Vérification : tile en haut à gauche de la newtile = à la tile*/      
        tile = tile.getRightUpperTile();
        assertEquals(tile.getRightBottomTile(), newtile);
        assertEquals(newtile.getLeftUpperTile(), tile);    
    }

   
    /**
     * Test of hasEntityOfType method, of class GameBoard.
     *
    @Test
    public void testHasEntityOfType() {
        System.out.println("hasEntityOfType");   
        /**tester avec aucune entité dans le jeu **/
        /**tester avec une entité de chaque **/
        /** tester avec plusieurs entité de chaque **/
        GameBoard instance = new GameBoard();
        Class type = null;
        boolean expResult = false;
        boolean result = instance.hasEntityOfType(type);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of hasTileAtWaterEdge method, of class GameBoard.
     */
    @Test
    public void testHasTileAtWaterEdge() {
        System.out.println("hasTileAtWaterEdge");
        GameBoard board = new GameBoard();
        
        // On laisse une tile de chaque (pour s'assurer du contact avec l'eau)
        for(int i =0; i<15;i++){
            GameTile killme = board.getTileSet().get("Beach #"+i);
            if (killme != null) board.sinkTile(new NullGameLogic(), killme);
        }
        for(int i =0; i<15;i++){
            GameTile killme = board.getTileSet().get("Forest #"+i);
            if (killme != null) board.sinkTile(new NullGameLogic(), killme);
        }
        for(int i =0; i<7;i++){
            GameTile killme = board.getTileSet().get("Mountain #"+i);
            if (killme != null) board.sinkTile(new NullGameLogic(), killme);
        }
        
        // Test plage
        assertTrue(board.hasTileAtWaterEdge(1));
        board.sinkTile(new NullGameLogic(), board.getTileSet().get("Beach #15"));
        assertFalse(board.hasTileAtWaterEdge(1));
        
        // Test foret
        assertTrue(board.hasTileAtWaterEdge(2));
        board.sinkTile(new NullGameLogic(), board.getTileSet().get("Forest #15"));
        assertFalse(board.hasTileAtWaterEdge(2));
        
        // Test montagne
        assertTrue(board.hasTileAtWaterEdge(3));
        board.sinkTile(new NullGameLogic(), board.getTileSet().get("Mountain #7"));
        assertFalse(board.hasTileAtWaterEdge(3));
    }
    
}