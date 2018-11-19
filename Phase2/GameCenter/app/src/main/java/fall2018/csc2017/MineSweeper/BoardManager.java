package fall2018.csc2017.MineSweeper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import fall2018.csc2017.LaunchCentre.GameLaunchActivity;

public class BoardManager {
    private Board board;

    public BoardManager(Board board){
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    /***
     * Processes a tap on a block.
     * @param position The index of the block in board.blocks
     */
    public void processClick(Context context, int position){
        if (board.getBlock(position).isMineType()){
            Toast.makeText(context, "You lost, loser", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < board.getNumCols(); i++) {
                for (int j = 0; j < board.getNumRows(); j++) {
                    board.getBlock(i*board.getNumCols()+j).setVisible();
                }
            }

            }
        else{
            Toast.makeText(context, "You haven't lost yet", Toast.LENGTH_SHORT).show();
            board.revealLocal(position);
        }
    }

    /***
     *  Processes a tap and hold on a clock
     * @param position The index of the block in board.blocks
     */
    public void processLongClick(Context context, int position){
        if(!board.getBlock(position).isVisible()){
            board.getBlock(position).toggleFlagged();
        }
    }

}
