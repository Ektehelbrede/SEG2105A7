public class TestEmail
{
	public static void main(String[] args)
	{
		Candidate candidate = new Candidate("Test Email", "password", "michael.mckee92@gmail.com", null, null);
		Timeslot timeslot = new Timeslot("monday", "bob", "jim");
		new Email(candidate, timeslot);
	}
}
