package fall2018.csc2017.MineSweeper;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private int numCols = 10;
    private int numRows = 10;
    private ArrayList<Block> blocks;

    public Board(int col, int row, int numMines){
        this.numCols = col;
        this.numRows = row;
        blocks = new ArrayList<>(col*row);
        generateBlocks(numMines);
    }

    private void generateBlocks(int numMines){
        //TODO: randomly generate the board
        for (Block i: blocks){
            i = new Block();
        }
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumBlocks(){
        return numCols*numRows;
    }

    public Block getBlock(int row, int col){
        return getBlock((row)*numCols+col);
    }

    public Block getBlock(int index){
        try{
            return blocks.get(index);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Block does not exist.");
            return null;
        }
    }
}
