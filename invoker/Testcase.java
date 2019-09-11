package invoker;

import java.util.HashMap;

public class Testcase{
	
	// Initialize
	public Testcase(HashMap<String, Course> coursecase, HashMap<String, Venue> venuecase, HashMap<String, Staff> staffcase ) 
	{
		coursecase.put("P101",new Course("P101", "Programming 1", "Teach Basic Programming"));
		coursecase.put("P102", new Course("P102", "Programming 2", "Teach Intermediate Programming"));
		coursecase.put("S101",new Course("S101", "Software Engineering", "Teach UML and Modelling"));	
		coursecase.put("WP1",new Course("WP1", "Web Programming", "Teach Web Technologies"));	
		coursecase.put("UI1",new Course("UI1", "User Interface", "Teach UI Principles"));	
		coursecase.put("Math",new Course("Math","Discret Maths","Teach Maths needed for CS"));	
		coursecase.put("Net1",new Course("Net1", "Networking","Teach networking principles"));	
			
		venuecase.put("12.10.02",new Venue("12.10.02",100,"Interactive"));
		venuecase.put("12.10.03",new Venue("12.10.03",200,"Interactive"));	
		venuecase.put("10.10.22",new Venue("10.10.22",36,"Traditional"));
		venuecase.put("10.10.23",new Venue("10.10.23",36,"Traditional"));
		
		staffcase.put("e44556",new Staff("e44556","Tim O'Connor","Lecturer","14.13.11"));
		staffcase.put("e44321",new Staff("e44321","Richard Cooper","Professor","14.13.12"));
		staffcase.put("e54321",new Staff("e54321","Jane Smith","Lecturer","14.13.13"));
		staffcase.put("e53457",new Staff("e53457", "Isaac Newton", "Associate Professor", "14.13.14"));	
	}
}
