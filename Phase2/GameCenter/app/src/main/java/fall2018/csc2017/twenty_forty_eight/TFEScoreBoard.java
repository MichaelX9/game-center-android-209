package fall2018.csc2017.twenty_forty_eight;

import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.score_board.ScoreBoard;

/**
 * The score_board for the 2048 game.
 */
public class TFEScoreBoard extends ScoreBoard {
    /**
     * The score that is being updated throughout the game.
     */
    private int score;

    /**
     * A collection of the current user's list of scores ranked from lowest to highest
     * allowing for look-up by username.
     */
    private ArrayList<String> userScores;

    /**
     * A list of all recorded scores ranked from lowest to highest.
     */
    private ArrayList<String> highScores;

    /***
     * Initialize a scoreboard
     */
    TFEScoreBoard() {
        userScores = new ArrayList<>();
        highScores = new ArrayList<>();
        score = 0;
    }

    /**
     * set userScores to the given ArrayList.
     *
     * @param lst the ArrayList used to set userScores.
     */
    void setUserScores(ArrayList<String> lst) {
        userScores = lst;
    }

    /**
     * set highScores to the given ArrayList.
     *
     * @param lst the ArrayList used to set highScores.
     */
    void setHighScores(ArrayList<String> lst) {
        highScores = lst;
    }

    /**
     * Return the highest score user username has achieved so far; if user username has no scores,
     * return null.
     */
    String getUserHighestScore() {
        String onlyScore = userScores.get(userScores.size() - 1);
        return onlyScore.substring(0, onlyScore.indexOf(':'));
    }

    /**
     * Return the highest score any user has achieved
     */
    String getGameHighestScore() {
        String onlyScore = highScores.get(highScores.size() - 1);
        return onlyScore.substring(0, onlyScore.indexOf(':'));
    }


    /**
     * Update the score (after each click) based the new sums created during merges.
     *
     * @param newSum the new sum created by tile merges
     */
    void updateScoreOnMerge(int newSum) {
        score += newSum;
    }


    /**
     * Return the score calculated based on all relevant factors for the current user after
     * adding the score to userToScores and highScores.
     */
    protected Integer calculateScore() {
        highScores.add(String.valueOf(score + ": "));
        userScores.add(String.valueOf(score + ": "));
        Collections.sort(highScores, new sortByScore());
        Collections.sort(userScores, new sortByScore());

        return score;
    }
}
