package fall2018.csc2017.MineSweeper;

import android.content.Intent;
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
    private int columnWidth,columnHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweeper);

        boardManager = new BoardManager(new Board(10,10,0.15));
        boardManager.getBoard().addObserver(this);


        gridView = findViewById(R.id.MSgridview);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setAdapter(new BoardGridAdapter(boardManager,this));
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getNumCols();
                        columnHeight = displayHeight / boardManager.getBoard().getNumRows();

                        ((BoardGridAdapter)gridView.getAdapter()).setColumnWidth(columnWidth);
                        ((BoardGridAdapter)gridView.getAdapter()).setColumnHeight(columnHeight);
                        ((BoardGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
                    }
                });

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

    @Override
    public void onBackPressed() {

        for (int i = 0; i < boardManager.getBoard().getNumCols(); i++) {
            for (int j = 0; j < boardManager.getBoard().getNumRows(); j++) {
                boardManager.getBoard().getBlock(i*boardManager.getBoard().getNumCols()+j).setNumMines(0);
            }
        }

        Intent i = new Intent(this, MenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

}
