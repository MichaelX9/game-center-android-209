package fall2018.csc2017.MineSweeper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.GameManager.GameManager;
import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
import fall2018.csc2017.slidingtiles.R;

public class ScoreboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper_scoreboard);

        TextView globalScores = findViewById(R.id.globalScores);
        TextView yourScores = findViewById(R.id.yourScores);
        String currentUser = GameLaunchActivity.username;
//        ArrayList<Integer> userHighScores = GameManager.scoreGetter(
//                this,"SlidingTiles", currentUser);
//
//        if (userHighScores.size() != 0) {
//            displayUserHighScores(currentUser, yourScores);
//        } else {
//            String display = "You do not have any scores yet. ";
//            yourScores.setText(display);
//        }
//        displayGlobalHighScores(globalScores);

    }

}
