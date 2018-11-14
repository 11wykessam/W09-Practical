import java.util.ArrayList;

// class that stores the state of the game
public class Game {

    // The following five constants were defined in the starter code (kt54)
    private static String WHITEPLAYS_MSG = "White plays. Enter move:";
    private static String BLACKPLAYS_MSG = "Black plays. Enter move:";
    private static String ILLEGALMOVE_MSG = "Illegal move!";

    private static String WHITECHECK = "White Check";
    private static String BLACKCHECK = "Black Check";
    private static String WHITECHECKMATE = "White Checkmate!";
    private static String BLACKCHECKMATE = "Black Checkmate!";

    private Board gameBoard;
    private boolean turn = true;

    // Minimal constructor. Expand as needed (kt54)
    public Game() {
        gameBoard = new Board(false);
    }

    // Build on this method to implement game logic.
    public void play() {

        // initialise variables
        EasyIn2 reader = new EasyIn2();

        gameBoard = new Board(false);

        gameBoard.setTurn(true);

        boolean done = false;

        // boolean stores whether AI playing or not
        boolean AI;

        // prompt the user to see if they want to play an AI
        while(true) {
            System.out.println("Would you like to play (1) AI or (2) Another Player");

            int input = reader.getInt();

            if (input == 1) {
                AI = true;
                break;
            }
            else if (input == 2) {
                AI = false;
                break;
            }


        }

        while (!done) {

            // create computer player
            ComputerPlayer computerPlayer = new ComputerPlayer(gameBoard, 3, this);

            // print the state of the board
            gameBoard.printBoard();

            // check who's turn it is and output valid message
            if(turn) System.out.println(WHITEPLAYS_MSG);
            else System.out.println(BLACKPLAYS_MSG);

            String pos1 = reader.getString();
            // check if the user types quit
            if(pos1.equals("quit")) {
                break;
            }
            String pos2 = reader.getString();
            // check if user types quit
            if(pos2.equals("quit")) {
                break;
            }

            // attempt to move piece the method will return false if the move is invalid and an error will be printed
            if (!gameBoard.movePiece(pos1, pos2, turn)) {
                System.out.println(ILLEGALMOVE_MSG);
                // continue without changing player turn
                continue;
            }

            // check for a win
            done = isDone();

            // if the AI is playing have them take their turn
            if (AI) {

                computerPlayer.makeMove();

                // check for a win
                done = isDone();


            }

            else {
                // change who's turn it is
                turn = !turn;
                gameBoard.setTurn(turn);
            }

        }
    }

    private boolean isDone() {
        if (gameBoard.getCheck(true)) {
            // there is definitely a check now see if black can make a move to stop this
            // first generate all black moves
            ArrayList<Board> possibleBlackMoves = gameBoard.getPossibleBoards(false);
            // see if any of these boards are not check for white
            boolean checkMate = true;
            for (Board board : possibleBlackMoves) {
                if(!board.getCheck(true)) checkMate = false;
            }

            if (checkMate) {
                System.out.println(WHITECHECKMATE);
                return true;
            }
            else System.out.println(WHITECHECK);
        }
        else if (gameBoard.getCheck(false)) {
            // there is definitely a check now see if black can make a move to stop this
            // first generate all white moves
            ArrayList<Board> possibleWhiteMoves = gameBoard.getPossibleBoards(true);
            // see if any of these boards are not check for white
            boolean checkMate = true;
            for (Board board : possibleWhiteMoves) {
                if(!board.getCheck(false)) checkMate = false;
            }

            if (checkMate) {
                System.out.println(BLACKCHECKMATE);
                return true;
            }
            else System.out.println(BLACKCHECK);
        }
        return false;
    }

    // set board
    public void setBoard(Board board) {
        this.gameBoard = board;
    }

}