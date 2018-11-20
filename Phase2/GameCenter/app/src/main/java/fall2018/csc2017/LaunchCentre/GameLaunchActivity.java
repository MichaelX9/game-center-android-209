package fall2018.csc2017.LaunchCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import fall2018.csc2017.MineSweeper.MenuActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.StartingActivity;

/**
 * Game Launcher Interface, will eventually be location from which all games are opened.
 */
public class GameLaunchActivity extends AppCompatActivity {

    /**
     * the tag for id purposes
     */
    private static final String TAG = "GameLaunchActivity";

    /**
     * stores the username taken from the server once a user logs in
     */
    public static String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_launch_centre);
        Log.d(TAG, "onCreate: Starting");
        addSlidingTilesListener();
        addMinesweeperListener();
        addTFEListener();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

    }

    /**
     * Opens SlidingTiles game.
     */
    private void addSlidingTilesListener() {
        ImageButton startSlidingTiles = findViewById(R.id.slidingTiles);
        startSlidingTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(GameLaunchActivity.this, StartingActivity.class);
                GameLaunchActivity.this.startActivity(tmp);
            }
        });
    }

    /**
     * Opens Minesweeper game.
     */
    private void addMinesweeperListener() {
        ImageButton startMinesweeper = findViewById(R.id.mineSweeper);
        startMinesweeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(GameLaunchActivity.this, MenuActivity.class);
                GameLaunchActivity.this.startActivity(tmp);
            }
        });
    }

    /**
     * Opens 2048 Game
     */
    private void addTFEListener() {
        ImageButton startMinesweeper = findViewById(R.id.twentyForty);
        startMinesweeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(GameLaunchActivity.this, fall2018.csc2017.TwentyFortyEight.MenuActivity.class);
                GameLaunchActivity.this.startActivity(tmp);
            }
        });
    }

}
