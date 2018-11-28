package fall2018.csc2017.TwentyFortyEight;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import fall2018.csc2017.GameManager.GameManager;
import static java.lang.Integer.min;

public class TFEManager extends GameManager {

     /**
     * Number of remaining undos for the player.
     */
    public int undos;

    /**
     * The path's of the recent boards, used for undo.
     * Updated every move, unless there are no more undos left. recentBoards[0] is the oldest
     */
    private String[] recentBoards = {null,null,null};

    /**
     * Documents which recentBoards are non-empty.
     */
    private int[] recentStates = {0,0,0};


    TFEManager(String name){
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
    int getRecent(){

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
            String temp = new String(recentBoards[0].toString());
            recentBoards[0] = new String(recentBoards[1].toString());
            recentBoards[1] = new String(recentBoards[2].toString());
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
    Object loadRecent(Context context){
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
    public boolean undo(Context context) {
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

    //Resets the undo fields, used on backpress.
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
