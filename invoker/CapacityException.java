package invoker;

public class CapacityException extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// venue capacity case
	public CapacityException(Venue venue)
	{
		super("Venue capacity only for:" + venue.getCapacity());
	}
	
	// course capacity case
	public CapacityException(CourseOffering co)
	{
		super("Course capacity only for:" + co.getMaxnum());
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}
	
}
