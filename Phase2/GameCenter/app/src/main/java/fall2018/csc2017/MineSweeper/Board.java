package fall2018.csc2017.MineSweeper;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class  Board extends Observable implements Observer, Serializable {
    private int numCols;
    private int numRows;
    //private ArrayList<Block> blocks;
    private Block[][] blocks;
    private double percentMines;

    Board(int col, int row, double percentMines){
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


    int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    int getNumBlocks(){
        return numCols*numRows;
    }

    /***
     * Reveal's all the blocks surrounding a given block.
     * @param pos the position of the given block
     */
    void revealLocal(int pos){

        getBlock(pos).setVisible();
        if(!getBlock(pos).isMineType() && getBlock(pos).getNumMines() == 0){
            for(int i: getLocalBlocks(pos)){
                if (i != -1 && !getBlock(i).isVisible() && getBlock(i).getNumMines() == 0){
                    revealLocal(i);
                }
                else if(i != -1 && !getBlock(i).isVisible() && getBlock(i).getNumMines() != 0){
                    getBlock(i).setVisible();
                }
            }
        }

    }


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



}
