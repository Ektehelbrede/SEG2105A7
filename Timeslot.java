/**
 * A Timeslot is an available time period during which an interview
 * can take place. 
 * 
 * @author Daniel Rosales and Michael Mckee
 * @version November 2014
 */
public class Timeslot 
{
	private String time;
	
	private String interviewerName;
	private String intervieweeName;
	
	public Timeslot(String time, String interviewer, String interviewee)
	{
		this.time = time;
		this.interviewerName = interviewer;
		this.intervieweeName = interviewee;
	}
	
	// Get Methods
	public String getTime() { return this.time; }
	public String getInterviewerName() { return this.interviewerName; }
	public String getIntervieweeName() { return this.intervieweeName; }
	
	// Set Methods
	public void setIntervieweeName(String name) { this.intervieweeName = name; }
	
	/**
	 * Creates a string representation of this Timeslot.
	 * 
	 * @return string representation of this timeslot.
	 */
	public String toString()
	{
		return this.time + ":" + this.interviewerName + ":" + this.intervieweeName;
	}
}
