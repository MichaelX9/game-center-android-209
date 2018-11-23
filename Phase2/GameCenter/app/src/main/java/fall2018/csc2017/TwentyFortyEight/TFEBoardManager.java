package fall2018.csc2017.TwentyFortyEight;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TFEBoardManager {

    private TFEBoard board;

    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }

    public TFEBoard getBoard(){return this.board;}

    void tileSlide(Context context, int slideDirection) {
        TFETile[][] boardTiles = board.getTiles();
        int row = board.getNumRow();
        int col = board.getNumCol();
        switch (slideDirection){
            case 0:
                leftSlider(row, col, boardTiles);
                break;
            case 1:
                rightSlider(row, col, boardTiles);
                break;
            case 2:
                upSlider(row, col, boardTiles);
                break;
            case 3:
                downSlider(row, col, boardTiles);
                break;
        }
        if(board.isSolved()){
            Toast.makeText(context,"YOU WIN!", Toast.LENGTH_LONG).show();
            MenuActivity.manager.save(context);
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

    private void leftSlider(int row, int col, TFETile[][] boardTiles){
        for(int r = 0; r < row; r++){
            List<TFETile> tileGroup = new ArrayList<>();
            for(int c = 0; c < col; c++){
                if(boardTiles[r][c].getTileValue() != 0){
                    tileGroup.add(boardTiles[r][c].copy());
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup =  merger(tileGroup);
            int i = tileGroup.size();
            for(int e = 1; e <= tileGroup.size(); e++ ){
                boardTiles[r][col - e] = (tileGroup.get(i-1));
                i--;
            }
        }
    }

    private void rightSlider(int row, int col, TFETile[][] boardTiles){
        for(int r = 0; r < row; r++) {
            List<TFETile> tileGroup = new ArrayList<>();
            for (int c = col - 1; c >= 0; c--) {
                if (boardTiles[r][c].getTileValue() != 0) {
                    tileGroup.add(boardTiles[r][c].copy());
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

    private void upSlider(int row, int col, TFETile[][] boardTiles){
        for(int c = 0; c < col; c++){
            List<TFETile> tileGroup = new ArrayList<>();
            for(int r = row - 1; r >= 0; r--){
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

    private void downSlider(int row, int col, TFETile[][] boardTiles){
        for(int c = 0; c < col; c++) {
            List<TFETile> tileGroup = new ArrayList<>();
            for (int r = 0; r < row; r++) {
                if (boardTiles[r][c].getTileValue() != 0) {
                    tileGroup.add(boardTiles[r][c]);
                    boardTiles[r][c].TFEvaluesetter(0);
                }
            }
            tileGroup = merger(tileGroup);
            int i = tileGroup.size();
            for (int e = 1; e <= tileGroup.size(); e++) {
                boardTiles[row - e][c] = (tileGroup.get(i - 1));
                i--;
            }
        }
    }

}

