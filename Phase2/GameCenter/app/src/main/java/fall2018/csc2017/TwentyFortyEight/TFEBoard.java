package fall2018.csc2017.TwentyFortyEight;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
        boardTiles = new TFETile[col][row];
        generateBoard();
    }

    void generateBoard() {
        List<TFETile> tiles = new ArrayList<>();
        for (int i = 1; i <= numCol * numRow; i++) {
            TFETile newTile = new TFETile(i);
            tiles.add(newTile);
        }
        Collections.shuffle(tiles);
        tiles.get(0).TFEvaluesetter(Math.random() < 0.9 ? 2 : 4);
        tiles.get(1).TFEvaluesetter(Math.random() < 0.9 ? 2 : 4);

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
        return boardTiles[col][row];
    }

    TFETile tileGetter(int position){
        int row = position /numCol;
        int col = position % numCol;
        return tileGetter(row, col);
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
