/**
 * A Timeslot is an available time period during which an interview
 * can take place. 
 * 
 * @author 
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
	
	public String getTime() { return this.time; }
	public String getInterviewerName() { return this.interviewerName; }
	public String getIntervieweeName() { return this.intervieweeName; }
	
	public void setIntervieweeName(String name) { this.intervieweeName = name; }
	
	public String toString()
	{
		return this.time + ":" + this.interviewerName + ":" + this.intervieweeName;
	}
}
