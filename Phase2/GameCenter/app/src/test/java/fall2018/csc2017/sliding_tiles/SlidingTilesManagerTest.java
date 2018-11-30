package fall2018.csc2017.sliding_tiles;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SlidingTilesManagerTest {

    private SlidingTilesManager manager;
    @Before
    public void setUp(){
        manager = new SlidingTilesManager("name");
        manager.setUndo(3);
    }
    @Test
    public void getUndos() {
        assertEquals(3, manager.getUndos());
    }
}