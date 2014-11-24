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
	private Application application;			
	private List<Question> listOfQuestions;		
	private Candidate candidate;				
	
	// --------------
	private final int MINIMUM_SCORE = 6;
	private int currentScoreTotal;
	
	public Score(Candidate candidate, Application application)
	{
		this.candidate = candidate;
		this.application = application;
		listOfQuestions = new ArrayList<Question>();
		currentScoreTotal = 0;
		
		listOfQuestions.add(new Question("Do you have any relevant past work experience?", 3, this));
		listOfQuestions.add(new Question("Do you have more than 40 hours of commmunity service?", 1, this));
		listOfQuestions.add(new Question("Do you possess a college diploma or greater?", 3, this));
		listOfQuestions.add(new Question("Do you speak more than one language?", 2, this));
		listOfQuestions.add(new Question("Are you fluent in french?", 3, this));	// CURRENT MAXIMUM OF 12 POINTS. SUGGESTING A MINIMUM OF 6?
	}
	
	/**
	 * Evaluate's the candidate's responses to the list of questions.
	 * 
	 * @param responses The candidate's responses.
	 */
	public void evaluate(String responses)
	{
		currentScoreTotal = 0;
		
		for (int i = 0; i < listOfQuestions.size(); i++)
		{
			if (responses.charAt(i) == 'y')
			{
				currentScoreTotal += listOfQuestions.get(i).getPointValue();
			}
		}
		
		candidate.setScoreAchieved(currentScoreTotal);
		
		if (currentScoreTotal >= MINIMUM_SCORE)
		{
			candidate.setMeetsMinimumScore(true);
		}
	}
	
	public String getQuestion(int indexOfQuestion)
	{
		return listOfQuestions.get(indexOfQuestion).getDescription();
	}
	
	public int getMinimumScore() 
	{ 
		return this.MINIMUM_SCORE; 
	}
	
	public int numberOfQuestions()
	{
		return this.listOfQuestions.size();
	}
}
