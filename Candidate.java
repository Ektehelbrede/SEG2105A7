/**
 * Candidate is responsible for holding all of the information
 * about an individual user, it acts as an "Account" for 
 * that user.
 * 
 * @author 
 */
public class Candidate 
{
	// Associations
	private Application application;	// * -- 1 Application
	private Score score;				// 1 -- 1 Score
	
	// --------------
	private String username;
	private String password;
	private String emailAddress;
	
	private ConnectionToClient client;
	
	private int scoreAchieved;			// Score from the application questions.
	private boolean isQualified;		// True if the candidate passes the application
	private boolean isScheduled;		// True if the candidate has an appointment
	
	public Candidate(String username, String password, String emailAddress, ConnectionToClient client, 
		Application application)
	{
		this.client = client;
		this.application = application;
		
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.isQualified = false;
		this.isScheduled = false;
		this.scoreAchieved = -1;
	}
	
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	public String getEmailAddress() { return this.emailAddress; }
	public ConnectionToClient getConnectionToCandidate() { return this.client; }
	public int getScoreAchieved() { return this.scoreAchieved; }
	public boolean getIsQualified() { return this.isQualified; }
	public boolean getIsScheduled() { return this.isScheduled; }
	public Score getScore() { return this.score; }
	
	public void setIsQualified(boolean qualified) { this.isQualified = qualified; }
	public void setScore(Score score) { this.score = score; }
}
