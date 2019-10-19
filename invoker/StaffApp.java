package invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StaffApp extends Application {

	Scanner input;
	String error;
	private final String course = "course";
	private final String staff = "staff";

	private HashMap<String,Course> courses;
	private HashMap<String,Staff> staffs;
	private ArrayList<Lesson> lessons;
	
	public StaffApp(HashMap<String,Course>courses,HashMap<String,Staff> staffs,ArrayList<Lesson> lessons)
	{
		this.input = super.input;
		this.courses = courses;
		this.staffs = staffs;
		this.lessons = lessons;
		this.error = "";
	}
	
	public void control() throws PreExistException, ClashException
	{
		Menu.staffmenu();
		if(input.hasNextInt())
			this.staff(input.nextInt());
		else
			error = input.nextLine();
	}
	
	public void staff(int n) throws PreExistException, ClashException
	{
		String staffID;
		if(n==3)
			this.listAllStaff();
		else if(n<6 && n>0)
		{
			System.out.print("Enter the staff Number: ");
			input.useDelimiter("\n");
			staffID = input.next();
			boolean exist = this.checkstaff(n,staffID);
			if(n==2 && exist)
				this.getStaff(staffID);
			if(n==5 && exist)
				this.listAllLessons(staffs.get(staffID));
			if(n==4 && exist)
			{
				Staff s = staffs.get(staffID);
				String courseID;
				int day;
				double start;
				if(s!=null)
				{
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
										if(l.getCourseOffering().getCourse().getID()==co.getCourse().getID() && l.getDay()==day && l.getStart()==start && l.getStaff()==null)
										{
											this.assignStaff(staffID, l);
											break;
										}
									}
									if(index==lessons.size())
										System.out.println("Do not have such a lesson or already have staff for the lesson. ");
								}	
							}
							else
								error = input.nextLine();
						}
						else
							error = input.nextLine();
					}
					else
						System.out.println("Do not have such a course. ");
				}
				else
					System.out.println("Do not have such a staff. ");
			}
		}
	}
	
	private boolean checkstaff(int choice, String eNo) throws PreExistException{
		PreExistException ex = new PreExistException(eNo,staffs,2);

		if(choice==1)
		{
			try 
			{
				// it already has the staff
				if(ex.checkStaff())
					throw ex;
				return false;
			}
			catch(Exception ex1) 
			{
				System.out.println(ex.stafftoString());
				return true;
			}
		}
		else
			return super.check(staff, eNo,null,null,staffs);
		
	}

	
	public void addStaff(String eNo) throws PreExistException
	{
		input.useDelimiter("\n");
		System.out.print("Enter the name: ");
		String name = input.next();
		System.out.print("Enter the position: ");
		String position = input.next();
		System.out.print("Enter the office: ");
		String office = input.next();
		staffs.put(eNo, new Staff(eNo,name,position,office));
	}
	
	public Staff getStaff(String staffID) throws PreExistException
	{
		Staff value = staffs.get(staffID);
		System.out.println(staffs.get(staffID).toString());
		return value;
	}
	
	public void listAllStaff() 
	{
		staffs.forEach((k,v) ->
		{
			System.out.println(v.toString());
		});
	}	
	
	public void listAllLessons(Staff s)
	{
		int index;
		Lesson n;
		for(index=0;index<lessons.size();index++)
		{
			n = lessons.get(index);
			if(n.getStaff()!=null)			
				if(s.geteNo().compareTo(n.getStaff().geteNo())==0)
				{
					System.out.print(n.getCourseOffering().getCourse().toString()+ "; ");
					if(n.getVenue()!=null)
						System.out.print(n.getVenue().toString() + "; ");
					else
						System.out.print("Location: To Be Determined; ");
					System.out.println(n.toString());
				}
		}
	}
	
	
	public void assignStaff(String staffID, Lesson l) throws ClashException
	{
			staffs.get(staffID).assign(l,lessons);
	}	
		
}
