/**
 * A Standard is a requirement on a user which must be true, else
 * the user does not qualify for the job.
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */
public class Standard 
{
	// Associations
	private Application application;	
	
	// --------------
	private String description;
	
	public Standard(String description, Application application)
	{
		this.description = description;
		this.application = application;
	}
	
	// Get Methods
	public String getDescription() { return this.description; }
}
