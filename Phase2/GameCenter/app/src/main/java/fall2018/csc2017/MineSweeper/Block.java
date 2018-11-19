package fall2018.csc2017.MineSweeper;

import java.io.Serializable;
import java.util.Random;

public class Block implements Serializable {

    /***
     * Number of mines around this block.
     * Equals MINE if the block itself is a mine.
     */
    private int numMines;
    private boolean flagged = false;
    private boolean visible = false;
    private boolean isMine = false;

    static final int MINE = -1;

    public Block(){
        numMines = 0;
    }

    public Block(double percentMines){
        Random rand = new Random();

        if (rand.nextDouble() < percentMines) {
            isMine = true;
        }

    }

    public boolean isMineType(){
        return isMine;
    }

    public boolean isVisible(){
        return visible;
    }

    public boolean isFlagged(){
        return flagged;
    }

    public void setVisible(){
        visible = true;
        if (isFlagged()){
            flagged = false;
        }
    }

    public void toggleFlagged(){
        if (isVisible()){
            flagged = false;
        }else{
            flagged = !flagged;
        }

    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }
}
