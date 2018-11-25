package fall2018.csc2017.TwentyFortyEight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.GameManager.GameManager;
import fall2018.csc2017.LaunchCentre.GameLaunchActivity;
import fall2018.csc2017.slidingtiles.R;

public class TFEScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfe_score_board);
        TextView globalScores = findViewById(R.id.globalScores);
        TextView yourScores = findViewById(R.id.yourScores);
        ArrayList<Integer> userHighScores = GameManager.scoreGetter(
                this,"TwentyFortyEight", GameLaunchActivity.username);

        if (userHighScores.size() != 0) {
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
        ArrayList<Integer> userHighScores = GameManager.scoreGetter(
                this,"TwentyFortyEight", GameLaunchActivity.username);
        int numberOfUserScores = userHighScores.size();
        Collections.sort(userHighScores);
        StringBuilder userHighScoresBuilder = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            if (i < numberOfUserScores) {
                userHighScoresBuilder.append(
                        userHighScores.get(numberOfUserScores - i));
                userHighScoresBuilder.append("\n");
            }
        }
        userScores.setText(userHighScoresBuilder.toString());
    }

    /**
     * Display the game's high scores on the Scores page.
     * @param globalScores the TextView for the game's high scores
     */
    private void displayGlobalHighScores(TextView globalScores) {
        ArrayList<Integer> globalHighScores = GameManager.scoreGetter(this,
                "TwentyFortyEight");
        int numberOfGlobalScores = globalHighScores.size();
        Collections.sort(globalHighScores);
        StringBuilder globalHighScoresBuilder = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            if (i < numberOfGlobalScores) {
                globalHighScoresBuilder.append(globalHighScores.get(numberOfGlobalScores - i));
                globalHighScoresBuilder.append("\n");
            }
        }
        globalScores.setText(globalHighScoresBuilder.toString());
    }

}
