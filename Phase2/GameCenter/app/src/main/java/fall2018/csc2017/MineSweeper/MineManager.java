package fall2018.csc2017.MineSweeper;

import fall2018.csc2017.GameManager.GameManager;

public class MineManager extends GameManager {

    /**
     * A new MineManager.
     */
    MineManager(String name) {
        super(name, "Minesweeper");
        autosaveInterval = 2;
    }

    /**
     * Getter for the stored GameState in MineManager.
     *
     * @return BoardManager
     */
    @Override
    public BoardManager getGameState() {
        return (BoardManager) gameState;
    }
}

