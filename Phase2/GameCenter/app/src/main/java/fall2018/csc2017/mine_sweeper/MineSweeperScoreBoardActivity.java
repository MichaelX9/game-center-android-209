package fall2018.csc2017.mine_sweeper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fall2018.csc2017.R;
import fall2018.csc2017.game_manager.GameManager;
import fall2018.csc2017.launch_centre.GameLaunchActivity;

/***
 * This activity displays the scoreboard for minesweeper.
 */
public class MineSweeperScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_score_board);
        TextView globalScores = findViewById(R.id.globalScores);
        TextView yourScores = findViewById(R.id.yourScores);
        ArrayList<String> userHighScores = GameManager.scoreGetter(
                this, "Minesweeper", GameLaunchActivity.username);

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
     *
     * @param userScores the TextView for the user's high scores
     */
    private void displayUserHighScores(TextView userScores) {
        ArrayList<String> userHighScores = GameManager.scoreGetter(
                this, "Minesweeper", GameLaunchActivity.username);
        if (userHighScores != null) {
            Collections.sort(userHighScores, new sortByScore());
            Collections.reverse(userHighScores);
            StringBuilder userHighScoresBuilder = new StringBuilder();
            for (String score : userHighScores) {
                String scoreOnly = score.substring(0, score.indexOf(':'));
                userHighScoresBuilder.append(scoreOnly);
                userHighScoresBuilder.append("\n");
            }
            userScores.setText(userHighScoresBuilder);
        }
    }

    /**
     * Display the game's high scores on the Scores page.
     *
     * @param globalScores the TextView for the game's high scores
     */
    private void displayGlobalHighScores(TextView globalScores) {
        ArrayList<String> globalHighScores = GameManager.scoreGetter(this,
                "Minesweeper");
        if (globalHighScores != null) {
            Collections.sort(globalHighScores, new sortByScore());
            Collections.reverse(globalHighScores);
            StringBuilder globalHighScoresBuilder = new StringBuilder();
            for (String score : globalHighScores) {
                globalHighScoresBuilder.append(score);
                globalHighScoresBuilder.append("\n");
            }
            globalScores.setText(globalHighScoresBuilder);
        }
    }

    /**
     * Overrides Comparator's compare method to sort by the "number part" of the String
     */
    class sortByScore implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return Integer.parseInt(a.substring(0, a.indexOf(':'))) -
                    Integer.parseInt(b.substring(0, b.indexOf(':')));
        }
    }

}
