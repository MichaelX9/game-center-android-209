package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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

    /***
     * The images for tiles
     */
    private transient List<Drawable> BGs;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    Board(int row, int col) {
        numRows = row;
        numCols = col;
        tiles = new Tile[numRows][numCols];
        BGs = new ArrayList<>(numTiles());
    }

    /***
     * Generate tiles based on images in BGs
     */
    void generateTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = getNumRows() * getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, BGs.get(tileNum)));
        }
        tiles.get(numTiles - 1).setEmpty();
        Collections.shuffle(tiles);
        while (!isSolvable(tiles)){
            Collections.shuffle(tiles);
        }

        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Checker for board solvability.
     */

    boolean isSolvable(List<Tile> tiles){
        int parity = 0;
        int width = numCols;
        int curRow = 0;
        int blankRow = 0;

        for (int i = 0; i < numCols * numRows; i++){
            if (i % width == 0){
                curRow ++;
            }
            if (tiles.get(i).getId() == numRows * numCols){
                blankRow = curRow;
                continue;
            }
            for (int j = i+1; j < numCols * numRows; j++){
                if (tiles.get(i).getId() > tiles.get(j).getId() && tiles.get(j).getId() != 0){
                    parity++;
                }
            }

        }
        if (width%2 == 0){
            if (blankRow % 2 ==0){
                return parity % 2 ==0;
            }
            else {
                return parity % 2 != 0;
            }
        }
        else {
            return parity % 2 ==0;
        }
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

    void decompress(Context context) {
        Iterator<Tile> iter = iterator();
        if (BGs == null) {
            BGs = new ArrayList<>(numTiles());
        }

        BGs.clear();
        while (iter.hasNext()) {
            Tile t = iter.next();
            t.decompress(context);
            BGs.add(t.getBackground());
            if (t.getId() == numCols * numRows) {
                Tile.setEmptyTile(t.getBackground());
            }
        }

    }

    /***
     * add an image to BGs
     * @param d the image to be added
     */
    void addBG(Drawable d) {
        BGs.add(d);
    }

    /***
     * empty the BGs list
     */
    void clearBGs() {
        BGs.clear();
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
