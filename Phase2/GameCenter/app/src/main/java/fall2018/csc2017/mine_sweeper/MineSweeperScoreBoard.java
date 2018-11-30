package fall2018.csc2017.mine_sweeper;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fall2018.csc2017.score_board.ScoreBoard;

/**
 * The score_board for the mine_sweeper game.
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
    private ArrayList<String> userScores;

    /**
     * A list of all recorded scores ranked from lowest to highest.
     */
    private ArrayList<String> highScores;

    /***
     * Initialize a scoreboard
     */
    public MineSweeperScoreBoard() {
        score = 0;
        userScores = new ArrayList<>();
        highScores = new ArrayList<>();
        durationPlayed = Duration.ofSeconds(0);
    }

    /**
     * set userScores to the given ArrayList.
     * @param lst the ArrayList used to set userScores.
     */
    public void setUserScores(ArrayList<String> lst) {
        userScores = lst;
    }

    /**
     * set highScores to the given ArrayList.
     * @param lst the ArrayList used to set highScores.
     */
    public void setHighScores(ArrayList<String> lst) {
        highScores = lst;
    }

    /**
     * Return the highest score user username has achieved so far; if user username has no scores,
     * return null.
     */
    public String getUserHighestScore() {
        if (userScores == null){
            return null;
        }
        String onlyScore = userScores.get(userScores.size() - 1);
        return onlyScore.substring(0, onlyScore.indexOf(':'));
    }

    /**
     * Return the highest score any user has achieved
     */
    public String getGameHighestScore() {
        String onlyScore = highScores.get(highScores.size() - 1);
        return onlyScore.substring(0, onlyScore.indexOf(':'));
    }


    /**
     * Update the score (after each click) based on the number of blocks revealed.
     * @param position the position of the block clicked.
     */
    void updateScoreOnClick(int position) {
        if (MenuActivity.manager.getGameState() == null){
            return;
        }
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
        if (highScores != null) {
            highScores.add(String.valueOf(score) + ": ");
            userScores.add(String.valueOf(score) + ": ");
            Collections.sort(highScores, new sortByScore());
            Collections.sort(userScores, new sortByScore());
        }
        return score;
    }
}
