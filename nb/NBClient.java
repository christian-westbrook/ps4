import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ConnectException;

public class NBClient
{
	public NBClient()
	{	
		try
		{
			// Port to listen on
			int port = 5000;
			
			// Create a server socket that will listen at a specified port number
			ServerSocket serverSocket = new ServerSocket(port);
			
			// Notify administrator that the server is listening
			System.out.println("[Status] Listening on port " + serverSocket.getLocalPort());
			
			// Create a client socket connection by accepting an incoming connection to the server socket
			Socket clientSocket = serverSocket.accept();
			
			// Notify the administrator that a connection has been accepted
			System.out.println("[Status] Accepted client connection at " + clientSocket.getLocalPort());
			
			// Define inServer as the inputStream of the client socket
			BufferedReader inServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			// Notify the administrator that the server is reading input from the client
			System.out.println("[Status] Receiving input from the client connection");
			
			// Read one line of input from the client connection
			String input = inServer.readLine();
			
			// Print the line of input
			System.out.println("Input: " + input);
			
			// Notify the administrator that the server is closing the connection
			System.out.println("[Status] Closing connection");
			
			// Close the sockets
			serverSocket.close();
			clientSocket.close();
		}
		catch (ConnectException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		// Notify the administrator that the application is terminating
		System.out.println("[Status] Terminating");
	}
	
	public static void main(String[] args)
	{
		new NBClient();
	}
}