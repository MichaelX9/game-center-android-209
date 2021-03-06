package fall2018.csc2017.twenty_forty_eight;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import fall2018.csc2017.game_manager.GameManager;
import static java.lang.Integer.min;

/***
 * The game manager for 2048
 */
class TFEGameManager extends GameManager {

     /**
     * Number of remaining undos for the player.
     */
    private int undos;

    /**
     * GameState getter.
     */
    @Override
    public TFEBoardManager getGameState(){
        return (TFEBoardManager) gameState;
    }

    /**
     * The path's of the recent boards, used for undo.
     * Updated every move, unless there are no more undos left. recentBoards[0] is the oldest
     */
    private String[] recentBoards = {null,null,null};

    /**
     * Documents which recentBoards are non-empty.
     */
    private int[] recentStates = {0,0,0};

    /**
     * Constructure for new game manager class.
     * @param name - username of user logged in.
     */
    TFEGameManager(String name){
        super(name, "TFE");
        undos = 3;
    }

    /**
     * Creates the recentBoard files if they don't exist.
     */
    void undoSetup(Context context){
        String path;
        for (int i = 0; i < recentBoards.length; i++){
            recentBoards[i] = "R"+i+currentFile;
            path = context.getFilesDir() + File.separator + "/saves/" + File.separator+ "/" + super.gameName +"/"+recentBoards[i];
            File file = new File(path);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * Getter for number of undos remaining.
     * @return number of undos
     */
    int getUndos() {
        return undos;
    }

    /**
     * Setter for number of undos remaining.
     * @param i number of undos
     */
    void setUndos(int i) {
        this.undos = i;
    }

    /**
     * Returns the newest recent (written) board
     * @return index of the file name of that board
     */
    private int getRecent(){

        if (recentStates[2] == 0 && recentStates[1] == 0 && recentStates[0] == 0){
            return -1;
        }
        else if(recentStates[2] == 0 && recentStates[1] != 0){
            return 1;
        }
        else if (recentStates[2] != 0){
            return 2;
        }
        else{return 0;}

    }

    /**
     * Updates the recent boards by adding & shuffling if necessary.
     */
    void addRecent(Context context, Object bManager){

        int recent = getRecent();
        recentStates[min(recent+1, 2)] = 1;
        String path = context.getFilesDir() + File.separator + "/saves/" + File.separator+ "/" + super.gameName +"/";
        File file = new File(path);

        File save = new File(file, recentBoards[min(recent+1,2)]);
        if (recent == 2){
            String temp = recentBoards[0];
            recentBoards[0] = recentBoards[1];
            recentBoards[1] = recentBoards[2];
            recentBoards[2] = temp;
            save = new File(file, temp);
        }

        FileOutputStream fileout;
        try {
            fileout = new FileOutputStream(save);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileout);
            outputStream.writeObject(bManager);
            outputStream.close();
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads the most recent board (for undo)
     * @return the boardManager of said board
     */
    private Object loadRecent(Context context){
        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        String fileName;
        Object bManager = null;

        if (getRecent() == -1){
            return null;
        }
        else{
            fileName = recentBoards[getRecent()];
        }
        File load = new File(path, fileName);
        InputStream inputStream;
        ObjectInputStream input;

        try {
            inputStream = new FileInputStream(load);
            input = new ObjectInputStream(inputStream);
            bManager = input.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bManager;

    }

    /**
     * Undo functionality for 2048.
     */
    boolean undo(Context context) {
        int recent = getRecent();

        if (recent == -1){
            return false;
        }

        Object next = loadRecent(context);
        if (next != null){
            undos -= 1;
            MenuActivity.manager.setGameState(next);

            recentStates[recent] = 0;
            return true;
        }

        return false;

    }

    /**
     * Reseter for undo functionality on backpress
     */
    void reset(){
        undos = 3;
        recentStates[0] = 0;
        recentStates[1] = 0;
        recentStates[2] = 0;
        recentBoards[0] = "R0"+currentFile;
        recentBoards[1] = "R1"+currentFile;
        recentBoards[2] = "R2"+currentFile;
    }


}
