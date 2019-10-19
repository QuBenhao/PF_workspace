package invoker;

public class ClashException extends Exception {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Lesson clash happens when there is already a lesson for the course at the time period
	public ClashException(Lesson lesson)
	{
		super("Already has a lesson for this time period: " + lesson.getCourseOffering().getCourse().getID());
	}
	
	// Venue clash happens when there is already a lesson for the venue at the time period
	public ClashException(Venue venue)
	{
		super("Venue already been taken: "+venue.getLocation() );
	}
	
	// Staff clash happens when there is already a lesson for the staff at the time period
	public ClashException(Staff staff)
	{
		super("Staff already been taken: "+staff.geteNo());
	}
	
	// Student clash happens when there is already a lesson for the student at the time period
	public ClashException(Student student)
	{
		super("Cannot enrol to different lessons at the same time: " + student.getID());
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}

}
