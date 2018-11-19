package fall2018.csc2017.MineSweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


import fall2018.csc2017.slidingtiles.R;


public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper_menu);

        addStartButtonListener();

    }
    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        ImageButton startButton = findViewById(R.id.newGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(MenuActivity.this, GameActivity.class);
                MenuActivity.this.startActivity(tmp);
            }
        });
    }
}


