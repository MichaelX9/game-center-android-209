package fall2018.csc2017.TwentyFortyEight;

import java.io.Serializable;

public class TFEBoardManager implements Serializable {

    /**
     * Current 2048 board.
     */
    private TFEBoard board;

    /**
     * Number of total undoes allowed.
     */
    int undos = 3;

    /**
     * Constructor for a new board manager.
     * @param board - board to be managed.
     */
    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }

    /**
     * Getter for number of undoes.
     * @return number of undoes remaining.
     */
    int getUndos(){
        return undos;
    }

    /**
     * Setter for number of undoes
     * @param i - number of undoes allowed.
     */
    void setUndos(int i){
        this.undos = i;
    }

    /**
     * Getter for current board state.
     * @return current board being managed.
     */
    public TFEBoard getBoard(){return this.board;}


}

