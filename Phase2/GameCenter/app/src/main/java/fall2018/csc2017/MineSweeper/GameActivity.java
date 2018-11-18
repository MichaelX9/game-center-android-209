package fall2018.csc2017.MineSweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.R;


public class GameActivity extends AppCompatActivity {

    private GridView gridView;
    private int columnWidth,columnHeight;
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweeper);

        gridView = findViewById(R.id.MSgridview);
        int displayWidth = gridView.getMeasuredWidth();
        int displayHeight = gridView.getMeasuredHeight();
        columnWidth = displayWidth / boardManager.getBoard().getNumCols();
        columnHeight = displayHeight / boardManager.getBoard().getNumRows();
        gridView.setAdapter(new BoardGridAdapter(boardManager, columnWidth,columnHeight,this));

    }

    private ArrayList<Button> generateButtons(){
        //TODO: generate buttons of blocks from the board
        return null;
    }

    private void display(){

    }
}
