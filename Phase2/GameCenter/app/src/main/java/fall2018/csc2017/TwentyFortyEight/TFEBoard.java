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
    TFEBoard(int col, int row) {
        numCol = col;
        numRow = row;
        boardTiles = new TFETile[row][col];
        generateBoard();
    }

    /**
     * Generator for the board.
     */
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
     * getter for the number of total tiles on the board.
     *
     * @return the number of total tiles
     */
    int numTiles() {
        return numCol * numRow;
    }

    /**
     * getter for the number of columns on the board
     *
     * @return numCol attribute
     */
    int getNumCol() {
        return numCol;
    }

    /**
     * getter for the number of rows on the board
     *
     * @return numRow attribute
     */
    int getNumRow() {
        return numRow;
    }

    /**
     * Tile getter from the board.
     */
    private TFETile tileGetter(int row, int col) {
        return boardTiles[row][col];
    }

    /**
     * Getter for the TFETile object at the indicated position
     *
     * @param position the given position
     * @return the tile at that position
     */
    TFETile tileGetter(int position) {
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
     * Checker for whether the game has been solved.
     *
     * @return whether the game has been solved
     */
    boolean isSolved() {
        for (int c = 0; c < numCol; c++) {
            for (int r = 0; r < numRow; r++) {
                if (boardTiles[r][c].getTileValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checker for whether or not there are no valid moves possible.
     * @return whether valid moves remain
     */
    boolean isOver(){
        for (int c = 0; c < numCol; c++) {
            for (int r = 0; r < numRow; r++) {
                try{
                    if (boardTiles[r][c].getTileValue() == 0) {
                        return false;
                    }
                    else if(boardTiles[r][c].getTileValue() == boardTiles[r][c+1].getTileValue() || boardTiles[r][c].getTileValue() == boardTiles[r+1][c].getTileValue()){
                        return false;
                    }
                }
                catch(IndexOutOfBoundsException e){
                }
            }
        }
        return true;
    }

    /**
     * Iterator class for TFETiles.
     */
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

    /**
     * Switcher function to call correct directional slider function depending on direction of swipe.
     * @param slideDirection - integer representing one of four possible swipe directions.
     */
    void tileSlide(int slideDirection) {
        TFETile[][] toCheck = arrCopy(boardTiles);
        switch (slideDirection){
            case 0:
                rightSlider(boardTiles);
                break;
            case 1:
                leftSlider(boardTiles);
                break;
            case 2:
                upSlider(boardTiles);
                break;
            case 3:
                downSlider(boardTiles);
                break;
        }
        if(!sameArray(boardTiles, toCheck)){
            newBlock(boardTiles);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Generator for new numbered block after a move is made.
     * @param boardTiles - current array representing the tiles on the board.
     */
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

    /**
     * Helper function to compare two TFETile 2D arrays.
     * @param arr1 - First array to compare.
     * @param arr2 - Second array to compare.
     * @return whether or not the two arrays contain the same value TFETiles at the same locations.
     */
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

    /**
     * Helper function to create a copy of a 2D TFETile array
     * @param tiles - 2D array to copy.
     * @return a new 2D TFETile array containing TFETiles with values equal to the TFETile at the
     * corresponding location in the input list.
     */
    private TFETile[][] arrCopy(TFETile[][] tiles){
        TFETile[][] newCopy = new TFETile[numRow][numCol];
        for(int r = 0; r<numRow; r++){
            for(int c = 0; c<numCol; c++){
                newCopy[r][c] = new TFETile((tiles[r][c].getTileValue()));
            }
        }
        return newCopy;
    }

    /**
     * Helper function to help merge any TFETiles which should be merged after a slide input.
     * @param list - a list of TFETiles from one row or one column.
     * @return - List of TFETiles after all possible merges are made.
     */
    private List<TFETile> merger(List<TFETile> list){
        for(int i = 0; i < list.size() - 1; i++){
            if(list.get(i).getTileValue() == 0){
                list.remove(i);
            }
            else if(list.get(i).getTileValue() == list.get(i+1).getTileValue()){
                int value = list.get(i).getTileValue();
                list.set(i, new TFETile(value * 2));


                MenuActivity.manager.getGameState().getScoreBoard().updateScoreOnMerge(value*2);

                list.remove(i+1);
            }
        }
        return list;
    }

    /**
     * Slider for right slide motion input.
     * @param boardTiles - current board state.
     */
    private void rightSlider(TFETile[][] boardTiles){
        for(int r = 0; r < numRow; r++){
            List<TFETile> tileGroup = new ArrayList<>();
            for(int c = numCol-1; c >= 0; c--){
                if(boardTiles[r][c].getTileValue() != 0){
                    tileGroup.add(boardTiles[r][c].copy());
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup =  merger(tileGroup);
            for(int e = 1; e <= tileGroup.size(); e++ ){
                boardTiles[r][numCol-e] = (tileGroup.get(e-1));
            }
        }
    }

    /**
     * Slider for left slide motion input.
     * @param boardTiles - current board state.
     */
    private void leftSlider(TFETile[][] boardTiles){
        for(int r = 0; r < numRow; r++) {
            List<TFETile> tileGroup = new ArrayList<>();
            for (int c = 0; c < numCol; c++) {
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

    /**
     * Slider for up slide motion input.
     * @param boardTiles - current board state.
     */
    private void upSlider(TFETile[][] boardTiles){
        for(int c = 0; c < numCol; c++){
            List<TFETile> tileGroup = new ArrayList<>();
            for(int r = 0; r < numRow; r++){
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

    /**
     * Slider for down slide motion input.
     * @param boardTiles - current board state.
     */
    private void downSlider(TFETile[][] boardTiles){
        for(int c = 0; c < numCol; c++) {
            List<TFETile> tileGroup = new ArrayList<>();
            for (int r = numRow - 1; r >= 0; r--) {
                if (boardTiles[r][c].getTileValue() != 0) {
                    tileGroup.add(boardTiles[r][c].copy());
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup = merger(tileGroup);
            for (int e = 1; e <= tileGroup.size(); e++) {
                boardTiles[numRow - e][c] = (tileGroup.get(e-1));
            }
        }
    }
}
