/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;

import fr.miage.atlantis.entities.GameEntity;
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
     * Test of addEntity method, of class GameTile.
     */
    @Test
    public void testAddEntity() {
        System.out.println("addEntity");
        GameEntity gE = null;
        GameTile instance = null;
        instance.addEntity(gE);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEntity method, of class GameTile.
     */
    @Test
    public void testRemoveEntity() {
        System.out.println("removeEntity");
        GameEntity gE = null;
        GameTile instance = null;
        instance.removeEntity(gE);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFromBoard method, of class GameTile.
     */
    @Test
    public void testRemoveFromBoard() {
        System.out.println("removeFromBoard");
        GameTile instance = null;
        instance.removeFromBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class GameTile.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        GameTile instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntities method, of class GameTile.
     */
    @Test
    public void testGetEntities() {
        System.out.println("getEntities");
        GameTile instance = null;
        List expResult = null;
        List result = instance.getEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAction method, of class GameTile.
     */
    @Test
    public void testGetAction() {
        System.out.println("getAction");
        GameTile instance = null;
        TileAction expResult = null;
        TileAction result = instance.getAction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class GameTile.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        GameTile instance = null;
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoard method, of class GameTile.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        GameTile instance = null;
        GameBoard expResult = null;
        GameBoard result = instance.getBoard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOnBoard method, of class GameTile.
     */
    @Test
    public void testIsOnBoard() {
        System.out.println("isOnBoard");
        GameTile instance = null;
        boolean expResult = false;
        boolean result = instance.isOnBoard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class GameTile.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        GameTile instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLeftTile method, of class GameTile.
     */
    @Test
    public void testGetLeftTile() {
        System.out.println("getLeftTile");
        GameTile instance = null;
        GameTile expResult = null;
        GameTile result = instance.getLeftTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRightTile method, of class GameTile.
     */
    @Test
    public void testGetRightTile() {
        System.out.println("getRightTile");
        GameTile instance = null;
        GameTile expResult = null;
        GameTile result = instance.getRightTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLeftUpperTile method, of class GameTile.
     */
    @Test
    public void testGetLeftUpperTile() {
        System.out.println("getLeftUpperTile");
        GameTile instance = null;
        GameTile expResult = null;
        GameTile result = instance.getLeftUpperTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLeftBottomTile method, of class GameTile.
     */
    @Test
    public void testGetLeftBottomTile() {
        System.out.println("getLeftBottomTile");
        GameTile instance = null;
        GameTile expResult = null;
        GameTile result = instance.getLeftBottomTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRightUpperTile method, of class GameTile.
     */
    @Test
    public void testGetRightUpperTile() {
        System.out.println("getRightUpperTile");
        GameTile instance = null;
        GameTile expResult = null;
        GameTile result = instance.getRightUpperTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRightBottomTile method, of class GameTile.
     */
    @Test
    public void testGetRightBottomTile() {
        System.out.println("getRightBottomTile");
        GameTile instance = null;
        GameTile expResult = null;
        GameTile result = instance.getRightBottomTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class GameTile.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String mName = "";
        GameTile instance = null;
        instance.setName(mName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLeftTile method, of class GameTile.
     */
    @Test
    public void testSetLeftTile() {
        System.out.println("setLeftTile");
        GameTile mLeftTile = null;
        GameTile instance = null;
        instance.setLeftTile(mLeftTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRightTile method, of class GameTile.
     */
    @Test
    public void testSetRightTile() {
        System.out.println("setRightTile");
        GameTile mRightTile = null;
        GameTile instance = null;
        instance.setRightTile(mRightTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLeftUpperTile method, of class GameTile.
     */
    @Test
    public void testSetLeftUpperTile() {
        System.out.println("setLeftUpperTile");
        GameTile mLeftUpperTile = null;
        GameTile instance = null;
        instance.setLeftUpperTile(mLeftUpperTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLeftBottomTile method, of class GameTile.
     */
    @Test
    public void testSetLeftBottomTile() {
        System.out.println("setLeftBottomTile");
        GameTile mLeftBottomTile = null;
        GameTile instance = null;
        instance.setLeftBottomTile(mLeftBottomTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRightUpperTile method, of class GameTile.
     */
    @Test
    public void testSetRightUpperTile() {
        System.out.println("setRightUpperTile");
        GameTile mRightUpperTile = null;
        GameTile instance = null;
        instance.setRightUpperTile(mRightUpperTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRightBottomTile method, of class GameTile.
     */
    @Test
    public void testSetRightBottomTile() {
        System.out.println("setRightBottomTile");
        GameTile mRightBottomTile = null;
        GameTile instance = null;
        instance.setRightBottomTile(mRightBottomTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIsOnBoard method, of class GameTile.
     */
    @Test
    public void testSetIsOnBoard() {
        System.out.println("setIsOnBoard");
        boolean mIsOnBoard = false;
        GameTile instance = null;
        instance.setIsOnBoard(mIsOnBoard);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAction method, of class GameTile.
     */
    @Test
    public void testSetAction() {
        System.out.println("setAction");
        TileAction action = null;
        GameTile instance = null;
        instance.setAction(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class GameTileImpl extends GameTile {

        public GameTileImpl() {
            super(null, "", 0);
        }
    }
}