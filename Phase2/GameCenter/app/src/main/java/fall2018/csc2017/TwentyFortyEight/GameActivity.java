package fall2018.csc2017.TwentyFortyEight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
import fall2018.csc2017.slidingtiles.R ;

import static java.lang.Math.min;

public class GameActivity extends AppCompatActivity implements Observer, View.OnTouchListener {

    /**
     * Gridview which displays the gameboard.
     */
    private GridView gridView;

    /**
     * Boardmanager class for the current game.
     */
    private TFEBoardManager tfeBoardManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        findViewById(R.id.TFE_Grid).setOnTouchListener(this);

        gridView = findViewById(R.id.TFE_Grid);

        MenuActivity.manager.load(GameActivity.this, "temp.txt");
        tfeBoardManager = (TFEBoardManager) MenuActivity.manager.getGameState();
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

    float prevX, prevY;
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


                //Calculates where we swiped
                if (MenuActivity.manager.getUndos() > 0){
                    MenuActivity.manager.addRecent(this, tfeBoardManager);
                }
                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)) {
                    //LEFT - RiGHT Direction

                    if( newX > prevX) {
                        //RIGHT
                        tfeBoardManager.getBoard().tileSlide(0);
                    } else {
                        //LEFT
                        tfeBoardManager.getBoard().tileSlide(1);
                    }
                } else {
                    // UP-DOWN Direction
                    if (newY > prevY) {
                        //DOWN
                        tfeBoardManager.getBoard().tileSlide(3);
                    } else {
                        //UP
                        tfeBoardManager.getBoard().tileSlide(2);
                    }
                }
                if(tfeBoardManager.getBoard().isSolved()){
                    tfeBoardManager.makeTextForSolvedGame(this);
                }
                if(tfeBoardManager.getBoard().isOver()){
                    tfeBoardManager.makeTextForLostGame(this);
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
    private void display(){
        ((TFEGridAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }


    /**
     * Clicker for save button to initiate save game function.
     * @param view - current view of the board.
     */
    public void saveClicker(View view){
        MenuActivity.manager.setGameState(tfeBoardManager);
        MenuActivity.manager.save(this);
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Clicker for undo button to initiate undo functionality.
     * @param view - current view of board
     */
    public void undoClicker(View view){
        if (MenuActivity.manager.getUndos() == 0){
            Toast.makeText(this, "No more undos left!", Toast.LENGTH_SHORT).show();
        }
        else{
            if (!MenuActivity.manager.undo(GameActivity.this)){
                Toast.makeText(this, "Couldn't undo", Toast.LENGTH_SHORT).show();
            }
            else {
                for(int i = 0; i < tfeBoardManager.getBoard().numTiles(); i++){
                    tfeBoardManager.getBoard().tileGetter(i).TFEvaluesetter(
                            ((TFEBoardManager)MenuActivity.manager.getGameState()
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

