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
        boardTiles = new TFETile[col][row];
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
        return boardTiles[col][row];
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

//    void sucker(TFETile[][] board, int direction, int row, int col) {
//        TFETile sucking = boardTiles[row][col];
//        if (direction == 0) {
//            int ogCol = col;
//            while (col < numCol - 1 && boardTiles[row][col + 1].getTileValue() == 0) {
//                col++;
//            }
//            if (boardTiles[row][col + 1].getTileValue() == sucking.getTileValue()) {
//                board[row][ogCol].TFEvaluesetter(sucking.getTileValue() * 2);
//                board[row][col + 1].TFEvaluesetter(0);
//            }
//
//        }
//        if (direction == 1) {
//            int ogCol = col;
//            while (col > 0 && boardTiles[row][col - 1].getTileValue() == 0) {
//                col--;
//            }
//            if (boardTiles[row][col - 1].getTileValue() == sucking.getTileValue()) {
//                board[row][ogCol].TFEvaluesetter(sucking.getTileValue() * 2);
//                board[row][col - 1].TFEvaluesetter(0);
//            }
//        }
//        if (direction == 2) {
//            int ogRow = row;
//            while (row > 0 && boardTiles[row - 1][col].getTileValue() == 0) {
//                row--;
//            }
//            if (boardTiles[row - 1][col].getTileValue() == sucking.getTileValue()) {
//                board[ogRow][col].TFEvaluesetter(sucking.getTileValue() * 2);
//                board[row - 1][col].TFEvaluesetter(0);
//            }
//        }
//        if (direction == 3) {
//            int ogRow = row;
//            while (row < numRow - 1 && boardTiles[row + 1][col].getTileValue() == 0) {
//                row++;
//            }
//            if (boardTiles[row + 1][col].getTileValue() == sucking.getTileValue()) {
//                board[ogRow][col].TFEvaluesetter(sucking.getTileValue() * 2);
//                board[row + 1][col].TFEvaluesetter(0);
//            }
//        }
//    }
//
//    void slider(TFETile[][]board, int direction, int row, int col){
//        TFETile sliding = boardTiles[row][col];
//        if(direction == 0){
//            int ogCol = col;
//            while(col < numCol - 1 && boardTiles[row][col+1].getTileValue() == 0){
//                col++;
//            }
//            board[row][col].TFEvaluesetter(boardTiles[row][ogCol].getTileValue());
//            board[row][ogCol].TFEvaluesetter(0);
//        }
//        if(direction == 1){
//            int ogCol = col;
//            while(col > 0 && boardTiles[row][col-1].getTileValue()==0){
//                col--;
//            }
//            board[row][col].TFEvaluesetter(boardTiles[row][ogCol].getTileValue());
//            board[row][ogCol].TFEvaluesetter(0);
//        }
//        if(direction == 2){
//            int ogRow = row;
//            while (row > 0 && boardTiles[row - 1][col].getTileValue() == 0) {
//                row--;
//            }
//            board[row][col].TFEvaluesetter(boardTiles[ogRow][col].getTileValue());
//            board[ogRow][col].TFEvaluesetter(0);
//        }
//        if(direction == 3){
//            int ogRow = row;
//            while (row < numRow - 1 && boardTiles[row + 1][col].getTileValue() == 0) {
//                row++;
//            }
//            board[row][col].TFEvaluesetter(boardTiles[ogRow][col].getTileValue());
//            board[ogRow][col].TFEvaluesetter(0);
//        }
//    }
//
    void tileSlide(int slideDirection) {
        //TFETile[][] newBoard = boardTiles.clone();
        if(slideDirection == 0){
            for(int r = 0; r < numRow; r++){
                List<TFETile> tileGroup = new ArrayList<>();
                for(int c = 0; c < numCol; c++){
                    if(boardTiles[r][c].getTileValue() != 0){
                        tileGroup.add(boardTiles[r][c]);
                        boardTiles[r][c].TFEvaluesetter(0);
                    }
                }
                tileGroup =  merger(tileGroup);
                int i = tileGroup.size();
                for(int e = 1; e <= tileGroup.size(); e++ ){
                    boardTiles[r][numCol - e] = (tileGroup.get(i-1));
                    i--;
                }

            }
        }
        if(slideDirection == 1){
            for(int r = 0; r < numRow; r++) {
                List<TFETile> tileGroup = new ArrayList<>();
                for (int c = numCol - 1; c >= 0; c--) {
                    if (boardTiles[r][c].getTileValue() != 0) {
                        tileGroup.add(boardTiles[r][c]);
                        boardTiles[r][c].TFEvaluesetter(0);
                    }
                }
                tileGroup = merger(tileGroup);
                int i = tileGroup.size();
                for (int e = 0; e < tileGroup.size(); e++) {
                    boardTiles[r][e] = tileGroup.get(i - 1);
                    i--;
                }
            }
        }
        if(slideDirection == 2){
            for(int c = 0; c < numCol; c++){
                List<TFETile> tileGroup = new ArrayList<>();
                for(int r = numRow - 1; r >= 0; r--){
                    if(boardTiles[r][c].getTileValue() != 0){
                        tileGroup.add(boardTiles[r][c]);
                        boardTiles[r][c].TFEvaluesetter(0);
                    }
                }
                tileGroup = merger(tileGroup);
                int i = tileGroup.size();
                for(int e = 0; e < tileGroup.size(); e++){
                    boardTiles[e][c] = tileGroup.get(i-1);
                    i--;
                }
            }
        }
        if(slideDirection == 3){
            for(int c = 0; c < numCol; c++) {
                List<TFETile> tileGroup = new ArrayList<>();
                for (int r = 0; r < numRow; r++) {
                    if (boardTiles[r][c].getTileValue() != 0) {
                        tileGroup.add(boardTiles[r][c]);
                        boardTiles[r][c].TFEvaluesetter(0);
                    }
                }
                tileGroup = merger(tileGroup);
                int i = tileGroup.size();
                for (int e = 1; e <= tileGroup.size(); e++) {
                    boardTiles[numRow - e][c] = (tileGroup.get(i - 1));
                    i--;
                }
            }
        }
    }

    private List<TFETile> merger(List<TFETile> list){
        for(int i = 0; i < list.size() - 1; i++){
            if(list.get(i) == list.get(i+1)){
                int value = list.get(i).getTileValue();
                list.set(i, new TFETile(value));
                list.remove(i+1);
            }
        }
        return list;
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
