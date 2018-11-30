package fall2018.csc2017.SlidingTiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameManager.GameManager;
import fall2018.csc2017.LaunchCentre.GameLaunchActivity;


/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /***
     * The game board of current game
     */
    private Board board;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuActivity.manager.load(GameActivity.this, GameManager.currentFile);
        this.board = MenuActivity.manager.getGameState().getBoard();
        MenuActivity.manager.tempSave(this);

        createTileButtons(this);
        setContentView(R.layout.activity_slidingtile_game);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(board.getNumCols());
        gridView.setBoardManager(MenuActivity.manager.getGameState());
        (MenuActivity.manager.getGameState()).getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / board.getNumCols();
                        columnHeight = displayHeight / board.getNumRows();
                        display();
                    }
                });
        MenuActivity.manager.getGameState().scoreBoard.setCurrentUser(GameLaunchActivity.username);
        MenuActivity.manager.getGameState().scoreBoard.startTiming();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        //
        Board board = (MenuActivity.manager.getGameState()).getBoard();
        //
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getNumRows(); row++) {
            for (int col = 0; col != board.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackground(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = (MenuActivity.manager.getGameState()).getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getNumCols();
            int col = nextPos % board.getNumCols();
            b.setBackground(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Adds the undo button functionality to the game interface.
     */
    public void undoClicker(View view){
        if (MenuActivity.manager.getPastMoves().size() > 0 && MenuActivity.manager.getUndos() != 0) {
            MenuActivity.manager.undo();
            String str;
            if (MenuActivity.manager.getUndos() < 0){
                str = "You have unlimited undos remaining.";
            }
            else {
                str = "You have " + MenuActivity.manager.getUndos() + " undos remaining.";
            }
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No Undos Remaining.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Adds the save button functionality to the game interface
     */
    public void saveClicker (View view) {
        MenuActivity.manager.save(this);
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {

        MenuActivity.manager.getGameState().scoreBoard.finishTiming();
        MenuActivity.manager.getGameState().scoreBoard.updateDurationPlayed();

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
