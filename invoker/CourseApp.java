package invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CourseApp extends Application{
	
	Scanner input;
	String error;
	private final String course = "course";
	
	private HashMap<String,Course> courses;
	private ArrayList<Lesson> lessons;
	
	public CourseApp(HashMap<String,Course> courses,ArrayList<Lesson> lessons)
	{
		this.input = super.input;
		this.courses = courses;
		this.lessons = lessons;
		this.error = "";
	}
	
	public void control() throws Exception
	{
		Menu.coursemenu();
		if(input.hasNextInt())
		{ 
			this.course(input.nextInt());
		}
		else
			error = input.nextLine();
	}
	
	public void course(int n) throws Exception
	{
		String courseID = null;
		// case that do not need any input
		if(n==4)
			this.listAllCourse();
		else
		{
			// as all the other case need to input a courseID, input first
			System.out.println("Enter the course ID:");
			input.useDelimiter("\n");
			courseID = input.next();
			this.checkcourse(n, courseID);
		}
	}

	private boolean checkcourse(int choice, String courseID) throws PreExistException
	{
		PreExistException ex = new PreExistException(0,courseID,courses);
		// To add course
		if(choice==1)
		{
			try 
			{
				// If it already has the course when adding the course, throw PreExistException
				if(ex.checkCourse())
					throw ex;
				this.addCourse(courseID);
				return false;
			}
			catch(Exception ex1) 
			{
				// Print the exist course information
				System.out.println(ex.coursetoString());
				return true;
			}
		}
		// Others like finding a course information
		else
		{
			// if the course does not exist, temp = false
			boolean temp = super.check(course, courseID,courses,null,null);
			// To add course offering
			if(temp)
			{
				if(choice==2)
				{
					System.out.println("Enter the course offering max number:");					
					if(input.hasNextInt())
					{
						int max = input.nextInt();
						this.createCourseOffering(courseID, max);
					}
					else
					{
						error = input.nextLine();
						System.out.println("Input error");
					}
				}
				// To find course information
				if(choice==3)
					this.getCourse(courseID);
				// To remove the course
				if(choice==5)
					this.removeCourse(courseID);
				// To find course offering information
				if(choice==6)
				{
					Course value = courses.get(courseID);
					if(value.getOffering()!=null)
						System.out.println("The course offering max number is: " + value.getOffering().getMaxnum());
					else
						System.out.println("The course does not have course offering yet.");
				}
				if(choice==7)
					this.listAllLessons(courses.get(courseID));
			}
			return temp;
		}
		
	}
	
	public void addCourse(String courseID)
	{
		System.out.println("Enter the course name:");
		String coursename = input.next();
		System.out.println("Enter the course purpose:");
		String coursepurpose = input.next();
		courses.put(courseID, new Course(courseID,coursename,coursepurpose));
	}
	
	public Course getCourse(String courseID) throws PreExistException
	{
		Course value = courses.get(courseID);
		System.out.println(courses.get(courseID).toString());
		return value;
	}
	
	public void listAllCourse() 
	{
		courses.forEach((k,v) ->
		{
			System.out.println(v.toString());
		});
	}
	
	public void removeCourse(String courseID)
	{
		int size = lessons.size();
		for(int index=0;index<size;index++)
		{
			if(courseID.compareTo(lessons.get(index).getCourseOffering().getCourse().getID())==0)
				lessons.remove(index);
		}
		courses.remove(courseID);
	}
	
	public void createCourseOffering(String courseID, int max)
	{
		Course value = courses.get(courseID);
		value.createOffering(max,value);
	}
	
	public void listAllLessons(Course c)
	{
		Lesson n;
		for(int index=0;index<lessons.size();index++)
		{
			n = lessons.get(index);	
			if(c.getID().compareTo(n.getCourseOffering().getCourse().getID())==0)
			{
				if(n.getVenue()!=null)
					System.out.print(n.getVenue().toString() + "; ");
				else
					System.out.print("Location: To Be Determined; ");
				if(n.getStaff()!=null)
					System.out.print(n.getStaff().toString() + "; ");
				else
					System.out.print("Staff: To Be Determined; ");
				System.out.println(n.toString());
			}
		}
	}
		
}
