package fall2018.csc2017.TwentyFortyEight;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TFEBoardManager implements Serializable {

    private TFEBoard board;

    TFEBoardManager(TFEBoard board) {
        this.board = board;
    }

    public TFEBoard getBoard(){return this.board;}


}

