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
	
	public Standard(String description, Application application)
	{
		this.description = description;
		this.application = application;
	}
	
	public String getDescription() { return this.description; }
}
