package fall2018.csc2017.TwentyFortyEight;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

import fall2018.csc2017.GameManager.GameManager;
import fall2018.csc2017.LaunchCentre.GameLaunchActivity;

public class TFEBoardManager implements Serializable {

    /**
     * Current 2048 board.
     */
    private TFEBoard board;

    /**
     * The scoreBoard for the game.
     */
    private TFEScoreBoard scoreBoard;

    /**
     * Constructor for a new board manager.
     * @param board - board to be managed.
     */
    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }

    /**
     * Getter for current board state.
     * @return current board being managed.
     */
    public TFEBoard getBoard(){return this.board;}

    /**
     * setter for scoreboard
     *
     * @param scoreBoard the scoreboard
     */
    void setScoreBoard(Context context, TFEScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
        this.scoreBoard.setUserScores(GameManager.scoreGetter(context, "TFE",
                GameLaunchActivity.username));
        this.scoreBoard.setHighScores(GameManager.scoreGetter(context, "TFE"));
    }

    /**
     * Getter for the scoreBoard.
     */
    TFEScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    /**
     * Make the text bubble for when the game is solved after recording the score.
     */
    void makeTextForSolvedGame(Context context) {
        if(scoreBoard.getNumberOfMoves() % GameManager.autosaveInterval ==0) {
            MenuActivity.manager.save(context);
        }
        int score = scoreBoard.calculateScore();
        MenuActivity.manager.addScore(context, score,"TFE");
        Toast.makeText(context,"YOU WIN!"+" \n Your score is "+score +
                ".\n Your highest score is "+scoreBoard.getUserHighestScore()+
                ".\n The game's highest score is "+scoreBoard.getGameHighestScore()+
                ". ",Toast.LENGTH_LONG).show();
    }

    /**
     * Make the text bubble for when the game is over after recording the score.
     */
    void makeTextForLostGame(Context context) {
        int score = scoreBoard.calculateScore();
        MenuActivity.manager.addScore(context, score, "TFE");
        Toast.makeText(context,  "YOU LOST!" + " \n Your score is " + score +
                ".\n Your highest score is " + scoreBoard.getUserHighestScore() +
                ".\n The game's highest score is " + scoreBoard.getGameHighestScore() +
                ". ", Toast.LENGTH_LONG).show();
        MenuActivity.manager.save(context);
    }



}

