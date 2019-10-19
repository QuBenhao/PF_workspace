package invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class VenueApp extends Application {

	Scanner input;
	private final String course = "course";
	private static String venue = "venue";

	private HashMap<String,Course> courses;
	private HashMap<String,Venue> venues;
	private ArrayList<Lesson> lessons;
	String error;
	
	public VenueApp(HashMap<String,Course>courses,HashMap<String,Venue> venues,ArrayList<Lesson> lessons)
	{
		this.input = super.input;
		this.courses = courses;
		this.venues = venues;
		this.lessons = lessons;
		this.error = "";
	}
	
	public void control() throws PreExistException, ClashException, CapacityException
	{
		Menu.venuemenu();
		if(input.hasNextInt())
		{
			this.venue(input.nextInt());
		}
		else
			error = input.nextLine();
	}
	

	public void venue(int n) throws PreExistException, ClashException, CapacityException
	{
		String location = null;
		if(n==3)
			this.listAllVenue();
		else 
		{
			System.out.print("Enter the location of the venue: ");
			input.useDelimiter("\n");
			location = input.next();	
			boolean exist = this.checkvenue(n, location);
				
			if(n==5 && exist)
				this.listAllLessons(venues.get(location));
			// add lesson to a venue
			if(n==4 && exist)
			{
				String courseID;
				int day;
				double start;
				System.out.print("Enter the course ID of the lesson:");
				input.useDelimiter("\n");
				courseID = input.next();
				if(super.check(course, courseID,courses,null,null))
				{
					Course value = courses.get(courseID);
					System.out.print("Enter the day of the lesson:");
					if(input.hasNextInt())
					{
						day = input.nextInt();
						System.out.print("Enter the start hour of the lesson:");
						if(input.hasNextDouble())
						{
							start = input.nextDouble();
							CourseOffering co = value.getOffering();
							if(co!=null)
							{
								int index;
								for(index=0;index<lessons.size();index++)
								{
									Lesson l = lessons.get(index);
									String type;
									if(l.getCourseOffering().getCourse().getID().equals(co.getCourse().getID()) && l.getDay()==day && l.getStart()==start && l.getVenue()==null)
									{
										type = l.printType();
										double dur = l.getEnd()-start;
										l.getCourseOffering().getLessons().remove(l);
										lessons.remove(l);
										if(type.compareTo("Tutorial")==0)
										{
											Lesson e = co.addTutorial(day, start, start+dur, venues.get(location));
											lessons.add(e);
											venues.get(location).addLesson(e);
										}
										else if(type.compareTo("Lecture")==0)
										{
											l.getCourseOffering().removeLecture();
											Lesson e = co.addLecture(day, start, start+dur, venues.get(location));
											lessons.add(e);
											venues.get(location).addLesson(e);
										}
										break;
									}
								}
								if(index==lessons.size())
									System.out.println("Do not have such lesson or already have venue for the lesson");
							}
						}
						else
							error = input.nextLine();
					}	
					else
						error = input.nextLine();
				}
				else
					System.out.println("The course does not exist.");
		
			}
		}
	}
	
	private boolean checkvenue(int choice, String location) throws PreExistException{
		PreExistException ex = new PreExistException(location,1,venues);

		if(choice==1)
		{
			try 
			{
				// it already has the venue
				if(ex.checkVenue())
					throw ex;
				this.addVenue(location);
				return false;
			}
			catch(Exception ex1) 
			{
				System.out.println(ex.venuetoString());
				return true;
			}
		}
		else
		{
			boolean temp = super.check(venue, location,null,venues,null);
			if(choice==2&&temp)
				this.getVenue(location);
			return temp;
		}
		
	}
	
	public void addVenue(String location) throws PreExistException
	{
		System.out.print("Enter the capacity: ");
		if(input.hasNextInt())
		{
			int capacity = input.nextInt();
			System.out.print("Enter the purpose: ");
			input.useDelimiter("\n");
			String purpose = input.next();
			venues.put(location, new Venue(location,capacity,purpose));	
		}
		else
		{
			error = input.nextLine();
			System.out.println("Input error");
		}
	}
	
	public Venue getVenue(String location) throws PreExistException
	{
		Venue value = venues.get(location);
		System.out.println(venues.get(location).toString());
		return value;
	}
	
	public void listAllVenue() 
	{
		venues.forEach((k,v) ->
		{
			System.out.println(v.toString());
		});
	}
	
	public void listAllLessons(Venue v)
	{
		ArrayList<Lesson> lessonsv = new ArrayList<Lesson>();
		lessonsv = v.getLessons();
		Lesson n;
		for(int index=0;index<lessonsv.size();index++)
		{
			n = lessonsv.get(index);
			if(n.getVenue()!=null)			
				if(v.getLocation().compareTo(n.getVenue().getLocation())==0)
				{
					System.out.print(n.getCourseOffering().getCourse().toString()+ "; ");
					if(n.getStaff()!=null)
						System.out.print(n.getStaff().toString() + "; ");
					else
						System.out.print("Staff: To Be Determined; ");
					System.out.println(n.toString());
				}
		}
		
	}
	
	
}
