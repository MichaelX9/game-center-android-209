package fall2018.csc2017.sliding_tiles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Observable;


/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The number of rows.
     */
    private int numCols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    Board(int row, int col, Tile[][] tiles) {
        numRows = row;
        numCols = col;
        this.tiles = new Tile[numRows][numCols];
        this.tiles = tiles;
    }


    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return numCols * numRows;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }


    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        int firstID = getTile(row1, col1).getId();
        Drawable firstBG = getTile(row1, col1).getBackground();
        int secondID = getTile(row2, col2).getId();
        Drawable secondBG = getTile(row2, col2).getBackground();
        tiles[row1][col1] = new Tile(secondID, secondBG);
        tiles[row2][col2] = new Tile(firstID, firstBG);

        setChanged();
        notifyObservers();
    }

    void postSer(Context context) {
        for (Tile t : this) {
            t.postSer(context);
        }

    }

    @Override
    @NonNull
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /***
     * Getter for numRows
     * @return numRows
     */
    int getNumRows() {
        return numRows;
    }

    /***
     * Getter for numCols
     * @return numCols
     */
    int getNumCols() {
        return numCols;
    }

    /**
     * Tile iterator for Board
     */
    private class TileIterator implements Iterator<Tile> {
        int nextRowIndex = 0;
        int nextColIndex = 0;

        @Override
        public Tile next() {
            Tile nTile = getTile(nextRowIndex, nextColIndex);
            if (nextColIndex == numCols - 1) {
                nextRowIndex++;
                nextColIndex = 0;

            } else {
                nextColIndex++;
            }

            return nTile;
        }

        @Override
        public boolean hasNext() {
            return nextRowIndex != numRows;

        }
    }
}
