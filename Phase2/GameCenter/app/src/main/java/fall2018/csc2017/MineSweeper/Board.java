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
        setMineNumbers();
    }

    /***
     * Generates the field of mines randomly.
     */
    private void generateBlocks() {

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                blocks[i][j] = new Block(percentMines);
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
                int[] local = getLocalBlocks(i*numCols+j);
                for(int x: local){
                    if (getBlock(x) != null && ((Block) getBlock(x)).isMineType()) {
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
        local[0] = (pos-1-numCols);
        local[1] = (pos-numCols);
        local[2] = (pos+1+numCols);
        local[3] = (pos-1);
        local[4] = (pos+1);
        local[5] = (pos-1+numCols);
        local[6] = (pos+numCols);
        local[7]= (pos+1+numCols);

        return local;
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

    /***
     * Reveal's all the blocks surrounding a given block.
     * @param pos the position of the given block
     */
    public void revealLocal(int pos){
        if (getBlock(pos).getNumMines() == 0){
            for (int l: getLocalBlocks(pos)){
                if (getBlock(l) != null){
                    revealLocal(l);
                }
            }
        }
        getBlock(pos).setVisible();
    }



    public Block getBlock(int pos){
        try{
            int r = pos % numCols;
            int c = (pos - r)/numRows;
            return blocks[c][r];
        }catch (IndexOutOfBoundsException e){
            System.out.println("Block does not exist.");
            return null;
        }
    }
}
