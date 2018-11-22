package fall2018.csc2017.TwentyFortyEight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import fall2018.csc2017.slidingtiles.R;

public class GameActivity extends AppCompatActivity {

    private GridView gridView;
    private TFEBoardManager tfeBoardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);

        gridView = findViewById(R.id.TFE_Grid);
        tfeBoardManager = new TFEBoardManager();

    }
}
