package fall2018.csc2017.twenty_forty_eight;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TFETileTest {

    private TFETile tile;

    @Before
    public void setUp() throws Exception{
        tile = new TFETile(4);

    @Test
    public void TFEvaluesetter() {
    }

    @Test
    public void getTileValue() {
        assertEquals(4, tile.getTileValue());
    }

    @Test
    public void copy() { }
}