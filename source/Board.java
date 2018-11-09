import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

// class that stores the state of a board
public class Board {

    // List of pieces in game
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    // The following five constants were defined in the starter code (kt54)
    private static final int  DEFAULT_SIZE = 8;
    private static final char FREE         = '.';

    // The following constants are not needed for the basic solution, but some
    // people might find them useful for extensions
    private static final char WHITEKING    = '♔';
    private static final char BLACKKING    = '♚';
    private static final char WHITEQUEEN   = '♕';
    private static final char BLACKQUEEN   = '♛';
    private static final char WHITEKNIGHT  = '♘';
    private static final char BLACKKNIGHT  = '♞';
    private static final char WHITEPAWN    = '♙';
    private static final char BLACKPAWN    = '♟';

    private int boardsize;
    private char[][] board;

    // Default constructor was provided by the starter code. Extend as needed (kt54)
    public Board() {
        this.boardsize = DEFAULT_SIZE;

        board = new char[boardsize][boardsize];

        // Clear all playable fields
        for(int x=0; x<boardsize; x++)
            for(int y=0; y<boardsize; y++)
                board[x][y] = FREE;

        // add pieces to the board
        Rook blackRook1 = new Rook(0, 0, false, this);
        pieces.add(blackRook1);
        Rook blackRook2 = new Rook(boardsize - 1, 0, false, this);
        pieces.add(blackRook2);
        Bishop blackBishop1 = new Bishop(2, 0, false, this);
        pieces.add(blackBishop1);
        Bishop blackBishop2 = new Bishop(boardsize - 3, 0, false, this);
        pieces.add(blackBishop2);

        Rook whiteRook1 = new Rook(0, boardsize - 1, true, this);
        pieces.add(whiteRook1);
        Rook whiteRook2 = new Rook(boardsize - 1, boardsize - 1, true, this);
        pieces.add(whiteRook2);
        Bishop whiteBishop1 = new Bishop(2, boardsize - 1, true, this);
        pieces.add(whiteBishop1);
        Bishop whiteBishop2 = new Bishop(boardsize - 3, boardsize - 1, true ,this);
        pieces.add(whiteBishop2);

        // update characters
        updateGameState();
    }

    // updates the board with information from pieces list
    public void updateGameState() {
        // first set every slot to empty
        for (int x = 0; x < boardsize; x++) {
            for(int y = 0; y < boardsize; y++) {
                board[x][y] = FREE;
            }
        }

        // now iterate through persons list changing things accordingly
        for (ChessPiece chessPiece : pieces) {
            board[chessPiece.getX()][chessPiece.getY()] = chessPiece.getSymbol();
        }
    }

    // Prints the board. This method was provided with the starter code. Better not modify to ensure
    // output consistent with the autochecker (kt54)
    public void printBoard() {

        // Print the letters at the top
        System.out.print(" ");
        for(int x=0; x<boardsize; x++)
            System.out.print(" " + (char)(x + 'a'));
        System.out.println();

        for(int y=0; y<boardsize; y++)
        {
            // Print the numbers on the left side
            System.out.print(8-y);

            // Print the actual board fields
            for(int x=0; x<boardsize; x++) {
                System.out.print(" ");
                char value = board[x][y];
                if(value == FREE) {
                    System.out.print(".");
                } else if(value >= WHITEKING && value <= BLACKPAWN) {
                    System.out.print(value);
                } else {
                    System.out.print("X");
                }
            }
            // Print the numbers on the right side
            System.out.println(" " + (8-y));
        }

        // Print the letters at the bottom
        System.out.print(" ");
        for(int x=0; x<boardsize; x++)
            System.out.print(" " + (char)(x + 'a'));
        System.out.print("\n\n");
    }

    // method to move a piece from one position to another, returns a boolean telling the program whether the move was successful
    public boolean movePiece(String startPos, String endPos, boolean playerColour) {

        // declare integer variables for the positions
        int startX;
        int startY;

        int endX;
        int endY;

        // declare a variable to be used as the chessPiece being moved
        ChessPiece pieceToMove;

        // firstly check the input is valid and then convert to x and y positions
        if (startPos.length() == 2 && endPos.length() == 2) {

            // use the convert method to find x position corresponding to character entered and convert to array index number
            startX = convertLetterCoord(startPos.charAt(0));
            endX = convertLetterCoord(endPos.charAt(0));

            // make sure that the letters entered was a-h
            if (startX == -1 || endX == -1) return false;

            try {
                // change the number position entered and convert to array index number
                startY = 8 - Character.getNumericValue(startPos.charAt(1));
                endY = 8 - Character.getNumericValue(endPos.charAt(1));

                // make sure these values are within the correct range
                if (!(startY >= 0) || !(startY < 8)) return false;
                if (!(endY >= 0) || !(endY < 8)) return false;
            }
            catch (Exception e) {
                return false;
            }

            // now we have confirmed that the input was a valid position on the board the next check is confirming that the piece belongs to the player who's move it is
            // first check that there is a piece there
            if (isPositionOccupied(startX, startY)) {

                // check if that piece belongs to the currentPlayer
                // first find the piece
                pieceToMove = findPieceAt(startX, startY);

                // now check if the move is valid
                if(pieceToMove.isMoveValid(endX, endY, playerColour)) {

                    // we know the move to be valid so we must update the piece's position and the board's characters
                    pieceToMove.setX(endX);
                    pieceToMove.setY(endY);

                    updateGameState();

                }
                else return false;

            }
            else return false;

        }
        else {
            return false;
        }

        return true;

    }

    // method to check for a victory, returns 0 for black victory, 1 for white and -1 for no victory
    public int getWin() {
        boolean blackWin = true;
        boolean whiteWin = true;

        // iterate through pieces if there are any blacks left white has not win and vice versa for black
        for(ChessPiece piece: pieces) {
            if (piece.getColour()) blackWin = false;
            if (!piece.getColour()) whiteWin = false;
        }

        // return 0 for black victory
        if(blackWin) return 0;
        // return 1 for white victory
        else if(whiteWin) return 1;
        // for no victory return -1
        else return -1;


    }

    // method to convert char letter coordinate to int coordinate
    private int convertLetterCoord(char letter) {

        int value = -1;

        switch (letter) {
            case 'a' :
                value = 0;
                break;
            case 'b':
                value = 1;
                break;
            case 'c':
                value = 2;
                break;
            case 'd':
                value = 3;
                break;
            case 'e':
                value = 4;
                break;
            case 'f':
                value = 5;
                break;
            case 'g':
                value = 6;
                break;
            case 'h':
                value = 7;
                break;
            default:
                value = -1;
                break;
        }

        return value;

    }

    // method to check if a space is occupied
    public boolean isPositionOccupied(int x, int y) {
        if (board[x][y] != FREE) return true;
        else return false;
    }

    // method returns piece in a certain space providing there is one there
    public ChessPiece findPieceAt(int x, int y) {
        for (ChessPiece piece : pieces) {
            if(piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        return null;
    }

    // method removes any pieces in a slot
    public void removePieceAt(int x, int y) {
        ChessPiece pieceToRemove = null;
        for (ChessPiece chessPiece : pieces) {
            if(chessPiece.getX() == x && chessPiece.getY() == y) {
                pieceToRemove = chessPiece;
            }
        }
        try {
            pieces.remove(pieceToRemove);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}
