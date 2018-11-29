package fall2018.csc2017.GameManager;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.ScoreBoard.ScoreBoard;


/**
 * Abstract class to be implemented by all games.
 * Manages saves, scores.
 */
public abstract class GameManager {


    /**
     * The number of moves required to trigger an autosave.
     */
    public static int autosaveInterval;
    /**
     * The file associated with the game the current user is playing. Null if no file.
     */
    public static String currentFile;
    public Object gameState = null;
    /**
     * gameData's implementation depends on how a child class calculates score & stores game info.
     */
    protected List<Object> gameData = new ArrayList<Object>();
    protected ScoreBoard scoreBoard;
    /**
     * The current user's name, set to the global variables.
     */
    private String username;
    /**
     * The current game being played.
     */
    protected String gameName;

    /**
     * Standard constructor for GameManager to be called by all subclasses.
     *
     * @param name - username of associated user needed for save file creation and retrieval.
     */
    public GameManager(String name, String gameName) {
        username = name;
        currentFile = username + ".txt";
        gameData.add(scoreBoard);
        gameData.add(gameState);
        this.gameName = gameName;

    }

    /**
     * Default constructor GameManager.
     */

    public GameManager(){
    }

    /**
     * getter for scoreboard
     * @return scoreboard
     */
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    /**
     * getter for the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /*
     * For the file-reading/writing aspects, Noah cited the following:
     * https://stackoverflow.com/questions/35481924/write-a-string-to-a-file
     * https://stackoverflow.com/questions/5627353/how-to-create-file-directories-and-folders-in-android-data-data-project-filesyst
     */

    /**
     * Saves the gameData into two files, one for scoreboard related info, and another for game-position info.
     */
    public void save(Context context) {
        String path = context.getFilesDir() + File.separator + "/saves/" + File.separator+ "/" + gameName +"/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File save = new File(file, currentFile);
        File save2 = new File(file, "INFO_" + currentFile);
        FileOutputStream fileout;
        FileOutputStream fileout2;
        try {
            fileout = new FileOutputStream(save);
            fileout2 = new FileOutputStream(save2);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileout);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileout2);
            outputStream.writeObject(gameState);
            outputStream.close();
            fileout.close();
            outputStreamWriter.close();
            fileout2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads a given file.
     */
    public void load(Context context, String filename) {
        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        File load = new File(path, filename);
        File loadInfo = new File(path, "INFO_" + filename);
        InputStream inputStream;
        ObjectInputStream input;
        InputStream inputStream2;

        try {
            inputStream = new FileInputStream(load);
            inputStream2 = new FileInputStream(loadInfo);
            input = new ObjectInputStream(inputStream);

            //gameStats = inputStream2.read();
            gameState = input.readObject();
            inputStream.close();
            inputStream2.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Return all saves of a given user, of a given game.
     */
    public final String[] findSaves(Context context) {

        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        File file = new File(path);
        String[] saves = file.list();
        if (saves != null) {
            for (int i = 0; i < saves.length; i++) {
                if (!(saves[i].startsWith(username))) {
                    saves[i] = null;
                }
            }
        }
        return saves;
    }

    /**
     * Get game state info
     */
    public Object getGameState() {
        return gameState;
    }

    /**
     * Set game state info
     */
    public final void setGameState(Object newState) {
        gameState = newState;
    }

    /**
     * Delete a given save file (gameState & INFO_file)
     * Noah cited https://www.geeksforgeeks.org/delete-file-using-java/
     */
    public final boolean deleteSave(Context context, String file){
        File mainDelete = new File(context.getFilesDir(),"/saves/"+ gameName +"/"+file);
        File infoDelete = new File(context.getFilesDir(), "/saves/" + gameName +"/"+"INFO_"+file);
        return mainDelete.delete() && infoDelete.delete();

    }

    //Used for transition between activities
    public final void tempSave(Context context) {
        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File save = new File(file, "temp.txt");
        File save2 = new File(file, "INFO_temp.txt");
        FileOutputStream fileOut;
        FileOutputStream fileOut2;
        try {
            fileOut = new FileOutputStream(save);
            fileOut2 = new FileOutputStream(save2);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut2);
            outputStream.writeObject(gameState);
            outputStream.close();
            fileOut.close();
            outputStreamWriter.close();
            fileOut2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates necessary files/folders if user is opening app for the first time.
     */
    public void checkNew(Context context){
        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        path = context.getFilesDir() + File.separator + "/saves/"+ gameName +"/";
        File[] files = {new File(path+"temp.txt"), new File(path+"INFO_temp.txt"),
                new File(path + "scores.txt")};
        for (File create: files){
            if (!create.exists()) {
                try {
                    create.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addScore(Context context, Integer newScore, String gameName){
        if (context.getFilesDir() == null){
            return;
        }
        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        String score = newScore.toString();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "scores.txt", true));
            writer.append(gameName + '-' + username + '-' + score + '\n');
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> scoreGetter(Context context, String gameName){
        if (context.getFilesDir() == null){
            return null;
        }
        System.out.println(context.getFilesDir());
        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        ArrayList<Integer> gameScores = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path + "scores.txt"));
            String curLine;
            while((curLine = reader.readLine()) != null){
                String[] split = curLine.split("-");
                if (split[0].equals(gameName)){
                    gameScores.add(Integer.parseInt(split[2]));
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return gameScores;
    }

    public static ArrayList<Integer> scoreGetter(Context context, String gameName, String username){
        if (context.getFilesDir() == null){
            return null;
        }
        String path = context.getFilesDir() + File.separator + "/saves/" + gameName +"/";
        ArrayList<Integer> gameScores = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path + "scores.txt"));
            String curLine;
            while((curLine = reader.readLine()) != null){
                String[] split = curLine.split("-");
                if (split[0].equals(gameName) && split[1].equals(username)){
                    gameScores.add(Integer.parseInt(split[2]));
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return gameScores;
    }

}
