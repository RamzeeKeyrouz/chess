
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images


	// add all the connection variables oos, ios, socket....

	// Logical and graphical representations of board
	private Square[][] board;
	private final GameWindow g;

	// List of pieces and whether they are movable
//	public final ArrayList<Piece> Bpieces;
//	public final ArrayList<Piece> Wpieces;

	// contains true if it's white's turn.
	private boolean whiteTurn;

	// if the player is currently dragging a piece this variable contains it.
	private Piece currPiece;
	private Square fromMoveSquare;
	// used to keep track of the x/y coordinates of the mouse.
	private int currX;
	private int currY;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	public Board(GameWindow g) {
		board = null;
		// establish connection
		// get the localhost IP address, if server is running on some other IP, you need
		// to use that
		InetAddress host;
		try {
			host = InetAddress.getLocalHost();

			// establish socket connection to server
			Socket socket;
			// write to socket using ObjectOutputStream

			socket = new Socket(host.getHostName(), 9876);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			System.out.println("Sending request to Socket Server");

			// read the server response message
			
			 board = (Square[][])(ois.readObject());
			 System.out.println("I'm player "+((String)(ois.readObject())));
			 update();
			 whiteTurn = (boolean)(ois.readObject());
			Thread t = new Thread() {
				public void run() {
					while (socket.isConnected()) {
						try {
							board = (Square[][])(ois.readObject());
							whiteTurn = (Boolean)(ois.readObject());
							update();
							System.out.println("got a new boardstate");

						} catch (Exception e) {
							System.out.println("Error on connection with: " + ": " + e);
						}
					}
				}
			};
			t.start();
		} catch (Exception e) {

		}
		// after connection is set up

		this.g = g;

//		Bpieces = new ArrayList<Piece>();
//		Wpieces = new ArrayList<Piece>();
		setLayout(new GridLayout(8, 8, 0, 0));

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// TO BE IMPLEMENTED FIRST

		// for (.....)
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.
		
	

		this.setPreferredSize(new Dimension(400, 400));
		this.setMaximumSize(new Dimension(400, 400));
		this.setMinimumSize(this.getPreferredSize());
		this.setSize(new Dimension(400, 400));


	}

	public void update() {
		this.removeAll();
		for(Square[] row: board) {
			for(Square s: row) {
				this.add(s);
			}
		}
		repaint();
	}

	public Square[][] getSquareArray() {
		return this.board;
	}

	public boolean getTurn() {
		return whiteTurn;
	}

	public void setCurrPiece(Piece p) {
		this.currPiece = p;
	}

	public Piece getCurrPiece() {
		return this.currPiece;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(board != null) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Square sq = board[y][x];
					sq.paintComponent(g);
				}
			}
	
			if (currPiece != null) {
				if ((currPiece.getColor() && whiteTurn) || (!currPiece.getColor() && !whiteTurn)) {
					final Image i = currPiece.getImage();
					g.drawImage(i, currX, currY, null);
				}
			}
		}
	}

	// precondition - the board is initialized and contains a king of either color.
	// The boolean kingColor corresponds to the color of the king we wish to know
	// the status of.
	// postcondition - returns true of the king is in check and false otherwise.
	public boolean isInCheck(boolean kingColor) {
		// loop through all the squares in the board
		// ask each square do you contain a piece of the opposite color from kingColor
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// find all the squares that have a piece in them that is the opposite color of
				// kingColor
				if (board[i][j].isOccupied() && board[i][j].getOccupyingPiece().getColor() != kingColor) {
					for (Square s : board[i][j].getOccupyingPiece().getControlledSquares(board, board[i][j])) {
						if (s.getOccupyingPiece() instanceof King && s.getOccupyingPiece().getColor() == kingColor) {
							return true;
						}
					}
				}
			}
		}
		return false;
		// ask each of those pieces for their controlled squares
		// loop through the controlledSquares and ask them if they contain a king of
		// kingColor, if they do you're in check
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currX = e.getX();
		currY = e.getY();

		Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

		if (sq.isOccupied()) {
			currPiece = sq.getOccupyingPiece();
			System.out.println(currPiece);
			fromMoveSquare = sq;
			if (!currPiece.getColor() && whiteTurn) {
				currPiece = null;
				return;
			}
			if (currPiece.getColor() && !whiteTurn) {
				currPiece = null;
				return;
			}
			sq.setDisplay(false);
			// add this
			for (Square s : currPiece.getControlledSquares(board, sq)) {
				s.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.blue)); // mouse over "color" and import
																						// java.awt.*
			}
			for (Square s : currPiece.getLegalMoves(this, sq)) {
				s.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red)); // mouse over "color" and import
																						// java.awt.*
			}

		}
		repaint();
	}

	// TO BE IMPLEMENTED!
	// should move the piece to the desired location only if this is a legal move.
	// use the pieces "legal move" function to determine if this move is legal, then
	// complete it by
	// using the capture function if necessary and moving the new piece to it's new
	// board location.
	@Override
	public void mouseReleased(MouseEvent e) {

		for (Square[] sq : board) {
			for (Square s : sq) {
				s.setBorder(null);
			}
		}
		if (currPiece != null) {

			Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
			Piece temp = endSquare.getOccupyingPiece();

			// System.out.println("from square: " + fromMoveSquare.getYNum() + " " +
			// fromMoveSquare.getXNum());
			// System.out.println("end square: " + endSquare.getYNum() + " " +
			// endSquare.getXNum());
			// using currPiece
			ArrayList<Square> legalMove = currPiece.getLegalMoves(this, fromMoveSquare);
			if (legalMove.contains(endSquare)) {
				endSquare.put(currPiece);
				fromMoveSquare.removePiece();

				if (isInCheck(whiteTurn)) {
					// return everything to the way it was before you move the piece
					endSquare.put(temp);
					fromMoveSquare.put(currPiece);
				} else {
					whiteTurn = !whiteTurn;
					// send the new state to the server
					// you send the board
					// ((String) ois.readObject());
					try {
						oos.writeObject(board);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			fromMoveSquare.setDisplay(true);
			currPiece = null;
			endSquare.setDisplay(true);
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		currX = e.getX() - 24;
		currY = e.getY() - 24;

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}