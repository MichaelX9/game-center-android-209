package fall2018.csc2017.SlidingTiles;

import android.content.Context;

import java.util.ArrayList;

import fall2018.csc2017.GameManager.GameManager;

public class SlidingTilesManager extends GameManager {

    /**
     * Number of remaining undos for the player. Negative values denote unlimited undo chances
     */
    private static int undos;

    /**
     * List of positions of each tapped tile after swapTile occurs.
     */
    private ArrayList<Integer> pastMoves = new ArrayList<>();

    /**
     * Class constructor for SlidingTilesManager.
     *
     * @param name - username of player.
     */
    SlidingTilesManager(String name) {
        super(name, "SlidingTiles");
        autosaveInterval = 3;
    }

    /**
     * Getter for number of undos remaining.
     * @return number of undos
     */
    int getUndos() {
        return undos;
    }

    /**
     * Getter for list of pastMoves.
     *
     * @return ArrayList pastMoves.
     */
    ArrayList<Integer> getPastMoves() {
        return pastMoves;
    }

    /**
     * Getter for the stored GameState in SlidingTilesManager.
     *
     * @return BoardManager
     */
    @Override
    public BoardManager getGameState() {
        return (BoardManager) gameState;
    }

    /**
     * Undo move subtraction helper function.
     */
    private void subtractMoves() {
        getGameState().scoreBoard.minusMoveCount();
    }

    /**
     * Setter for number of undo chances.
     *
     * @param newUndo - new number of allowed Undo chances.
     */
    void setUndo(int newUndo) {
        undos = newUndo;
    }

    /**
     * Undo functionality for SlidingTilesGame. Will revert both recorded number of moves and the
     * board back one move, while decreasing undo chances by one.
     */
    void undo() {
        BoardManager boardToChange = MenuActivity.manager.getGameState();
        undos -= 1;
        boardToChange.touchMove(pastMoves.remove(pastMoves.size() - 1));
        MenuActivity.manager.subtractMoves();
        pastMoves.remove(pastMoves.size() - 1);
        getGameState().scoreBoard.incrementUndoCount();
    }

    @Override
    public void load(Context context, String filename) {
        super.load(context, filename);
        getGameState().decompress(context);
    }
}

