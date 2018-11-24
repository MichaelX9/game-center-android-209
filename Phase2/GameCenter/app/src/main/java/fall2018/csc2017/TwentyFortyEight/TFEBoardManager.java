
package fall2018.csc2017.TwentyFortyEight;

public class TFEBoardManager {

    TFEBoard board;

    public TFEBoardManager(){
        board = new TFEBoard(4,4);
    }

    public TFEBoard getBoard(){
        return board;
    }
}
