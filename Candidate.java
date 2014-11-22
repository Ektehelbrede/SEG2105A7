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
	private Timeslot scheduledTime;		// 1 -- 1 Timeslot
	
	// --------------
	private String username;
	private String password;
	private String emailAddress;
	
	private ConnectionToClient client;
	
	private int scoreAchieved;			// Score from the application questions.
	private boolean isQualified;		// True if the candidate passes the qualification standards.
	private boolean meetsMinimumScore;	// True if the candidate meets the minimum application question score.
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
		this.meetsMinimumScore = false;
		this.isScheduled = false;
		this.scoreAchieved = -1;
	}
	
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	public String getEmailAddress() { return this.emailAddress; }
	public ConnectionToClient getConnectionToCandidate() { return this.client; }
	public int getScoreAchieved() { return this.scoreAchieved; }
	public boolean getIsQualified() { return this.isQualified; }
	public boolean getMeetsMinimumScore() { return this.meetsMinimumScore; }
	public boolean getIsScheduled() { return this.isScheduled; }
	public Score getScore() { return this.score; }
	public Timeslot getScheduledTime() { return this.scheduledTime; }
	
	public void setIsQualified(boolean qualified) { this.isQualified = qualified; }
	public void setMeetsMinimumScore(boolean meetsMinimumScore) { this.meetsMinimumScore = meetsMinimumScore; }
	public void setScore(Score score) { this.score = score; }
	public void setConnection(ConnectionToClient client) { this.client = client; }
	public void setScoreAchieved(int score) { this.scoreAchieved = score; }
	public void setIsScheduled(boolean isScheduled) { this.isScheduled = isScheduled; }
	public void setInterviewTime(Timeslot timeslot) { this.scheduledTime = timeslot; }
	
	public String toString()
	{
		String candidate = "Username: " + getUsername() + "\n"
			+ "Password: " + getPassword() + "\n"
			+ "Email: " + getEmailAddress() + "\n"
			+ "ScoreAchieved: " + getScoreAchieved() + "\n"
			+ "isQualified: " + getIsQualified() + "\n"
			+ "meetsMinimumScore: " + getMeetsMinimumScore() + "\n"
			+ "isScheduled: " + getIsScheduled()
			+ "Timeslot: " + getScheduledTime();
				
		return candidate;
	}
}
