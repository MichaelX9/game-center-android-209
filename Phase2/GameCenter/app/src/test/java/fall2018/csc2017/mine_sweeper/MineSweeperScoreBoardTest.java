package fall2018.csc2017.mine_sweeper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Unit tests for MineSweeperScoreBoard
 */
public class MineSweeperScoreBoardTest {

    private MineSweeperScoreBoard scoreBoard;

    @Before
    public void setUp(){
        scoreBoard = new MineSweeperScoreBoard();
    }

    /**
     * Tests if getUserHighestScore() returns the user's highest score.
     */
    @Test
    public void getUserHighestScore() {
        ArrayList<String> lst1 = new ArrayList<>();
        Random r = new Random();
        lst1.add(((Integer) r.nextInt(10000)).toString()+ ": ");
        scoreBoard.setUserScores(lst1);
        assertEquals(lst1.get(0).substring(0,  lst1.get(0).indexOf(':') ),
                scoreBoard.getUserHighestScore());
    }

    /**
     * Tests if getGameHighestScore() returns the game's highest score.
     */
    @Test
    public void getGameHighestScore() {
        ArrayList<String> lst1 = new ArrayList<>();
        Random r = new Random();
        lst1.add(((Integer)r.nextInt(10000)).toString() + ": ");
        scoreBoard.setHighScores(lst1);
        assertEquals(lst1.get(0).substring(0,  lst1.get(0).indexOf(':') ),
                scoreBoard.getGameHighestScore());
    }
}