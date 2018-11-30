package fall2018.csc2017.twenty_forty_eight;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TFETileTest {

    private int newValue;
    private TFETile tile;
    @Before
    public void setUp(){
        tile = new TFETile(4);
    }

    @Test
    public void getTileValue() {
        assertEquals(4, tile.getTileValue());
    }

    @Test
    public void copy() {
        TFETile newTile = tile.copy();
        assertEquals(tile.getTileValue(), newTile.getTileValue());
    }
    @Test
    public void TFEvaluesetter() {
        newValue = 8;
        tile.TFEvaluesetter(newValue);
        assertEquals(8, tile.getTileValue());
    }

}