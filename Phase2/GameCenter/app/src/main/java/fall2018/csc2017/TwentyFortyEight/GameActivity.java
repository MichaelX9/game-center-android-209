package fall2018.csc2017.TwentyFortyEight;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.R;

import static java.lang.Math.min;

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
    }

    @Override
    public void update(Observable o, Object arg) {
        ((TFEGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }
}
