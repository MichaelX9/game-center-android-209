package fall2018.csc2017.twenty_forty_eight;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TFEBoardTest {
    TFEBoard board;

    @Before
    public void setUp() throws Exception {
        board = new TFEBoard(2, 2);
    }

    @Test
    public void numTiles() {
        assertEquals(4, board.numTiles());
    }
}