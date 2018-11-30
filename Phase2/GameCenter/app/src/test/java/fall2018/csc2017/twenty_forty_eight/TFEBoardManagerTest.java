package fall2018.csc2017.twenty_forty_eight;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TFEBoardManagerTest {

    private TFEBoardManager manager;
    private TFEBoard board;

    @Before
    public void setUp(){
        board = new TFEBoard(4, 4);
        manager = new TFEBoardManager(board);
    }

    @Test
    public void getBoard() {
        assertEquals(board, manager.getBoard());
    }
}