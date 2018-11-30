package fall2018.csc2017.mine_sweeper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests 100% of (Minesweeper) MineManager
 */
public class MineManagerTest {
    private MineManager manager;
    private BoardManager boardManager = null;

    @Before
    public void setUp(){
        manager = new MineManager("test");
    }

    /**
     * Tests method getGameState to see if it returns a BoardManager Object
     */
    @Test
    public void getGameState() {
        assertEquals(boardManager, manager.getGameState());
    }
}