/**
 * This TestCase has a user successfully going through the process of
 * submitting qualifications and responses to the application questions.
 * Then the user successfully schedules an interview appointment.
 * 
 * Two emails will be sent to the email address if the testcase is successful.
 * One email will confirm success of the application process, while the other
 * confirms the scheduled appointment.
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */
public class TestCaseScheduleSuccess
{
	public static void main(String[] args)
	{
		ClientConsole client;
		
		new ServerConsole(1520);
		client = new ClientConsole("client", "localhost", 1520);
		
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		client.handleMessageFromTestCases("#createaccount <Arliden Sinclair, 1234; connect.inc.hr@gmail.com>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		client.handleMessageFromTestCases("#login <Arliden Sinclair, 1234>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		client.handleMessageFromTestCases("#submitqualificationresponse <yes>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		client.handleMessageFromTestCases("#submitapplication <yyyyy>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		client.handleMessageFromTestCases("#submitschedulerequest <08/05/15 4:00>");
		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

		
		System.out.println("Test has been completed, an email has been sent if successful.");
	}
}
