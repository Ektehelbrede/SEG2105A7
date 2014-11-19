public class TestingDuringProgramming
{
	public static void main(String[] args)
	{
		Candidate candidate = new Candidate("John Street", "1234", "michael.mckee92@gmail.com");
		Timeslot timeslot = new Timeslot("March 3 2018, 4:00pm", "Higgs Boson", "John Street");
		
		new Email(candidate);
		new Email(candidate, timeslot);
	}
}
