package fall2018.csc2017.MineSweeper;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Board extends Observable implements Serializable {
    private int numCols;
    private int numRows;
    //private ArrayList<Block> blocks;
    private Block[][] blocks;
    double percentMines;

    public Board(int col, int row, double percentMines){
        this.numCols = col;
        this.numRows = row;
        this.percentMines = percentMines;
        //blocks = new ArrayList<>(col*row);
        blocks = new Block[col][row];
        generateBlocks();
    }

    private void generateBlocks() {
        //Randomly generates the board
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                blocks[i][j] = new Block(percentMines);
            }
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



    public Block getBlock(int pos){
        try{

            int r = pos % numCols;
            int c = (pos - r)/numRows;
            Log.d("lmao", r+" ," +c);
            return blocks[c][r];
        }catch (IndexOutOfBoundsException e){
            System.out.println("Block does not exist.");
            return null;
        }
    }
}
