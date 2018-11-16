import java.io.Serializable;
import java.util.ArrayList;

// abstract class that holds the attributes and methods that must be shared by all chess pieces
public abstract class ChessPiece implements Serializable {

    // attributes all pieces must have
    private int x;
    private int y;
    private boolean colour;
    private Board board;

    // store the value of that piece for the AI to work out best move
    private int pieceValue;

    // constructor
    public ChessPiece(int x, int y, boolean colour, Board board) {
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.board = board;
    }

    // abstract methods, these are methods that all ChessPiece subclasses must have

    // method that checks if a move is valid
    public abstract boolean isMoveValid(int endX, int endY, boolean playerTurn);

    // method that returns a list of 2D coords
    public ArrayList<int[]> getPossibleMoves() {
        ArrayList<int[]> possibleMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isMoveValid(i, j, this.getColour())) {
                    possibleMoves.add(new int[] {i, j});
                }
            }
        }

        return possibleMoves;
    }

    // print possible moves (used for debugging)
    public void printPossibleMoves(boolean playerTurn) {
        ArrayList<int[]> possibleMoves = this.getPossibleMoves();

        for (int[] coords : possibleMoves) {
            System.out.println("[" + coords[0] + ", " + coords[1] + "]");
        }
    }

    // method to get char that represents piece
    public abstract char getSymbol();

    // getters and setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
        if (this instanceof Pawn) {
            ((Pawn) this).setHasMoved();
        }
    }

    public boolean getColour() {
        return colour;
    }
    public void setColour(boolean colour) {
        this.colour = colour;
    }

    public Board getBoard() {
        return this.board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public int getPieceValue() {
        return pieceValue;
    }
    public void setPieceValue(int pieceValue) {
        this.pieceValue = pieceValue;
    }
}
