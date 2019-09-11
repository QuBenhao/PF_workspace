package invoker;

abstract class Lesson {

	// Instance variables
	private int day;
	private double startHour;
	private double endHour;
	private Staff staff;
	private Venue venue;
	private CourseOffering co;
	
	// constructor
	public Lesson(int day ,double startHour, double endHour, Staff staff, Venue venue, CourseOffering co){
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.staff = staff;
		this.venue = venue;
		this.co = co;
	}
	
	
	public int getDay()
	{
		return day;
	}
	
	public double getStart()
	{
		return startHour;
	}
	
	public double getEnd()
	{
		return endHour;
	}
	
	public Staff getStaff()
	{
		return staff;
	}
	
	public Venue getVenue()
	{
		return venue;
	}
	
	public CourseOffering getCourseOffering()
	{
		return co;
	}
	
	public void setStaff(Staff s)
	{
		this.staff = s;
	}
	
	
	abstract String printType();
	
	protected String week(int i)
	{
		String[] week = new String[7];
		week[0]="Monday";
		week[1]="Tuesday";
		week[2]="Wednesday";
		week[3]="Thursday";
		week[4]="Friday";
		week[5]="Saturday";
		week[6]="Sunday";
		return week[i];
	}
	
	protected int hour(double i)
	{
		return (int)i;
	}
	
	protected int minute(double i)
	{
		double temp;
		temp = i-(int)i;
		temp *= 100;
		return (int)temp;
	}
	
	@Override
	public String toString()
	{
		return "Type: "+ printType() +", Day: " +week(getDay()-1) + ", Start Time: "+hour(getStart()) +":"+minute(getStart()) + ", End Time: " + hour(getEnd()) +":"+minute(getEnd());                               
	}
}
