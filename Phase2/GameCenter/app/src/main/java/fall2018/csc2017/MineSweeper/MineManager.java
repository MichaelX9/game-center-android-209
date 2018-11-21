package fall2018.csc2017.MineSweeper;

import fall2018.csc2017.GameManager.GameManager;

public class MineManager extends GameManager {
    MineManager(String name) {
        super(name, "Minesweeper");
        autosaveInterval = 2;
    }

}
