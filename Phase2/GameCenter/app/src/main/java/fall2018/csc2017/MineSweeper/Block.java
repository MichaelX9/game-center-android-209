package fall2018.csc2017.MineSweeper;

import java.io.Serializable;
import java.util.Observable;
import java.util.Random;

public class Block extends Observable implements Serializable {

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

    Block(double percentMines){
        Random rand = new Random();

        if (rand.nextDouble() < percentMines) {
            isMine = true;
        }

    }

    boolean isMineType(){
        return isMine;
    }

    public boolean isVisible(){
        return visible;
    }

    public boolean isFlagged(){
        return flagged;
    }

    void setVisible(){
        visible = true;
        if (isFlagged()){
            flagged = false;
        }
        setChanged();
        notifyObservers();
    }

    void toggleFlagged(){
        if (isVisible()){
            flagged = false;
        }else{
            flagged = !flagged;
        }
        setChanged();
        notifyObservers();
    }

    int getNumMines() {
        return numMines;
    }

    void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    void setMine(boolean state){this.isMine = state;}

}
