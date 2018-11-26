package fall2018.csc2017.TwentyFortyEight;

import java.io.Serializable;

class TFETile implements Serializable {

    private int tileValue;

    /**
     * Constructor for TFETile.
     */
    TFETile(int value){
        tileValue = value;
    }

    void TFEvaluesetter(int newValue){
        tileValue = newValue;
    }

    int getTileValue(){return tileValue;}

    TFETile copy(){
        return new TFETile(getTileValue());
    }
}

