package fall2018.csc2017.mine_sweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.R;
import fall2018.csc2017.game_manager.GameManager;
import fall2018.csc2017.launch_centre.GameLaunchActivity;

import static fall2018.csc2017.launch_centre.GameLaunchActivity.username;

/***
 * The activity class for the menu of minesweeper
 */
public class MenuActivity extends AppCompatActivity {

    /***
     * The game manager for this game.
     */
    public static MinesweeperGameManager manager = new MinesweeperGameManager(username);

    /**
     * Maximum # of save files a use is allowed.
     */
    int MAX_SAVES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper_menu);
        manager.checkNew(MenuActivity.this);
        addStartButtonListener();
        addLoadButtonListener();
        addDeleteButtonListener();
        addScoreButtonListener();

        final EditText editText = findViewById(R.id.fileInput);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                GameManager.currentFile = username + "_" + editText.getText() + ".txt";
                editText.setVisibility(View.INVISIBLE);
                manager.setGameState(new BoardManager(new Board(10, 10, 0.15)));
                manager.tempSave(MenuActivity.this);
                switchToGame();
                return true;
            }
        });

    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        ImageButton startButton = findViewById(R.id.newGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;

                for (String save : manager.findSaves(MenuActivity.this)) {
                    if (save != null) {
                        counter += 1;
                    }
                }

                if (counter < MAX_SAVES) {
                    findViewById(R.id.fileInput).setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MenuActivity.this, "You've reached max saves!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {

        ImageButton loadButton = findViewById(R.id.loadGame);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Displays user-appropriate buttons
                addLoadFilesListener();
            }
        });
    }

    /**
     * Activates the score button
     */
    private void addScoreButtonListener() {
        ImageButton scoreButton = findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scorePage = new Intent(MenuActivity.this,
                        MineSweeperScoreBoardActivity.class);
                MenuActivity.this.startActivity(scorePage);
            }
        });

    }

    /**
     * Activates the load file buttons as invisible.
     */
    private void addLoadFilesListener() {
        final Button[] loadFiles = {findViewById(R.id.save1), findViewById(R.id.save2),
                findViewById(R.id.save3), findViewById(R.id.save4)};


        manager.tempSave(this);
        String[] saves = manager.findSaves(MenuActivity.this);
        int counter = 0;
        for (String save : saves) {
            if (save != null) {
                loadFiles[counter].setVisibility(View.VISIBLE);
                loadFiles[counter].setText(save);
                final int finalI = counter;
                loadFiles[counter].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setInvisible(loadFiles);
                        GameManager.currentFile = (String) loadFiles[finalI].getText();

                        manager.load(MenuActivity.this, GameManager.currentFile);

                        switchToGame();
                    }
                });
                counter += 1;
            }
        }
    }

    /**
     * Set a list of buttons to invisible.
     */
    public void setInvisible(Button[] buttons) {
        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Activates the delete file buttons as invisible.
     */
    private void addDeleteFilesListener() {
        final Button[] loadFiles = {findViewById(R.id.save1), findViewById(R.id.save2),
                findViewById(R.id.save3), findViewById(R.id.save4)};

        manager.tempSave(this);
        String[] saves = manager.findSaves(MenuActivity.this);
        int counter = 0;
        for (String save : saves) {
            if (save != null) {
                loadFiles[counter].setVisibility(View.VISIBLE);
                loadFiles[counter].setText(save);
                final int finalI = counter;
                loadFiles[counter].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setInvisible(loadFiles);
                        if (manager.deleteSave(MenuActivity.this,
                                ((String) loadFiles[finalI].getText()))) {
                            Toast.makeText(MenuActivity.this,
                                    "Save deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MenuActivity.this,
                                    "Could not delete :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                counter += 1;
            }
        }
    }

    private void addDeleteButtonListener() {
        ImageButton deleteButton = findViewById(R.id.deleter);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDeleteFilesListener();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        manager.tempSave(this);

        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, GameLaunchActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }
}


