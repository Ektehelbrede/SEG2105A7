/**
 * A Question is worth a certain number of points, depending on 
 * the answer given.
 * 
 * @author 
 */
public class Question 
{
	// Associations
	private Score score;	// * -- 1 Score
	
	// --------------
	private String description;
	private int pointValue;
	
	public Question(String description, int pointValue, Score score)
	{
		this.score = score;
		this.description= description;
		this.pointValue= pointValue;
		
	}
	
	public String getDescription(){return description;}
	public int getPointValue(){return pointValue;}
}
