package fall2018.csc2017.ScoreBoard;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Abstract class to be implemented by all games.
 * The scoreboard for a game.
 * Calculates score for the current user after the game is finished.
 * Keeps track of scores of other users and historical high scores.
 */
public abstract class ScoreBoard implements Serializable {

    /**
     * The username of the current user;
     */
    protected String currentUser;

    /**
     * A list of all recorded scores achieved by the current user ranked from lowest to highest.
     */
    protected ArrayList<Integer> userScores;

    /**
     * The duration for which the current user has played the current round.
     */
    protected Duration durationPlayed;

    /**
     * The local time when the current user started the game.
     */
    private transient LocalDateTime timeStarted;

    /**
     * The local time when the current user finished the game.
     */
    private transient LocalDateTime timeFinished;

    /**
     * The complexity measure of the game using an integer value. Generally, the higher the value
     * of this integer the more complicated or difficult the game (or the selected configuration
     * of the game) is expected to be.
     */
    protected int complexityMeasure;

    /**
     * The number of moves made by the current user so far during the current game play.
     */
    protected int numberOfMoves = 0;

    /**
     * The number of undos made by the current user so far during the current game play.
     */
    protected int numberOfUndos = 0;

     /**
      *Set currentUser to the user that is currently logged in.
      *@param username the username that is currently logged in.
      */
    public void setCurrentUser(String username) {
        currentUser = username;
        userScores = new ArrayList<>();
    }

    /**
     * Set complexityMeasure to the value of the parameter.
     * @param complexity the given complexity measure for the game.
     */
    public void setComplexityMeasure(int complexity) {
        this.complexityMeasure = complexity;
    }

    /**
     * Set timeStarted to the exact time this method is called.
     */
    public void startTiming() {
        this.timeStarted = LocalDateTime.now();
    }

    /**
     * Set timeFinished to the exact time this method is called.
     */
    public void finishTiming() {
        this.timeFinished = LocalDateTime.now();
    }

    /**
     * Update durationPlayed.
     */
    public void updateDurationPlayed() {
        this.durationPlayed = this.durationPlayed.plusSeconds(Duration.between(timeStarted,
                timeFinished).getSeconds());
    }

    public void minusMoveCount(){this.numberOfMoves -= 2; }

    /**
     * Increment numberOfMoves by 1;
     */
    public void incrementMoveCount() {
        this.numberOfMoves++;
    }

    /**
     * Increment numberOfMoves by 1;
     */
    public void incrementUndoCount() {
        this.numberOfUndos++;
    }

    /**
     * Return the score calculated based on all relevant factors (number of moves, number of undos,
     * time taken to finish the game, game complexity) for the current user after adding it to
     * userToScores and highScores.
     */
    protected abstract Integer calculateScore();

    /**
     * Returns # of moves
     */
    public int getNumberOfMoves(){return numberOfMoves;}

    /**
     * writes object using default implementation.
     * @param out the content to be written
     * @throws IOException as in default implementation
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * reads object using default implementation.
     * @param in the input to be read
     * @throws IOException as in default implementation
     * @throws ClassNotFoundException as in default implementation
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

}
