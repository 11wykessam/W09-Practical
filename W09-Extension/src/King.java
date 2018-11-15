// class that represents all kings on the board
public class King extends ChessPiece {

    // attributes
    private static final char WHITECHARACTER ='♔';
    private static final char BLACKCHARACTER ='♚';
    private final char SYMBOL;

    public King(int x, int y, boolean colour, Board board) {
        super(x, y, colour, board);

        // assign appropriate symbol
        if(colour) SYMBOL = WHITECHARACTER;
        else SYMBOL = BLACKCHARACTER;

        this.setPieceValue(1000000000);
    }

    // methods that override methods from abstract classes
    // check if a given move is valid
    @Override
    public boolean isMoveValid(int endX, int endY, boolean playerTurn) {
        // first check whether the end position is within a 3x3 square around the piece
        if (endX <= this.getX() + 1 && endX >= this.getX() - 1 && endY <= this.getY() + 1 && endY >= this.getY() - 1) {
            // next check that the piece is actually moving
            if(!(endX == this.getX() && endY == this.getY())) {
                // now check if there is a piece in the final destination
                if (this.getBoard().isPositionOccupied(endX, endY)) {
                    // check if that piece is of the opposite colour to the player
                    if(this.getBoard().findPieceAt(endX, endY).getColour() == playerTurn) {
                        return false;
                    }

                }

                return true;
            }
            return false;
        }
        else return false;
    }

    // getters and setters
    @Override
    public char getSymbol() {
        return this.SYMBOL;
    }
}
