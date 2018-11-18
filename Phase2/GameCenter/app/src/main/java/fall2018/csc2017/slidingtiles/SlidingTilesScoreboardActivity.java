package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fall2018.csc2017.LaunchCentre.GameLaunchActivity;

public class SlidingTilesScoreboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);

        TextView globalScores = findViewById(R.id.globalScores);
        TextView yourScores = findViewById(R.id.yourScores);
        String currentUser = GameLaunchActivity.username;

        if (SlidingTilesScoreBoard.userToScores.get(currentUser) != null) {
            displayUserHighScores(currentUser, yourScores);
        } else {
            String display = "You do not have any scores yet. ";
            yourScores.setText(display);
        }
        displayGlobalHighScores(globalScores);

    }

    /**
     * Display the current user's high scores on the Scores page.
     * @param username the current user's username
     * @param userScores the TextView for the user's high scores
     */
    private void displayUserHighScores(String username, TextView userScores) {
        int numberOfUserScores = SlidingTilesScoreBoard.userToScores.get(username).size();
        StringBuilder userHighScoresBuilder = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            if (i < numberOfUserScores) {
                userHighScoresBuilder.append(
                        SlidingTilesScoreBoard.userToScores.get(
                                username).get(numberOfUserScores - i));
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
        int numberOfGlobalScores = SlidingTilesScoreBoard.highScores.size();
        StringBuilder globalHighScoresBuilder = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            if (i < numberOfGlobalScores) {
                globalHighScoresBuilder.append(
                        SlidingTilesScoreBoard.highScores.get(numberOfGlobalScores - i));
                globalHighScoresBuilder.append("\n");
            }
        }
        globalScores.setText(globalHighScoresBuilder.toString());
    }
}