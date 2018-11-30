package fall2018.csc2017.mine_sweeper;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests 100% of Minesweeper blocks.
 * simple getters and setters not tested.
 */
public class BlockTest {
    private Block block;
    private Block mineblock;


    /**
     * Creates 2 blocks. 1 that is a mine and 1 is not a mine.
     */
    @Before
    public void setUp() {
        //generates a mine block
        block = new Block(1);
        //generates a non-mine block
        mineblock = new Block(-1);
    }

    /**
     * Tests if block is a mine or not
     */
    @Test
    public void isMineType() {
        assertTrue(block.isMineType());
        assertFalse(mineblock.isMineType());
    }

    /**
     * Tests methods isVisible() and setVisible()
     */
    @Test
    public void setVisible() {
        assertFalse(mineblock.isVisible());
        mineblock.setVisible();
        assertTrue(mineblock.isVisible());
    }

    /**
     * Tests methods isFlagged() and toggleFlagged()
     */
    @Test
    public void toggleFlagged() {
        assertFalse(mineblock.isFlagged());
        mineblock.toggleFlagged();
        assertTrue(mineblock.isFlagged());
    }

    /**
     * Tests method toggleFlagged() when block is visible
     */
    @Test
    public void flaggedVisible() {
        mineblock.setVisible();
        assertTrue(mineblock.isVisible());
        mineblock.toggleFlagged();
        assertFalse(mineblock.isFlagged());
    }

    /**
     * Tests method setVisible() when block is flagged
     */
    @Test
    public void visibleFlagged() {
        mineblock.toggleFlagged();
        assertTrue(mineblock.isFlagged());
        mineblock.setVisible();
        assertFalse(mineblock.isFlagged());
    }
}