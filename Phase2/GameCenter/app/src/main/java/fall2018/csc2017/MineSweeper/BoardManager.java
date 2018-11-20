package fall2018.csc2017.MineSweeper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

import fall2018.csc2017.GameManager.GameManager;
import fall2018.csc2017.LaunchCentre.GameLaunchActivity;

public class BoardManager extends GameManager implements Serializable {
    private Board board;

    BoardManager(Board board, String username){
        super(username, "Minesweeper");
        this.board = board;
        autosaveInterval = 2;
    }

    public Board getBoard() {
        return board;
    }

    /***
     * Processes a tap on a block.
     * @param position The index of the block in board.blocks
     */
    void processClick(Context context, int position){
        if (board.getBlock(position).isMineType()){
            Toast.makeText(context, "You lost, loser", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < board.getNumCols(); i++) {
                for (int j = 0; j < board.getNumRows(); j++) {
                    board.getBlock(i*board.getNumCols()+j).setVisible();
                }
            }

            }
        else{
            board.revealLocal(position);
            if (board.solved()){
                Toast.makeText(context, "You won!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /***
     *  Processes a tap and hold on a clock
     * @param position The index of the block in board.blocks
     */
    void processLongClick(Context context, int position){
        if(!board.getBlock(position).isVisible()){
            board.getBlock(position).toggleFlagged();
        }
    }






}
