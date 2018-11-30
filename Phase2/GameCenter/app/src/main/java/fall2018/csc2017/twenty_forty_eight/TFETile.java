package fall2018.csc2017.twenty_forty_eight;

import java.io.Serializable;

class TFETile implements Serializable {

    /**
     * Value of the tile.
     */
    private int tileValue;

    /**
     * Constructor for TFETile.
     */
    TFETile(int value) {
        tileValue = value;
    }

    /**
     * Setter for value of a tile.
     *
     * @param newValue - new value of the tile.
     */
    void TFEvaluesetter(int newValue) {
        tileValue = newValue;
    }

    /**
     * Getter for value of a tile
     *
     * @return value of the tile.
     */
    int getTileValue() {
        return tileValue;
    }

    /**
     * Copy function to generate a new tile with same value as current tile.
     *
     * @return new tile with same value as current tile.
     */
    TFETile copy() {
        return new TFETile(getTileValue());
    }

}

