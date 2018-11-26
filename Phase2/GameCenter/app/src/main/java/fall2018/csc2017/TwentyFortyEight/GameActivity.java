package fall2018.csc2017.TwentyFortyEight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
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
        tfeBoardManager = new TFEBoardManager(new TFEBoard(4, 4));
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
//        detector = new GestureDetector(this, new SlideDetector());
//        gListener = new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent e){
//                return detector.onTouchEvent(e);
//            }
//        };
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
}
//    @Override
//    public void onClick(View v){
//    }
//
//    class SlideDetector extends GestureDetector.SimpleOnGestureListener{
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            try {
//                float deltaY = e2.getY() - e1.getY();
//                float deltaX = e2.getX() - e1.getX();
//
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    if (Math.abs(deltaX) > SLIDE_THRESHOLD) {
//                        if (deltaX > 0) {
//                            // the user made a sliding right gesture
//                            tfeBoardManager.tileSlide(0);
//                        } else {
//                            // the user made a sliding left gesture
//                            tfeBoardManager.tileSlide(1);
//                        }
//                    }
//                } else {
//                    if (Math.abs(deltaY) > SLIDE_THRESHOLD) {
//                        if (deltaY > 0) {
//                            // the user made a sliding down gesture
//                            tfeBoardManager.tileSlide(2);
//                        } else {
//                            // the user made a sliding up gesture
//                            tfeBoardManager.tileSlide(3);
//                        }
//                    }
//                }
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            return false;
//        }
//
//        @Override
//        public boolean onDown(MotionEvent e){
//            return true;
//        }
//    }
////
////            @Override
////            public boolean onTouch(View v, MotionEvent e) {
////                detector.onTouchEvent(e);
////                return false;
////            }
////        });
////    }
////
////    @Override
////    public boolean onTouchEvent(MotionEvent e) {
////        detector.onTouchEvent(e);
////        return super.onTouchEvent(e);
////    }
//
////    class SlideListener extends GestureDetector.SimpleOnGestureListener {
////        private static final int SLIDE_THRESHOLD = 100;
////
////        @Override
////        public boolean onDown(MotionEvent e) {
////            return true;
////        }
////
////        @Override
////        public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
////            try {
////                float deltaY = e2.getY() - e1.getY();
////                float deltaX = e2.getX() - e1.getX();
////
////                if (Math.abs(deltaX) > Math.abs(deltaY)) {
////                    if (Math.abs(deltaX) > SLIDE_THRESHOLD) {
////                        if (deltaX > 0) {
////                            // the user made a sliding right gesture
////                            onSlideRight();
////                        } else {
////                            // the user made a sliding left gesture
////                            onSlideLeft();
////                        }
////                    }
////                } else {
////                    if (Math.abs(deltaY) > SLIDE_THRESHOLD) {
////                        if (deltaY > 0) {
////                            // the user made a sliding down gesture
////                            onSlideDown();
////                        } else {
////                            // the user made a sliding up gesture
////                            onSlideUp();
////                        }
////                    }
////                }
////            } catch (Exception exception) {
////                exception.printStackTrace();
////            }
////            return false;
////        }
////    }
////
////    public void onSlideRight() {
////        tfeBoardManager.tileSlide(0);
////    }
////
////    public void onSlideLeft() {
////        tfeBoardManager.tileSlide(1);
////    }
////
////    public void onSlideUp() {
////        tfeBoardManager.tileSlide(2);
////    }
////
////    public void onSlideDown() {
////        tfeBoardManager.tileSlide(3);
////    }
//
////        gridView.setOnTouchListener(new SlidingTouchListener(this){
////            public void onSlideRight(){
////                tfeBoardManager.tileSlide(context,0);
////            }
////
////            public void  onSlideLeft(){
////                tfeBoardManager.tileSlide(context, 1);
////            }
////
////            public void onSlideUp(){
////                tfeBoardManager.tileSlide(context, 2);
////            }
////
////            public void onSlideDown(){
////                tfeBoardManager.tileSlide(context, 3);
////            }});
////        }
//
//
//
//
//
//
//
//
//        //        gridView.setOnTouchListener(new SlidingTouchListener(this) {
////            @Override
////            public boolean onSlideLeft() {
////                tfeBoardManager.tileSlide(context,0);
////                return true;
////            }
////
////            @Override
////            public boolean onSlideRight() {
////                tfeBoardManager.tileSlide(context,1);
////                return true;
////            }
////
////            @Override
////            public boolean onSlideUp() {
////                tfeBoardManager.tileSlide(context,2);
////                return true;
////            }
////
////            @Override
////            public boolean onSlideDown() {
////                tfeBoardManager.tileSlide(context,3);
////                return true;
////            }
////        });
////    }
//
