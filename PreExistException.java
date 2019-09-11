package invoker;

import java.util.HashMap;

public class PreExistException extends Exception {
	
	HashMap<String, Course> courses;
	HashMap<String, Venue> venues;
	HashMap<String, Staff> staffs;
	private String check = null;
	
	public PreExistException(String courseID,HashMap<String, Course> courses)
	{
		this.courses = courses;
		this.check = courseID;
	}

}
