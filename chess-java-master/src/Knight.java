

//Name: Carissa Chen

//Piece name: Knight
//brief description: it moves two squares vertically and one square horizontally, or two squares horizontally and one square vertically, jumping over other pieces.


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Knight extends Piece {
       
    public Knight(boolean isWhite, String img_file) {
       super(isWhite, img_file);
    }
   
    //precondition: none
    //postcondition: returns a string that represents the knight piece, including its color and type
    public String toString() 
    {
    	return "A " + super.toString() + "Knight";
    }
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece could move there legally.
    
    //precondition:takes a 2D array representing the chess board and a starting square as input. the 2D array cannot be null and the start square must be on the board.
    //postcondition: returns an ArrayList of squares that are controlled by the knight piece
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    	ArrayList<Square> moves = new ArrayList<Square>();
    	
    	int row = start.getYNum();
    	int col = start.getXNum();

    	if ((row-2) < 8 && (row-2) > -1) 
    	{
    		if((col-1) < 8 && (col-1) > -1) 
    		{
    			moves.add(board[row-2][col-1]);
    		}
    	}
    	
    	if ((row+2) < 8 && (row+2) > -1) 
    	{
    		if((col-1) < 8 && (col-1) > -1) 
    		{		
    			moves.add(board[row+2][col-1]);
    		}
    	}
    	
    	if ((row-2) < 8 && (row-2) > -1) 
    	{
    		if((col+1) < 8 && (col+1) > -1) 
    		{
    			moves.add(board[row-2][col+1]);
    		}
    	}
    	
    	if ((row+2) < 8 && (row+2) > -1) 
    	{
    		if((col+1) < 8 && (col+1) > -1) 
    		{
    			moves.add(board[row+2][col+1]);
    		}
    	}
    	
    	if ((row-1) < 8 && (row-1) > -1) 
    	{
    		if((col+2) < 8 && (col+2) > -1) 
    		{
    			moves.add(board[row-1][col+2]);
    		}
    	}
    	
      	if ((row-1) < 8 && (row-1) > -1) 
    	{
    		if((col-2) < 8 && (col-2) > -1) 
    		{
    				moves.add(board[row-1][col-2]);
    		}
    	}
      	
      	if ((row+1) < 8 && (row+1) > -1) 
    	{
    		if((col+2) < 8 && (col+2) > -1) 
    		{
    			moves.add(board[row+1][col+2]);
    		}
    	}
    	
      	if ((row+1) < 8 && (row+1) > -1) 
    	{
    		if((col-2) < 8 && (col-2) > -1) 
    		{
    			moves.add(board[row+1][col-2]);
    		}
    	}
    	
    	return moves;
    	}
    	//code from getlegalMoves slightly different variables

    
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    
    //a white knight moves two spaces either left, right, up, or down, and then one square perpendicular after. if there is no piece in the square, the knight can move there. if there is a black piece in the square,
    //the knight
    
    //precondition: takes a Board object and a starting square as input. the Board object cannot be null, the square array inside of the Board object cannot be null, and the start square must be on the board.
    //postcondition: returns an ArrayList of squares that the knight piece can legally move to while considering other pieces and if the squares are on the board
    public ArrayList<Square> getLegalMoves(Board b, Square start)
    {
    	ArrayList<Square> legalMoves = new ArrayList<Square>();
    	Square[][] board = b.getSquareArray();
    	int row = start.getYNum();
    	int col = start.getXNum();

    	if ((row-2) < 8 && (row-2) > -1) 
    	{
    		if((col-1) < 8 && (col-1) > -1) 
    		{
    			if((!board[row-2][col-1].isOccupied()))
    			{
    				
    			legalMoves.add(board[row-2][col-1]);
    			
    			} else if (board[row-2][col-1].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row-2][col-1]);
    				}
    		}
    	}
    	
    	if ((row+2) < 8 && (row+2) > -1) 
    	{
    		if((col-1) < 8 && (col-1) > -1) 
    		{
    			if((!board[row+2][col-1].isOccupied()))
    			{
    				
    			legalMoves.add(board[row+2][col-1]);
    			
    			} else if (board[row+2][col-1].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row+2][col-1]);
    				}
    		}
    	}
    	
    	if ((row-2) < 8 && (row-2) > -1) 
    	{
    		if((col+1) < 8 && (col+1) > -1) 
    		{
    			if((!board[row-2][col+1].isOccupied()))
    			{
    				
    			legalMoves.add(board[row-2][col+1]);
    			
    			} else if (board[row-2][col+1].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row-2][col+1]);
    				}
    		}
    	}
    	
    	if ((row+2) < 8 && (row+2) > -1) 
    	{
    		if((col+1) < 8 && (col+1) > -1) 
    		{
    			if((!board[row+2][col+1].isOccupied()))
    			{
    				
    			legalMoves.add(board[row+2][col+1]);
    			
    			} else if (board[row+2][col+1].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row+2][col+1]);
    				}
    		}
    	}
    	
    	if ((row-1) < 8 && (row-1) > -1) 
    	{
    		if((col+2) < 8 && (col+2) > -1) 
    		{
    			if((!board[row-1][col+2].isOccupied()))
    			{
    				
    			legalMoves.add(board[row-1][col+2]);
    			
    			} else if (board[row-1][col+2].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row-1][col+2]);
    				}
    		}
    	}
    	
      	if ((row-1) < 8 && (row-1) > -1) 
    	{
    		if((col-2) < 8 && (col-2) > -1) 
    		{
    			if((!board[row-1][col-2].isOccupied()))
    			{
    				
    			legalMoves.add(board[row-1][col-2]);
    			
    			} else if (board[row-1][col-2].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row-1][col-2]);
    				}
    		}
    	}
      	
      	if ((row+1) < 8 && (row+1) > -1) 
    	{
    		if((col+2) < 8 && (col+2) > -1) 
    		{
    			if((!board[row+1][col+2].isOccupied()))
    			{
    				
    			legalMoves.add(board[row+1][col+2]);
    			
    			} else if (board[row+1][col+2].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row+1][col+2]);
    				}
    		}
    	}
    	
      	if ((row+1) < 8 && (row+1) > -1) 
    	{
    		if((col-2) < 8 && (col-2) > -1) 
    		{
    			if((!board[row+1][col-2].isOccupied()))
    			{
    				
    			legalMoves.add(board[row+1][col-2]);
    			
    			} else if (board[row+1][col-2].getOccupyingPiece().getColor() == !this.getColor())
    				{
    				legalMoves.add(board[row+1][col-2]);
    				}
    		}
    	}
    	
    		return legalMoves;
    	}
    
    
   }
    	