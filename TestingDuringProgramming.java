public class TestingDuringProgramming
{
	public static void main(String[] args)
	{
		String testString = "<yyyyy>";
		String beingPassed = testString.substring(testString.indexOf("<") + 1, testString.indexOf(">"));
		
		System.out.println(beingPassed);
		
		for (int i = 0; i < beingPassed.length(); i++)
		{
			System.out.println(beingPassed.charAt(i));
		}
	}
}
