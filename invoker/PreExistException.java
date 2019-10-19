package invoker;

import java.util.HashMap;

public class PreExistException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, Course> courses;
	private HashMap<String, Venue> venues;
	private HashMap<String, Staff> staffs;
	private String courseID;
	private String location;
	private String staffID;
	
	// Course PreExist
	public PreExistException(int i, String courseID, HashMap<String, Course> courses)
	{
		super("The course already exists. ");
		this.courses = courses;
		this.courseID = courseID;
	}
	
	// Venue PreExist
	public PreExistException(String location, int i, HashMap<String, Venue> venues)
	{
		super("The venue already exists. ");
		this.location = location;
		this.venues = venues;
	}
	
	// Staff PreExist
	public PreExistException(String staffID,HashMap<String, Staff> staffs, int i)
	{
		super("The staff already exists. ");
		this.staffID = staffID;
		this.staffs = staffs;
	}
	
	// HashMap containsKey
	public boolean checkCourse() 
	{
		return courses.containsKey(courseID);
	}
	
	public boolean checkVenue()
	{
		return venues.containsKey(location);
	}
	
	public boolean checkStaff()
	{
		return staffs.containsKey(staffID);
	}
	
	public String coursetoString()
	{
		return super.toString()+courses.get(courseID).toString() ;
		
	}
	
	public String venuetoString()
	{
		return super.toString()+venues.get(location).toString() ;		
	}
	
	public String stafftoString()
	{
		return super.toString()+staffs.get(staffID).toString() ;		
	}
}
