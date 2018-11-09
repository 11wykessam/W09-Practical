
// class that stores the
public class Game {

    // The following five constants were defined in the starter code (kt54)
    private static String WHITEPLAYS_MSG = "White plays. Enter move:";
    private static String BLACKPLAYS_MSG = "Black plays. Enter move:";
    private static String ILLEGALMOVE_MSG = "Illegal move!";
    private static String WHITEWINS_MSG = "White wins!";
    private static String BLACKWINS_MSG = "Black wins!";

    private Board gameBoard;
    private boolean turn = true;

    // Minimal constructor. Expand as needed (kt54)
    public Game() {
        gameBoard = new Board();
    }

    // Build on this method to implement game logic.
    public void play() {

        // initialise variables
        EasyIn2 reader = new EasyIn2();

        gameBoard = new Board();

        boolean done = false;

        while (!done) {
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
            int winState = gameBoard.getWin();
            switch (winState) {
                // black win
                case 0:
                    done = true;
                    System.out.println(BLACKWINS_MSG);
                    break;
                // white win
                case 1:
                    done = true;
                    System.out.println(WHITEWINS_MSG);
                    break;
            }

            // change who's turn it is
            turn = !turn;
        }
    }

}