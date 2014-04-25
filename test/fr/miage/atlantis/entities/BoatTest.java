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
        GameBoard gb = new GameBoard(true);
        GameLogic gl = new NullGameLogic();
        
        // Quelques tiles de test
        GameTile t1 = gb.getTileSet().get("Water #4");
        GameTile t2 = gb.getTileSet().get("Water #5");
        GameTile t3 = gb.getTileSet().get("Water #6");
        
        // On fait un bateau
        PlayerToken pt1 = new PlayerToken(new Player("Leonardo Di Caprio",0,false), 5);
        PlayerToken pt2 = new PlayerToken(new Player("Lara Fabian",0,false), 5);
        PlayerToken pt3 = new PlayerToken(new Player("Le capitaine crochet",0,false), 10);
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
        
        PlayerToken pt1 = new PlayerToken(new Player("P1",0,false),10);
        b.addPlayer(pt1);
        
        //Un ajout fonctionne
        assertTrue(b.getOnboardTokens().contains(pt1));
        
        PlayerToken pt2 = new PlayerToken(new Player("P2",1,false),10);
        b.addPlayer(pt2);
        PlayerToken pt3 = new PlayerToken(new Player("P3",2,false),10);
        b.addPlayer(pt3);
        
        //Un total de 3 ajouts fonctionne
        assertTrue(b.getOnboardTokens().contains(pt2));
        assertTrue(b.getOnboardTokens().contains(pt3));
        
        // On ne peut pas ajouter plus de 4 éléments.
        try{
            PlayerToken pt4 = new PlayerToken(new Player("P3",2,false),10);
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
        PlayerToken pt = new PlayerToken(new Player("P1",0,false), 10);
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
     */
    @Test
    public void testGetPlayerSlot() {
        System.out.println("getPlayerSlot");
        Boat b = new Boat();
        PlayerToken pt1 = new PlayerToken(new Player("Leonardo Di Caprio",0,false), 5);
        PlayerToken pt2 = new PlayerToken(new Player("Lara Fabian",0,false), 5);
        PlayerToken pt3 = new PlayerToken(new Player("Le capitaine crochet",0,false), 10);
        b.addPlayer(pt1);
        b.addPlayer(pt2);
        b.addPlayer(pt3);
        
        assertEquals(0,b.getPlayerSlot(pt1));
        assertEquals(1,b.getPlayerSlot(pt2));
        assertEquals(2,b.getPlayerSlot(pt3));
        
        try{
            b.getPlayerSlot(new PlayerToken(new Player("P",0,false), 90));
            fail("On get un player sur un bateau alors qu'il y est pas, étonnant");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    /**
     * Test of hasRoom method, of class Boat.
     */
    @Test
    public void testHasRoom() {
        System.out.println("hasRoom");
        Boat b = new Boat();
        PlayerToken pt1 = new PlayerToken(new Player("Leonardo Di Caprio",0,false), 5);
        PlayerToken pt2 = new PlayerToken(new Player("Lara Fabian",0,false), 5);
        PlayerToken pt3 = new PlayerToken(new Player("Le capitaine crochet",0,false), 10);
        
        //Bateau vide
        assertTrue(b.hasRoom());
        
        //Un passager
        b.addPlayer(pt1);
        assertTrue(b.hasRoom());
        
        //Deux passagers
        b.addPlayer(pt2);
        assertTrue(b.hasRoom());
        
        //Trois passagers - plus de place
        b.addPlayer(pt3);
        assertFalse(b.hasRoom());
        
        //Il reste de nouveau une place
        b.removePlayer(pt2);
        assertTrue(b.hasRoom());
    }

    /**
     * Test of belongsToPlayer method, of class Boat.
     */
    @Test
    public void testBelongsToPlayer() {
        System.out.println("belongsToPlayer");
        Boat b = new Boat();
        
        // Joueur 1 et son token
        Player p1 = new Player("P1",0,false);
        PlayerToken pt1 = new PlayerToken(p1, 10);
        
        // Joueur 2 et ses token
        Player p2 = new Player("P2",0,false);
        PlayerToken pt2 = new PlayerToken(p2, 8);
        PlayerToken pt3 = new PlayerToken(p2, 2);
        
        // Personne sur le bateau - Tout le mode peut le déplacer
        assertTrue(b.belongsToPlayer(p1));
        assertTrue(b.belongsToPlayer(p2));
        
        // Un pion sur le bateau - Joueur 1 a le controle
        b.addPlayer(pt2);
        assertFalse(b.belongsToPlayer(p1));
        assertTrue(b.belongsToPlayer(p2));
        
        // Un pion de chaque sur le bateau - J1 et J2 ont le controle
        b.addPlayer(pt1);
        assertTrue(b.belongsToPlayer(p1));
        assertTrue(b.belongsToPlayer(p2));
        
        // Joueur 2 a deux pions sur le bateau - J2 a le controle
        b.addPlayer(pt3);
        assertFalse(b.belongsToPlayer(p1));
        assertTrue(b.belongsToPlayer(p2));
        
        // On retire les pions du joueur 2 - J1 a le controle
        b.removePlayer(pt2);
        b.removePlayer(pt3);
        assertTrue(b.belongsToPlayer(p1));
        assertFalse(b.belongsToPlayer(p2));
    }
}