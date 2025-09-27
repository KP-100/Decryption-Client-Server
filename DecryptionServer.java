package cop2805;
import java.io.*;
import java.net.*;

public class DecryptionServer {
	private static final int PORT = 1236; // port # to use
	
	public static void main(String[] args) {
		boolean running = true;
		System.out.println("Server is starting... on PORT:" + PORT);
		
		try(ServerSocket serverSocket = new ServerSocket(PORT)){
			while (running) {
				// get a client connection here
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client is now connected..");
				
				// do input and output streams for comms
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				
				// read the message
				String input = in.readLine();
				
				// shutoff command
				if ("shutdown".equalsIgnoreCase(input)) {
					running = false;
					out.println("Server is now shut down.");
					System.out.println("Shutdown command accepted. Server is shutting down.");
					
				} else {
					// if no shutdown, we send a message back
					String decrypted = decryptMessage(input);
					out.println(decrypted);
					System.out.println("Message decrypted and sent to client.");
				}
				
				// once sent back, we will now close the connection.
				clientSocket.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Server has stopped.");
	}
	
	// Caesar cipher method which adds 10 to each char
	private static String decryptMessage(String message) {
		StringBuilder result = new StringBuilder();
		for (char c: message.toCharArray()) {
			result.append((char)(c + 10));
		}
		return result.toString(); // returns the result
	}

}
