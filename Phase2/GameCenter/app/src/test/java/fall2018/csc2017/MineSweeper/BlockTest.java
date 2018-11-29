package fall2018.csc2017.MineSweeper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {
    private Block block;
    private Block mineblock;


    @Before
    public void setUp(){
        //generates a mine block
        block = new Block(1);
        //generates a non-mine block
        mineblock = new Block(-1);
    }

    @After
    public void tearDown(){
    }

    /**
     * Tests if block is a mine or not
     */
    @Test
    public void isMineType() {
        assertTrue(block.isMineType());
        assertFalse(mineblock.isMineType());
    }

    @Test
    public void isVisible() {
    }

    @Test
    public void isFlagged() {
    }

    @Test
    public void setVisible() {
    }

    @Test
    public void toggleFlagged() {
    }

    @Test
    public void getNumMines() {
    }

    @Test
    public void setNumMines() {
    }

    @Test
    public void setMine() {
    }
}