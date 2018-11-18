package fall2018.csc2017.MineSweeper;

import java.io.Serializable;
import java.util.Random;

public class Block implements Serializable {

    private int visibility = UNKNOWN;

    /***
     * Number of mines around this block.
     * Equals MINE if the block itself is a mine.
     */
    private int numMines;
    private boolean isMine = false;
    private boolean flagged = false;

    static final int MINE = -1;
    static final int KNOWN = 9;
    static final int UNKNOWN = 10;
    static final int FLAG = 11;
    static final int MARKED = 12;

    public Block(){
        numMines = 0;
    }

    public Block(double percentMines){
        Random rand = new Random();

        if (rand.nextDouble() < percentMines) {
            this.isMine = true;
        }

    }

    public boolean isMineType(){
        return isMine;
    }

    public boolean getVisibility(){
        return visibility!=UNKNOWN;
    }

    public void toggleFlagged(){
        flagged = !flagged;
    }

    public void setVisible(){
        visibility = KNOWN;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }
}
