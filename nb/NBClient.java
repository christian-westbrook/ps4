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
		int port = 5000;
		
		try
		{
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			
			BufferedReader inServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String input = inServer.readLine();
			System.out.println(input);
			
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