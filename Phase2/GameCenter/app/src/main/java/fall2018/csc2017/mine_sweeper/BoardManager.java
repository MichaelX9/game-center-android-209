package fall2018.csc2017.mine_sweeper;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

import fall2018.csc2017.game_manager.GameManager;
import fall2018.csc2017.launch_centre.GameLaunchActivity;

/***
 * A class manages the minesweeper gameboard.
 */
public class BoardManager implements Serializable {

    /**
     * The scoreBoard for the game.
     */
    public MineSweeperScoreBoard scoreBoard;
    /**
     * The board for the game to be managed.
     */
    private Board board;

    /**
     * A new BoardManager.
     *
     * @param board the board to be assigned
     */
    public BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Getter for the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * setter for scoreboard
     *
     * @param scoreBoard the scoreboard
     */
    void setScoreBoard(Context context, MineSweeperScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
        this.scoreBoard.setUserScores(GameManager.scoreGetter(context, "Minesweeper",
                GameLaunchActivity.username));
        this.scoreBoard.setHighScores(GameManager.scoreGetter(context, "Minesweeper"));
    }

    /***
     * Processes a tap on a block.
     * @param context the context of this click
     * @param position The index of the block in board.blocks
     */
    void processClick(Context context, int position) {
        if (board.getBlock(position).isMineType()) {
            scoreBoard.finishTiming();
            scoreBoard.updateDurationPlayed();
            int score = scoreBoard.calculateScore();
            MenuActivity.manager.addScore(context, score, "Minesweeper");
            Toast.makeText(context, "YOU LOST!" + " \n Your score is " + score +
                    ".\n Your highest score is " + scoreBoard.getUserHighestScore() +
                    ".\n The game's highest score is " + scoreBoard.getGameHighestScore() +
                    ". ", Toast.LENGTH_LONG).show();
            MenuActivity.manager.save(context);

            for (int i = 0; i < board.getNumCols(); i++) {
                for (int j = 0; j < board.getNumRows(); j++) {
                    board.getBlock(i * board.getNumCols() + j).setVisible();
                }
            }
        } else {
            board.revealLocal(position);
            scoreBoard.updateScoreOnClick(position);

            if (board.solved()) {
                scoreBoard.finishTiming();
                scoreBoard.updateDurationPlayed();
                scoreBoard.incrementMoveCount();
                if (scoreBoard.getNumberOfMoves() % GameManager.autosaveInterval == 0) {
                    MenuActivity.manager.save(context);
                }
                int score = scoreBoard.calculateScore();
                MenuActivity.manager.addScore(context, score, "Minesweeper");
                Toast.makeText(context, "YOU WIN!" + " \n Your score is " + score +
                        ".\n Your highest score is " + scoreBoard.getUserHighestScore() +
                        ".\n The game's highest score is " + scoreBoard.getGameHighestScore() +
                        ". ", Toast.LENGTH_LONG).show();
            }
        }
    }

    /***
     *  Processes a tap and hold on a clock
     * @param position The index of the block in board.blocks
     */
    void processLongClick(int position) {
        if (!board.getBlock(position).isVisible()) {
            board.getBlock(position).toggleFlagged();
        }
    }


}
