// class that represents all queen pieces in a game of chess
public class Queen extends ChessPiece {

    // attributes
    private static final char WHITECHARACTER ='♕';
    private static final char BLACKCHARACTER ='♛';
    private final char SYMBOL;

    // constructor
    public Queen(int x, int y, boolean colour, Board board) {
        super(x, y, colour, board);

        // set the symbol to be black or white
        if(colour) SYMBOL = WHITECHARACTER;
        else SYMBOL = BLACKCHARACTER;

        this.setPieceValue(9);

    }

    // overriding methods from superclass
    // method that tests if move is valid, combines code from rook and bishop
    @Override
    public boolean isMoveValid(int endX, int endY, boolean playerTurn) {
        // create a rook and bishop object that have the same position and check if the move would be valid for either of them
        Rook testRook = new Rook(this.getX(), this.getY(), this.getColour(), this.getBoard());
        Bishop testBishop = new Bishop(this.getX(), this.getY(), this.getColour(), this.getBoard());
        return testRook.isMoveValid(endX, endY, playerTurn) || testBishop.isMoveValid(endX, endY, playerTurn);
    }

    // getters and setters
    @Override
    public char getSymbol() {
        return this.SYMBOL;
    }
}
