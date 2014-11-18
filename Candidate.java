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
	
	private int scoreAchieved;
	private boolean isQualified;
	
	public Candidate(String username, String password, String emailAddress)
	{
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
	}
}
