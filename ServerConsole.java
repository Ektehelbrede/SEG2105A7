import java.io.*;

/**
 * This class constructs the UI for the server. It implements chat interface
 * in order to activate the display() method.
 * 
 * Adapted from SEG2105 Assignment 2 Simplechat
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */

public class ServerConsole implements ChatIF
{
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 1520;
	
	/**
	 * The instance of the client that created this Server.
	 */
	Server server;
	
	/**
	 * Constructs an instance of the ServerConsole UI.
	 * 
	 * @param port The port to listen on.
	 */
	public ServerConsole(int port)
	{
		try
		{
			server = new Server(port, this);
		}
		catch (IOException exception)
		{
			System.out.println("Error: Can't setup server!"
				+ " Terminating.");
			System.exit(1);
		}
		
		try
		{
			server.listen();
		}
		catch (Exception ex)
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
	
	/**
	 * This method waits for input from the console.
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
				server.handleMessageFromServerUI(message);
			}
		}
		catch (Exception ex)
		{
			System.out.println
				("Unexpected error while reading from console!");
		}
		
	}
	
	/**
	 * This method overrides the method in the ChatIF interface. It 
	 * displays a message onto the screen.
	 * 
	 * @param message The string to be displayed.
	 */
	public void display(String message)
	{
		System.out.println("> " + message);
	}
	
	/**
	 * This method is responsible for the creation of the server
	 * instance with UI.
	 * 
	 * @param args[0] The port number to listen on. Defaults to 1520.
	 */
	public static void main(String[] args)
	{
		int port = 0;
		
		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch (Throwable t)
		{
			port = DEFAULT_PORT;
		}
		
		ServerConsole sv = new ServerConsole(port);
		sv.accept();
	}
}