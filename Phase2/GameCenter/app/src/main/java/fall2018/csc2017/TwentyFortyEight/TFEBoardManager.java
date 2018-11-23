package fall2018.csc2017.TwentyFortyEight;


import java.io.Serializable;

import fall2018.csc2017.GameManager.GameManager;

public class TFEBoardManager {

    private TFEBoard board;

    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }

    public TFEBoard getBoard(){return this.board;}

}

