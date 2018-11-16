import java.util.ArrayList;

// computer artificial intelligence class
public class ComputerPlayer {

    // attributes
    private Board currentBoard;
    private int recursionDepth;
    private Game game;

    private static final int LARGENEGATIVE = -10000;
    private static final int LARGEPOSITIVE = 10000;

    public ComputerPlayer(Board currentBoard, int recursionDepth, Game game) {
        this.currentBoard = currentBoard;
        this.recursionDepth = recursionDepth;
        this.game = game;
    }

    // makes a move
    public void makeMove() {

        // create list of possible boards
        ArrayList<Board> possibleBoards = currentBoard.getPossibleBoards(false);

        // create value we are trying to maximise
        int maxScore = LARGENEGATIVE;
        // create board that will be reassigned
        Board maxBoard = new Board(true, game);

        // iterate through them
        for (Board board : possibleBoards) {
            if (getBranchScore(board, LARGENEGATIVE, LARGEPOSITIVE, recursionDepth, true) > maxScore) {
                maxScore = board.getScore();
                maxBoard = board;
            }
        }

        game.setBoard(maxBoard);
    }

    // min max algorithm for finding best move
    private int getBranchScore(Board board, int alpha, int beta, int depth, boolean maximisingPlayer) {

        // if depth is 0 then return current board value
        if (depth == 0) return board.getScore();

        // if it is black's turn they are trying to maximise
        if (maximisingPlayer) {

            // create a list of possible moves
            ArrayList<Board> possibleBoards = board.getPossibleBoards(false);

            // set the current max to be extremely negative so all moves are better than nothing
            int maxScore = LARGENEGATIVE;

            // iterate through all possible boards
            for (Board tempBoard : possibleBoards) {

                // get the board's score and if appropriate reassign maxScore
                int score = getBranchScore(tempBoard, alpha, beta, depth - 1, !maximisingPlayer);
                if (score > maxScore) maxScore = score;

                if (alpha < maxScore) alpha = maxScore;
                if (alpha >= beta) {
                    break;
                }
            }

            return maxScore;

        }

        // if it is white's turn try to minimise
        else {

            // create a list of possible moves
            ArrayList<Board> possibleBoards = board.getPossibleBoards(true);

            // set the current min to be extremely positive
            int minScore = LARGEPOSITIVE;

            // iterate through all possible boards
            for (Board tempBoard : possibleBoards) {

                // get the board's score and if appropriate reassign minScore
                int score = getBranchScore(tempBoard, alpha, beta, depth - 1, !maximisingPlayer);
                if (score < minScore) minScore = score;

                if (beta > minScore) beta = minScore;
                if (alpha >= beta) break;

            }

            return minScore;

        }

    }

}
