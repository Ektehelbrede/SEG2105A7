import java.util.*;

/**
 * Application is responsible for interacting with the user (through OCSF)
 * in order to allow the user to complete an application, schedule an appointment,
 * cancel an appointment, etc.
 * 
 * @author 
 */
public class Application 
{
	// Associations [RENAME THESE TO BETTER DESCRIBE WHAT THE ASSOCIATION IS.]
	private List<Candidate> candidates;		// 1 -- * Candidate
	private List<Standard> standards;		// 1 -- 2..* Standard
	private List<Score> scores;				// 1 -- * Score
	private List<Email> emails;				// 1 -- * Email
	
	private Server server;	
	
	public Application(Server server)
	{
		this.candidates = new ArrayList<Candidate>();
		this.standards = new ArrayList<Standard>();
		this.scores = new ArrayList<Score>();
		this.emails = new ArrayList<Email>();
		
		this.server = server;
		
		this.standards.add(new Standard("Are you a Canadian citizen, or have a valid work visa?", this));
	}
	
	/**
	 * This method attempts to create a new account based on the provided details.
	 * 
	 * @param username The username of the new account.
	 * @param password The password of the new account.
	 * @param emailAddress The emailaddress of the new account.
	 * @param client The connection to the client attempting to create the account.
	 */
	public void createAccount(String username, String password, String emailAddress, ConnectionToClient client)
	{
		candidates.add(new Candidate(username, password, emailAddress, client, this));
		scores.add(new Score(candidates.get(candidates.size() - 1), this));
		candidates.get(candidates.size() - 1).setScore(scores.get(scores.size() - 1));
		sendMessageToServer("New user created, to login please use the command #login <username, password>", client);
	}
	
	/**
	 * Login attempts to match the provided username:password combination to an existing account.
	 * If successful, will save the index of the client in the ConnectionToClient.
	 * If unsuccessful, will prompt the user with information based on the error (password 
	 * incorrect, no account with those details).
	 * 
	 * @param username The username of the account to log into.
	 * @param password The password of the account to log into.
	 * @param client The connection to the client attempting to log in.
	 */
	public void login(String username, String password, ConnectionToClient client)
	{	
		boolean wasFound = false;
		
		for (int i = 0; i < candidates.size(); i++)
		{	
			if (candidates.get(i).getUsername().equals(username) && candidates.get(i).getPassword().equals(password))
			{	
				candidates.get(i).setConnection(client);
				client.setInfo("index", i);
				sendMessageToServer("Successfully logged in.", client);
				wasFound = true;
				break;
			}
			
			if (candidates.get(i).getUsername().equals(username) && !(candidates.get(i).getPassword().equals(password)))
			{	
				sendMessageToServer("Invalid password, please login again.", client);
				wasFound = true;
				break;
			}
		}
		
		if (!wasFound)
		{
			sendMessageToServer("Please check your username and password and try again. Or, create "
				+ "a new account using the command #createaccount <username, password; emailaddress>", client);
		}
	}
	
	/**
	 * This method prints the various qualifications that are required to apply for an interview.
	 * [NEEDS TO CHECK THAT THE USER EXISTS AND IS LOGGED IN.]
	 * 
	 * @param indexOfCandidate The index of the candidate in candidates.
	 */
	public void getQualificationRequirements(int indexOfCandidate)
	{
		for (int i = 0; i < standards.size(); i++)
		{
			sendMessageToServer("" + Integer.toString(i + 1) + ": " + standards.get(i).getDescription(), 
				candidates.get(indexOfCandidate).getConnectionToCandidate());
		}
		
		sendMessageToServer("Please use the command #submitqualificationresponse <answer> to"
			+ " submit either yes or no depending on if you meet the requirements or not.", 
			candidates.get(indexOfCandidate).getConnectionToCandidate());
	}
	
	/**
	 * This method determines if the user is qualified to submit an application.
	 * [NEEDS TO CHECK THAT THE USER EXISTS AND IS LOGGED IN.]
	 * 
	 * @param response The client's response to the list of qualifications.
	 * @param indexOfCandidate The index of the candidate in candidates.
	 */
	public void submitQualificationResponse(String response ,int indexOfCandidate)
	{
		if (response.equals("yes") || response.equals("true"))
		{
			candidates.get(indexOfCandidate).setIsQualified(true);
			sendMessageToServer("Thank you! Please continue with your application by"
				+ " using the command #requestapplication", candidates.get(indexOfCandidate).getConnectionToCandidate());
		}
		
		else
		{
			candidates.get(indexOfCandidate).setIsQualified(false);
			sendMessageToServer("We're sorry, but you do not meet the requirements for"
				+ " an application at this time. Please keep us in mind if your qualifications change!", 
				candidates.get(indexOfCandidate).getConnectionToCandidate());
		}
	}
	
	/**
	 * This method prints the various questions the candidate must answer in order to complete the
	 * application process.
	 * [NEEDS TO CHECK THAT THE USER EXISTS, IS LOGGED IN, AND IS QUALIFIED.]
	 * 
	 * @param indexOfCandidate The index of the candidate in candidates.
	 */
	public void getApplication(int indexOfCandidate)
	{
		Score score = candidates.get(indexOfCandidate).getScore();
		
		for (int i = 0; i < score.numberOfQuestions(); i++)
		{
			sendMessageToServer("" + Integer.toString(i + 1) + ": " + score.getQuestion(i), 
				candidates.get(indexOfCandidate).getConnectionToCandidate());
		}
		
		sendMessageToServer("Please use the command #submitapplication <aaaaaaa> to respond."
				+ " Encode each 'a' with a y or n depending on your response to the question.",
				candidates.get(indexOfCandidate).getConnectionToCandidate());
	}
	
	/**
	 * This method receives the candidate's responses to the application questions, and calls
	 * the evaluate method of that client's Score. If the client meets the minimum requirements
	 * for an interview, an email will be sent requesting that they schedule one.
	 * [NEEDS TO CHECK IF THE USER EXISTS, IS LOGGED IN, AND IS QUALIFIED.]
	 * 
	 * IF AN EXCEPTION TO THE CLIENT OCCURS WHEN SUBMITTING AN APPLICATION, PLEASE ENSURE THAT
	 * ANY ANTI VIRUS IS TURNED OFF OR THIS PROGRAM IS WHITELISTED - THE EMAIL CANNOT BE SENT.
	 * 
	 * @param responses The candidates responses to be evaluated.
	 * @param indexOfCandidate The index of the candidate in candidates.
	 */
	public void submitApplication(String responses, int indexOfCandidate)
	{
		candidates.get(indexOfCandidate).getScore().evaluate(responses);
		sendMessageToServer("Successfully evaluated your application.",
			candidates.get(indexOfCandidate).getConnectionToCandidate());
		
		if (candidates.get(indexOfCandidate).getIsQualified() == true)
		{
			sendMessageToServer("Sending an interview request email.",
				candidates.get(indexOfCandidate).getConnectionToCandidate());
			sendRequestForInterviewEmail(candidates.get(indexOfCandidate));
		}
	}
	
	
	public void scheduleAppointment()
	{
		
	}
	
	/**
	 * This method is responsible for sending the message to the server, which in turn will
	 * send it to the client.
	 * 
	 * @param message The message to be sent.
	 * @param client The client to send the message to.
	 */
	public void sendMessageToServer(String message, ConnectionToClient client)
	{
		server.handleMessageFromApplication(message, client);
	}
	
	public void sendRequestForInterviewEmail(Candidate candidate)
	{
		emails.add(new Email(candidate));
	}
}


















