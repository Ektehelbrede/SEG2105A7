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
		ServerConsole server;
		ClientConsole client;
		
		server = new ServerConsole(1520);
		client = new ClientConsole("client", "localhost", 1520);
		
		client.handleMessageFromTestCases("hello?");
		client.handleMessageFromTestCases("#createaccount <Arliden Sinclair, 1234, michael.mckee92@gmail.com>");
		client.handleMessageFromTestCases("#login <Arliden Sinclair, 1234>");
		client.handleMessageFromTestCases("#submitqualificationresponse <yes>");
		client.handleMessageFromTestCases("#submitapplication <yyyyy>");

		
		System.out.println("Test has been completed, an email has been sent if successful.");
	}
}
