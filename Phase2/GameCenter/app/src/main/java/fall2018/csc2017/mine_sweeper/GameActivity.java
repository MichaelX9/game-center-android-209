package fall2018.csc2017.mine_sweeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import fall2018.csc2017.R;
import fall2018.csc2017.launch_centre.GameLaunchActivity;

/***
 * The activity class for minesweeper game
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The grid view which displays the game board.
     */
    private GridView gridView;

    /**
     * The BoardManager instance for this game.
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweeper);
        MenuActivity.manager.load(GameActivity.this, "temp.txt");
        Board loadedBoard = MenuActivity.manager.getGameState().getBoard();

        boardManager = new BoardManager(new Board(loadedBoard.getNumCols(), loadedBoard.getNumRows(),
                loadedBoard.getPercentMines()));
        boardManager.setScoreBoard(this, new MineSweeperScoreBoard());
        boardManager.scoreBoard.setCurrentUser(GameLaunchActivity.username);
        boardManager.scoreBoard.startTiming();

        for (int a = 0; a < loadedBoard.getNumBlocks(); a++) {
            Block block = boardManager.getBoard().getBlock(a);
            if (loadedBoard.getBlock(a).isVisible()) {
                block.setVisible();
            }

            block.setMine(loadedBoard.getBlock(a).isMineType());
            block.setNumMines(loadedBoard.getBlock(a).getNumMines());

            if (loadedBoard.getBlock(a).isFlagged()) {
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
                                columnHeight, context));
                    }
                });
    }

    /**
     * Add the save button functionality to the game interface.
     *
     * @param view the view
     */
    public void saveClicker(View view) {
        MenuActivity.manager.setGameState(boardManager);
        MenuActivity.manager.save(this);
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Add the undo button functionality to the game interface.
     */
    public void undoClicker(View view) {
        Random rand = new Random();
        int n = rand.nextInt(5) + 1;
        if (n == 1) {
            Toast.makeText(this, "Sorry, I can't save you", Toast.LENGTH_SHORT).show();
        } else if (n == 2) {
            Toast.makeText(this, "Try again :)", Toast.LENGTH_SHORT).show();
        } else if (n == 3) {
            Toast.makeText(this, "Ha Ha, you can't Undo", Toast.LENGTH_SHORT).show();
        } else if (n == 4) {
            Toast.makeText(this, "Nice Try...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Undo NA ;-;", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Set background image of each grid space and call adaptor to change view.
     */
    private void display() {
        ((BoardGridAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {

        boardManager.scoreBoard.finishTiming();
        boardManager.scoreBoard.updateDurationPlayed();

        super.onPause();
        MenuActivity.manager.tempSave(GameActivity.this);

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
