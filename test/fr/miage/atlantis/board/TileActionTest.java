/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

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
public class TileActionTest {
    
    public TileActionTest() {
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
     * On teste si sous les 16 tiles, il y a bien une action random associé
     **/
    @Test
    public void testGenerateRandomTileActionBeach() {
        System.out.println("generateRandomTileActionBeach");
          
        /*Pour toutes les tiles Beach, on vérifie qu'il y a une action associée et qu'elle n'est pas nulle */
        for(int i=0; i< 16 ; i++){
            TileAction result = TileAction.generateRandomTileActionBeach();
            assertNotNull(result);
        }    
    }

    /**
     *  On teste si les 8 tiles Montagne, il y a bien une action random associée
     */
    @Test
    public void testGenerateRandomTileActionMountain() {
        System.out.println("generateRandomTileActionMountain");
        
         /*Pour les 8 tiles montagne, on vérifie qu'il y a une action associée */
        for(int i=0; i< 8 ; i++){
            TileAction result = TileAction.generateRandomTileActionMountain();
            assertNotNull(result);
        }    
    }

    /**
     * On teste si les 8 tiles Forêt, il y a bien une action random associée
     */
    @Test
    public void testGenerateRandomTileActionForest() {
        System.out.println("generateRandomTileActionForest");
        
        /*Pour les 16 tiles forêt, on vérifie qu'il y a une action associée */
        for(int i=0; i< 16 ; i++){
            TileAction result = TileAction.generateRandomTileActionMountain();
            assertNotNull(result);
        }    
    }

   

    /**
     * Test of isImmediate method, of class TileAction.
     */
    @Test
    public void testIsImmediate() {
        System.out.println("isImmediate");
        TileAction instance = null;
        boolean expResult = false;
        boolean result = instance.isImmediate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTriggerable method, of class TileAction.
     */
    @Test
    public void testIsTriggerable() {
        System.out.println("isTriggerable");
        TileAction instance = null;
        boolean expResult = false;
        boolean result = instance.isTriggerable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isVolcano method, of class TileAction.
     */
    @Test
    public void testIsVolcano() {
        System.out.println("isVolcano");
        TileAction instance = null;
        boolean expResult = false;
        boolean result = instance.isVolcano();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAction method, of class TileAction.
     */
    @Test
    public void testGetAction() {
        System.out.println("getAction");
        TileAction instance = null;
        int expResult = 0;
        int result = instance.getAction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntity method, of class TileAction.
     */
    @Test
    public void testGetEntity() {
        System.out.println("getEntity");
        TileAction instance = null;
        int expResult = 0;
        int result = instance.getEntity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class TileAction.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        TileAction instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}