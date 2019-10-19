package invoker;

import java.io.Serializable;
import java.util.ArrayList;

public class Venue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	// Instance variables
	private String location;
	private int capacity;
	private String purpose;
	ArrayList<Lesson> lessons = new ArrayList<Lesson>();
	
	public Venue(String location,int capacity, String purpose)
	{
		this.location = location;
		this.capacity = capacity;
		this.purpose = purpose;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public String getPurpose()
	{
		return purpose;
	}
		
	public void addLesson(Lesson l)
	{
		lessons.add(l);
	}
	
	public ArrayList<Lesson> getLessons()
	{
		return lessons;
	}
	
	@Override
	public String toString()
	{
		return "Location: "+getLocation()+", Capacity: "+getCapacity()+", Purpose: "+getPurpose();
	}
}
