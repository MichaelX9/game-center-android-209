package fall2018.csc2017.MineSweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.R;


public class GameActivity extends AppCompatActivity implements Observer {

    private GridView gridView;
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweeper);

        boardManager = new BoardManager(new Board(10,10,0.25));
        boardManager.getBoard().addObserver(this);

        gridView = findViewById(R.id.MSgridview);
        int displayWidth = gridView.getMeasuredWidth();
        int displayHeight = gridView.getMeasuredHeight();
        int columnWidth = displayWidth / boardManager.getBoard().getNumCols();
        int columnHeight = displayHeight / boardManager.getBoard().getNumRows();
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setAdapter(new BoardGridAdapter(boardManager, columnWidth,columnHeight,this));
        display();


    }



    private void display(){
        ((BoardGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    @Override
    protected void onResume() {
        super.onResume();
        display();
    }
}
