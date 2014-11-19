import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Email is responsible for creating a standard email to be
 * sent to an existing user, and sending it. This email will be for either 
 * completion of an application, or for scheduling an appointment.
 * 
 * @author
 */
public class Email 
{
	private String bodyOfEmail;
	
	/**
	 * Creates a personalized email for a particular candidate. This email is 
	 * confirmation of an interview appointment. The email is then sent.
	 * 
	 * @param candidateToPersonalizeEmailFor The Candidate to send the email to.
	 * @param timeScheduled The time that the candidate scheduled the interview for.
	 */
	public Email(Candidate candidateToPersonalizeEmailFor, Timeslot timeScheduled)
	{
		createScheduleConfirmationEmail(candidateToPersonalizeEmailFor, timeScheduled);
		sendEmail(candidateToPersonalizeEmailFor, "Connect Appointment Confirmation");
	}
	
	/**
	 * Creates a personalized email for a particular candidate. This email is 
	 * confirmation of the candidate successfully passing the application form,
	 * and requesting that they schedule an interview time. The email is then sent.
	 * 
	 * @param candidateToPersonalizeEmailFor The candidate to send the email to.
	 */
	public Email(Candidate candidateToPersonalizeEmailFor)
	{
		createRequestForInterviewEmail(candidateToPersonalizeEmailFor);
		sendEmail(candidateToPersonalizeEmailFor, "Connect Application Success!");
	}
	
	/**
	 * @return bodyOfEmail The body of the created email.
	 */
	private String getBodyOfEmail()
	{
		return this.bodyOfEmail;
	}
	
	/**
	 * Creates a personalized email for a particular candidate. This email is 
	 * confirmation of an interview appointment.
	 * 
	 * @param candidateToPersonalizeEmailFor The Candidate information to personalize the email.
	 * @param timeScheduled The time that the candidate scheduled the interview for.
	 */
	private void createScheduleConfirmationEmail(Candidate candidateToPersonalizeEmailFor,
		Timeslot timeScheduled)
	{
		this.bodyOfEmail = "" + candidateToPersonalizeEmailFor.getUsername() + ",\n"
			+ "Thank you for scheduling an appointment for: " + timeScheduled.getTime()
			+ "\n\n" + "I look forward to meeting you" + ",\n" 
			+ timeScheduled.getInterviewerName();
	}
	
	/**
	 * Creates a personalized email for a particular candidate. This email is 
	 * confirmation of the candidate successfully passing the application form,
	 * and requesting that they schedule an interview time.
	 * 
	 * @param candidateToPersonalizeEmailFor The Candidate information to personalize the email.
	 */
	private void createRequestForInterviewEmail(Candidate candidateToPersonalizeEmailFor)
	{
		this.bodyOfEmail = "" + candidateToPersonalizeEmailFor.getUsername() + ",\n"
			+ "Thank you for completing the application questions. Connect HR would "
			+ "like to request that you schedule an interview appointment at your "
			+ "earliest convenience." + "\n\n" + "In order to schedule an appointment "
			+ "please log into the account that you used to complete the application "
			+ "questions with and use the <#schedule> function." + "\n\n"
			+ "Thank you for considering Connect," + "\n" + "Connect HR";
	}
	
	/**
	 * Send the email using JavaMailAPI.
	 * 
	 * @param candidate Candidate to send the email to.
	 */
	private void sendEmail(Candidate candidate, String subject)
	{
		// Candidate's email address to send to.
		String to = candidate.getEmailAddress();
		
		// Connect's email address to be sent from.
		String from = "connect.inc.hr@gmail.com";
		final String username = "connect.inc.hr";
		final String password = "seg2105a7";
		
		// Send the email through relay.jangosmtp.net
		String host = "smtp.gmail.com";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		
		// Get the Session object
		Session session = Session.getInstance(properties, 
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
			});
		
		try
		{
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			
			// Set the various From, To, Subject, and body fields.
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(this.getBodyOfEmail());
			
			// Send the email.
			Transport.send(message);
		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}
}





















