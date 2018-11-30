package fall2018.csc2017.sliding_tiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BoardManagerTest {

    BoardManager boardManager;

    @Before
    public void setUp() {
        Bitmap b = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        Drawable d = new BitmapDrawable(b);
        Tile[][] tiles = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile(i * 4 + j + 1, d);
            }
        }
        Board board = new Board(4,4,tiles);
        boardManager = new BoardManager(board);

    }

    @Test
    public void puzzleSolved() {
        assertTrue(boardManager.puzzleSolved());
    }
}