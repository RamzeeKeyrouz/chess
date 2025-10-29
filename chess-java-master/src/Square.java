
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

//Please read the following class carefully! It represents a single chess board square and is what you'll be using
//to represent the chessboard.
@SuppressWarnings("serial")
public class Square extends JComponent {
	// a reference back to the board that stores this square.
	private Board b;

	// true for white, false for black.
	private final boolean color;
	// if there's a piece on the square this stores it. If there isn't this stores
	// null.
	private Piece occupyingPiece;
	// if desired you can use this to retain the piece where it is but make it
	// invisible to the user.
	// by default is true and can be safely ignored.
	private boolean dispPiece;

	// the coordinates of the square.
	private int xNum;
	private int yNum;

	public Square(boolean isWhite, int yNum, int xNum) {

		this.color = isWhite;
		this.dispPiece = true;
		this.xNum = xNum;
		this.yNum = yNum;

		this.setBorder(BorderFactory.createEmptyBorder());
	}

	public boolean getColor() {
		return this.color;
	}

	public Piece getOccupyingPiece() {
		return occupyingPiece;
	}

	public boolean isOccupied() {
		return (this.occupyingPiece != null);
	}

	public int getXNum() {
		return this.xNum;
	}

	public int getYNum() {
		return this.yNum;
	}

	public void setDisplay(boolean v) {
		this.dispPiece = v;
	}

	public void put(Piece p) {
		this.occupyingPiece = p;
	}

	public Piece removePiece() {
		Piece p = this.occupyingPiece;
		this.occupyingPiece = null;
		return p;
	}

	public void capture(Piece p) {

		this.occupyingPiece = p;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.color) {
			g.setColor(new Color(221, 192, 127));
		} else {
			g.setColor(new Color(101, 67, 33));
		}

		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

		if (occupyingPiece != null && dispPiece) {
			occupyingPiece.draw(g, this);
		}
	}

}
