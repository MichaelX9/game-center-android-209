package fall2018.csc2017.twenty_forty_eight;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import fall2018.csc2017.R;

public class TFEGridAdapter extends BaseAdapter {
    private TFEBoardManager tfeBoardManager;
    private AbsListView.LayoutParams params;
    private Context context;

    TFEGridAdapter(TFEBoardManager tfeBoardManager, int columnWidth, int columnHeight, Context context) {
        this.tfeBoardManager = tfeBoardManager;
        this.params = new AbsListView.LayoutParams(columnWidth, columnHeight);
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return tfeBoardManager.getBoard().numTiles();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(params);
        } else {
            imageView = (ImageView) convertView;
        }
        updateViews(imageView, position, context);
        return imageView;
    }

    private void updateViews(ImageView imageView, int position, Context context) {
        switch (tfeBoardManager.getBoard().tileGetter(position).getTileValue()) {
            case 2:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_1);
                break;
            case 4:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_2);
                break;
            case 8:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_3);
                break;
            case 16:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_4);
                break;
            case 32:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_5);
                break;
            case 64:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_6);
                break;
            case 128:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_7);
                break;
            case 256:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_8);
                break;
            case 512:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_9);
                break;
            case 1024:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_10);
                break;
            case 2048:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_11);
                break;
            default:
                imageView.setImageResource(R.drawable.twentyfortyeight_tile_empty);
        }
    }
}
