package fall2018.csc2017.sliding_tiles;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SlidingTilesGameManagerTest {

    private SlidingTilesGameManager manager;
    @Before
    public void setUp(){
        manager = new SlidingTilesGameManager("name");
        manager.setUndo(3);
    }
    @Test
    public void getUndos() {
        assertEquals(3, manager.getUndos());
    }
}