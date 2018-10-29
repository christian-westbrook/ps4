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
			
			// Create a client socket connection by accepting an incoming connection to the server socket
			Socket clientSocket = serverSocket.accept();
			
			// Define inServer as the inputStream of the client socket
			BufferedReader inServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			// Read one line of input from the client connection
			String input = inServer.readLine();
			
			// Print the line of input
			System.out.println(input);
			
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
	}
	
	public static void main(String[] args)
	{
		new NBClient();
	}
}