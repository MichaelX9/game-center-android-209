package fall2018.csc2017.TwentyFortyEight;

import java.io.Serializable;

public class TFEBoardManager implements Serializable {

    /**
     * Current 2048 board.
     */
    private TFEBoard board;

    /**
     * Constructor for a new board manager.
     * @param board - board to be managed.
     */
    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }

    /**
     * Getter for current board state.
     * @return current board being managed.
     */
    public TFEBoard getBoard(){return this.board;}


}

