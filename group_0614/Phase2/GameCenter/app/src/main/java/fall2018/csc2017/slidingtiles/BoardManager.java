package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Iterator;

import fall2018.csc2017.GameManager.GameManager;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class  BoardManager implements Serializable {

    static int maximumComplexity = 200;
    SlidingTilesScoreBoard scoreBoard;
    /**
     * The board being managed.
     */
    private Board board;


    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /***
     * setter for scoreboard
     * @param scoreBoard the scoreboard
     */
    void setScoreBoard(SlidingTilesScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /***
     * Set the empty tile image.
     * @param d the image to be set
     */
    void setEmptyTile(Drawable d) {
        Tile.setEmptyTile(d);
    }

    /***
     * decompress the board after unserialized
     * @param context the context
     */
    void decompress(Context context) {
        board.decompress(context);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    private boolean puzzleSolved() {
        Iterator<Tile> iter = this.board.iterator();
        int i = 1;
        while (iter.hasNext()) {
            if (iter.next().getId() != i) {
                return false;
            } else {
                i++;
            }
        }
        return true;

    }

    /**
     * Return whether the empty Tile is "Above", "Below", "Left", or "Right"
     *
     * @param position the tile to check
     * @return the direction of the blank tile relative to position
     */
    private String posEmptyTile(int position) {
        int row = position / board.getNumCols();
        int col = position % board.getNumCols();
        int blankId = board.numTiles();

        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNumRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNumCols() - 1 ? null : board.getTile(row, col + 1);

        if (below != null && below.getId() == blankId) {
            return "Below";
        } else if (above != null && above.getId() == blankId) {
            return "Above";
        } else if (left != null && left.getId() == blankId) {
            return "Left";
        } else if (right != null && right.getId() == blankId) {
            return "Right";
        } else {
            return "None";
        }
    }


    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    private boolean isValidTap(int position) {
        return !posEmptyTile(position).equals("None");
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        //StartingActivity.manager.addMove();
        int row = position / board.getNumCols();
        int col = position % board.getNumCols();
        String emptyTilePos = posEmptyTile(position);
        switch (emptyTilePos) {
            case "Above":
                board.swapTiles(row - 1, col, row, col);
                StartingActivity.manager.getPastMoves().add(position - board.getNumCols());
                break;
            case "Below":
                board.swapTiles(row + 1, col, row, col);
                StartingActivity.manager.getPastMoves().add(position + board.getNumCols());
                break;
            case "Right":
                board.swapTiles(row, col + 1, row, col);
                StartingActivity.manager.getPastMoves().add(position + 1);
                break;
            case "Left":
                board.swapTiles(row, col - 1, row, col);
                StartingActivity.manager.getPastMoves().add(position - 1);
                break;
        }
    }

    /***
     * processes a tap
     * @param context the context
     * @param position tap position
     */
    void processTapMovement(Context context, int position) {
        if (isValidTap(position)) {
            touchMove(position);
            scoreBoard.incrementMoveCount();
            //Checks if its time to autoSave
            if (scoreBoard.getNumberOfMoves() % GameManager.autosaveInterval == 0) {
                StartingActivity.manager.save(context);
            }

            if (puzzleSolved()) {
                scoreBoard.finishTiming();
                scoreBoard.updateDurationPlayed();
                scoreBoard.setComplexityMeasure(board.numTiles());
                int score = scoreBoard.calculateScore();
                int userHighestScore = scoreBoard.getUserHighestScore();
                int gameHighestScore = scoreBoard.getGameHighestScore();
                StartingActivity.manager.addScore(context, score,"SlidingTiles");
                Toast.makeText(context, "YOU WIN!" + " \n Your score is " + score +
                                ".\n Your highest score is " + userHighestScore +
                                ".\n The game's highest score is " + gameHighestScore + ". ",
                        Toast.LENGTH_LONG).show();
                StartingActivity.manager.save(context);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
