package fall2018.csc2017.MineSweeper;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Board extends Observable implements Observer, Serializable {

    /**
     * The number of columns on the board.
     */
    private int numCols;

    /**
     * The number of rows on the board.
     */
    private int numRows;

    /**
     * The number of blocks on the board.
     */
    private Block[][] blocks;

    /**
     * The percentage of the blocks on the board that are mines.
     */
    private double percentMines;

    /**
     * A new board.
     * @param col number of columns
     * @param row number of rows
     * @param percentMines percentage of blocks on the board which are mines
     */
    public Board(int col, int row, double percentMines){
        this.numCols = col;
        this.numRows = row;
        this.percentMines = percentMines;
        blocks = new Block[col][row];
        generateBlocks();
        setMineNumbers();
    }

    /***
     * Generates the field of mines randomly.
     */
    private void generateBlocks() {

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                blocks[i][j] = new Block(percentMines);
                blocks[i][j].addObserver(this);
            }
        }
    }

    /***
     * Sets each block's numMines attribute.
     */
    private void setMineNumbers() {

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                int mines = 0;
                int[] local = getLocalBlocks(i*numRows+j);
                for(int x: local){
                    if (x != -1 && getBlock(x).isMineType()) {
                        mines += 1;
                    }
                }
                blocks[i][j].setNumMines(mines);
            }
        }

    }

    /***
     * Returns the positions of the blocks surrounding a given block.
     * @param pos the position of that block
     * [0][1][2]
     * [3]pos[4]
     * [5][6][7]
     */
    private int[] getLocalBlocks(int pos){
        int[] local = new int[8];
        local[0]=local[1]=local[2]=local[3]=local[4]=local[5]=local[6]=local[7]=-1;
        int c = pos % numRows;
        int r = (pos - c)/numCols;

        if (c > 0){
            local[3] = (pos-1);
            if (r > 0){
                local[0] = (pos-1-numCols);
            }
            if (r+1 < numRows){
                local[5] = (pos-1+numCols);
            }
        }
        if (c+1 < numCols){
            local[4] = (pos+1);
            if (r > 0){
                local[2] = (pos+1-numCols);
            }
            if (r+1 < numRows){
                local[7]= (pos+1+numCols);
            }
        }
        if (r> 0){
            local[1] = (pos-numCols);
        }
        if (r+1 < numRows){
            local[6] = (pos+numCols);
        }
        return local;

    }


    /**
     * Getter for the number of columns.
     * @return numCols
     */
    int getNumCols() {
        return numCols;
    }

    /**
     * Setter for the number of columns.
     * @param numCols the desired number of columns.
     */
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    /**
     * Getter for the number of rows.
     * @return the number of rows.
     */
    int getNumRows() {
        return numRows;
    }

    /**
     * Setter for the number of rows.
     * @param numRows the desired number of rows.
     */
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * Get the total number of blocks on the board.
     * @return number of blocks on the board.
     */
    int getNumBlocks(){
        return numCols*numRows;
    }

    /***
     * Reveal all the blocks surrounding a given block and return the number of blocks revealed.
     * @param pos the position of the given block
     * @return the number of blocks revealed
     */
    int revealLocal(int pos){

        int numBlocksRevealed = 0;

        getBlock(pos).setVisible();
        if(!getBlock(pos).isMineType() && getBlock(pos).getNumMines() == 0){
            for(int i: getLocalBlocks(pos)){
                if (i != -1 && !getBlock(i).isVisible() && getBlock(i).getNumMines() == 0){
                    revealLocal(i);
                }
                else if(i != -1 && !getBlock(i).isVisible() && getBlock(i).getNumMines() != 0){
                    getBlock(i).setVisible();
                    numBlocksRevealed++;
                }
            }
        }
        return numBlocksRevealed + 1;
    }


    /**
     * Get the block at the given position.
     * @param pos the position clicked
     * @return the Block at pos
     */
    Block getBlock(int pos){
        try{
            int c = pos % numRows;
            int r = (pos - c)/numCols;
            return blocks[r][c];
        }catch (IndexOutOfBoundsException e){
            System.out.println("Block does not exist.");
            return null;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    /***
     *Checks (and returns true) if the board is solved.
     */
    boolean solved(){
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                if (!getBlock(i*numCols+j).isVisible() && !getBlock(
                        i*numCols+j).isMineType()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Getter for the percentMines attribute.
     * @return percentMines
     */
    double getPercentMines(){return percentMines;}


}
