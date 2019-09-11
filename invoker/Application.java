package invoker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Application {
	
	Scanner input = new Scanner(System.in);
	
	HashMap<String, Course> courses;
	HashMap<String, Venue> venues;
	HashMap<String, Staff> staffs;
	ArrayList<Lesson> lessons;
	
	// Constructor
	public Application()
	{
		this.courses = new HashMap<String,Course>();
		this.venues = new HashMap<String,Venue>();
		this.staffs = new HashMap<String,Staff>();
		this.lessons = new ArrayList<Lesson>();
	}
	
	public void start()
	{
		new Testcase(courses,venues,staffs);
    	this.showmenu();
		this.control();	
		input.close();
	}
	
	public void showmenu()
	{
		System.out.println("**************** Welcome to the Timetabling and Enrolment System ****************");
		System.out.println("1: To do actions related to courses");
		System.out.println("2: To do actions related to venues");
		System.out.println("3: To do actions related to staffs");
		System.out.println("4: To add lecture or tutorial");
		System.out.println("5: To print timetable");
//		System.out.println("6: ");
//		System.out.println("7: To enroll student in course offering");
//		System.out.println("8: To register student in tutorial");
//		System.out.println("9: To show menu again");
//		System.out.println("0 or letters: To exit");
		System.out.println("*********************************************************************************");
	}
	
	public void control()
	{
		int choice = 0;
		int coursechoice = 0;
		int venuechoice = 0;
		int staffchoice = 0;
		String lessonchoice = null;
		ArrayList<Timetable> timetables = new ArrayList<Timetable>();
		
	    do
		{
			System.out.println("Please enter your choices:");
			if(input.hasNextInt())
				choice = input.nextInt();
			else
				choice = 0;
			switch(choice)
			{
				case 1: 
					System.out.println("Enter 1: To add course");
					System.out.println("Enter 2: To add course Offering");
					System.out.println("Enter 3: To find course information");
					System.out.println("Enter 4: To list all the course");
					System.out.println("Enter 5: To remove a course");
					System.out.println("Enter 6: To find course offering information");
					coursechoice = input.nextInt();
					this.course(coursechoice);
					break;
				case 2:
					System.out.println("Enter 1: To add venue");
					System.out.println("Enter 2: To find venue information");
					System.out.println("Enter 3: To list all the venue");
					System.out.println("Enter 4: To add lesson to the venue");
					System.out.println("Enter 5: To list all the lessons of a venue");
					venuechoice = input.nextInt();
					this.venue(venuechoice);
					break;
				case 3:
					System.out.println("Enter 1: To add staff");
					System.out.println("Enter 2: To find staff information");
					System.out.println("Enter 3: To list all the staff");
					System.out.println("Enter 4: To assign lesson to the staff");
					System.out.println("Enter 5: To list all the lessons of a staff");
					staffchoice = input.nextInt();
					this.staff(staffchoice);
					break;
				case 4:
					System.out.print("To add Lecture(L) or Tutorial(T)? Or enter List All to list all lessons Or enter the course ID to find a certain lesson:");
					input.useDelimiter("\n");
					lessonchoice = input.next();
					if(lessonchoice.compareToIgnoreCase("List All")==0)
						this.listAllLessons();
					else if(courses.containsKey(lessonchoice))
					{
						int day;
						double start;
						System.out.print("Enter the day for the lesson:");
						day = input.nextInt();
						System.out.print("Enter the start time for the lesson:");
						start = input.nextDouble();
						this.getLesson(lessonchoice,day,start);		
					}
					else
						this.lesson(lessonchoice);
					break;
				case 5:
					Timetable t = new Timetable(lessons);
					System.out.print("Print timetable based on course, staff or venue?");
					input.useDelimiter("\n");
					String type0 = input.next();
					System.out.print("Enter the course ID or Staff number or venue location:");
					input.useDelimiter("\n");
					String ID = input.next();
					// check if it exists here!
					t.print(type0,ID);
					timetables.add(t);
					break;
				default:
					for(int index=0;index<timetables.size();index++)
						timetables.get(index).close();
					System.out.println("Exiting the program");
					choice = 0;

			}
		}while(choice!=0);
	}
	
	public void course(int n)
	{
		String courseID = null;
		switch(n)
		{
			case 1:
				this.addCourse();
				break;
			case 2:
				int max;
				System.out.println("Enter the course ID that want to create offering:");
				input.useDelimiter("\n");
				courseID = input.next();
				System.out.println("Enter the course offering max number:");
				max = input.nextInt();
				this.createCourseOffering(courseID, max);
				break;
			case 3:
				System.out.println("Enter the course ID that want to find information about:");
				input.useDelimiter("\n");
				courseID = input.next();
				this.getCourse(courseID);
				break;
			case 4:
				this.listAllCourse();
				break;
			case 5:
				System.out.println("Enter the course ID that want to remove:");
				input.useDelimiter("\n");
				courseID = input.next();
				this.removeCourse(courseID);
				break;
			case 6:
				System.out.println("Enter the course ID that want to get course offering:");
				input.useDelimiter("\n");
				courseID = input.next();
				Course value = courses.get(courseID);
				if(courses.containsKey(courseID))
				{
					if(value.getOffering()!=null)
						System.out.println("The course offering max number is: " + value.getOffering().getMaxnum());
					else
						System.out.println("The course does not have course offering yet.");
				}
				break;
		}
	}
	

	public boolean addCourse()
	{
		String courseID;
		String coursename;
		String coursepurpose;
		System.out.println("Enter the course ID:");
		input.useDelimiter("\n");
		courseID = input.next();
		if(courses.containsKey(courseID))
		{
			System.out.println("The course already exists, back to the last menu.");
			return false;
		}
		else
		{
			System.out.println("Enter the course name:");
			coursename = input.next();
			System.out.println("Enter the course purpose:");
			coursepurpose = input.next();
			courses.put(courseID, new Course(courseID,coursename,coursepurpose));
			return true;
		}
		
	}
	
	public Course getCourse(String courseID)
	{
		Course value = courses.get(courseID);
		if(courses.containsKey(courseID))
			System.out.println(value.toString()+";");
		else
			System.out.println("The course does not exist!");
		return value;
	}
	
	public void listAllCourse() 
	{
		courses.forEach((k,v) ->
		{
			this.getCourse(k);
		});
	}
	
	public void removeCourse(String courseID)
	{
		if(courses.containsKey(courseID))
		{
			int size = lessons.size();
			for(int index=0;index<size;index++)
			{
				if(courseID.compareTo(lessons.get(index).getCourseOffering().getCourse().getID())==0)
					lessons.remove(index);
			}
			courses.remove(courseID);
		}
		else
			System.out.println("The course does not exist!");
	}
	
	public void createCourseOffering(String courseID, int max)
	{
		Course value = courses.get(courseID);
		if(value == null)
			System.out.println("The course does not exist, create the course first.");
		else
			value.createOffering(max,value);
	}
	
	public void venue(int n)
	{
		String location = null;
		switch(n)
		{
			case 1:
				this.addVenue();
				break;
			case 2:
				System.out.println("Enter the venue location that want to find information about:");
				input.useDelimiter("\n");
				location = input.next();
				this.getVenue(location);
				break;
			case 3:
				this.listAllVenue();
				break;
			case 4:
				System.out.println("Enter the venue location that want to add lesson:");
				input.useDelimiter("\n");
				location = input.next();
				String courseID;
				int day;
				double start;
				System.out.print("Enter the course ID of the lesson:");
				input.useDelimiter("\n");
				courseID = input.next();
				if(courses.containsKey(courseID))
				{
					Course value = courses.get(courseID);
					System.out.print("Enter the day of the lesson:");
					day = input.nextInt();
					System.out.print("Enter the start hour of the lesson:");
					start = input.nextDouble();
					CourseOffering co = value.getOffering();
					if(co!=null)
					{
						int index;
						for(index=0;index<lessons.size();index++)
						{
							Lesson l = lessons.get(index);
							String type;
							if(l.getCourseOffering().getCourse().getID()==co.getCourse().getID() && l.getDay()==day && l.getStart()==start && l.getVenue()==null)
							{
								type = l.printType();
								double dur = l.getEnd()-start;
								l.getCourseOffering().removeLecture();
								lessons.remove(l);
								this.addLesson(type, courseID, day, start, dur, venues.get(location));
								System.out.println("!!!!!!!!!!!!!!!!");
								break;
							}
						}
						if(index==lessons.size())
							System.out.println("Do not have such lesson or already have venue for the lesson");
					}
				}
				else
					System.out.println("The course does not exist.");
				break;
			case 5:
				System.out.println("Enter the venue location that want to list all lessons:");
				input.useDelimiter("\n");
				location = input.next();
				this.listAllLessons(venues.get(location));
		}
	}
	
	public boolean addVenue()
	{
		String location;
	    int capacity;
		String purpose;
		System.out.print("Enter the location: ");
		location = input.nextLine();
		if(venues.containsKey(location))
		{
			System.out.println("The venue already exists, back to the last menu.");
			return false;
		}
		else
		{
			System.out.print("Enter the capacity: ");
			capacity = input.nextInt();
			System.out.print("Enter the purpose: ");
			purpose = input.nextLine();
			venues.put(location, new Venue(location,capacity,purpose));
			return true;
		}
	}
	
	public Venue getVenue(String location)
	{
		Venue value = venues.get(location);
		//change here -> toString
		if(venues.containsKey(location))
			System.out.println(value.toString()+";");
		else
			System.out.println("The venue does not exist.");
		return value;
	}
	
	public void listAllVenue() 
	{
		venues.forEach((k,v) ->
		{
			this.getVenue(k);
		});
	}
	
	public void staff(int n)
	{
		String staffID;
		switch(n)
		{
			case 1:
				this.addStaff();
				break;
			case 2:
				System.out.print("Enter the staff number that want to find his or her information:");
				input.useDelimiter("\n");
				staffID = input.next();
				this.getStaff(staffID);
				break;
			case 3:
				this.listAllStaff();
				break;
			case 4:
				System.out.print("Enter the staff number that want to set with a lesson :");
				input.useDelimiter("\n");
				staffID = input.next();
				Staff s = staffs.get(staffID);
				String courseID;
				int day;
				double start;
				if(s!=null)
				{
					System.out.print("Enter the course ID of the lesson:");
					input.useDelimiter("\n");
					courseID = input.next();
					if(courses.containsKey(courseID))
					{
						Course value = courses.get(courseID);
						System.out.print("Enter the day of the lesson:");
						day = input.nextInt();
						System.out.print("Enter the start hour of the lesson:");
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
									l.setStaff(s);
									break;
								}
							}
							if(index==lessons.size())
								System.out.println("Do not have such lesson or already have staff for the lesson");
						}	
					}
				}
				break;
			case 5:
				System.out.print("Enter the staff number that want to list all the lessons :");
				input.useDelimiter("\n");
				staffID = input.next();
				Staff s1 = staffs.get(staffID);
				this.listAllLessons(s1);
				break;
		}
	}
	
	public boolean addStaff()
	{
		String eNo;
		String name;
		String position;
		String office;
		System.out.print("Enter the staff Number: ");
		eNo = input.nextLine();
		if(staffs.containsKey(eNo))
		{
			System.out.println("The staff already exists, back to the last menu.");
			return false;
		}
		else
		{
			System.out.print("Enter the name: ");
			name = input.nextLine();
			System.out.print("Enter the position: ");
			position = input.nextLine();
			System.out.print("Enter the office: ");
			office = input.nextLine();
			staffs.put(eNo, new Staff(eNo,name,position,office));
			return true;
		}
	}
	
	public Staff getStaff(String staffID)
	{
		Staff value = staffs.get(staffID);
		if(staffs.containsKey(staffID))
			System.out.println(value.toString()+";");
		else
			System.out.println("The staff does not exist.");
		return value;
	}
	
	public void listAllStaff() 
	{
		staffs.forEach((k,v) ->
		{
			this.getStaff(k);
		});
	}
	
	
	
	public void lesson(String type)
	{
		String courseID;
		int day = 0;
		double start = 0.0;
		double dur = 0.0;
		System.out.print("Enter the course ID to add lesson:");
		input.useDelimiter("\n");
		courseID = input.next();
		if(courses.containsKey(courseID))
		{
			if(courses.get(courseID).getOffering()!=null && (type.compareToIgnoreCase("L")==0 || type.compareToIgnoreCase("LECTURE")==0 ) && courses.get(courseID).getOffering().a !=null)
					System.out.println("The lecture for this course already exist!");
			else
			{
				System.out.print("Enter the day for the lesson:");
				day = input.nextInt();
				System.out.print("Enter the start time for the lesson:");
				start = input.nextDouble();
				System.out.print("Enter the durition of the lesson:");
				dur = input.nextDouble();
				System.out.print("Enter the location of the lesson, if do not have idea, enter N:");
				input.useDelimiter("\n");
				String location = input.next();
				if(location.compareToIgnoreCase("n")!=0)
				{
					Venue ven = venues.get(location);
					this.addLesson(type, courseID, day, start, dur,ven);
				}
				else
					this.addLesson(type, courseID, day, start, dur,null);
			}
		}
		else
			System.out.println("The course does not exist.");
	}
	
	
	
	public void addLesson(String type,String courseID,int day,double start,double dur,Venue ven)
	{
		Course value = courses.get(courseID);

		CourseOffering cusoff = value.getOffering();
		if(cusoff == null)
		{
			System.out.println("Please enter the max number of students for the course:");
			int max = input.nextInt();
			value.createOffering(max,value);
			cusoff = value.getOffering();
		}
		if(type.compareToIgnoreCase("L")==0 || type.compareToIgnoreCase("LECTURE")==0)
		{
			lessons.add(cusoff.addLecture(day,start,start+dur,ven));
			if(ven!=null)
				ven.addLesson(lessons.get(lessons.size()-1));
		}
		if(type.compareToIgnoreCase("T")==0 || type.compareToIgnoreCase("TUTORIAL")==0)
		{
			lessons.add(cusoff.addTutorial(day,start,start+dur,ven));	
			if(ven!=null)
				ven.addLesson(lessons.get(lessons.size()-1));
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
	
	public void assignStaff(String staffID, Lesson l)
	{
		l.setStaff(staffs.get(staffID));
	}	
	
	
}



