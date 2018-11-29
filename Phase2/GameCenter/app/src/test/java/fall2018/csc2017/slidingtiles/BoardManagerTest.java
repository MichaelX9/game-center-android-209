package fall2018.csc2017.slidingtiles;
import android.app.Instrumentation;
import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;


import fall2018.csc2017.MineSweeper.BoardManager;
import fall2018.csc2017.MineSweeper.Board;
import fall2018.csc2017.MineSweeper.MineSweeperScoreBoard;
import static org.junit.Assert.*;

/**
 * Tests 100% of (Minesweeper) Board Manager
 */
public class BoardManagerTest {


    // Defines a board with of normal size & mine percentage
    private Board mineBoard = new Board(10, 10, 0.15);

    private BoardManager minesweeperBM = new BoardManager(mineBoard);
    private MineSweeperScoreBoard mineScoreBoard = new MineSweeperScoreBoard();
    private Context context = new MockContext();

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
     * Tests if processClick properly sets a tile as visbile
     */
    @Test
    public void processClick(){
        minesweeperBM.setScoreBoard(context, mineScoreBoard);

        assert !minesweeperBM.getBoard().getBlock(0).isVisible();
        minesweeperBM.processClick(context, 0);
        assert minesweeperBM.getBoard().getBlock(0).isVisible();
    }




    /**
     * Tests if processLongClick properly toggles a block as flagged / not flagged
     */
    @Test
    public void processLongClick(){

        minesweeperBM.setScoreBoard(context, mineScoreBoard);

        assert !minesweeperBM.getBoard().getBlock(99).isFlagged();
        minesweeperBM.processLongClick(context, 99);
        assert minesweeperBM.getBoard().getBlock(99).isFlagged();
        minesweeperBM.processLongClick(context, 99);
        assert !minesweeperBM.getBoard().getBlock(99).isFlagged();


    }



}