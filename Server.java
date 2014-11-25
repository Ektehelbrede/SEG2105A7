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
	Application application;
	
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
		this.application = new Application(this);
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
		String msg = message.toString();
		msg = msg.toLowerCase();
		
		if (client.getInfo("loginstatus") == "true")	// User may use these commands after they have logged in.
		{
			if (msg.startsWith("#qualificationrequirements"))
			{
				try{client.sendToClient("Requesting list of requirements...");}catch(IOException e){}
				application.getQualificationRequirements((int)client.getInfo("index"));
			}
			
			else if (msg.startsWith("#submitqualificationresponse"))
			{
				if (msg.contains("<") && msg.contains(">"))
				{
					try{client.sendToClient("Submitting response...");}catch(IOException e){}
					application.submitQualificationResponse(msg.substring(msg.indexOf("<") + 1, msg.indexOf(">")), 
						(int)client.getInfo("index"));
				}
				
				else
				{
					try{client.sendToClient("Please check the syntax of your response.");}catch(IOException e){}
				}
			}
			
			else if (msg.startsWith("#requestapplication"))
			{
				try{client.sendToClient("Requesting list of questions...");}catch(IOException e){}
				application.getApplication((int)client.getInfo("index"));
			}
			
			else if (msg.startsWith("#submitapplication"))
			{
				if (msg.contains("<") && msg.contains(">"))
				{
					try{client.sendToClient("Submitting responses...");}catch(IOException e){}
					application.submitApplication(msg.substring(msg.indexOf("<") + 1, msg.indexOf(">")), (int)client.getInfo("index"));
				}
				
				else
				{
					try{client.sendToClient("Please check the syntax of your response.");}catch(IOException e){}
				}
			}
			
			else if (msg.startsWith("#requestschedule"))
			{
				try{client.sendToClient("Requesting available interview times...");}catch(IOException e){}
				application.getSchedule((int)client.getInfo("index"));
			}
			
			else if (msg.startsWith("#submitschedulerequest"))	
			{
				if (msg.contains("<") && msg.contains(">"))
				{
					try{client.sendToClient("Submitting request...");}catch(IOException e){}
					application.scheduleAppointment(msg.substring(msg.indexOf("<") + 1, msg.indexOf(">")), (int)client.getInfo("index"));
				}
				
				else
				{
					try{client.sendToClient("Please check the syntax of your response.");}catch(IOException e){}
				}
			}	
			
			else if (msg.startsWith("#getmyinformation"))	// FOR DEBUGGING
			{
				try{client.sendToClient("GETTING CANDIDATE INFORMATION [DEBUGGING]...");}catch(IOException e){}
				application.getCandidateInformation((int)client.getInfo("index"));
			}
			
			else if (msg.startsWith("#"))
			{
				try
				{
					client.sendToClient("Invalid command, please refer to the #help command if you are unsure of what to do. ");
				}
				catch (IOException e) {}
			}
		}
		
		else	// These commands are valid at any time.
		{
			if (msg.startsWith("#connect"))
			{
				try
				{
					client.sendToClient("Please choose from the following commands:"
						+ "\n#createaccount <username, password; emailaddress>"
						+ "\n#login <username, password>"
						+ "\nUse the #help command for additional options.");
				}
				catch (IOException e) {}
			}
			
			else if (msg.startsWith("#help"))
			{
				try
				{
					client.sendToClient("Here are some additional commands, please ensure that you"
							+ " complete the application process in the correct order:"
							+ "\n#qualificationrequirements" + "\nsubmitqualificationresponse <answer>"
							+ "\n#requestapplication" 
							+ "\n#submitapplication <1. answer 2. answer 3. answer 4. answer>"
							+ "\n#availableappointments" + "\n#requestappointment <time>");
				}
				catch (IOException e) {}
			}
			
			else if (msg.startsWith("#createaccount"))
			{
				if (msg.contains("<") && msg.contains(">") && msg.contains(";") && msg.contains(","))
				{
					try{client.sendToClient("Creating account...");}catch(IOException e){}
					application.createAccount(msg.substring(msg.indexOf("<") + 1, msg.indexOf(",")), 
							msg.substring(msg.indexOf(",") + 1, msg.indexOf(";")), 
							msg.substring(msg.indexOf(";") + 1, msg.indexOf(">")), client);
				}
				
				else
				{
					try{client.sendToClient("Please check the syntax of your response.");}catch(IOException e){}
				}
			}
			
			else if (msg.startsWith("#login"))
			{
				if (msg.contains("<") && msg.contains(">") && msg.contains(","))
				{
					try{client.sendToClient("Logging in...");}catch(IOException e){}
					application.login(msg.substring(msg.indexOf("<") + 1, msg.indexOf(",")), 
							msg.substring(msg.indexOf(",") + 1, msg.indexOf(">")), client);
					client.setInfo("loginstatus", "true");
				}
				
				else
				{
					try{client.sendToClient("Please check the syntax of your response.");}catch(IOException e){}
				}
			}
			
			else if (msg.startsWith("#"))
			{
				try
				{
					client.sendToClient("Invalid command, please ensure your are logged in and try again. ");
				}
				catch (IOException e) {}
			}
		}
	}
	
	/**
	 * This method handles any messages received from the application.
	 * 
	 * @param message The message received.
	 * @param client The client to send the message to.
	 */
	public void handleMessageFromApplication
		(Object message, ConnectionToClient client)
	{
		try
		{
			client.sendToClient(message);
		}
		catch (Exception ex) {}
	}
	
	
	/**
	 * This method handles any messages received from the server.
	 * 
	 * @param message The message received from the server.
	 */
	public void handleMessageFromServerUI(Object message)
	{
		String msg = message.toString();
		msg = msg.toLowerCase();
		
		// Check if message is intended to be a command.
		if (message.toString().startsWith("#"))
		{
			if (msg.startsWith("#addinterviewtime"))
			{
				if (msg.contains("<") && msg.contains(">") && msg.contains(","))
				{
					serverUI.display("Adding timeslot...");
					application.addInterviewTimeslot(msg.substring(msg.indexOf("<") + 1, msg.indexOf(",")), 
						msg.substring(msg.indexOf(",") + 1, msg.indexOf(">")));
				}
			}
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