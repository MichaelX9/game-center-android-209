package fall2018.csc2017.TwentyFortyEight;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import fall2018.csc2017.slidingtiles.Tile;

public class TFEBoard extends Observable implements Serializable, Iterable<TFETile> {

    /**
     * Number of columns in the game board.
     */
    private int numCol;

    /**
     * Number of rows in the game board.
     */
    private int numRow;

    /**
     * Tiles on the board in row-tile order.
     */
    private TFETile[][] boardTiles;

    /**
     * Constructor for a new 2048 board.
     */
    TFEBoard(int col, int row){
        numCol = col;
        numRow = row;
        boardTiles = new TFETile[row][col];
        generateBoard();
    }

    void generateBoard() {
        List<TFETile> tiles = new ArrayList<>();
        for (int i = 1; i <= numCol * numRow; i++) {
            TFETile newTile = new TFETile(0);
            tiles.add(newTile);
        }
        tiles.get(0).TFEvaluesetter(Math.random() < 0.9 ? 2 : 4);
        tiles.get(1).TFEvaluesetter(Math.random() < 0.9 ? 2 : 4);
        Collections.shuffle(tiles);

        Iterator<TFETile> tilesIterator = tiles.iterator();
        for (int row = 0; row != numRow; row++) {
            for (int col = 0; col != numCol; col++) {
                this.boardTiles[row][col] = tilesIterator.next();
            }
        }
    }

    /**
     * Number of total tiles on the board.
     */

    int numTiles(){
        return numCol * numRow;
    }

    /**
     * Tile getter from the board.
     */

    TFETile tileGetter(int row, int col){
        return boardTiles[row][col];
    }

    TFETile tileGetter(int position){
        int r = position / numRow;
        int c = position % numCol;
        return boardTiles[r][c];
    }

    @Override
    @NonNull
    public Iterator<TFETile> iterator() {
        return new TFEIterator();
    }

    /**
     * Getter for number of columns.
     */

    public int getNumCol(){
        return numCol;
    }

    /**
     * Getter for number of rows.
     */

    public int getNumRow(){
        return numRow;
    }

    boolean isSolved(){
        for (int c = 0; c < numCol; c++) {
            for (int r = 0; r < numRow; r++) {
                if (boardTiles[r][c].getTileValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public TFETile[][] getTiles(){
        return boardTiles;
    }

    private class TFEIterator implements Iterator<TFETile> {
        int nextRowIndex = 0;
        int nextColIndex = 0;

        @Override
        public TFETile next() {
            TFETile nTile = tileGetter(nextRowIndex, nextColIndex);
            if (nextColIndex == numCol - 1) {
                nextRowIndex++;
                nextColIndex = 0;

            } else {
                nextColIndex++;
            }

            return nTile;
        }

        @Override
        public boolean hasNext() {
            return nextRowIndex != numRow;

        }
    }
}
