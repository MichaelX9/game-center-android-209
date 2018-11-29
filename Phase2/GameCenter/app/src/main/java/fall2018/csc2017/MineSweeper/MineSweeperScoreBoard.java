package fall2018.csc2017.MineSweeper;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.ScoreBoard.ScoreBoard;

/**
 * The ScoreBoard for the MineSweeper game.
 */
public class MineSweeperScoreBoard extends ScoreBoard {

    /**
     * The score that is being updated throughout the game.
     */
    private int score;

    /**
     * A collection of the current user's list of scores ranked from lowest to highest
     * allowing for look-up by username.
     */
    private ArrayList<Integer> userScores;

    /**
     * A list of all recorded scores ranked from lowest to highest.
     */
    private ArrayList<Integer> highScores;

    /***
     * Initialize a scoreboard
     */
    MineSweeperScoreBoard() {
        score = 0;
        userScores = new ArrayList<>();
        highScores = new ArrayList<>();
        durationPlayed = Duration.ofSeconds(0);
    }

    /**
     * set userScores to the given ArrayList.
     * @param lst the ArrayList used to set userScores.
     */
    void setUserScores(ArrayList<Integer> lst) {
        userScores = lst;
    }

    /**
     * set highScores to the given ArrayList.
     * @param lst the ArrayList used to set highScores.
     */
    void setHighScores(ArrayList<Integer> lst) {
        highScores = lst;
    }

    /**
     * Return the highest score user username has achieved so far; if user username has no scores,
     * return null.
     */
    Integer getUserHighestScore() {
        return userScores.get(userScores.size() - 1);
    }

    /**
     * Return the highest score any user has achieved
     */
    Integer getGameHighestScore() {
        return highScores.get(highScores.size() - 1);
    }


    /**
     * Update the score (after each click) based on the number of blocks revealed.
     * @param position the position of the block clicked.
     */
    void updateScoreOnClick(int position) {
        int numBlocksRevealed = MenuActivity.manager.getGameState().getBoard().revealLocal(position);
        score += 300 * numBlocksRevealed;
    }


    /**
     * Return the score calculated based on all relevant factors for the current user after
     * adding the score to userToScores and highScores.
     */
    protected Integer calculateScore() {
        score -= (int) durationPlayed.getSeconds();
        if (score < 0) {
            score = 0;
        }
        highScores.add(score);
        userScores.add(score);
        Collections.sort(highScores);
        Collections.sort(userScores);

        return score;
    }

}
