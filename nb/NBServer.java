import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ConnectException;

public class NBServer
{
	public NBServer()
	{	
		// Port to listen on
		int port = 5000;
		
		// Create a server socket that will listen at a specified port number
		ServerSocket serverSocket = new ServerSocket(port);
	
		// While listening
		while(true)
		{
			try
			{
				// Notify administrator that the server is listening
				System.out.println("[Status] Listening on port " + serverSocket.getLocalPort());
			
				// Create a client socket connection by accepting an incoming connection to the server socket
				Socket clientSocket = serverSocket.accept();
			
				// Notify the administrator that a connection has been accepted
				System.out.println("[Status] Accepted client connection at " + clientSocket.getLocalPort());
			
				// Define inClient as the inputStream of the client socket
				BufferedReader inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
				// Notify the administrator that the server is reading input from the client
				System.out.println("[Status] Receiving input from the client connection");
			
				// Read one line of input from the client connection
				String input = inClient.readLine();
			
				// Notify the administrator that the message was received
				System.out.println("[Status] Message received: " + input);
				
				// Notify the administrator that the naive Bayes algorithm is calculating sentiment
				System.out.println("[Status] Calculating sentiment");
				
				// Create sentence transfer object
				STO inSTO = new STO();
				inSTO.setInput(input);
				
				// Calculate sentiment
				NB naiveBayes = new NB();
				STO outSTO = naiveBayes.calc(inSTO);
				
				// Notify the administrator that the server is writing back to the client
				
				// Format results
				String pos = Double.parseDouble(outSTO.getPos());
				String neu = Double.parseDouble(outSTO.getNeu());
				String neg = Double.parseDouble(outSTO.getNeg());
				String output = outSTO.getClassifier() + "," + pos + "," + neu + "," + neg + ",\r\n";
				
				// Define outClient as the outputStream of the client socket
				outClient = new PrintWriter(clientSocket.getOutputStream(), true);
				
				// Write the output back to the client
				outClient.write(output);
				
				// Notify the administrator that the server is closing the connection
				System.out.println("[Status] Closing connection");
				
				// Close the client connection
				inClient.close();
				outClient.close();
				clientSocket.close();
			}
			catch(ConnectException ex)
			{
				ex.printStackTrace();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
			
		// Close the server socket
		serverSocket.close();
		
		// Notify the administrator that the application is terminating
		System.out.println("[Status] Terminating");
	}
	
	public static void main(String[] args)
	{
		new NBServer();
	}
}