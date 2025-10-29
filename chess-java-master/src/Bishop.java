
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

//It's a Bishop!
//It can move diagonally in any direction and capture a piece on its way.

//you will need to implement two functions in this file.
public class Bishop extends Piece {

    
    public Bishop(boolean isWhite, String img_file) {
        super(isWhite,img_file);
    }      
    
    public String toString() {
    	if (super.getColor()){
    		return "A white Bishop";
    	}
    	return "A black Bishop";
    }
    // TO BE IMPLEMENTED!
    //Precondition: board is not null and contains at least one square. Start is not null and is inside board.
    //Poscondition: The list of all squares controlled by this piece is returned.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    	ArrayList<Square> moves = new ArrayList<Square>();
    	//records amount of squares that can be moved to
    	int size =0;
		int col=start.getXNum();
    	for (int row=start.getYNum()-1;col>0 && row>-1;row--) {
    		  col--;
    		  if(!(board[row][col].isOccupied())) {
    			  moves.add(board[row][col]);
    			  size++;
    		  }
    		  else{
    			      moves.add(board[row][col]);
    				  row=-1;
    				  size++;
    			  }
    		  }
    	
    	int coltwo=start.getYNum();
    	for (int row=start.getXNum()-1;coltwo<7 && row>-1;row--) {
    		  coltwo++;
    		  if(!(board[row][coltwo].isOccupied())) {
    			  moves.add(board[row][coltwo]);
    			  size++;
    		  }
    		  else {
    			      moves.add(board[row][coltwo]);
    				  row=-1;
    				  size++;
    			  }
    		  }
    	
    	int coltree=start.getXNum();
    	for (int row=start.getYNum()+1;coltree>0 && row<8;row++) {
    		  coltree--;
    		  if(!(board[row][coltree].isOccupied())) {
    			  moves.add(board[row][coltree]);
    			  size++;
    		  }
    		  else {
    			      moves.add(board[row][coltree]);
    				  row=8;
    				  size++;
    			  }
    		  }
    	
    	int colfour=start.getXNum();
    	for (int row=start.getYNum()+1;colfour<7 && row<8;row++) {
    		  colfour++;
    		  if(!(board[row][colfour].isOccupied())) {
    			  moves.add(board[row][colfour]);
    			  size++;
    		  }
    		  else  {
    			      moves.add(board[row][colfour]);
    				  row=8;
    				  size++;
    			  }
    		  }	
    	
    	return moves;
    }
    
    
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    //Precondition: Board is not null and contains an initialized double array of squares. Start is not null and is inside board.
    //Poscondition: The list of all squares that can be moved to is returned.
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> moves = new ArrayList<Square>();
    	//Allows Piece to move NorthWest
		int col=start.getXNum();
    	for (int row=start.getYNum()-1;col>0 && row>-1;row--) {
    		  col--;
    		  //checks if Square is open
    		  if(!(b.getSquareArray()[row][col].isOccupied())) {
    			  moves.add(b.getSquareArray()[row][col]);
    		  }
    		  //checks if square has same color piece on it
    		  else if(b.getSquareArray()[row][col].getOccupyingPiece().getColor()==super.getColor()) {
    			  row=-1;
    		  }
    		  else {
    			  
    			      moves.add(b.getSquareArray()[row][col]);
    				  row=-1;
    			  }
    		  }
    	//Allows Piece to move NorthEast
    	int coltwo=start.getXNum();
    	for (int row=start.getYNum()-1;coltwo<7 && row>-1;row--) {
    		  coltwo++;
    		  if(!(b.getSquareArray()[row][coltwo].isOccupied())) {
    			  moves.add(b.getSquareArray()[row][coltwo]);
    		  }
    		  else if(b.getSquareArray()[row][coltwo].getOccupyingPiece().getColor()==super.getColor()) {
    			  row=-1;
    		  }
    		  else {
    			      moves.add(b.getSquareArray()[row][coltwo]);
    				  row=-1;
    			  }
    		  }
    	//Allows Piece to move SouthWest
    	int coltree=start.getXNum();
    	for (int row=start.getYNum()+1;coltree>0 && row<8;row++) {
    		  coltree--;
    		  if(!(b.getSquareArray()[row][coltree].isOccupied())) {
    			  moves.add(b.getSquareArray()[row][coltree]);
    		  }
    		  else if(b.getSquareArray()[row][coltree].getOccupyingPiece().getColor()==super.getColor()) {
    			  row=8;
    		  }
    		  else {
    			      moves.add(b.getSquareArray()[row][coltree]);
    				  row=8;
    			  }
    		  }
    	//Allows Piece to move SouthEast
    	int colfour=start.getXNum();
    	for (int row=start.getYNum()+1;colfour<7 && row<8;row++) {
    		  colfour++;
    		  if(!(b.getSquareArray()[row][colfour].isOccupied())) {
    			  moves.add(b.getSquareArray()[row][colfour]);
    		  }
    		  else if(b.getSquareArray()[row][colfour].getOccupyingPiece().getColor()==super.getColor()) {
    			  row=8;
    		  }
    		  else {
    			      moves.add(b.getSquareArray()[row][colfour]);
    				  row=8;
    			  }
    		  }	
    	return moves;
    }
}