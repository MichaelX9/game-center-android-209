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
        if(slideDirection == 0){
            for(int r = 0; r < row; r++){
                List<TFETile> tileGroup = new ArrayList<>();
                for(int c = 0; c < col; c++){
                    if(boardTiles[r][c].getTileValue() != 0){
                        tileGroup.add(boardTiles[r][c]);
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
        if(slideDirection == 1){
            for(int r = 0; r < row; r++) {
                List<TFETile> tileGroup = new ArrayList<>();
                for (int c = col - 1; c >= 0; c--) {
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
        if(slideDirection == 3){
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

}

