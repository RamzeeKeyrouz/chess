import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client {
	/*
	 * Modify this example so that it opens a dialogue window using java swing,
	 * takes in a user message and sends it to the server. The server should output
	 * the message back to all connected clients (you should see your own message
	 * pop up in your client as well when you send it!). We will build on this
	 * project in the future to make a full fledged server based game, so make sure
	 * you can read your code later! Use good programming practices. ****HINT****
	 * you may wish to have a thread be in charge of sending information and another
	 * thread in charge of receiving information.
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        

            
            //establish socket connection to server
       final Socket socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream
          final  ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            
            //read the server response message
          final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            JFrame f = new JFrame("client");
            f.setLayout(new GridLayout(3,1));
            JTextField input = new JTextField("");
            JTextArea output = new JTextArea("");
            output.setEditable(false);
            f.setSize(1000,600);
            f.add(input);
            f.add(output);
            
            
            
         
            //a screen to keep the server running
           
            input.addActionListener(new ActionListener() {
            	 public void actionPerformed(ActionEvent e){
                     try {
						oos.writeObject(input.getText());
						input.setText("");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
               }
            });
            
            f.setVisible(true);
      
    }
}
