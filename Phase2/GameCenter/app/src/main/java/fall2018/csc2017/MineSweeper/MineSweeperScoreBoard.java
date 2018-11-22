package fall2018.csc2017.MineSweeper;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
     * A collection of each users' list of scores ranked from lowest to highest
     * allowing for look-up by username.
     */
    ArrayList<Integer> userScores = new ArrayList<>();

    /**
     * A list of all recorded scores ranked from lowest to highest.
     */
    ArrayList<Integer> highScores = new ArrayList<>();

    /***
     * Initialize a scoreboard
     */
    MineSweeperScoreBoard() {
        score = 0;
        durationPlayed = Duration.ofSeconds(0);
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
        int[] blocksRevealed = MenuActivity.manager.getGameState().getBoard().getLocalBlocks(position);
        int numBlocksRevealed = blocksRevealed.length;
        score += 100 * numBlocksRevealed;
    }


    /**
     * Return the score calculated based on all relevant factors for the current user after
     * adding the score to userToScores and highScores.
     */
    protected Integer calculateScore() {
        score -= (int) durationPlayed.getSeconds();
        highScores.add(score);
        userScores.add(score);
        Collections.sort(highScores);
        Collections.sort(userScores);

        return score;
    }

}
