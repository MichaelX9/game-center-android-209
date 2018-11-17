package fall2018.csc2017.MineSweeper;

public class BoardManager {
    private Board board;

    public BoardManager(Board board){
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    /***
     * To process a tap on a block.
     * @param position The index of the block in board.blocks
     */
    public void processClick(int position){
        //TODO
    }

    /***
     *  To process a tap and hold on a clock
     * @param position The index of the block in board.blocks
     */
    public void processLongClick(int position){
        //TODO
    }

}
