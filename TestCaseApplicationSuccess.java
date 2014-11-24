/**
 * This TestCase has a user successfully going through the process of
 * submitting qualifications and responses to the application questions.
 * 
 * An email will be sent to the email address if the testcase is successful.
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */
public class TestCaseApplicationSuccess
{
	public static void main(String[] args)
	{
		ClientConsole client;
		
		System.out.println("-- Creating Server and Client --");
		new ServerConsole(1520);
		client = new ClientConsole("client", "localhost", 1520);
		
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		System.out.println("-- Creating an account. Username: Arliden Sinclair; Password: 1234; Email Address: "
				+ "connect.inc.hr@gmail.com --");
		client.handleMessageFromTestCases("#createaccount <Arliden Sinclair, 1234; connect.inc.hr@gmail.com>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		System.out.println("-- Logging in. Username: Arliden Sinclair; Password: 1234 --");
		client.handleMessageFromTestCases("#login <Arliden Sinclair, 1234>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		System.out.println("-- Submitting response to standards: yes --");
		client.handleMessageFromTestCases("#submitqualificationresponse <yes>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		System.out.println("-- Submitting application: answering yes to every question --");
		client.handleMessageFromTestCases("#submitapplication <yyyyy>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

		
		System.out.println("Test has been completed, an email has been sent if successful.");
	}
}
