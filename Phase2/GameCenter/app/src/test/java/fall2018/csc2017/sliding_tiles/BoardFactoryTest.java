package fall2018.csc2017.sliding_tiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

public class BoardFactoryTest {

    @Before
    public void setUp(){
        BoardFactory.setNumRowsCows(2, 2);
    }

    @Test
    public void clearBackground() throws NoSuchFieldException, IllegalAccessException {
        Bitmap b = Bitmap.createBitmap(10,10,Bitmap.Config.ARGB_8888);
        Drawable d = new BitmapDrawable(b);
        BoardFactory.addBackground(d);
        BoardFactory.clearBackground();
        Field field = BoardFactory.class.getDeclaredField("backgrounds");
        field.setAccessible(true);
        assertTrue(((List)field.get(null)).isEmpty());
    }

    @Test
    public void createBoard() {
        Bitmap b = Bitmap.createBitmap(10,10,Bitmap.Config.ARGB_8888);
        Drawable d = new BitmapDrawable(b);
        for (int i = 0; i<4;i++){
            BoardFactory.addBackground(d);
        }
        Board board = BoardFactory.createBoard();
        assertEquals(2, board.getNumCols());
    }
}