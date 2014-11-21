import java.util.*;

/**
 * 1 -- 1 Application
 * 1 -- * Email
 * 1 -- * Timeslot
 * 
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
		
		this.availableTimeslots.add(new Timeslot("08/05/15 4:00", null, "Jim Hudson"));
		this.availableTimeslots.add(new Timeslot("08/05/15 5:00", null, "Jim Hudson"));
		this.availableTimeslots.add(new Timeslot("09/05/15 3:00", null, "Kevin Thatcher"));
		this.availableTimeslots.add(new Timeslot("09/05/15 4:00", null, "Margaret Chell"));
		this.availableTimeslots.add(new Timeslot("09/05/15 5:00", null, "Margaret Chell"));
	}
	
	public String getAvailableTimes(int index)
	{
		return availableTimeslots.get(index).getTime();
	}
	
	public void addToAvailableTimeslots(Timeslot time)
	{
		
	}
	
	public void addToScheduledTimeslots(Timeslot time)
	{
		scheduledTimeslots.add(time);
	}
	
	public boolean scheduleInterview(String response)
	{
		boolean wasAdded = false;
		
		for (int i = 0; i < availableTimeslots.size(); i++)
		{
			if (availableTimeslots.get(i).getTime().equals(response))
			{
				addToScheduledTimeslots(availableTimeslots.remove(i));
				wasAdded = true;
			}
		}
		
		return wasAdded;
	}
	
	public boolean cancelInterview(String response)
	{
		boolean wasRemoved = false;
		
		
		return wasRemoved;
	}
	
	public int numberOfAvailableTimeslots()
	{
		return availableTimeslots.size();
	}
}
