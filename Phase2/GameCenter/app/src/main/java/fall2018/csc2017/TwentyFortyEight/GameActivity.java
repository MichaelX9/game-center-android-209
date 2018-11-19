package fall2018.csc2017.TwentyFortyEight;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.TwentyFortyEight.SlidingTouchListener;

public class GameActivity extends AppCompatActivity implements Observer {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);

        View myview = findViewById(R.id.TFEgrid);
        myview.setOnTouchListener(new SlidingTouchListener().OnSlidingTouchListener(this)){
            @Override
            public boolean onSlideLeft(){
                return true;
            }

            @Override
            public boolean onSlideRight(){
                return true;
            }

            @Override
            public boolean onSlideUp(){
                return true;
            }

            @Override
            public boolean onSlideDown(){
                return true;
            }
        }
    }
}
