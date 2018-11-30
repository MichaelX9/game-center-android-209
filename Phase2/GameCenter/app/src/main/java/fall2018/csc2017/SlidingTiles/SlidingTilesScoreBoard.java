package fall2018.csc2017.SlidingTiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import fall2018.csc2017.ScoreBoard.ScoreBoard;

/**
 * The ScoreBoard for the SlidingTiles game.
 */
class SlidingTilesScoreBoard extends ScoreBoard {

    /**
     * A collection of each users' list of scores ranked from lowest to highest
     * allowing for look-up by username.
     */
    HashMap<String, ArrayList<String>> userToScores = new HashMap<>();

    /**
     * A list of all recorded scores ranked from lowest to highest.
     */
    ArrayList<String> highScores = new ArrayList<>();

    /***
     * Initialize a scoreboard
     */
    SlidingTilesScoreBoard() {
        durationPlayed = Duration.ofSeconds(0);
    }


    /**
     * Return the highest score user username has achieved so far; if user username has no scores,
     * return null.
     */
    String getUserHighestScore() {
        if (userToScores.get(currentUser) != null) {
            return userToScores.get(currentUser).get(userScores.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Return the highest score any user has achieved
     */
    String getGameHighestScore() {
        return highScores.get(highScores.size() - 1);
    }


    /**
     * Return the score calculated based on all relevant factors (number of moves, number of undos,
     * time taken to finish the game, game complexity) for the current user.
     */
    protected Integer calculateScore() {
        int score = 0;
        int numberOfSeconds = (int) durationPlayed.getSeconds();

        //Get the base score of 100 times the complexityMeasure, other factors decrease this score.
        score += complexityMeasure * 1000;

        //Calculate the amount to be subtracted from the base score: For every minute spent,
        //5 points are subtracted; For every undo, a number of points equal to (maximumComplexity -
        // complexityMeasure) is subtracted; For every move made during the game, 3 points are subtracted.
        int d = ((numberOfSeconds / 60) * 5) + (numberOfUndos * (BoardManager.maximumComplexity -
                complexityMeasure)) + (numberOfMoves * 3);
        score -= d;
        updateData(score);
        return score;
    }

    /**
     * adds score to userToScores and highScores.
     *
     * @param score the recently calculated score for the player.
     */
    private void updateData(Integer score) {
        highScores.add(String.valueOf(score));
        userScores.add(String.valueOf(score));
        Collections.sort(highScores);
        Collections.sort(userScores);
        userToScores.put(currentUser, userScores);
    }
}