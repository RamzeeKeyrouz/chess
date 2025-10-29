import java.net.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWithThreads {
	public static final int LISTENING_PORT = 9876;
	// For communication with the connecting program.
	private final Square[][] board;
	private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
	boolean turn = true;
	private ArrayList<ConnectionHandler> connections;

	public ServerWithThreads() {
		ServerSocket listener; // Listens for incoming connections.
		connections = new ArrayList<ConnectionHandler>();

		try {
			listener = new ServerSocket(LISTENING_PORT);
			System.out.println("Listening on port " + LISTENING_PORT);
			int conNum = 0;
			while (conNum < 2) {
				// Accept next connection request and handle it.
				Socket s = listener.accept();
				System.out.println("made a connection " + conNum);
				ConnectionHandler handler = new ConnectionHandler(s, conNum);
				connections.add(handler);
				handler.start();
				conNum++;
			}
		} catch (Exception e) {
			System.out.println("Sorry, the server has shut down.");
			System.out.println("Error:  " + e);

		}

		board = new Square[8][8];
		turn = true;

		boolean color = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Square(color, i, j);
				color = !color;
			}
			color = !color;
		}

		initializePieces();
		for (ConnectionHandler h : connections) {
			h.send(board);
			h.send("Player " + h.addressing);
			h.send(turn);
		}

	}

	public static void main(String[] args) {

		new ServerWithThreads();

	} // end main()

	// set up the board such that the black pieces are on one side and the white
	// pieces are on the other.
	// since we only have one kind of piece for now you need only set the same
	// number of pieces on either side.
	// it's up to you how you wish to arrange your pieces.
	private void initializePieces() {
		// white pieces
		board[7][0].put(new Rook(true, RESOURCES_WROOK_PNG));
		board[7][1].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
		board[7][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
		board[7][3].put(new King(true, RESOURCES_WKING_PNG));
		board[7][4].put(new Queen(true, RESOURCES_WQUEEN_PNG));
		board[7][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
		board[7][6].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
		board[7][7].put(new Rook(true, RESOURCES_WROOK_PNG));
		for (int i = 0; i < 8; i++) {
			board[6][i].put(new Pawn(true, RESOURCES_WPAWN_PNG));
		}
		// black pieces
		board[0][0].put(new Rook(false, RESOURCES_BROOK_PNG));
		board[0][1].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
		board[0][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
		board[0][3].put(new King(false, RESOURCES_BKING_PNG));
		board[0][4].put(new Queen(false, RESOURCES_BQUEEN_PNG));
		board[0][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
		board[0][6].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
		board[0][7].put(new Rook(false, RESOURCES_BROOK_PNG));
		for (int i = 0; i < 8; i++) {
			board[1][i].put(new Pawn(false, RESOURCES_BPAWN_PNG));
		}

	}

	/**
	 * Defines a thread that handles the connection with one client.
	 */

	private class ConnectionHandler extends Thread {
		Socket client;
		private ObjectOutputStream os;
		private ObjectInputStream is;
		private int addressing;

		ConnectionHandler(Socket socket, int addressing) {
			this.addressing = addressing;
			try {
				is = new ObjectInputStream(socket.getInputStream());
				os = new ObjectOutputStream(socket.getOutputStream());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client = socket;
		}

		// method to help us send information!
		public void send(Object input) {
			try {
				os.writeObject(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			String clientAddress = client.getInetAddress().toString();
			while (client.isConnected()) {
				try {
					Object input = is.readObject();

					turn = !turn;

// your code to send messages goes here.
					System.out.println("got a message from " + addressing);
					for (ConnectionHandler handler : connections) {
// make sure you don't try to access the handler from two different threads
// simultainously
						synchronized (handler) {
							System.out.println("trying to send to " + handler.addressing);
							if (handler != this)
								handler.send(input);
							handler.send(turn);
// handler.send(addressing+": "+input);
						}
					}

				} catch (Exception e) {
					System.out.println("Error on connection with: " + clientAddress + ": " + e);
				}

			}
			connections.remove(client);
		}
	}

}
