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
		server.handleMessageFromApplication("New user created, to login please use the command #login <username, password>", client);
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
				client.setInfo("index", i);
				server.handleMessageFromApplication("Successfully logged in.", client);
				wasFound = true;
				break;
			}
			
			if (candidates.get(i).getUsername().equals(username) && !(candidates.get(i).getPassword().equals(password)))
			{	
				server.handleMessageFromApplication("Invalid password, please login again.", client);
				wasFound = true;
				break;
			}
		}
		
		if (!wasFound)
		{
			server.handleMessageFromApplication("Please check your username and password and try again. Or, create "
				+ "a new account using the command #createaccount <username, password; emailaddress>", client);
		}
	}
	
	/**
	 * This method prints the various qualifications that are required to apply for an interview.
	 * [NEEDS TO CHECK THAT THE USER EXISTS AND IS LOGGED IN.]
	 * 
	 * @param client The client that requested the list of requirements.
	 */
	public void getQualificationRequirements(ConnectionToClient client)
	{
		for (int i = 0; i < standards.size(); i++)
		{
			server.handleMessageFromApplication("" + Integer.toString(i + 1) + ": " + standards.get(i).getDescription(), client);
		}
		
		server.handleMessageFromApplication("Please use the command #submitqualificationresponse <answer> to"
			+ " submit either yes or no depending on if you meet the requirements or not.", client);
	}
	
	/**
	 * This method determines if the user is qualified to submit an application.
	 * [NEEDS TO CHECK THAT THE USER EXISTS AND IS LOGGED IN.]
	 * 
	 * @param response The client's response to the list of qualifications.
	 * @param indexOfCandidate The index of the candidate in candidates.
	 * @param client The client submitting the response.
	 */
	public void submitQualificationResponse(String response ,int indexOfCandidate, ConnectionToClient client)
	{
		if (response.equals("yes") || response.equals("true"))
		{
			candidates.get(indexOfCandidate).setIsQualified(true);
			server.handleMessageFromApplication("Thank you! Please continue with your application by"
				+ " using the command #requestapplication", client);
		}
		
		else
		{
			candidates.get(indexOfCandidate).setIsQualified(false);
			server.handleMessageFromApplication("We're sorry, but you do not meet the requirements for"
				+ " an application at this time. Please keep us in mind if your qualifications change!", client);
		}
	}
	
	/**
	 * This method prints the various questions the candidate must answer in order to complete the
	 * application process.
	 * [NEEDS TO CHECK THAT THE USER EXISTS, IS LOGGED IN, AND IS QUALIFIED.]
	 * 
	 * @param indexOfCandidate The index of the candidate in candidates.
	 * @param client The client that requested the application questions.
	 */
	public void getApplication(int indexOfCandidate, ConnectionToClient client)
	{
		Score score = candidates.get(indexOfCandidate).getScore();
		
		for (int i = 0; i < score.numberOfQuestions(); i++)
		{
			server.handleMessageFromApplication("" + Integer.toString(i + 1) + score.getQuestion(i), client);
		}
	}
	
	public void submitApplication(boolean[] responses, int indexOfCandidate, ConnectionToClient client)
	{
		
	}
	
	public void scheduleAppointment()
	{
		
	}
}


















