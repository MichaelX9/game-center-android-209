package fall2018.csc2017.mine_sweeper;

import java.io.Serializable;
import java.util.Observable;
import java.util.Random;

/***
 * This class represents a block in minesweeper game board.
 */
public class Block extends Observable implements Serializable {

    /**
     * Number of mines around this block.
     * Equals MINE if the block itself is a mine.
     */
    private int numMines;

    /**
     * Whether the block is flagged.
     */
    private boolean flagged = false;

    /**
     * Whether the block has been made visible.
     */
    private boolean visible = false;

    /**
     * Whether the block is a mine.
     */
    private boolean isMine = false;


    /**
     * A new block.
     */
    Block(double percentMines) {
        Random rand = new Random();

        if (rand.nextDouble() < percentMines) {
            isMine = true;
        }

    }

    /**
     * @return whether the block is a mine.
     */
    boolean isMineType() {
        return isMine;
    }

    /**
     * @return whether the block is visible.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @return return whether the block is Flagged.
     */
    boolean isFlagged() {
        return flagged;
    }

    /**
     * Make the block visible.
     */
    void setVisible() {
        visible = true;
        if (isFlagged()) {
            flagged = false;
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Toggle flagged
     */
    void toggleFlagged() {
        if (isVisible()) {
            flagged = false;
        } else {
            flagged = !flagged;
        }
        setChanged();
        notifyObservers();
    }

    /**
     * @return the number of mines on the map.
     */
    int getNumMines() {
        return numMines;
    }

    /**
     * Setter for numMines.
     *
     * @param numMines the desired number of mines.
     */
    void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    /**
     * Set a mine.
     *
     * @param state of whether this is a mine
     */
    void setMine(boolean state) {
        this.isMine = state;
    }

}
