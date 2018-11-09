public class Bishop extends ChessPiece{

    // attributes
    private static final char WHITECHARACTER ='♗';
    private static final char BLACKCHARACTER ='♝';
    private final char SYMBOL;

    // constructor
    public Bishop(int x, int y, boolean colour, Board board) {
        super(x, y, colour, board);

        if(colour) SYMBOL = WHITECHARACTER;
        else SYMBOL = BLACKCHARACTER;
    }

    // override abstract methods
    // check if a move is valid when given end position
    @Override
    public boolean isMoveValid(int endX, int endY, boolean playerTurn) {
        // firstly check that the two points are in the same diagonal and the piece is actually being moved
        if((endX - this.getX() == endY - this.getY() || endX - this.getX() == this.getY() - endY)
                && !(endX == this.getX() && endY == this.getY())) {

            // now check there are no pieces in the way
            // the if statements determine which direction the piece is moving in
            if (endX > this.getX() && endY > this.getY()) {
                // this code is executed if the piece is moving down to the right
                // iterate in a diagonal checking if there are any pieces in the way
                for (int i = 1; i < endX - this.getX(); i++) {
                    if(this.getBoard().isPositionOccupied(this.getX() + i, this.getY() + i)) return false;
                }
            }
            if(endX > this.getX() && endY < this.getY()) {
                // this code is executed if the piece is moving up to the right
                // iterate in a diagonal checking if there are any pieces in the way
                for(int i = 1; i < endX - this.getX(); i++) {
                    if(this.getBoard().isPositionOccupied(this.getX() + i, this.getY() - i)) return false;
                }
            }
            if(endX < this.getX() && endY > this.getY()) {
                // this code is executed if the piece is moving down and to the left
                // iterate in a diagonal checking if there are any pieces in the way
                for(int i = 1; i < endY - this.getY(); i++) {
                    if(this.getBoard().isPositionOccupied(this.getX() - i, this.getY() + i)) return false;
                }
            }
            if(endX < this.getX() && endY < this.getY()) {
                // this code is executed if the piece is moving up and to the left
                // iterate in a diagonal checking if there are any pieces in the way
                for(int i = 1; i < this.getY() - endY; i++) {
                    if(this.getBoard().isPositionOccupied(this.getX() - i, this.getY() - 1)) return false;
                }
            }


            // now check if there is a piece in the final destination
            if (this.getBoard().isPositionOccupied(endX, endY)) {
                // check if that piece is of the opposite colour to the player
                if(this.getBoard().findPieceAt(endX, endY).getColour() != playerTurn) {
                    // remove the piece at that slot
                    this.getBoard().removePieceAt(endX, endY);
                }
                else {
                    // if spot already occupied by this player's piece then move is invalid
                    return false;
                }

            }

            return true;
        }
        else return false;
    }

    // get the character that represents this piece
    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
