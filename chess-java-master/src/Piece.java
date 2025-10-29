import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Piece implements Serializable{
	
	protected boolean color;
	private String img_file;
	private transient BufferedImage img;

	public Piece(boolean color, String img_file) {
		this.color = color;
		this.img_file = img_file;
		
	}

	public boolean move(Square currentSquare, Square fin) {
		Piece occup = fin.getOccupyingPiece();
		if (occup != null) {
			if (occup.getColor() == this.color)
				return false;
			else
				fin.capture(this);
		}
		currentSquare.removePiece();
		fin.put(this);
		return true;
	}

	public boolean getColor() {
		return color;
	}

	public Image getImage() {
		return img;
	}

	public void draw(Graphics g, Square currentSquare) {
		try {
			if (this.img == null) {
				this.img = ImageIO.read(getClass().getResource(img_file));
			}
		} catch (IOException e) {
			System.out.println("File not found: " + e.getMessage());
		}
		int x = currentSquare.getX();
		int y = currentSquare.getY();
		g.drawImage(this.img, x, y, null);
	}

	public ArrayList<Square> getLinearOccupations(Square[][] board, int row, int col) {
		ArrayList<Square> occups = new ArrayList<Square>();
		int x = col;
		int y = row;
		System.out.println(row + " " + col);
		x++;
		while (x <= 7) {
			if (board[y][x].isOccupied()) {
				if (board[y][x].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					occups.add(board[y][x]);
					break;
				}
			} else {
				occups.add(board[y][x]);
				x++;
			}
		}
		x = col;
		y = row;
		x--;
		while (x >= 0) {
			if (board[y][x].isOccupied()) {
				if (board[y][x].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					occups.add(board[y][x]);
					break;
				}
			} else {
				occups.add(board[y][x]);
				x--;
			}
		}
		x = col;
		y = row;
		y++;
		while (y <= 7) {
			if (board[y][x].isOccupied()) {
				if (board[y][x].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					occups.add(board[y][x]);
					break;
				}
			} else {
				occups.add(board[y][x]);
				y++;
			}
		}
		x = col;
		y = row;
		y--;
		while (y >= 0) {
			if (board[y][x].isOccupied()) {
				if (board[y][x].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					occups.add(board[y][x]);
					break;
				}
			} else {
				occups.add(board[y][x]);
				y--;
			}
		}
		return occups;
	}

	public ArrayList<Square> getDiagonalOccupations(Square[][] board, int col, int row) {
		ArrayList<Square> diagOccup = new ArrayList<Square>();
		int y = col;
		int x = row;
		int xNW = x - 1;
		int xSW = x - 1;
		int xNE = x + 1;
		int xSE = x + 1;
		int yNW = y - 1;
		int ySW = y + 1;
		int yNE = y - 1;
		int ySE = y + 1;
		while (xNW >= 0 && yNW >= 0) {
			if (board[yNW][xNW].isOccupied()) {
				if (board[yNW][xNW].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					diagOccup.add(board[yNW][xNW]);
					break;
				}
			} else {
				diagOccup.add(board[yNW][xNW]);
				yNW--;
				xNW--;
			}
		}
		while (xSW >= 0 && ySW < 8) {
			if (board[ySW][xSW].isOccupied()) {
				if (board[ySW][xSW].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					diagOccup.add(board[ySW][xSW]);
					break;
				}
			} else {
				diagOccup.add(board[ySW][xSW]);
				ySW++;
				xSW--;
			}
		}
		while (xSE < 8 && ySE < 8) {
			if (board[ySE][xSE].isOccupied()) {
				if (board[ySE][xSE].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					diagOccup.add(board[ySE][xSE]);
					break;
				}
			} else {
				diagOccup.add(board[ySE][xSE]);
				ySE++;
				xSE++;
			}
		}
		while (xNE < 8 && yNE >= 0) {
			if (board[yNE][xNE].isOccupied()) {
				if (board[yNE][xNE].getOccupyingPiece().getColor() == this.color) {
					break;
				} else {
					diagOccup.add(board[yNE][xNE]);
					break;
				}
			} else {
				diagOccup.add(board[yNE][xNE]);
				yNE--;
				xNE++;
			}
		}
		return diagOccup;
	}

// to be overriden in each subclass
	public ArrayList<Square> getLegalMoves(Board b, Square currentSquare) {
		ArrayList<Square> ret = getDiagonalOccupations(b.getSquareArray(), currentSquare.getXNum(),
				currentSquare.getYNum());
		ret.addAll(getLinearOccupations(b.getSquareArray(), currentSquare.getXNum(), currentSquare.getYNum()));
		return ret;
	}

//make sure to override this!
	public String toString() {
		if (color)
			return "white";
		else
			return "black";
	}

// to be implemented by each subclass
	public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
		return null;
	}
}
