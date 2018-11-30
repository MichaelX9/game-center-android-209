package fall2018.csc2017.TwentyFortyEight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.GameManager.GameManager;
import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
import fall2018.csc2017.SlidingTiles.R;

public class TFEScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfe_score_board);
        TextView globalScores = findViewById(R.id.globalScores);
        TextView yourScores = findViewById(R.id.yourScores);
        ArrayList<String> userHighScores = GameManager.scoreGetter(
                this,"TFE", GameLaunchActivity.username);

        if (userHighScores != null && userHighScores.size() != 0) {
            displayUserHighScores(yourScores);
        } else {
            String display = "You do not have any scores yet. ";
            yourScores.setText(display);
        }
        displayGlobalHighScores(globalScores);
    }

    /**
     * Display the current user's high scores on the Scores page.
     * @param userScores the TextView for the user's high scores
     */
    private void displayUserHighScores(TextView userScores) {
        ArrayList<String> userHighScores = GameManager.scoreGetter(
                this,"TFE", GameLaunchActivity.username);
        if (userHighScores != null) {
            Collections.sort(userHighScores);
            Collections.reverse(userHighScores);
            StringBuilder userHighScoresBuilder = new StringBuilder();
            for (String score : userHighScores) {
                userHighScoresBuilder.append(score);
                userHighScoresBuilder.append("\n");
            }
            userScores.setText(userHighScoresBuilder);
        }
    }

    /**
     * Display the game's high scores on the Scores page.
     * @param globalScores the TextView for the game's high scores
     */
    private void displayGlobalHighScores(TextView globalScores) {
        ArrayList<String> globalHighScores = GameManager.scoreGetter(this,
                "TFE");
        if (globalHighScores != null) {
            Collections.sort(globalHighScores);
            Collections.reverse(globalHighScores);
            StringBuilder globalHighScoresBuilder = new StringBuilder();
            for (String score : globalHighScores) {
                globalHighScoresBuilder.append(score);
                globalHighScoresBuilder.append("\n");
            }
            globalScores.setText(globalHighScoresBuilder);
        }
    }

}
