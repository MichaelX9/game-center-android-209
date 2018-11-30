package fall2018.csc2017.SlidingTiles;

import android.graphics.drawable.ColorDrawable;

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
        BoardFactory.addBackground(new ColorDrawable());
        BoardFactory.clearBackground();
        Field field = BoardFactory.class.getField("backgrounds");
        field.setAccessible(true);
        assertTrue(((List)field.get(null)).isEmpty());
    }

    @Test
    public void createBoard() {
        Board board = BoardFactory.createBoard();
        assertEquals(2, board.getNumCols());
    }
}