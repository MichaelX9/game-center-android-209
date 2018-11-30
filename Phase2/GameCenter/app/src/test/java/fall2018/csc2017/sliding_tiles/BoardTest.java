package fall2018.csc2017.sliding_tiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        Bitmap b = Bitmap.createBitmap(10,10,Bitmap.Config.ARGB_8888);
        Drawable d = new BitmapDrawable(b);
        Tile[][] tiles = new Tile[2][2];
        tiles[0][0] = new Tile(1, d);
        tiles[0][1] = new Tile(2, d);
        tiles[1][0] = new Tile(3, d);
        tiles[1][1] = new Tile(4, d);
        board = new Board(2,2, tiles);
    }

    @Test
    public void swapTiles() {
        board.swapTiles(0,0,0,1);
        assertEquals(board.getTile(0,0).getId(), 2);
    }
}