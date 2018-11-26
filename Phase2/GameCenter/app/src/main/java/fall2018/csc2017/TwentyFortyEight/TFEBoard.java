package fall2018.csc2017.TwentyFortyEight;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Random;


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

    private void generateBoard() {
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

    int getNumCol(){return numCol;}

    int getNumRow(){return numRow;}

    /**
     * Tile getter from the board.
     */

    private TFETile tileGetter(int row, int col){
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
    void tileSlide(int slideDirection) {
        int row = numRow;
        int col = numCol;
        TFETile[][] toCheck = arrCopy(boardTiles);
        switch (slideDirection){
            case 0:
                rightSlider(row, col, boardTiles);
                break;
            case 1:
                leftSlider(row, col, boardTiles);
                break;
            case 2:
                upSlider(row, col, boardTiles);
                break;
            case 3:
                downSlider(row, col, boardTiles);
                break;
        }
        if(!sameArray(boardTiles, toCheck)){
            newBlock(boardTiles);
        }
        setChanged();
        notifyObservers();
    }

    private void newBlock(TFETile[][] boardTiles){
        int row = numRow;
        int col = numCol;
        List<TFETile> blankTiles = new ArrayList<>();
        for(int c = 0; c<col; c++){
            for(int r = 0; r<row; r++){
                if(boardTiles[r][c].getTileValue() == 0){
                    blankTiles.add(boardTiles[r][c]);
                }
            }
        }
        Random random = new Random();
        int tileInd = random.nextInt(blankTiles.size());
        blankTiles.get(tileInd).TFEvaluesetter(Math.random() < 0.9 ? 2 : 4);
    }

    private boolean sameArray(TFETile[][] arr1, TFETile[][] arr2){
        for(int r =0; r<numRow; r++){
            for(int c = 0; c<numCol; c++){
                if(arr1[r][c].getTileValue() != arr2[r][c].getTileValue()){
                    return false;
                }
            }
        }
        return true;
    }

    private TFETile[][] arrCopy(TFETile[][] tiles){
        TFETile[][] newCopy = new TFETile[numRow][numCol];
        for(int r = 0; r<numRow; r++){
            for(int c = 0; c<numCol; c++){
                newCopy[r][c] = new TFETile((tiles[r][c].getTileValue()));
            }
        }
        return newCopy;
    }
    private List<TFETile> merger(List<TFETile> list){
        for(int i = 0; i < list.size() - 1; i++){
            if(list.get(i).getTileValue() == 0){
                list.remove(i);
            }
            else if(list.get(i).getTileValue() == list.get(i+1).getTileValue()){
                int value = list.get(i).getTileValue();
                list.set(i, new TFETile(value*2));
                list.remove(i+1);
            }
        }
        return list;
    }

    private void rightSlider(int row, int col, TFETile[][] boardTiles){
        for(int r = 0; r < row; r++){
            List<TFETile> tileGroup = new ArrayList<>();
            for(int c = col-1; c >= 0; c--){
                if(boardTiles[r][c].getTileValue() != 0){
                    tileGroup.add(boardTiles[r][c].copy());
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup =  merger(tileGroup);
            for(int e = 1; e <= tileGroup.size(); e++ ){
                boardTiles[r][col-e] = (tileGroup.get(e-1));
            }
        }
    }

    private void leftSlider(int row, int col, TFETile[][] boardTiles){
        for(int r = 0; r < row; r++) {
            List<TFETile> tileGroup = new ArrayList<>();
            for (int c = 0; c < col; c++) {
                if (boardTiles[r][c].getTileValue() != 0) {
                    tileGroup.add(boardTiles[r][c].copy());
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup = merger(tileGroup);
            for (int e = 0; e < tileGroup.size(); e++) {
                boardTiles[r][e] = tileGroup.get(e);
            }
        }
    }

    private void upSlider(int row, int col, TFETile[][] boardTiles){
        for(int c = 0; c < col; c++){
            List<TFETile> tileGroup = new ArrayList<>();
            for(int r = 0; r < row; r++){
                if(boardTiles[r][c].getTileValue() != 0){
                    tileGroup.add(boardTiles[r][c].copy());
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup = merger(tileGroup);
            for(int e = 0; e < tileGroup.size(); e++){
                boardTiles[e][c] = tileGroup.get(e);
            }
        }
    }

    private void downSlider(int row, int col, TFETile[][] boardTiles){
        for(int c = 0; c < col; c++) {
            List<TFETile> tileGroup = new ArrayList<>();
            for (int r = row - 1; r >= 0; r--) {
                if (boardTiles[r][c].getTileValue() != 0) {
                    tileGroup.add(boardTiles[r][c].copy());
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup = merger(tileGroup);
            for (int e = 1; e <= tileGroup.size(); e++) {
                boardTiles[row - e][c] = (tileGroup.get(e-1));
            }
        }
    }
}
