
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.

public class Rook extends Piece {
   
    
    public Rook(boolean isWhite, String img_file) {
       super(isWhite, img_file);
       
    }
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece could move there legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    	int row  = start.getYNum();
    	int col = start.getXNum();
    	ArrayList<Square> move = new ArrayList<Square>();
    	// have it make sure that the piece will remain on the board
    	//goes down
    	for(int r = row + 1; r < 8; r++) {
    		
    		if (!board[r][col].isOccupied()){
    			move.add(board[r][col]);
    		}
    		else {
    			move.add(board[r][col]);
    			break;
    		}
    		
    	}
    	//up
    	for(int r = row-1; r >= 0; r--) {
    		if (!board[r][col].isOccupied()){
    			move.add(board[r][col]);
    		}
    		else {
    			move.add(board[r][col]);
    			break;
    		}
    	}
    	//right
    	for (int c = col + 1; c < 8; c++) {
    		if (!board[row][c].isOccupied()){
    			move.add(board[row][c]);
    		}
    		else {
    			move.add(board[row][c]);
    			break;
    		}
    	}
    	//left
    	for (int c = col - 1; c >= 0; c--) {
    		if (!board[row][c].isOccupied()){
    			move.add(board[row][c]);
    		}
    		else {
    			move.add(board[row][c]);
    			break;
    		}
    	}
    	return move;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	Square[][] board = b.getSquareArray();
    	int row  = start.getYNum();
    	int col = start.getXNum();
    	ArrayList<Square> move = new ArrayList<Square>();

    	// have it make sure that the piece will remain on the board
    	//goes down
    	for(int r = row + 1; r < 8; r++) {
    		
    		if (!board[r][col].isOccupied() || board[r][col].getOccupyingPiece().getColor() != color){
    			move.add(board[r][col]);
    			if(board[r][col].isOccupied()) {
    				break;
    			}
    		}
    		else {
    			break;
    		}
    		
    	}
    	//up
    	for(int r = row-1; r >= 0; r--) {
    		if (!board[r][col].isOccupied() || board[r][col].getOccupyingPiece().getColor() != color){
    			move.add(board[r][col]);
    			if(board[r][col].isOccupied()) {
    				break;
    			}
    		}
    		else {
    			break;
    		}
    	}
    	//right
    	for (int c = col + 1; c < 8; c++) {
    		if (!board[row][c].isOccupied() || board[row][c].getOccupyingPiece().getColor() != color){
    			move.add(board[row][c]);
    			if(board[row][c].isOccupied()) {
    				break;
    			}
    		}
    		else {
    			break;
    		}
    	}
    	//left
    	for (int c = col - 1; c >= 0; c--) {
    		if (!board[row][c].isOccupied() || board[row][c].getOccupyingPiece().getColor() != color){
    			move.add(board[row][c]);
    			if(board[row][c].isOccupied()) {
    				break;
    			}
    		}
    		else {
    			break;
    		}
    	}
    	return move;
    }
    public String toString() {
    	return "" + super.toString() + " rook";
    }


}