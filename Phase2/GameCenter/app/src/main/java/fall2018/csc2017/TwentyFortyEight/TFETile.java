package fall2018.csc2017.TwentyFortyEight;

public class TFETile {

    private int tileID;
    private int tileValue;

    /**
     * Constructor for TFETile.
     */
    TFETile(int ID){
        tileID = ID;
        tileValue = 0;
    }

    public void TFEvaluesetter(int newValue){
        tileValue = newValue;
    }

    public int getTileValue(){return tileValue;}
}

