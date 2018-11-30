package fall2018.csc2017.sliding_tiles;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/***
 * This class is used to generate the game board and tiles for sliding_tiles game
 */
abstract class BoardFactory {

    /***
     * Number of rows of the board to be created
     */
    static private int numRows;

    /***
     * Number of columns of the board to be created
     */
    static private int numCols;

    /***
     * A list of images to be set as the background of tiles
     */
    static private List<Drawable> backgrounds;

    /***
     * The image to represent the empty tile
     */
    static private Drawable emptyTileBackground;

    /***
     * Setter of emptyTileBackground
     * @param d the image to be set as emptyTileBackground
     */
    static void setEmptyTileBackground(Drawable d) {
        emptyTileBackground = d;
    }

    /***
     * Getter for numRows
     * @return numRows
     */
    static int getNumRows() {
        return numRows;
    }

    /***
     * Setter for numRows and numCols.
     * Also initiate backgrounds with new capacity indicated by numRows and numCols
     * @param numRows the value to be set as numRows
     * @param numCols the value to be set as numCols
     */
    static void setNumRowsCows(int numRows, int numCols) {
        BoardFactory.numRows = numRows;
        BoardFactory.numCols = numCols;
        backgrounds = new ArrayList<>(numCols * numRows);
    }

    /***
     * Getter for numCols
     * @return numCols
     */
    static int getNumCols() {
        return numCols;
    }

    /***
     * Adds an image into backgrounds
     * @param d the image to be added into backgrounds
     */
    static void addBackground(Drawable d) {
        backgrounds.add(d);
    }

    /***
     * Clear all images in backgrounds
     */
    static void clearBackground() {
        backgrounds.clear();
    }

    /***
     * Return how many tiles the board will have after generation.
     * @return the number of tiles the board will have
     */
    static int numTiles() {
        return numCols * numRows;
    }

    /***
     * Create the board based on preset values.
     * Also creates the tiles of the board in random but solvable order,
     * with their images properly setup.
     * Returns null if setNumRowsCols has not been called.
     * @return the generated board.
     */
    static Board createBoard() {
        if (backgrounds == null) {
            return null;
        }
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = getNumRows() * getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, backgrounds.get(tileNum)));
        }
        tiles.get(numTiles - 1).setBackground(emptyTileBackground);
        Collections.shuffle(tiles);
        while (!isSolvable(tiles)) {
            Collections.shuffle(tiles);
        }

        Iterator<Tile> iter = tiles.iterator();

        Tile[][] tiles2 = new Tile[numRows][numCols];

        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                tiles2[row][col] = iter.next();
            }
        }

        return new Board(numRows, numCols, tiles2);
    }

    /**
     * Checks whether or not generated board can be solved. This was adapted from the formula to
     * calculate for tile game solvability from: https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html.
     * @param tiles - list of all the tiles in the row-column order they appear on the board
     * @return whether or not the board can be solved
     */
    static private boolean isSolvable(List<Tile> tiles){
        int parity = 0;
        int width = numCols;
        int curRow = 0;
        int blankRow = 0;

        for (int i = 0; i < numCols * numRows; i++) {
            if (i % width == 0) {
                curRow++;
            }
            if (tiles.get(i).getId() == numRows * numCols) {
                blankRow = curRow;
                continue;
            }
            for (int j = i + 1; j < numCols * numRows; j++) {
                if (tiles.get(i).getId() > tiles.get(j).getId() && tiles.get(j).getId() != 0) {
                    parity++;
                }
            }

        }
        if (width % 2 == 0){
            if (blankRow % 2 ==0){
                return parity % 2 == 0;
            } else {
                return parity % 2 != 0;
            }
        } else {
            return parity % 2 ==0;
        }
    }
}
