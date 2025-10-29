import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

/* Creator: Aiden Holdren
 * 
 * Pawn
 * Can move one space forward but cannot capture that square. Can move two spaces forward on the first turn.
 * Can take pieces diagonally forward one space.
 */

public class Pawn extends Piece {
	
	public Pawn(boolean color, String img_file) {
		super(color, img_file);
	}
	
    /*
     * Precondition: Board is size 8 by 8
     * Postcondition: Returns a list of every square that the piece could attack legally if the opposing king were there
     */
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    	ArrayList<Square> moves = new ArrayList<Square>();
    	
    	// attacks diagonally one space
    	
    	if(start.getXNum() > 0) {
    		if(start.getYNum() > 0 && super.getColor())
    			moves.add(board[start.getYNum()-1][start.getXNum()-1]);
    		if(start.getYNum() < 7 && !super.getColor()) 
    			moves.add(board[start.getYNum()+1][start.getXNum()-1]);
    	}
    	if(start.getXNum() < 7) {
    		if(start.getYNum() > 0 && super.getColor())
    			moves.add(board[start.getYNum()-1][start.getXNum()+1]);
    		if(start.getYNum() < 7 && !super.getColor())
    			moves.add(board[start.getYNum()+1][start.getXNum()+1]);
    	}
    	
    	

    	return moves;
    }
    
    public String toString() {
    	return super.toString() + " pawn";
    }

    /*
     * Precondition: start is in b
     * Postcondition: Returns an arraylist of the squares the piece could move to if at it were at the starting square
     */
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	Piece thisPiece = start.getOccupyingPiece();
    	ArrayList<Square> moves = new ArrayList<Square>();
    	int r = start.getYNum();
    	int c = start.getXNum();
    	Square currSquare = b.getSquareArray()[r][c];
    	
    	// moves one towards the other side, or two if at starting row, cannot take

    	if(thisPiece.getColor() && r > 0) {
    		r--;
    		currSquare = b.getSquareArray()[r][c];
    		if(!currSquare.isOccupied()) {
    			moves.add(currSquare);
    			if(start.getYNum() == 6) {
    				r--;
            		currSquare = b.getSquareArray()[r][c];
            		if(!currSquare.isOccupied()) {
            			moves.add(currSquare);
            		}
    			}
    		}
    	}
    	if(!thisPiece.getColor() && r < 7) {
    		r++;
    		currSquare = b.getSquareArray()[r][c];
    		if(!currSquare.isOccupied()) {
    			moves.add(currSquare);
    			if(start.getYNum() == 1) {
        			moves.add(currSquare);
        			r++;
            		currSquare = b.getSquareArray()[r][c];
            		if(!currSquare.isOccupied()) {
            			moves.add(currSquare);
            		}
    			}
    		}
    	}
    	
    	// moves diagonally forward one space, only if that space is occupied
    	r = start.getYNum();
    	c = start.getXNum();
    	currSquare = b.getSquareArray()[r][c];
    	
    	if(thisPiece.getColor() && r > 0 && c > 0) {
    		r--;
    		c--;
    		currSquare = b.getSquareArray()[r][c];
    		if((currSquare.isOccupied() && currSquare.getOccupyingPiece().getColor() != thisPiece.getColor())) {
    			moves.add(currSquare);
    		}
    	}
    	if(!thisPiece.getColor() && r < 7 && c > 0) {
    		r++;
    		c--;
    		currSquare = b.getSquareArray()[r][c];
    		if((currSquare.isOccupied() && currSquare.getOccupyingPiece().getColor() != thisPiece.getColor())) {
    			moves.add(currSquare);
    		}
    	}
    	
    	r = start.getYNum();
    	c = start.getXNum();
    	currSquare = b.getSquareArray()[r][c];
    	
    	if(thisPiece.getColor() && r > 0 && c < 7) {
    		r--;
    		c++;
    		currSquare = b.getSquareArray()[r][c];
    		if((currSquare.isOccupied() && currSquare.getOccupyingPiece().getColor() != thisPiece.getColor()) ) {
    			moves.add(currSquare);
    		}
    	}
    	if(!thisPiece.getColor() && r < 7 && c < 7) {
    		r++;
    		c++;
    		currSquare = b.getSquareArray()[r][c];
    		if((currSquare.isOccupied() && currSquare.getOccupyingPiece().getColor() != thisPiece.getColor())) {
    			moves.add(currSquare);
    		}
    	}
    	
    	return moves;
    }
}
