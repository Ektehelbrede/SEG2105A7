import java.io.*;

/**
 * This class overrides some of the methods in the abstract superclass
 * in order to give more functionality to the server.
 * 
 * Adapted from SEG2105 Assignment 2 Simplechat
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */

public class Server extends AbstractServer
{
	/**
	 * The interface type variable. It allows the implementation of the
	 * display method in the client.
	 */
	ChatIF serverUI;
	
	/**
	 * Constructs an instance of the server.
	 * 
	 * @param port 		The port to listen on.
	 * @param serverUI 	The interface type variable.
	 */
	public Server(int port, ChatIF serverUI) throws IOException
	{
		super(port);
		this.serverUI = serverUI;
	}
	
	/**
	 * This method handles any messages received from the client.
	 * 
	 * @param message	The message received from the client.
	 * @param client	The connection from which the message originated.
	 */
	public void handleMessageFromClient
		(Object message, ConnectionToClient client)
	{
		/*
		 * COMMANDS:
		 * message.toString().startsWith("#login") && client.getInfo("ID") == null, etc
		 */
		
		// else
		serverUI.display("Message received: " + message.toString() + " from "
			+ client.getInfo("ID"));
		this.sendToAllClients(client.getInfo("ID").toString() + ": " + message);
	}
	
	/**
	 * This method handles any messages received from the server.
	 * 
	 * @param message The message received from the server.
	 */
	public void handleMessageFromServerUI(Object message)
	{
		// Check if message is intended to be a command.
		if (message.toString().startsWith("#"))
		{
			/*
			 * COMMANDS
			 */
		}
		
		else
		{
			serverUI.display(message.toString());
			this.sendToAllClients("#SERVER MSG > " + message.toString());
		}
	}
	
	/**
	 * This method overrides the one in the superclass. Called when the
	 * server starts listening for connections.
	 */
	protected void serverStarted()
	{
		serverUI.display("Server listening for connections on port " + getPort());
	}
	
	/**
	 * This method overrides the one in the superclass. Called when the 
	 * server stops listening for connections.
	 */
	protected void serverStopped()
	{
		serverUI.display("Server has stopped listening for connections");
		this.sendToAllClients("WARNING - The server has stopped listening for connections.");
	}
	
	/**
	 * This method is called each time a new client connects.
	 * 
	 * @param client The connected from the new client.
	 */
	protected void clientConnected(ConnectionToClient client)
	{
		serverUI.display("A new client has connected: " 
			+ client.getInetAddress().getHostAddress());
	}
	
	/**
	 * This method is called each time the server disconnects a client using a call to
	 * the close method of ConnectionToClient.
	 * 
	 * @param client The connection which is being disconnected.
	 */
	synchronized protected void clientDisconnected(ConnectionToClient client)
	{
		serverUI.display("Disconnecting the client: " 
			+ client.getInetAddress().getHostAddress());
		this.sendToAllClients(client.getInfo("ID") + " has been disconnected.");
	}
}