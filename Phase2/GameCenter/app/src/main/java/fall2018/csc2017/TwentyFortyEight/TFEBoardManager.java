pacckage fall2018.csc2017.TwentyFortyEight;


import java.io.Serializable;

import fall2018.csc2017.GameManager.GameManager;

public class TFEBoardManager extends GameManager implements Serializable {

    private TFEBoard board;

    TFEBoardManager(TFEBoard board, String username) {
        super(username, "Minesweeper");
        this.board = board;
        autosaveInterval = 3;
    }

    public TFEBoard getBoard(){return this.board;}

}

