/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Lucie
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({fr.miage.atlantis.logic.GameTurnTest.class, fr.miage.atlantis.logic.GameLogicTest.class, fr.miage.atlantis.logic.GameLogTest.class, fr.miage.atlantis.logic.GameTurnListenerTest.class})
public class LogicSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}