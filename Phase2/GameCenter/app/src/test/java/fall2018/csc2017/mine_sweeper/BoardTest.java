package fall2018.csc2017.mine_sweeper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    @Before
    public void setUp(){
        board = new Board(3,3,.3);
    }

    @Test
    public void getNumCols() {
        assertEquals(3, board.getNumCols());
    }
}