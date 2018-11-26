package fall2018.csc2017.slidingtiles;
import org.junit.Test;


import static org.junit.Assert.*;


public class BoardManagerTest {
    private Board slidingBoard = new Board(4, 4);
    private BoardManager slidingBoardManager = new BoardManager(slidingBoard);

    @Test
    public void setScoreBoard() {
    }

    /**
     * Tests if getBoard() returns Board Object
     */
    @Test
    public void getBoard() {
        assertEquals(slidingBoard, slidingBoardManager.getBoard());
    }

//    @Test
//    public void setEmptyTile() {
//        slidingBoardManager.setEmptyTile(getDrawable(R.drawable.tile_25))
//    }

    @Test
    public void decompress() {
    }

    @Test
    public void touchMove() {

    }

    @Test
    public void processTapMovement() {
    }

}