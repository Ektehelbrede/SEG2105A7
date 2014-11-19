/**
 * A Standard is a requirement on a user which must be true, else
 * the user does not qualify for the job.
 * 
 * @author 
 */
public class Standard 
{
	// Associations
	private Application application;	// 2..* -- 1 Application
	
	// --------------
	private String description;
	private Boolean isFulfilled;
	
	public Standard(String description)
	{
		this.description = description;
	}
	
	public String getDescription() { return this.description; }
	public boolean getIsFulfilled() { return this.isFulfilled; }
	
	public void setIfFulfilled(boolean answer) { this.isFulfilled = answer; }
}
