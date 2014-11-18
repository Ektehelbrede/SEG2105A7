/**
 * Email is responsible for creating a standard email to be
 * sent to an existing user, and sending it. This email will be for either 
 * completion of an application, or for scheduling an appointment.
 * 
 * @author
 */
public class Email 
{
	// Associations
	private Application application;	// * -- 1 Application
	
	// --------------
	private String bodyOfEmail;
	
	public Email()
	{
		
	}
}
