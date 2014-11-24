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
	// Associations
	private List<Candidate> candidates;		
	private List<Standard> standards;		
	private List<Score> scores;				
	private List<Email> emails;				
	private Schedule schedule;
	
	private Server server;	
	
	public Application(Server server)
	{
		this.candidates = new ArrayList<Candidate>();
		this.standards = new ArrayList<Standard>();
		this.scores = new ArrayList<Score>();
		this.emails = new ArrayList<Email>();
		this.schedule = new Schedule(this);
		
		this.server = server;
		
		// Default list of standards to be used. [From the problem description in Assignment 5.]
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
				
				if (candidates.get(i).getIsScheduled() == true) { sendMessageToServer("Welcome back. You currently have "
						+ "an interview scheduled for: " + candidates.get(i).getScheduledTime().getTime(), client); }
				else if (candidates.get(i).getMeetsMinimumScore() == true) { sendMessageToServer("Welcome back. Please use "
						+ "the #requestschedule command to begin scheduling an interview.", client); }
				else if (candidates.get(i).getIsQualified() == true) { sendMessageToServer("Welcome back. Please use "
						+ "the #requestapplication command to begin the application process.", client); }
				else { sendMessageToServer("Please use the #qualificationrequirements command to begin.", client); }
				
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
				+ " using the command #requestapplication", 
				candidates.get(indexOfCandidate).getConnectionToCandidate());
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
		
		sendMessageToServer("Please use the command #submitapplication <aaaaa> to respond."
			+ " Encode each 'a' with a y or n depending on your response to the question.",
			candidates.get(indexOfCandidate).getConnectionToCandidate());
	}
	
	/**
	 * This method receives the candidate's responses to the application questions, and calls
	 * the evaluate method of that client's Score. If the client meets the minimum requirements
	 * for an interview, an email will be sent requesting that they schedule one.
	 * 
	 * [IF AN EXCEPTION TO THE CLIENT OCCURS WHEN SUBMITTING AN APPLICATION, PLEASE ENSURE THAT
	 * ANY ANTI VIRUS IS TURNED OFF OR THIS PROGRAM IS WHITELISTED - THE EMAIL CANNOT BE SENT.]
	 * 
	 * @param responses The candidates responses to be evaluated.
	 * @param indexOfCandidate The index of the candidate in candidates.
	 */
	public void submitApplication(String responses, int indexOfCandidate)
	{
		candidates.get(indexOfCandidate).getScore().evaluate(responses);
		sendMessageToServer("Successfully evaluated your application.",
			candidates.get(indexOfCandidate).getConnectionToCandidate());
		
		sendMessageToServer("Sending an email.",
			candidates.get(indexOfCandidate).getConnectionToCandidate());
		sendRequestForInterviewEmail(candidates.get(indexOfCandidate));
	}
	
	/**
	 * The method prints the currently available interview times to the candidate.
	 * 
	 * @param indexOfCandidate The index of the candidate in candidates.
	 */
	public void getSchedule(int indexOfCandidate)
	{
		for (int i = 0; i < schedule.numberOfAvailableTimeslots(); i++)
		{
			sendMessageToServer(schedule.getAvailableTimes(i), 
				candidates.get(indexOfCandidate).getConnectionToCandidate());
		}
		
		sendMessageToServer("Please use the command #submitschedulerequest <...> to respond."
			+ " Ensure that the date and time are copied exactly in place of ... (ex. <04/12/28 2:00>) ",
			candidates.get(indexOfCandidate).getConnectionToCandidate());
	}
	
	/**
	 * This method receives the candidate's response indicating the time they
	 * wish to schedule an appointment for. 
	 * 
	 * @param response The time the candidate wishes to schedule an appointment for.
	 * @param indexOfCandidate The index of the candidate in candidates.
	 */
	public void scheduleAppointment(String response, int indexOfCandidate)
	{
		boolean successfullyScheduled = schedule.scheduleInterview(response, candidates.get(indexOfCandidate));
		
		if (successfullyScheduled)
		{
			candidates.get(indexOfCandidate).setIsScheduled(true);
			sendMessageToServer("Successfully scheduled an interview for: " + response
				+ "\n" + "Sending a confirmation email to the address listed in your account.",
				candidates.get(indexOfCandidate).getConnectionToCandidate());
			sendScheduleConfirmationEmail(candidates.get(indexOfCandidate));
		}
		
		else
		{
			sendMessageToServer("Your response was: " + response
					+ "\n" + "This time is not currently available, please try to schedule again."
					+ "\n" + "Use the command #requestschedule to print the list of available times again.",
					candidates.get(indexOfCandidate).getConnectionToCandidate());
		}
	}
	
	/**
	 * Prints the candidate username, password, email address, and score achieved to
	 * the Client. 
	 * [FOR DEBUGGING PURPOSES.]
	 * 
	 * @param indexOfCandidate The index of the candidate in candidates to print information about.
	 */
	public void getCandidateInformation(int indexOfCandidate)
	{
		sendMessageToServer(candidates.get(indexOfCandidate).toString(), 
			candidates.get(indexOfCandidate).getConnectionToCandidate());
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
	
	/**
	 * This method creates (and thus sends) an email which either asks the user to schedule
	 * an interview (if they have passed the application) or thanks the user for trying
	 * (if they have not passed the application.
	 * 
	 * @param candidate The candidate to send the email to.
	 */
	public void sendRequestForInterviewEmail(Candidate candidate)
	{
		emails.add(new Email(candidate));
	}
	
	/**
	 * This method creates (and thus sends) an email confirming the appointment time
	 * that the user scheduled an interview for.
	 * 
	 * @param candidate The candidate to send the email to.
	 */
	public void sendScheduleConfirmationEmail(Candidate candidate)
	{
		emails.add(new Email(candidate, candidate.getScheduledTime()));
	}
	
	/**
	 * Adds a qualification standard to the application process.
	 * Should only be called when there are no users already in the process
	 * of filling out an application (when a new round of applications is started).
	 * 
	 * [V2.0 IDEA ONLY]
	 * 
	 * @param description
	 */
	public void addStandard(String description)
	{
		
	}
	
	/**
	 * Adds a question to the application process.
	 * Should only be called when there are no users already in the process
	 * of filling out an application (when a new round of applications is started).
	 * 
	 * [V2.0 IDEA ONLY]
	 * 
	 * @param description
	 * @param pointValue
	 */
	public void addQuestion(String description, int pointValue)
	{
		
	}
	
	/**
	 * Adds an interview time which users may select when scheduling an interview 
	 * appointment.
	 * 
	 * @param time The time to add in the format dd/mm/yy hour:minute (ex. 09/12/16 2:00)
	 * @param interviewer The name of the interviewer who will be conducting the interview.
	 */
	public void addInterviewTimeslot(String time, String interviewer)
	{
		schedule.addInterviewTime(time, interviewer);
	}
}


















