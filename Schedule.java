import java.util.*;

/**
 * Schedule is responsible for keeping track of all of the available 
 * Timeslots for appointments, as well as all of the unavailable 
 * (scheduled) Timeslots for appointments.
 * 
 * If a user requests an appointment, the appointment should become
 * unavailable, and the Emailer will be responsible for sending an
 * email to confirm the appointment.
 * 
 * @author 
 */
public class Schedule 
{
	private Application application;
	
	private List<Timeslot> availableTimeslots;	// Times that have not been scheduled
	private List<Timeslot> scheduledTimeslots; 	// Times that have been scheduled
	
	public Schedule(Application application)
	{
		this.application = application;
		this.availableTimeslots = new ArrayList<Timeslot>();
		this.scheduledTimeslots = new ArrayList<Timeslot>();
		
		// Create a list of default Timeslots to be used to schedule interviews.
		this.availableTimeslots.add(new Timeslot("08/05/15 4:00", "Jim Hudson", null));
		this.availableTimeslots.add(new Timeslot("08/05/15 5:00", "Jim Hudson", null));
		this.availableTimeslots.add(new Timeslot("09/05/15 3:00", "Kevin Thatcher", null));
		this.availableTimeslots.add(new Timeslot("09/05/15 4:00", "Margaret Chell", null));
		this.availableTimeslots.add(new Timeslot("09/05/15 5:00", "Margaret Chell", null));
	}
	
	/**
	 * Get an available interview time.
	 * 
	 * @param index The index of the available interview time to return.
	 * @return The available interview time located at the given index.
	 */
	public String getAvailableTimes(int index)
	{
		return availableTimeslots.get(index).getTime();
	}
	
	/**
	 * @return The size of the available timeslot list.
	 */
	public int numberOfAvailableTimeslots()
	{
		return availableTimeslots.size();
	}
	
	/**
	 * Add a timeslot to the available timeslots. To be used by the server owner
	 * (Connect Inc) to add interview times as they become available.
	 * 
	 * @param time The Timeslot storing when the interview will occur, and which interviewer will conduct the interview. 
	 */
	public void addToAvailableTimeslots(Timeslot time)
	{
		availableTimeslots.add(time);
	}
	
	/**
	 * Add a timeslot to the scheduled timeslots (unavailable).
	 * 
	 * @param time The timeslot to add.
	 */
	public void addToScheduledTimeslots(Timeslot time)
	{
		scheduledTimeslots.add(time);
	}
	
	/**
	 * Scans through the list of available timeslots, and attempts to find one which
	 * matches the user's request. If it does, will move that timeslot to the list of
	 * unavailable times to prevent another candidate from scheduling the same interview
	 * time.
	 * 
	 * @param response The user's time request.
	 * @param candidate The candidate which requested this interview slot.
	 * @return wasAdded Indicates if the time was successfully scheduled.
	 */
	public boolean scheduleInterview(String response, Candidate candidate)
	{
		boolean wasAdded = false;
		
		for (int i = 0; i < availableTimeslots.size(); i++)
		{
			if (availableTimeslots.get(i).getTime().equals(response))
			{
				availableTimeslots.get(i).setIntervieweeName(candidate.getUsername());
				addToScheduledTimeslots(availableTimeslots.remove(i));
				candidate.setInterviewTime(scheduledTimeslots.get(scheduledTimeslots.size() - 1));
				wasAdded = true;
			}
		}
		
		return wasAdded;
	}
	
	/**
	 * Calls the method to add a new interview time to the list of available times.
	 * Used by the server (Connect Inc). Creates a new Timeslot based on the 
	 * provided information.
	 * 
	 * @param time The time to add.
	 * @param interviewer The interviewer who will be conducting the interview. 
	 */
	public void addInterviewTime(String time, String interviewer)
	{
		addToAvailableTimeslots(new Timeslot(time, interviewer, null));
	}
	
	/**
	 * Method which cancelled an interview appointment.
	 * 
	 * [V2.0 IDEA ONLY]
	 * 
	 * @param response
	 * @return
	 */
	public boolean cancelInterview(String response)
	{
		boolean wasRemoved = false;
		
		
		return wasRemoved;
	}
}
