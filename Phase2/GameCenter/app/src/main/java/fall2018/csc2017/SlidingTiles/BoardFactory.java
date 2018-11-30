package fall2018.csc2017.SlidingTiles;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class BoardFactory {
    static private int numRows;
    static private int numCols;
    static private List<Drawable> backgrounds;
    static private Drawable emptyTileBackground;

    public static void setEmptyTileBackground(Drawable d){
        emptyTileBackground = d;
    }

    public static int getNumRows() {
        return numRows;
    }

    public static void setNumRowsCows(int numRows, int numCols) {
        BoardFactory.numRows = numRows;
        BoardFactory.numCols = numCols;
        backgrounds = new ArrayList<>(numCols*numRows);
    }

    public static int getNumCols() {
        return numCols;
    }

    static void addBackground(Drawable d){
        backgrounds.add(d);
    }

    static void clearBackground(){
        backgrounds.clear();
    }

    static int numTiles(){
        return numCols*numRows;
    }

    static Board createBoard(){
        if (backgrounds == null){
            return null;
        }
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = getNumRows() * getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, backgrounds.get(tileNum)));
        }
        tiles.get(numTiles - 1).setBackground(emptyTileBackground);
        Collections.shuffle(tiles);
        while (isSolvable(tiles)){
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

    static private boolean isSolvable(List<Tile> tiles){
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
}
