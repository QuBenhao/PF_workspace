package invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LessonApp extends Application {

	Scanner input;
	String error;
	private final String course = "course";
	private final String venue = "venue";
	private final String staff = "staff";

	private HashMap<String,Course> courses;
	private HashMap<String,Venue> venues;
	private HashMap<String,Staff> staffs;
	private ArrayList<Lesson> lessons;
	
	public LessonApp(HashMap<String,Course>courses,HashMap<String,Venue> venues, HashMap<String,Staff> staffs,ArrayList<Lesson> lessons)
	{
		this.input = super.input;
		this.courses = courses;
		this.venues = venues;
		this.staffs = staffs;
		this.lessons = lessons;
		this.error = "";
	}
	
	public void control() throws ClashException, CapacityException, PreExistException
	{
		String lessonchoice = null;
		System.out.print("To add Lecture(L) or Tutorial(T)\nEnter List All to list all lessons\nEnter the course ID to find a certain lesson:");
		input.useDelimiter("\n");
		lessonchoice = input.next();
		if(lessonchoice.compareToIgnoreCase("List All")==0)
			this.listAllLessons();
		// Find a certain lesson
		else if(courses.containsKey(lessonchoice))
		{
			int day;
			double start;
			System.out.print("Enter the day for the lesson:");
			if(input.hasNextInt())
			{
				day = input.nextInt();
				System.out.print("Enter the start time for the lesson:");
				if(input.hasNextDouble())
				{
					start = input.nextDouble();
					this.getLesson(lessonchoice,day,start);
				}
				else
					error = input.nextLine();
			}
			else
				error = input.nextLine();
		}
		// Add a lesson
		else
			this.lesson(lessonchoice);
	}
	
	public void lesson(String type) throws ClashException, CapacityException, PreExistException
	{
		String courseID;
		int day = 0;
		double start = 0.0;
		double dur = 0.0;
		System.out.print("Enter the course ID to add lesson:");
		input.useDelimiter("\n");
		courseID = input.next();
		if(super.check(course, courseID,courses,null,null))
		{
			if(courses.get(courseID).getOffering()!=null && (type.compareToIgnoreCase("L")==0 || type.compareToIgnoreCase("LECTURE")==0 ) && courses.get(courseID).getOffering().getLecture() !=null)
					System.out.println("The lecture for this course already exist!");
			else
			{
				System.out.print("Enter the day for the lesson:");
				if(input.hasNextInt())
				{
					day = input.nextInt();
					System.out.print("Enter the start time for the lesson:");
					if(input.hasNextDouble())
					{
						start = input.nextDouble();
						System.out.print("Enter the durition of the lesson:");
						if(input.hasNextDouble())
						{
							dur = input.nextDouble();
							System.out.print("Enter the location of the lesson, if do not have idea, enter N:");
							input.useDelimiter("\n");
							String location = input.next();
							if(location.compareToIgnoreCase("n")!=0)
							{
								if(super.check(venue, location,null,venues,null))
								{
									Venue ven = venues.get(location);
									this.addLesson(type, courseID, day, start, dur,ven);
								}
							}
							else
								this.addLesson(type, courseID, day, start, dur,null);
						}
						else
							error = input.nextLine();
					}
					else
						error = input.nextLine();
				}
				else
					error = input.nextLine();
			}
		}
		else
			System.out.println("The course does not exist.");
	}
		
	public void addLesson(String type,String courseID,int day,double start,double dur,Venue ven) throws ClashException, CapacityException, PreExistException
	{
		Course value = courses.get(courseID);
		double sum;
		sum = start+dur;
		sum *= 100;
		String staffadd;
		// 60 minutes = 1 hour
		if(sum%100 >= 60 )
		{
			sum += 40;
		}
		sum /= 100;

		CourseOffering cusoff = value.getOffering();
		if(cusoff == null)
		{
			System.out.print("Create courseoffering for the course first\nPlease enter the max number of students for the course:");
			if(input.hasNextInt())
			{
				int max = input.nextInt();
				value.createOffering(max,value);
				cusoff = value.getOffering();
			}
			else
			{
				error = input.nextLine();
				cusoff = null;
			}
		}
		if(cusoff!=null)
		{
			boolean nostaff = true;
			System.out.print("Add staff now? Y/N:");
			input.useDelimiter("\n");
			staffadd = input.next();
			if(staffadd.charAt(0)=='Y')
			{
				nostaff = false;
				Staff s;
				System.out.print("Enter the staff ID:");
				staffadd = input.next();
				if(super.check(staff, staffadd,null,null,staffs))
				{	
					s = staffs.get(staffadd);
					if(type.compareToIgnoreCase("L")==0 || type.compareToIgnoreCase("LECTURE")==0)
					{
						Lesson check = cusoff.addLecture(day,start,sum,s,ven,lessons);
						// check lesson
						if(check!=null)
						{
							if(check.getVenue()!=null)
							{
								lessons.add(check);
								ven.addLesson(check);							
							}
							else
								lessons.add(check);
						}
					}
					if(type.compareToIgnoreCase("T")==0 || type.compareToIgnoreCase("TUTORIAL")==0)
					{
						
						Lesson check = cusoff.addTutorial(day,start,sum,s,ven,lessons);
						// check lesson
						if(check!=null)
						{
							if(cusoff.checkClash(check))
							{
								if(check.getVenue()!=null)
								{
									lessons.add(check);	
									// if capacity exception, no lesson added for venue
									if(lessons.get(lessons.size()-1).getVenue()!=null)
										ven.addLesson(lessons.get(lessons.size()-1));
								}
								else
									lessons.add(check);
							}
						}
					}
				}
				else
					nostaff = true;
			}
			if(nostaff)
			{
				if(type.compareToIgnoreCase("L")==0 || type.compareToIgnoreCase("LECTURE")==0)
				{
					Lesson check = cusoff.addLecture(day,start,sum,ven);
					// check lesson
					if(check!=null)
					{
						if(check.getVenue()!=null)
						{
							lessons.add(check);
							ven.addLesson(check);
								
						}
						else
							lessons.add(check);
					}
				}
				if(type.compareToIgnoreCase("T")==0 || type.compareToIgnoreCase("TUTORIAL")==0)
				{
					
					Lesson check = cusoff.addTutorial(day,start,sum,ven);
					// check lesson
					if(check!=null)
					{
						if(check.getVenue()!=null)
						{
							lessons.add(check);	
							// if capacity exception, no lesson added for venue
							if(lessons.get(lessons.size()-1).getVenue()!=null)
								ven.addLesson(lessons.get(lessons.size()-1));
						}
						else
							lessons.add(check);
					}
				}
			}
		}
	}
	
	public void getLesson(String courseID,int day, double start)
	{
		Lesson l = null;
		Course value = courses.get(courseID);
		CourseOffering cusoff = null;
		if(value!=null)
		{
			cusoff = value.getOffering();
			if(cusoff != null)
			{
				l = cusoff.getLesson(day, start);
				System.out.println(l.toString());	
			}
		}
	}
	
	public void listAllLessons()
	{
		int index;
		Lesson n;
		for(index=0;index<lessons.size();index++)
		{
			n = lessons.get(index);
			System.out.print("The course ID is: " + n.getCourseOffering().getCourse().getID()+"; ");
			if(n.getVenue()!=null)
				System.out.print("The location is: " + n.getVenue().getLocation() +"; ");
			else
				System.out.print("Location: To Be Determined; ");
			if(n.getStaff()!=null)
				System.out.print("The staff ID is: " + n.getStaff().geteNo() +"; ");
			else
				System.out.print("Staff: To Be Determined; ");
			System.out.println(n.toString());
			
		}
	}
}
