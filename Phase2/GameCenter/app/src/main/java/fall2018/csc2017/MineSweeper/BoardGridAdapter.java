package fall2018.csc2017.MineSweeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.R;

class BoardGridAdapter extends BaseAdapter {
    private BoardManager boardManager;
    private Context context;
    private int columnWidth = 0;
    private int columnHeight = 0;
    static private int[] numberBlocks = {
            R.drawable.minesweeper_tile_empty,
            R.drawable.minesweeper_tile_1,
            R.drawable.minesweeper_tile_2,
            R.drawable.minesweeper_tile_3,
            R.drawable.minesweeper_tile_4,
            R.drawable.minesweeper_tile_5,
            R.drawable.minesweeper_tile_6,
            R.drawable.minesweeper_tile_7,
            R.drawable.minesweeper_tile_8
    };

    public BoardGridAdapter(BoardManager boardManager, Context context)
    {
        this.boardManager = boardManager;
        this.context = context;
    }



    @Override
    public Object getItem(int position) {
        return boardManager.getBoard().getBlock(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return boardManager.getBoard().getNumBlocks();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            button = new Button(context);
        }else{
            button = (Button) convertView;
        }
        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(columnWidth, columnHeight);
        button.setLayoutParams(params);

        initiateButton(button, position);

        return button;
    }

    private void initiateButton(Button button, final int position){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.processClick(context, position);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boardManager.processLongClick(context, position);
                return true;
            }
        });
        //TODO: set the button style
        Block block = boardManager.getBoard().getBlock(position);
        if (block.isVisible()){
            if (!block.isMineType()) {
                button.setBackgroundResource(numberBlocks[block.getNumMines()]);
            }
            else{
                button.setBackgroundResource(R.drawable.minesweeper_tile_mine);
            }
        }
        else{
            if (block.isFlagged()){
                button.setBackgroundResource(R.drawable.minesweeper_tile_flaged);
            }
            else {
                button.setBackgroundResource(R.drawable.minesweeper_tile_unknown_selector);
            }
        }
    }

    public void setColumnHeight(int columnHeight) {
        this.columnHeight = columnHeight;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }
}
