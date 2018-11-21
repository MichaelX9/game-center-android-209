package fall2018.csc2017.MineSweeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameManager.GameManager;
import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
import fall2018.csc2017.slidingtiles.R;


public class GameActivity extends AppCompatActivity implements Observer {

    private GridView gridView;
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper_main_activity);

        findViewById(R.id.undoButton).setVisibility(View.INVISIBLE);
        MenuActivity.manager.load(GameActivity.this, "temp.txt");
        Board loadedBoard = ((BoardManager)MenuActivity.manager.getGameState()).getBoard();
        boardManager = new BoardManager(new Board(loadedBoard.getNumCols(),loadedBoard.getNumRows(),loadedBoard.getPercentMines()));

        for (int a = 0; a < loadedBoard.getNumBlocks(); a++){
            Block block = boardManager.getBoard().getBlock(a);
            if (loadedBoard.getBlock(a).isVisible()){
                block.setVisible();
            }

            block.setMine(loadedBoard.getBlock(a).isMineType());
            block.setNumMines(loadedBoard.getBlock(a).getNumMines());

            if (loadedBoard.getBlock(a).isFlagged()){
                block.toggleFlagged();
            }

        }

        boardManager.getBoard().addObserver(this);

        final Context context = this;

        gridView = findViewById(R.id.MSgridview);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        int columnWidth = displayWidth / boardManager.getBoard().getNumCols();
                        int columnHeight = displayHeight / boardManager.getBoard().getNumRows();

                        gridView.setAdapter(new BoardGridAdapter(boardManager, columnWidth,
                                columnHeight,context));
                    }
                });

    }

    public void saveClicker(View view){
        MenuActivity.manager.setGameState(boardManager);
        MenuActivity.manager.save(this);
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();

    }


    private void display(){
        ((BoardGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }



    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, MenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

}
