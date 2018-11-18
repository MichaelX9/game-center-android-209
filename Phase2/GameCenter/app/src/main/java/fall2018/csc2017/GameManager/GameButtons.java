package fall2018.csc2017.GameManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fall2018.csc2017.slidingtiles.R;

public class GameButtons extends Fragment {
    /**
     * Fragment constructor for the in-game buttons, save and undo, to be available for all games.
     * @param inflater - generic LayoutInflater for View object creation from XML layouts.
     * @param container - generic View which will wrap dynamic content in layout.
     * @param savedInstanceState - possible instance state from previous user interaction.
     * @return - view object created according to game_buttons.xml specified layout.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_buttons, container, false);
    }

}
