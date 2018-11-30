package fall2018.csc2017.mine_sweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;

import fall2018.csc2017.R;

/***
 * The adapter class to help display the minesweeper board to the gridview.
 */
class BoardGridAdapter extends BaseAdapter {
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
    private BoardManager boardManager;
    private Context context;
    private AbsListView.LayoutParams params;

    BoardGridAdapter(BoardManager boardManager, int columnWidth, int columnHeight, Context context) {
        this.boardManager = boardManager;
        this.context = context;
        this.params = new AbsListView.LayoutParams(columnWidth, columnHeight);
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
        button = new Button(context);
        initiateButton(button, position);

        updateButton(button, position);

        return button;

    }

    /***
     * Sets up button listeners and parameters.
     * @param button the button to be set up
     * @param position the position of the block corresponding to the button
     */
    private void initiateButton(Button button, final int position) {

        button.setLayoutParams(params);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boardManager.getBoard().solved()) {
                    boardManager.processClick(context, position);
                }
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!boardManager.getBoard().solved()) {
                    boardManager.processLongClick(position);
                }
                return true;
            }
        });

    }

    /***
     * Update button image.
     * @param button the button to be updated
     * @param position the position of the block corresponding to the button
     */
    private void updateButton(Button button, int position) {
        Block block = boardManager.getBoard().getBlock(position);
        if (block.isVisible()) {
            if (!block.isMineType()) {
                button.setBackgroundResource(numberBlocks[block.getNumMines()]);
            } else {
                button.setBackgroundResource(R.drawable.minesweeper_tile_mine);
            }
        } else {
            if (block.isFlagged()) {
                button.setBackgroundResource(R.drawable.minesweeper_tile_flaged);
            } else {
                button.setBackgroundResource(R.drawable.minesweeper_tile_unknown_selector);
            }
        }
    }
}
