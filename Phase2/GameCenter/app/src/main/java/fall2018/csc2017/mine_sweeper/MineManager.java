package fall2018.csc2017.mine_sweeper;

import fall2018.csc2017.game_manager.GameManager;

public class MineManager extends GameManager {
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

