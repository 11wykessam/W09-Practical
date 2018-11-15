import java.util.ArrayList;

// computer artificial intelligence class
public class ComputerPlayer {

    // attributes
    private Board currentBoard;
    private int recursionDepth;
    private Game game;

    public ComputerPlayer(Board currentBoard, int recursionDepth, Game game) {
        this.currentBoard = currentBoard;
        this.recursionDepth = recursionDepth;
        this.game = game;
    }

    // makes a move
    public void makeMove() {
        ArrayList<Board> possibleMoves = currentBoard.getPossibleBoards(false);
        Board maxBoard = new Board(false, game);
        int maxBoardScore = -1000000000;
        for (Board board : possibleMoves) {
            int branchScore = getBranchScore(board, recursionDepth, true);
            if (branchScore > maxBoardScore) {
                maxBoard = board;
                maxBoardScore = branchScore;
            }
        }

        game.setBoard(maxBoard);
    }

    private int getBranchScore(Board board, int depth, boolean turn) {
        // if depth is at 0 then return the score of the board
        if (depth == 0) return board.getScore();
        // otherwise recursively return the highest score
        else {
            ArrayList<Board> possibleBoards = board.getPossibleBoards(false);
            int maxScore = 0;
            for (Board tempBoard : possibleBoards) {
                int score = getBranchScore(tempBoard, depth - 1, !turn);
                if (score > maxScore) maxScore = score;
            }
            return maxScore;
        }
    }

}
