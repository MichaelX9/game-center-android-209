package fall2018.csc2017.MineSweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

class BoardGridAdapter extends BaseAdapter {
    private BoardManager boardManager;
    private Context context;
    private int columnWidth, columnHeight;

    public BoardGridAdapter(BoardManager boardManager, int columnWidth, int columnHeight, Context context)
    {
        this.boardManager = boardManager;
        this.context = context;
        this.columnWidth = columnWidth;
        this.columnHeight = columnHeight;
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
            initiateButton(button, position);
        }else{
            button = (Button) convertView;
        }
        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(columnWidth, columnHeight);
        button.setLayoutParams(params);

        return button;
    }

    private void initiateButton(Button button, final int position){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.processClick(position);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boardManager.processLongClick(position);
                return true;
            }
        });
        //TODO: set the button style
        if (boardManager.getBoard().getBlock(position).isMineType()){
            button.setBackgroundColor(Color.BLACK);
        }
    }

    public void setColumnHeight(int columnHeight) {
        this.columnHeight = columnHeight;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }
}
