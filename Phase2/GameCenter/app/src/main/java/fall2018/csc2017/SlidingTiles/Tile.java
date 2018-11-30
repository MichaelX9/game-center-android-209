package fall2018.csc2017.SlidingTiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {


    /**
     * The background id to find the tile image.
     */
    private transient Drawable background;
    /***
     * The background compressed in String format
     */
    private String compressedBackground;


    /**
     * The unique id.
     */
    private int id;

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    Tile(int id, Drawable background) {
        this.id = id;
        this.background = background;
        preSer();
    }


    /**
     * Return the background id.
     *
     * @return the background id
     */
    public Drawable getBackground() {
        return background;
    }

    /***
     * Setter for background
     * @param background the image to be set as background
     */
    public void setBackground(Drawable background) {
        this.background = background;
        preSer();
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /***
     * preSer the background to compressedBackground
     */
    private void preSer() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ((BitmapDrawable) background).getBitmap().compress(Bitmap.CompressFormat.PNG,
                100, byteArrayOutputStream);
        compressedBackground = Base64.encodeToString(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    /***
     * postSer the compressedBackground to background
     * @param context the context
     */
    void postSer(Context context) {
        byte[] decodedBytes = Base64.decode(compressedBackground.substring(
                compressedBackground.indexOf(",") + 1), Base64.DEFAULT);

        Bitmap b = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        background = new BitmapDrawable(context.getResources(), b);
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
