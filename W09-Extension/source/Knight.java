// class that represents all knights on the board
public class Knight extends ChessPiece {

    // attributes
    private static final char WHITECHARACTER ='♘';
    private static final char BLACKCHARACTER ='♞';
    private final char SYMBOL;

    public Knight(int x, int y, boolean colour, Board board) {
        super(x, y, colour, board);

        // set appropriate symbol
        if(colour) SYMBOL = WHITECHARACTER;
        else SYMBOL = BLACKCHARACTER;

        this.setPieceValue(3);
    }

    @Override
    public boolean isMoveValid(int endX, int endY, boolean playerTurn) {
        // first check the end location is in one of the valid end positions
        if (((endX == this.getX() - 1 || endX == this.getX() + 1) && (endY == this.getY() - 2 || endY == this.getY() + 2)) ^
                ((endX == this.getX() - 2 || endX == this.getX() + 2) && (endY == this.getY() - 1 || endY == this.getY() + 1))) {

            // now check the final point is not occupied by your own piece and take any pieces on the opposite side
            if (this.getBoard().isPositionOccupied(endX, endY)) {
                ChessPiece piece = this.getBoard().findPieceAt(endX, endY);
                // check if that piece is of the opposite colour to the player
                if(this.getBoard().findPieceAt(endX, endY).getColour() == playerTurn) {
                    return false;
                }

            }

            return true;
        }
        else return false;
    }

    @Override
    public char getSymbol() {
        return this.SYMBOL;
    }
}
