package fall2018.csc2017.TwentyFourtyEight;

import android.app.Activity;
import android.os.Bundle;
import android.view.View
public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);

        View myview = (View) findViewById(R.id.myview);
        myview.setOnTouchListener(new OnSlidingTouchListener(this)){
            @Override
            public boolean onSlideLeft(){
                return true;
            }

            @Override
            public boolean onSlideRight() {
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
