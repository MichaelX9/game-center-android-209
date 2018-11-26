package fall2018.csc2017.TwentyFortyEight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.R ;

import static java.lang.Math.min;

public class GameActivity extends AppCompatActivity implements Observer, View.OnTouchListener {

    private GridView gridView;
    private TFEBoardManager tfeBoardManager;
//    private GestureDetector detector;
//    View.OnTouchListener gListener;
//    private static final int SLIDE_THRESHOLD = 100;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        findViewById(R.id.TFE_Grid).setOnTouchListener(this);
//        detector = new GestureDetectorCompat(this, new SlideListener());

        gridView = findViewById(R.id.TFE_Grid);

        MenuActivity.manager.load(GameActivity.this, "temp.txt");
        tfeBoardManager = (TFEBoardManager) MenuActivity.manager.getGameState();
        tfeBoardManager.getBoard().addObserver(this);
        gridView.setNumColumns(tfeBoardManager.getBoard().getNumCol());
        final Context context = this;
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        int columnWidth = displayWidth / tfeBoardManager.getBoard().getNumCol();
                        int columnHeight = displayHeight / tfeBoardManager.getBoard().getNumRow();

                        int columnParam = min(columnHeight, columnWidth);

                        gridView.setAdapter(new TFEGridAdapter(tfeBoardManager, columnParam,
                                columnParam, context));
                    }
                });
    }

    float prevX, prevY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();

                return true;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();


                //Calculates where we swiped

                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)) {
                    //LEFT - RiGHT Direction

                    if( newX > prevX) {
                        //RIGHT
                        tfeBoardManager.getBoard().tileSlide(0);
                    } else {
                        //LEFT
                        tfeBoardManager.getBoard().tileSlide(1);
                    }
                } else {
                    // UP-DOWN Direction
                    if (newY > prevY) {
                        //DOWN
                        tfeBoardManager.getBoard().tileSlide(3);
                    } else {
                        //UP
                        tfeBoardManager.getBoard().tileSlide(2);
                    }
                }

                break;
        }
        return false;
    }
    @Override
    public void update(Observable o, Object arg) {
        ((TFEGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }


    public void saveClicker(View view){
        MenuActivity.manager.setGameState(tfeBoardManager);
        MenuActivity.manager.save(this);
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }


}

