import java.io.*;

/**
 * This class constructs the UI for a Client. It implements the chat interface
 * in order to implement the display() method.
 * 
 * Adapted from SEG2105 Assignment 2 Simplechat
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */

public class ClientConsole implements ChatIF
{
	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 1520;
	
	/**
	 * The instance of the client that created this console.
	 */
	Client client;
	
	/**
	 * Constructs an instance of the ClientConsole UI.
	 * 
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientConsole(String loginId, String host, int port)
	{
		try
		{
			client = new Client(loginId, host, port, this);
		}
		catch (IOException exception)
		{
			System.out.println("Error: Can't setup connection!"
				+ " Terminating client.");
			System.exit(1);
		}
	}
	
	/**
	 * This method waits for input from the console. Once it is received,
	 * it sends it to the client's message handler.
	 */
	public void accept()
	{
		try
		{
			BufferedReader fromConsole = 
				new BufferedReader(new InputStreamReader(System.in));
			String message;
			
			while (true)
			{
				message = fromConsole.readLine();
				client.handleMessageFromClientUI(message);
			}
		}
		catch (Exception ex)
		{
			System.out.println("Unexpected error while reading from console!");
		}
	}
	
	/**
	 * This method overrides the method in the ChatIF interface. It displays
	 * a message onto the screen.
	 * 
	 * @param message The string to be displayed.
	 */
	public void display(String message)
	{
		System.out.println("> " + message);
	}
	
	public void handleMessageFromTestCases(String message)
	{
		client.handleMessageFromClientUI(message);
	}
	
	/**
	 * This method is responsible for the creation of the Client UI.
	 * 
	 * @param args[0] The login id to use.
	 * @param args[1] The host to connect to.
	 * @param args[2] The port to connect to.
	 */
	public static void main(String[] args)
	{
		String loginId = "";
		String host = "";
		int port = 0;
		
		/*
		 * Login ID for the user to be known as. Must be provided, else
		 * the client will quit, no default will be provided.
		 */
		
		try
	    {
	      loginId = args[0];	
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      loginId = "client";
	    }
		
		try
	    {
	      host = args[1];	
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	    }
		
		try
	    {
	    	port = Integer.parseInt(args[2]);	
	    }
	    catch (ArrayIndexOutOfBoundsException ex)
	    {
	    	port = DEFAULT_PORT;
	    }
		
		ClientConsole console = new ClientConsole(loginId, host, port);
		console.accept(); // Wait for console data.
	}
}