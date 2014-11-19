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
	private Application application;	// * --- 1 Application
	
	// --------------
	private String username;
	private String password;
	private String emailAddress;
	
	private int scoreAchieved;			// Score from the application questions.
	private boolean isQualified;		// True if the candidate passes the application
	private boolean isScheduled;		// True if the candidate has an appointment
	
	public Candidate(String username, String password, String emailAddress)
	{
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		
		this.scoreAchieved = -1;
		this.isQualified = false;
		this.isScheduled = false;
	}
	
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	public String getEmailAddress() { return this.emailAddress; }
	public int getScoreAchieved() { return this.scoreAchieved; }
	public boolean getIsQualified() { return this.isQualified; }
	public boolean getIsScheduled() { return this.isScheduled; }
}
