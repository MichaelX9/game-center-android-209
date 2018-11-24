package fall2018.csc2017.TwentyFortyEight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
import fall2018.csc2017.slidingtiles.R ;

import static java.lang.Math.min;

public class GameActivity extends AppCompatActivity implements Observer {

    private GridView gridView;
    private TFEBoardManager tfeBoardManager;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);

        gridView = findViewById(R.id.TFE_Grid);
        tfeBoardManager = new TFEBoardManager(new TFEBoard(4,4));
        tfeBoardManager.getBoard().addObserver(this);
        gridView.setNumColumns(tfeBoardManager.getBoard().getNumCol());
        final Context context=this;
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
                                columnParam,context));
                    }
                });
        gridView.setOnTouchListener(new SlidingTouchListener(this) {
            @Override
            public boolean onSlideLeft() {
                tfeBoardManager.tileSlide(context,0);
                return true;
            }

            @Override
            public boolean onSlideRight() {
                tfeBoardManager.tileSlide(context,1);
                return true;
            }

            @Override
            public boolean onSlideUp() {
                tfeBoardManager.tileSlide(context,2);
                return true;
            }

            @Override
            public boolean onSlideDown() {
                tfeBoardManager.tileSlide(context,3);
                return true;
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        ((TFEGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }
}
