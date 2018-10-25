import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ConnectException;

public class NBClient
{
	public NBClient()
	{
		String IP = "localhost";
		int port = 5000;
		
		try
		{
			socket = new Socket(IP, port);
			inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String input = inServer.readLine();
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