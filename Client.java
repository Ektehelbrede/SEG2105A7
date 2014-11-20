import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 * 
 * Adapted from SEG2105 Assignment 2 Simplechat
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */
public class Client extends AbstractClient
{
	/**
	 * Interface variable, allows implementation of the display() method.
	 */
	ChatIF clientUI;
	
	String loginId;
	
	/**
	 * Constructs an instance of the client.
	 * 
	 * @param 
	 */
	public Client(String loginId, String host, int port, ChatIF clientUI) throws IOException
	{
		super(host, port); // Call the superclass constructor.
		this.clientUI = clientUI;
		this.loginId = loginId;
		openConnection();
	}
	
	/**
	 * Handle all of the data that comes from the server.
	 * 
	 * @param message The message from the server.
	 */
	public void handleMessageFromServer(Object message)
	{
		clientUI.display(message.toString());
	}
	
	/**
	 * Handle all of the data that comes from the Client UI.
	 * 
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(String message)
	{
		// Check if the message is intended to be a command.
		
			/*
			 * #login
			 * #apply
			 * #schedule
			 */
			
			/*
			 * SOME USEFUL THINGS:
			 * message.equals("#command"){}
			 * message.startsWith("#command"){}
			 * message.substring(message.indexOf("<") + 1. message.indexOf(">"))
			 */
			
			
		
		
		
			try
			{
				sendToServer(message);
			}
			catch (IOException e)
			{
				clientUI.display("Could not send message to server. Terminating client.");
				quit();
			}
		
	}
	
	/**
	 * Terminate the client.
	 */
	public void quit()
	{
		try
		{
			closeConnection();
		}
		catch (IOException e) {}
		
		clientUI.display("Terminating");
		System.exit(0);
	}
	
	/**
	 * Is called whenever a connection with the server is terminated by the client.
	 */
	protected void connectionClosed()
	{
		clientUI.display("The connection to the server has been closed.");
	}
	
	/**
	 * Is called when something goes wrong with the connection, such as when the
	 * connection is terminated by the server.
	 */
	protected void connectionException(Exception exception)
	{
		clientUI.display("The server has terminated the connection. Please ensure that"
				+ " you are progressing through the application process in the correct order.");
	}
	
	/**
	 * Whenever a client connects to a server, it should send the message
	 * #connect to the server.
	 */
	protected void connectionEstablished()
	{
		try
		{
			sendToServer("#connect");
		}
		catch (IOException e)
		{
			clientUI.display("Could not connect to the server.");
		}
	}
}