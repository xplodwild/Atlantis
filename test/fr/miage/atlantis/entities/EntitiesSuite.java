/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.entities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Loris
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({fr.miage.atlantis.entities.WhaleTest.class, fr.miage.atlantis.entities.SeaSerpentTest.class, fr.miage.atlantis.entities.PlayerTokenTest.class, fr.miage.atlantis.entities.GameEntityTest.class, fr.miage.atlantis.entities.SharkTest.class, fr.miage.atlantis.entities.BoatTest.class})
public class EntitiesSuite {

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
}