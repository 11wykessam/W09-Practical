import javafx.scene.chart.PieChart;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

// class that stores the state of a board
public class Board implements Serializable {

    // List of pieces in game
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    // The following five constants were defined in the starter code (kt54)
    private static final int  DEFAULT_SIZE = 8;
    private static final char FREE         = '.';

    // store board size and characters in board
    private int boardsize;
    private char[][] board;

    // store the game that this is being executed from
    private Game game;

    // store the current player turn
    private boolean turn;

    // constructor for board blank board sets whether the board is clear or not on initialisation
    public Board(boolean blankBoard, Game game) {
        this.game = game;
        this.boardsize = DEFAULT_SIZE;

        board = new char[boardsize][boardsize];

        // Clear all playable fields
        for(int x=0; x<boardsize; x++)
            for(int y=0; y<boardsize; y++)
                board[x][y] = FREE;

        // check if the board is supposed to be blank
        if (!blankBoard) {
            // add pieces to the board
            // black pieces
            // rooks
            Rook blackRook1 = new Rook(0, 0, false, this);
            pieces.add(blackRook1);
            Rook blackRook2 = new Rook(boardsize - 1, 0, false, this);
            pieces.add(blackRook2);
            // bishops
            Bishop blackBishop1 = new Bishop(2, 0, false, this);
            pieces.add(blackBishop1);
            Bishop blackBishop2 = new Bishop(boardsize - 3, 0, false, this);
            pieces.add(blackBishop2);
            // queen
            Queen blackQueen = new Queen(3, 0, false,this);
            pieces.add(blackQueen);
            // king
            King blackKing = new King(4, 0, false, this);
            pieces.add(blackKing);
            // knights
            Knight blackKnight1 = new Knight(1, 0, false, this);
            pieces.add(blackKnight1);
            Knight blackKnight2 = new Knight(6, 0, false, this);
            pieces.add(blackKnight2);
            // pawns
            for (int i = 0; i < boardsize; i++) {
                Pawn blackPawn = new Pawn(i, 1, false, this);
                pieces.add(blackPawn);
            }

            // white pieces
            // rooks
            Rook whiteRook1 = new Rook(0, boardsize - 1, true, this);
            pieces.add(whiteRook1);
            Rook whiteRook2 = new Rook(boardsize - 1, boardsize - 1, true, this);
            pieces.add(whiteRook2);
            //bishops
            Bishop whiteBishop1 = new Bishop(2, boardsize - 1, true, this);
            pieces.add(whiteBishop1);
            Bishop whiteBishop2 = new Bishop(boardsize - 3, boardsize - 1, true ,this);
            pieces.add(whiteBishop2);
            // queen
            Queen whiteQueen = new Queen(3, boardsize - 1, true, this);
            pieces.add(whiteQueen);
            // king
            King whiteKing = new King(4, boardsize - 1, true, this);
            pieces.add(whiteKing);
            // knights
            Knight whiteKnight1 = new Knight(1, boardsize - 1, true, this);
            pieces.add(whiteKnight1);
            Knight whiteKnight2 = new Knight(6, boardsize - 1, true, this);
            pieces.add(whiteKnight2);
            // pawns
            for (int i = 0; i < boardsize; i++) {
                Pawn whitePawn = new Pawn(i, boardsize - 2, true, this);
                pieces.add(whitePawn);
            }
        }

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
                } else if(value >= '♔' && value <= '♟') {
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

                // make sure that piece is the correct colour
                if(pieceToMove.getColour() != playerColour) return false;

                // now check if the move is valid
                if(pieceToMove.isMoveValid(endX, endY, playerColour)) {

                    // if the piece is a pawn set it to has moved
                    if (pieceToMove instanceof Pawn) ((Pawn) pieceToMove).hasMoved();

                    // if the place is occupied remove it
                    if (isPositionOccupied(endX, endY)) removePieceAt(endX, endY);

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

    // method to move a piece from one position to another when given raw coords
    public boolean movePiece(int startX, int startY, int endX, int endY, boolean playerColour) {

        String startPos = "";
        String endPos = "";

        startPos += convertCoordLetter(startX);
        startPos += (8 - startY);

        endPos += convertCoordLetter(endX);
        endPos += (8 - endY);

        updateGameState();

        return movePiece(startPos, endPos, playerColour);
    }

    // sees if a player can make a move to kill a king
    public boolean getCheck(boolean playerColour) {

        // find all available boards
        ArrayList<Board> possibleBoards = this.getPossibleBoards(playerColour);
        // check if any have a king missing
        for (Board board : possibleBoards) {
            if (board.countKings() == 1) return true;
        }

        return false;

    }

    // method to get possible boards from the current state
    public ArrayList<Board> getPossibleBoards(boolean playerTurn) {

        // to avoid a concurrent modification exception create an array to represent the pieces on the board
        ChessPiece[] chessPieceArray = new ChessPiece[pieces.size()];
        for (int i = 0; i < chessPieceArray.length; i++) chessPieceArray[i] = pieces.get(i);

        // create an array list of boards to add to
        ArrayList<Board> possibleBoards = new ArrayList<>();

        // iterate through pieces
        for (ChessPiece currentPiece : chessPieceArray) {
            // check if that piece is of the correct colour
            if (currentPiece.getColour() == playerTurn) {

                // get all the possible moves for that piece
                ArrayList<int[]> possibleMoves = currentPiece.getPossibleMoves();

                // iterate through possible moves
                for (int[] coords : possibleMoves) {

                    // create a copy of the board and then move the appropriate piece to create a new board state
                    Board board = this.getCopy();
                    board.movePiece(currentPiece.getX(), currentPiece.getY(), coords[0], coords[1], playerTurn);

                    possibleBoards.add(board);

                }

            }

        }

        return possibleBoards;
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

    // method to convert int coordinate to letter
    private char convertCoordLetter(int x) {
        switch (x) {
            case 0:
                return 'a';
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            case 4:
                return 'e';
            case 5:
                return 'f';
            case 6:
                return 'g';
            case 7:
                return 'h';
        }
        return '.';
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

    // method returns the AI's "score" of the board
    public int getScore() {

        int score = 0;

        for (ChessPiece piece : pieces) {
            // if the piece is white subtract its score
            if (piece.getColour()) score -= piece.getPieceValue();
            else score += piece.getPieceValue();
        }

        return score;
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

    // returns copy of this board
    public Board getCopy() {
        Board board = new Board(true, game);
        for (ChessPiece piece : pieces) {
            if (piece instanceof Bishop) board.getPieces().add(new Bishop(piece.getX(), piece.getY(), piece.getColour(), board));
            else if (piece instanceof King) board.getPieces().add(new King(piece.getX(), piece.getY(), piece.getColour(), board));
            else if (piece instanceof Knight) board.getPieces().add(new Knight(piece.getX(), piece.getY(), piece.getColour(), board));
            else if (piece instanceof Pawn) board.getPieces().add(new Pawn(piece.getX(), piece.getY(), piece.getColour(), board));
            else if (piece instanceof Queen) board.getPieces().add(new Queen(piece.getX(), piece.getY(), piece.getColour(), board));
            else if (piece instanceof Rook) board.getPieces().add(new Rook(piece.getX(), piece.getY(), piece.getColour(), board));
        }
        return board;
    }

    // counts kings on the board
    public int countKings() {
        int count = 0;
        for (ChessPiece piece : pieces) {
            if (piece instanceof King) count++;
        }
        return count;
    }

    // if there is 1 king left get its colour
    public boolean getKingColour() {
        for (ChessPiece piece : pieces) {
            if (piece instanceof King) return piece.getColour();
        }
        return false;
    }

    // getters and setters
    public boolean getTurn() {
        return turn;
    }
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public ArrayList<ChessPiece> getPieces() {
        return this.pieces;
    }
    public void setPieces(ArrayList<ChessPiece> pieces) {
        this.pieces = pieces;
    }

}
