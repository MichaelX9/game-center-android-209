package fall2018.csc2017.TwentyFortyEight;

import fall2018.csc2017.GameManager.GameManager;

public class TFEManager extends GameManager {

    TFEManager(String name){
        super(name, "TFE");
        autosaveInterval = 2;
    }
}
