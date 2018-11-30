package fall2018.csc2017.twenty_forty_eight;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TFEGameManagerTest {

    private int undos;
    private TFEGameManager manager = new TFEGameManager("name");
    @Before
    public void setUp(){
        undos = 3;
        manager.setUndos(3);
    }

    @Test
    public void getUndos() {
        assertEquals(undos, manager.getUndos());
    }
}