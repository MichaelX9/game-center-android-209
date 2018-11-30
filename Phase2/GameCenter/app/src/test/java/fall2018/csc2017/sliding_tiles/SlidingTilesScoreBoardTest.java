package fall2018.csc2017.sliding_tiles;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Unit tests for SlidingTilesScoreBoard.
 */
public class SlidingTilesScoreBoardTest {

    private SlidingTilesScoreBoard scoreBoard;

    @Before
    public void setUp(){
        scoreBoard = new SlidingTilesScoreBoard();
    }

    /**
     * Tests if getGameHighestScore() returns the game's highest score.
     */
    @Test
    public void getGameHighestScore() {
        ArrayList<String> lst1 = new ArrayList<>();
        Random r = new Random();
        lst1.add(((Integer)r.nextInt(10000)).toString() + ": ");
        scoreBoard.highScores = lst1;
        assertEquals(lst1.get(0).substring(0,  lst1.get(0).indexOf(':') ),
                scoreBoard.getGameHighestScore());
    }
}