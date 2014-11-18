import java.util.*;

/**
 * Appication is responsible for interacting with the user (through OCSF)
 * in order to allow the user to complete an application, schedule an appointment,
 * cancel an appointment, etc.
 * 
 * @author 
 */
public class Application 
{
	// Associations
	private List<Email> emails;				// 1 -- * Email
	private List<Candidate> candidates;		// 1 -- * Candidate
	private List<Standard> standards;		// 1 -- 2..* Standard
	private List<Score> scores;				// 1 -- * Score
	
	public Application()
	{
		
	}
	
	/**
	 * 
	 * 
	 * @param candidate The candidate from OCSF Server
	 * @return 
	 */
	public boolean apply(Candidate candidate)
	{
		boolean 
	}
}
