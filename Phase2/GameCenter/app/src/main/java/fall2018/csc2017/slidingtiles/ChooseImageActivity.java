package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/***
 * Activity for choosing image for the tiles.
 */
public class ChooseImageActivity extends AppCompatActivity {

    /***
     * Constant request code for requesting image from internal storage.
     */
    public static final int REQUEST_CODE = 7;

    /***
     * the board manager of the game board
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidingtile_choose_image);
        boardManager = StartingActivity.manager.getGameState();
    }

    /***
     * generate the tiles with number images
     * @param view Android view
     */
    public void StandardBG(View view) {
        if (boardManager.getBoard().numTiles() > 25) {
            Toast.makeText(this,
                    "Numbered background is only supported for boards with under 25 tiles"
                    , Toast.LENGTH_SHORT).show();
        } else {
            boardManager.getBoard().clearBGs();
            for (int id : resToList()) {
                Bitmap b = BitmapFactory.decodeResource(getResources(), id);
                Drawable d = new BitmapDrawable(getResources(), b);
                boardManager.getBoard().addBG(d);

            }
            boardManager.setEmptyTile(getDrawable(R.drawable.tile_25));
            boardManager.getBoard().generateTiles();
            gameStart();
        }
    }

    /***
     * Helper for StandardBG(), puts the drawables of numbers in an array
     * @return array of drawable numbers
     */
    public int[] resToList() {
        int[] ids = new int[25];
        ids[0] = R.drawable.tile_1;
        ids[1] = R.drawable.tile_2;
        ids[2] = R.drawable.tile_3;
        ids[3] = R.drawable.tile_4;
        ids[4] = R.drawable.tile_5;
        ids[5] = R.drawable.tile_6;
        ids[6] = R.drawable.tile_7;
        ids[7] = R.drawable.tile_8;
        ids[8] = R.drawable.tile_9;
        ids[9] = R.drawable.tile_10;
        ids[10] = R.drawable.tile_11;
        ids[11] = R.drawable.tile_12;
        ids[12] = R.drawable.tile_13;
        ids[13] = R.drawable.tile_14;
        ids[14] = R.drawable.tile_15;
        ids[15] = R.drawable.tile_16;
        ids[16] = R.drawable.tile_17;
        ids[17] = R.drawable.tile_18;
        ids[18] = R.drawable.tile_19;
        ids[19] = R.drawable.tile_20;
        ids[20] = R.drawable.tile_21;
        ids[21] = R.drawable.tile_22;
        ids[22] = R.drawable.tile_23;
        ids[23] = R.drawable.tile_24;
        ids[24] = R.drawable.tile_25;
        return ids;
    }

    /***
     * Generate tiles based on image selection 1
     * @param view Android view
     */
    public void imageSelection1(View view) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ib);
        imageToTiles(b);
        gameStart();
    }

    /***
     * Generate tiles based on image selection 2
     * @param view Android view
     */
    public void imageSelection2(View view) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.paul);
        imageToTiles(b);
        gameStart();
    }

    /***
     * Let user to choose an image from internal storage, and generate tiles basing on it
     * @param view Android view
     */
    public void imageFromStorage(View view) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        File imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imageDirectoryPath = imageDirectory.getPath();
        Uri data = Uri.parse(imageDirectoryPath);
        photoPicker.setDataAndType(data, "image/*");
        startActivityForResult(photoPicker, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                Uri imageUri = data.getData();
                if(imageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap image = BitmapFactory.decodeStream(inputStream);
                        imageToTiles(image);
                        gameStart();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Cannot open image.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Request fail.", Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * Generate tiles basing on the image read from an url
     * @param view Android view
     */
    public void imageFromUrl(View view) {
        EditText editText = findViewById(R.id.editText);
        try {
            URL url = new URL(editText.getText().toString());
            new GetUrlImage().execute(url).get();
            gameStart();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to read url.", Toast.LENGTH_SHORT).show();
        }

    }

    /***
     * Helper to generate tiles based on a bitmap
     * @param b bitmap of the image
     */
    public void imageToTiles(Bitmap b) {
        int trimH = b.getHeight() / boardManager.getBoard().getNumRows();
        int trimW = b.getWidth() / boardManager.getBoard().getNumCols();

        boardManager.getBoard().clearBGs();

        for (int i = 0; i < boardManager.getBoard().getNumRows(); i++) {
            for (int j = 0; j < boardManager.getBoard().getNumCols(); j++) {
                Bitmap bitmap = Bitmap.createBitmap(b, j * trimW, i * trimH, trimW, trimH);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                boardManager.getBoard().addBG(d);
            }
        }
        boardManager.setEmptyTile(getDrawable(R.drawable.tile_25));
        boardManager.getBoard().generateTiles();
    }

    /***
     * Start GameActivity
     */
    public void gameStart() {
        Intent intent = new Intent(this, GameActivity.class);
        StartingActivity.manager.save(this);
        startActivity(intent);
    }

    /***
     * An background task to download image from url
     */
    private class GetUrlImage extends AsyncTask<URL, Void, Void> {
        @Override
        protected Void doInBackground(URL... urls) {
            try {
                InputStream inputStream = urls[0].openStream();
                Bitmap b = BitmapFactory.decodeStream(inputStream);
                imageToTiles(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
