package fall2018.csc2017.TwentyFortyEight;

import java.io.Serializable;

public class TFEBoardManager implements Serializable {

    private TFEBoard board;

    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }

    public TFEBoard getBoard(){return this.board;}


}

