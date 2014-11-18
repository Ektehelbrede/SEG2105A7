import java.util.*;

/**
 * 1 -- * Candidate
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
	private List<Candidate> candidates;
	private List<Email> emails;
	
	private List<Timeslot> availableTimeslots;	// Times that have not been scheduled
	private List<Timeslot> scheduledTimeslots; 	// Times that have been scheduled
	
	public Schedule()
	{
		
	}
}
