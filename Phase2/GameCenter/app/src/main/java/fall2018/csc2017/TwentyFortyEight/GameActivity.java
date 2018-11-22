package fall2018.csc2017.TwentyFortyEight;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
import fall2018.csc2017.TwentyFortyEight.TFEBoardManager;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.TwentyFortyEight.SlidingTouchListener;
import fall2018.csc2017.slidingtiles.StartingActivity;

public class GameActivity extends AppCompatActivity implements Observer {

    private GridView gridView;
    private TFEBoardManager boardManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);

        boardManager = new TFEBoardManager(new TFEBoard(4,4),
                GameLaunchActivity.username);
        boardManager.getBoard().addObserver(this);

        View myview = findViewById(R.id.TFEgrid);
        myview.setOnTouchListener(new SlidingTouchListener(this) {
            @Override
            public boolean onSlideLeft() {
                boardManager.getBoard().tileSlide(0);
                return true;
            }

            @Override
            public boolean onSlideRight() {
                boardManager.getBoard().tileSlide(0);
                return true;
            }

            @Override
            public boolean onSlideUp() {
                boardManager.getBoard().tileSlide(0);
                return true;
            }

            @Override
            public boolean onSlideDown() {
                boardManager.getBoard().tileSlide(0);
                return true;
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

