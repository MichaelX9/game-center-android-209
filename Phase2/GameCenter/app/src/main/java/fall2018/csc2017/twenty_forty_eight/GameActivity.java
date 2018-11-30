package fall2018.csc2017.twenty_forty_eight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.R;
import fall2018.csc2017.launch_centre.GameLaunchActivity;

import static java.lang.Math.min;

/***
 * This activity is the game body of 2048.
 */
public class GameActivity extends AppCompatActivity implements Observer, View.OnTouchListener {


    float prevX, prevY;
    /**
     * GridView which displays the game board.
     */
    private GridView gridView;
    /**
     * BoardManager class for the current game.
     */
    private TFEBoardManager tfeBoardManager;
    /**
     * Boolean flag to mark when a gameState has ended.
     */
    private boolean gameOver = false;
    /**
     * Boolean flag to mark when a 2048 has been made.
     */
    private boolean TFEMade = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        findViewById(R.id.TFE_Grid).setOnTouchListener(this);

        gridView = findViewById(R.id.TFE_Grid);

        MenuActivity.manager.load(GameActivity.this, "temp.txt");
        tfeBoardManager = MenuActivity.manager.getGameState();
        MenuActivity.manager.undoSetup(this);
        MenuActivity.manager.setUndos(MenuActivity.manager.getUndos());

        tfeBoardManager.getBoard().addObserver(this);
        gridView.setNumColumns(tfeBoardManager.getBoard().getNumCol());
        final Context context = this;

        tfeBoardManager.setScoreBoard(this, new TFEScoreBoard());
        tfeBoardManager.getScoreBoard().setCurrentUser(GameLaunchActivity.username);

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

    }

    /**
     * Slide gesture sensor to allow swiping actions in 2048. Adapted from:
     * https://stackoverflow.com/questions/11327095/implement-the-swipe-gesture-on-grid-view.
     *
     * @param v     - current view
     * @param event - recorded touch event
     * @return whether or not a swipe was made
     */
    @SuppressLint("ClickableViewAccessibility")
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

                if (MenuActivity.manager.getUndos() > 0) {
                    MenuActivity.manager.addRecent(this, tfeBoardManager);
                }
                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)) {
                    if (newX > prevX) {
                        //Right-swipe
                        tfeBoardManager.getBoard().tileSlide(0);
                    } else {
                        //Left-swipe
                        tfeBoardManager.getBoard().tileSlide(1);
                    }
                } else {
                    if (newY < prevY) {
                        //Up-Swipe
                        tfeBoardManager.getBoard().tileSlide(2);
                    } else {
                        //Down-swipe
                        tfeBoardManager.getBoard().tileSlide(3);
                    }
                }
                if (tfeBoardManager.getBoard().isSolved() && !TFEMade) {
                    tfeBoardManager.makeTextForSolvedGame(this);
                    TFEMade = true;
                } else if (tfeBoardManager.getBoard().isOver() && !gameOver) {
                    tfeBoardManager.makeTextForLostGame(this);
                    gameOver = true;
                }

                break;

        }
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Set background image of each grid space and call adaptor to change view.
     */
    private void display() {
        ((TFEGridAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }


    /**
     * Clicker for save button to initiate save game function.
     *
     * @param view - current view of the board.
     */
    public void saveClicker(View view) {
        MenuActivity.manager.setGameState(tfeBoardManager);
        MenuActivity.manager.save(this);
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Clicker for undo button to initiate undo functionality.
     *
     * @param view - current view of board
     */
    public void undoClicker(View view) {
        if (MenuActivity.manager.getUndos() == 0) {
            Toast.makeText(this, "No more undos left!", Toast.LENGTH_SHORT).show();
        } else {
            if (!MenuActivity.manager.undo(GameActivity.this)) {
                Toast.makeText(this, "Couldn't undo", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < tfeBoardManager.getBoard().numTiles(); i++) {
                    tfeBoardManager.getBoard().tileGetter(i).TFEvaluesetter(
                            (MenuActivity.manager.getGameState()
                            ).getBoard().tileGetter(i).getTileValue());
                }
                display();
            }
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        MenuActivity.manager.tempSave(GameActivity.this);
    }

}

