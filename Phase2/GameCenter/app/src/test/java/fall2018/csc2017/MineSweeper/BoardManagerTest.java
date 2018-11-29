package fall2018.csc2017.MineSweeper;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests 100% of (Minesweeper) Board Manager
 */
public class BoardManagerTest {
    private Board mineBoard;
    private BoardManager minesweeperBM;
    private MineSweeperScoreBoard mineScoreBoard;
    private Context context;

    /**
     * Creates new Board, BoardManager, MineSweeperScoreBoard, and Context
     * after every method run
     */
    @Before
    public void setUp(){
        // Defines a board with of normal size & mine percentage
        mineBoard = new Board(10, 10, 0.15);
        minesweeperBM = new BoardManager(mineBoard);
        mineScoreBoard = new MineSweeperScoreBoard();
        context = new MockContext();
    }

    /**
     * Tests if setScoreBoard() sets ScoreBoard Object
     */
    @Test
    public void setScoreBoard() {
        minesweeperBM.setScoreBoard(context, mineScoreBoard);
        assertEquals(mineScoreBoard, minesweeperBM.scoreBoard);
    }


    /**
     * Tests if getBoard() returns Board Object
     */
    @Test
    public void getBoard() {
        assertEquals(mineBoard, minesweeperBM.getBoard());
    }


    /**
     * Tests if processClick properly sets a tile as visible
     */
    @Test
    public void processClick() {
        minesweeperBM.setScoreBoard(context, mineScoreBoard);

        assert !minesweeperBM.getBoard().getBlock(0).isVisible();
        minesweeperBM.processClick(context, 0);
        assert minesweeperBM.getBoard().getBlock(0).isVisible();
    }


    /**
     * Tests if processLongClick properly toggles a block as flagged / not flagged
     */
    @Test
    public void processLongClick() {

        minesweeperBM.setScoreBoard(context, mineScoreBoard);

        assert !minesweeperBM.getBoard().getBlock(99).isFlagged();
        minesweeperBM.processLongClick(context, 99);
        assert minesweeperBM.getBoard().getBlock(99).isFlagged();
        minesweeperBM.processLongClick(context, 99);
        assert !minesweeperBM.getBoard().getBlock(99).isFlagged();
    }

    /**
     * Sets mineScoreBoard to null after a method has been run.
     */
    @After
    public void tearDown() {
        mineScoreBoard = null;
    }

}