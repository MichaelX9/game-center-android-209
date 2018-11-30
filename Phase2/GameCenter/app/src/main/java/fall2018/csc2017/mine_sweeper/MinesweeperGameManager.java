package fall2018.csc2017.mine_sweeper;

import fall2018.csc2017.game_manager.GameManager;

/***
 * GameManager class for minesweeper
 */
public class MinesweeperGameManager extends GameManager {

    /**
     * A new MinesweeperGameManager.
     */
    MinesweeperGameManager(String name) {
        super(name, "Minesweeper");
        autosaveInterval = 2;
    }

    /**
     * Getter for the stored GameState in MinesweeperGameManager.
     *
     * @return BoardManager
     */
    @Override
    public BoardManager getGameState() {
        return (BoardManager) gameState;
    }
}

