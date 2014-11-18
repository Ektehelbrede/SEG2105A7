import java.util.*;

/**
 * Score simply keeps track of the number of points that a user has 
 * obtained while answering Questions. 
 * 
 * After all Questions have been answered, Score will ensure that the 
 * Candidate has a record of the number of points they have scored, 
 * and if that number of points qualifies them for an interview.
 * 
 * @author 
 */
public class Score 
{
	// Associations
	private Application application;			// * -- 1 Application
	private List<Question> listOfQuestions;		// 1 -- * Question
	
	// --------------
	private int currentScoreTotal;
	
	public Score()
	{
		
	}
}
