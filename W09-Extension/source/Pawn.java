// class to represent all pawns on the board
public class Pawn extends ChessPiece {

    // attributes
    private static final char WHITECHARACTER ='♙';
    private static final char BLACKCHARACTER ='♟';
    private final char SYMBOL;

    // boolean to store whether pawn has moved yet
    private boolean hasMoved = false;

    // constructor
    public Pawn(int x, int y, boolean colour, Board board) {
        super(x, y, colour, board);

        // set appropriate symbol
        if(colour) SYMBOL = WHITECHARACTER;
        else SYMBOL = BLACKCHARACTER;

        this.setPieceValue(1);
    }

    // overriding methods from superclass
    @Override
    public boolean isMoveValid(int endX, int endY, boolean playerTurn) {
        // pawns can only move in one direction so first check what the colour of the pawn is
        // this int stores the direction of movement
        int move;
        if (playerTurn) move = -1;
        else move = 1;

        // there are three situations, either the piece moves forward 1 place, 2 places or 1 place diagonally.
        // first check for a forward move
        if (endX == this.getX()) {
            // now check that the piece is moving one place
            if (endY == this.getY() + move) {
                // check that the final space isn't occupied
                if (!this.getBoard().isPositionOccupied(endX, endY)) {
                    return true;
                }
                else return false;
            }
            // check if the piece is moving two places
            else if (endY == this.getY() + (2 * move) && !hasMoved) {
                // check there are no pieces in the way
                if (!this.getBoard().isPositionOccupied(endX, endY) && !this.getBoard().isPositionOccupied(endX, endY - move)) {
                    return true;
                }
                else return false;
            }
        }
        // now check if the move is diagonal to take a piece
        else if ((endX == this.getX() + 1 || endX == this.getX() - 1) && endY == this.getY() + move) {
            // now check if there is a piece in the final position
            if (this.getBoard().isPositionOccupied(endX, endY)) {
                // check if the piece is on the other side
                if (this.getBoard().findPieceAt(endX, endY).getColour() != this.getColour()) {
                    // finish this move
                    return true;
                }
                else return false;
            }
            else return false;
        }

        return false;

    }

    @Override
    public char getSymbol() {
        return this.SYMBOL;
    }

    public void hasMoved() {
        hasMoved = true;
    }
}
