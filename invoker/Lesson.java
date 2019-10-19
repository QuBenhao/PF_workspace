package invoker;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Lesson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	// Instance variables
	private int day;
	private double startHour;
	private double endHour;
	private Staff staff;
	private Venue venue;
	private CourseOffering co;
	
	// constructor
	public Lesson(int day ,double startHour, double endHour, Venue venue, CourseOffering co) throws ClashException{
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		System.out.println("As no lessons information is offering, staff set to be empty");
		this.staff = null;
		this.venue = venue;
		this.co = co;
		this.checkVenClash();
	}
	
	public Lesson(int day ,double startHour, double endHour, Staff staff, Venue venue, CourseOffering co, ArrayList<Lesson> lessons) throws ClashException
	{
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.venue = venue;
		this.co = co;
		this.checkVenClash();
		this.setStaff(staff, lessons);
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
	
	public void setStaff(Staff s,ArrayList<Lesson> lessons) throws ClashException
	{
		this.staff = s;
		this.checkStaffClash(lessons);
	}
	
	protected void resetVenue()
	{
		this.venue = null;
	}
	
	abstract String printType();
	
	abstract void listStudents();
		
	// If the venue is already been used in this time period, it will result in a ClashException
	private void checkVenClash() throws ClashException
	{
		if(this.venue!=null)
		{
			Lesson check = null;
			ClashException ex = new ClashException(this.venue);
			try
			{
				for(int index=0;index<venue.getLessons().size();index++)
				{	
					check = venue.getLessons().get(index);
					// skip the lesson itself
					if(check == this)
						continue;
					if(check.getDay()==this.day)
					{
						// based on time period clash
						if((this.startHour>=check.getStart()&&this.startHour<check.getEnd())||(this.endHour<=check.getEnd()&&this.endHour>check.getStart()))
							throw ex;
					}
				}
			}
			catch(Exception ex0)
			{
				if(check!=null)
					System.out.println(ex.toString()+", Lesson: " + check.toString());
				System.out.println("Reset venue to empty: " + this.co.getCourse().getID() + ", " + this.toString() );
				this.resetVenue();
			}
		}
	}
	
	private void checkStaffClash(ArrayList<Lesson> lessons) throws ClashException
	{
		if(this.staff!=null)
		{
			Lesson check = null;
			ClashException ex = new ClashException(this.staff);
			try
			{
				for(int index=0;index<lessons.size();index++)
				{
					check = lessons.get(index);
					if(check == this)
						continue;
					// skip the lesson itself
					if(check.getStaff()!=null && check.getStaff().geteNo().compareTo(this.staff.geteNo())==0 && check.getDay()==this.day)
					{
						// based on time period clash
						if((this.startHour>=check.getStart()&&this.startHour<check.getEnd())||(this.endHour<=check.getEnd()&&this.endHour>check.startHour))
							throw ex;
					}
				}
				check = null;
			}
			catch(Exception ex0)
			{
				if(check!=null)
					System.out.println(ex.toString()+", Lesson: " + check.toString());
				System.out.println("Reset staff to empty: " + this.co.getCourse().getID() + ", " + this.toString() );
				this.staff = null;
			}
		}
	}
	
	// transfer Integer to String
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
	
	// calculate double to Hour
	protected int hour(double i)
	{
		return (int)i;
	}
	
	// calculate double to Minute
	protected int minute(double i)
	{
		double temp;
		int x;
		// as minute is formated as .xx
		temp = i*100;
		temp %= 100;
		x = (int)temp;
		return x;
	}
	
	@Override
	public String toString()
	{
		return "Type: "+ printType() +", Day: " +week(getDay()-1) + ", Start Time: "+hour(startHour) +":"+minute(startHour) + ", End Time: " + hour(endHour) +":"+ minute(endHour);                               
	}
}
