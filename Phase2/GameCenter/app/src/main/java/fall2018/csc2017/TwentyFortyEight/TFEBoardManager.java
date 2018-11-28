package fall2018.csc2017.TwentyFortyEight;

import java.io.Serializable;

public class TFEBoardManager implements Serializable {

    private TFEBoard board;
    int undos = 3;
    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }


    int getUndos(){
        return undos;
    }
    void setUndos(int i){
        this.undos = i;
    }
    public TFEBoard getBoard(){return this.board;}


}

