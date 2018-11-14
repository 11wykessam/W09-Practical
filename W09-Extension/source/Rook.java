// class that represents all rook pieces on the board
public class Rook extends ChessPiece{

    // attributes
    private static final char WHITECHARACTER ='♖';
    private static final char BLACKCHARACTER ='♜';
    private final char SYMBOL;

    // constructor
    public Rook(int x, int y, boolean colour, Board board) {
        super(x, y, colour, board);

        // set the appropriate colour symbol
        if (colour) SYMBOL = WHITECHARACTER;
        else SYMBOL = BLACKCHARACTER;

        this.setPieceValue(5);
    }

    // override abstract methods
    // check if a given final destination is reachable by the piece
    @Override
    public boolean isMoveValid(int endX, int endY, boolean playerTurn) {
        // firstly check that the move is either in the same row or column as the current position and the piece isn't moving nowhere
        if(endX == this.getX() ^ endY == this.getY()) {

            // now check there are no pieces in the way
            // first determine which direction
            if(endX > this.getX()) {
                // piece is moving to the right so check all slots to right
                for (int i = this.getX() + 1; i < endX; i++) {
                    if(this.getBoard().isPositionOccupied(i, this.getY()))  return false;
                }
            }
            else if(endX < this.getX()) {
                // the piece is moving to the left
                for(int i = this.getX() - 1; i > endX; i--) {
                    if(this.getBoard().isPositionOccupied(i, this.getY())) return false;
                }
            }
            else if(endY > this.getY()) {
                // the piece is moving down
                for(int i = this.getY() + 1; i < endY; i++) {
                    if(this.getBoard().isPositionOccupied(this.getX(), i)) return false;
                }
            }
            else if(endY < this.getY()) {
                // the piece is moving up
                for(int i = this.getY() - 1; i > endY; i--) {
                    if(this.getBoard().isPositionOccupied(this.getX(), i)) return false;
                }
            }

            // now check if there is a piece in the final destination
            if (this.getBoard().isPositionOccupied(endX, endY)) {
                // check if that piece is of the opposite colour to the player
                if(this.getBoard().findPieceAt(endX, endY).getColour() == playerTurn) {
                    return false;
                }

            }

            return true;
        }
        else return false;
    }

    // return the symbol corresponding to this piece
    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
