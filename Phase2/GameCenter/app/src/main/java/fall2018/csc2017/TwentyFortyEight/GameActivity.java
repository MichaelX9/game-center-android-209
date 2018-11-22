package fall2018.csc2017.TwentyFortyEight;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.R;

public class GameActivity extends AppCompatActivity implements Observer {

    private GridView gridView;
    private TFEBoardManager tfeBoardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);

        gridView = findViewById(R.id.TFE_Grid);
        tfeBoardManager = new TFEBoardManager();
        tfeBoardManager.getBoard().addObserver(this);
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

                        gridView.setAdapter(new TFEGridAdapter(tfeBoardManager, columnWidth,
                                columnHeight,context));
                    }
                });
        View myview = findViewById(R.id.TFEgrid);
        myview.setOnTouchListener(new SlidingTouchListener(this) {
            @Override
            public boolean onSlideLeft() {
                tfeBoardManager.getBoard().tileSlide(0);
                return true;
            }

            @Override
            public boolean onSlideRight() {
                tfeBoardManager.getBoard().tileSlide(0);
                return true;
            }

            @Override
            public boolean onSlideUp() {
                tfeBoardManager.getBoard().tileSlide(0);
                return true;
            }

            @Override
            public boolean onSlideDown() {
                tfeBoardManager.getBoard().tileSlide(0);
                return true;
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        ((TFEGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }
}
