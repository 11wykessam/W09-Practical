public abstract class ChessPiece {

    // attributes all pieces must have
    private int x;
    private int y;
    private boolean colour;
    private Board board;

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
}
