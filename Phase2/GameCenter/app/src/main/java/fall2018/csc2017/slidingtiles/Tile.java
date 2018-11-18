package fall2018.csc2017.slidingtiles;

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

    /***
     * the image for the emptyTile
     */
    static private transient Drawable emptyTile;
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
        compress();
    }

    /***
     * Setter for emptyTile
     * @param d image for the empty tile
     */
    static void setEmptyTile(Drawable d) {
        emptyTile = d;
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
        compress();
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
     * compress the background to compressedBackground
     */
    private void compress() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ((BitmapDrawable) background).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        compressedBackground = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    /***
     * decompress the compressedBackground to background
     * @param context the context
     */
    void decompress(Context context) {
        byte[] decodedBytes = Base64.decode(compressedBackground.substring(compressedBackground.indexOf(",") + 1),
                Base64.DEFAULT);

        Bitmap b = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        background = new BitmapDrawable(context.getResources(), b);
    }

    /***
     * Set this tile as empty tile.
     */
    void setEmpty() {
        background = emptyTile;
        compress();
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
