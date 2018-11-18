package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/***
 * The activity for user to choose board complexity and number of undoes.
 */
public class SettingupActivity extends AppCompatActivity {
    /***
     * The number of undoes user selected
     */
    int undoNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingup);
    }

    /***
     * Initialize a game with game complexity 3x3
     * @param view Android view
     */
    public void StartGame3x3(View view) {
        startStandardGame(3, 3);

    }

    /***
     * Initialize a game with game complexity 4x4
     * @param view Android view
     */
    public void StartGame4x4(View view) {
        startStandardGame(4, 4);
    }

    /***
     * Initialize a game with game complexity 5x5
     * @param view Android view
     */
    public void StartGame5x5(View view) {
        startStandardGame(5, 5);
    }

    /***
     * Hepper for starting a game.
     * @param row number of rows for the board
     * @param col number of columns for the board
     */
    public void startStandardGame(int row, int col) {
        undoEdit();
        switchToBGSelection(new Board(row, col));
    }

    /***
     * Initialize a game with custom complexity.
     * @param view Android view
     */
    public void StartGameCustom(View view) {
        EditText rowText = findViewById(R.id.textEdit);
        EditText colText = findViewById(R.id.textEdit2);
        int row = Integer.parseInt(rowText.getText().toString());
        int col = Integer.parseInt(colText.getText().toString());
        if (row > 20 || col > 10) {
            Toast.makeText(this, "Board cannot be larger than 20x10", Toast.LENGTH_SHORT).show();
        } else {
            startStandardGame(row, col);
        }

    }

    /***
     * switch to ChooseImageActivity
     * @param board the board initialized in this activity
     */
    public void switchToBGSelection(Board board) {
        Intent intent = new Intent(this, ChooseImageActivity.class);
        StartingActivity.manager.setGameState(new BoardManager(board));
        StartingActivity.manager.getGameState().setScoreBoard(this,
                new SlidingTilesScoreBoard());
        startActivity(intent);
    }

    /***
     * set up custom number of undoes
     */
    private void undoEdit() {
        EditText undo = findViewById(R.id.undoSetter);
        undoNum = Integer.parseInt(undo.getText().toString());
        StartingActivity.manager.setUndo(undoNum);
    }
}
