package invoker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Application{

	Scanner input = new Scanner(System.in);
	
	// In order to save file name uniquely, using current time
	LocalDate currentdate = LocalDate.now();
	LocalTime currenttime = LocalTime.now();
	File operation = new File(currentdate.format(DateTimeFormatter.BASIC_ISO_DATE)+"_"+currenttime.getHour()+currenttime.getMinute()+".txt");
	
	private HashMap<String, Course> courses;
	private HashMap<String, Venue> venues;
	private HashMap<String, Staff> staffs;
	private ArrayList<Lesson> lessons;
	private ArrayList<Student> students;
		
	// Constructor
	public Application()
	{
		this.courses = new HashMap<String,Course>();
		this.venues = new HashMap<String,Venue>();
		this.staffs = new HashMap<String,Staff>();
		this.lessons = new ArrayList<Lesson>();
		this.students = new ArrayList<Student>();
	}
	
	public void start(String user) throws Exception
	{		
		// read Default_data
	    File file = new File("Default_data.txt");
		new Testcase(file,courses,venues,staffs,lessons,students);
		
		// The other way to input data
		@SuppressWarnings("unused")
		String[] data = {"students.dat","lessons.dat","venues.dat","staffs.dat","courses.dat"};	
//		this.readAllObject(data);
		
		// Admin
		if(user.compareToIgnoreCase("admin") == 0 || user.compareToIgnoreCase("A") == 0)
		{
	    	Menu.mainmenu();
			this.control();	
		}
		// Student
		else if(user.compareToIgnoreCase("student")==0 || user.compareToIgnoreCase("S")==0)
		{
			file = new File("LessonForStudent.txt");
			new Testcase(file,courses,venues,staffs,lessons,students);
			StudentApp studentuse = new StudentApp(courses,venues,staffs,lessons,students);
			studentuse.enroll();
		}
		else
		{
			System.out.println("The user does not exist");
		}
		
		// Save all the information
		this.saveAll();
		
		// The other way to save information
//		this.saveAllObject(data);
	}
	
	//  Main menu
	private void control() throws Exception
	{
		int choice = 0;
		// Timetable class objects
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
					// Course menu
					CourseApp courseuse = new CourseApp(courses,lessons);
					courseuse.control();
					break;
				case 2:
					// Venue menu
					VenueApp venueuse = new VenueApp(courses,venues,lessons);
					venueuse.control();
					break;
				case 3:
					// Staff menu
					StaffApp staffuse = new StaffApp(courses,staffs,lessons);
					staffuse.control();
					break;
				case 4:
					// Lesson menu
					LessonApp lessonuse = new LessonApp(courses,venues,staffs,lessons);
					lessonuse.control();
					break;
				case 5:
					// Print lesson timetable
					Timetable t = new Timetable(lessons);
					System.out.print("Print timetable based on course, staff, venue or student?");
					input.useDelimiter("\n");
					String type = input.next();
					System.out.print("Enter the course ID or Staff number or venue location or student ID:");
					input.useDelimiter("\n");
					String ID = input.next();
					if(check(type,ID,courses,venues,staffs))
					{
						t.print(type,ID);
						t.savePic(type,ID);
						timetables.add(t);
					}
					else if(type.compareToIgnoreCase("student")==0)
					{
						Student student = null;
						int index;
						for(index=0;index<students.size();index++)
						{
							student = students.get(index);
							if(student.getID().compareTo(ID)==0)
								break;
						}
						if(index!=students.size() && students.size()!=0)
						{
							Timetable ts;
							if(student!=null)
							{
								ts = new Timetable(student.getLessons());
								ts.print(type, ID);
								ts.savePic(type, ID);
								timetables.add(ts);
							}
						}
					}
					break;
				case 6:
					// Retrieve data from the file
					System.out.print("Enter the filename:");
					input.useDelimiter("\n");
					String filename = input.next();
					File file = new File(filename);
					if(file.exists())
					{
						if(file.canRead())
							new Testcase(file,courses,venues,staffs,lessons,students);
						System.out.println("Input succeed");
					}
					else
						System.out.println("No such a file");
					break;
				case 7:
					// Enroll student
					StudentApp studentuse = new StudentApp(courses,venues,staffs,lessons,students);
					studentuse.enroll();
					break;
				case 8:
					for(Student s:students)
					{
						System.out.println(s.toString());
					}
					break;
				case 9:
					Menu.mainmenu();
					break;
				default:
					for(int index=0;index<timetables.size();index++)
						timetables.get(index).close();
					choice = 0;
			}
			if(choice!=0)
				System.out.println("Back to the main menu");
		}while(choice!=0);
		System.out.println("Exiting the program");
	}
	
	
	// Check exist or not
	protected boolean check(String type,String ID, HashMap<String, Course> courses, HashMap<String, Venue> venues, HashMap<String, Staff> staffs) throws PreExistException
	{
		PreExistException ex;
		if(type.compareToIgnoreCase("Course")==0)
		{
			ex = new PreExistException(0,ID,courses);
			try 
			{
				// if the course does not exist, throw Exception
				if(!ex.checkCourse())
					throw ex;
				return true;
			}
			catch(PreExistException ex0)
			{
				System.out.println("The course " + ID +" does not exist.");
				return false;
			}
		}
		else if(type.compareToIgnoreCase("Staff")==0)
		{
			ex = new PreExistException(ID,staffs,2);
			try 
			{
				// if the staff does not exist
				if(!ex.checkStaff())
					throw ex;			
				return true;					
			}
			catch(PreExistException ex0)
			{
				System.out.println("The staff " + ID + " does not exist.");
				return false;
			}	
		}
		else if(type.compareToIgnoreCase("Venue")==0)
		{
			ex = new PreExistException(ID,1,venues);
			try 
			{
				// if the venue does not exist
				if(!ex.checkVenue())
					throw ex;
				return true;
			}
			catch(PreExistException ex0)
			{
				System.out.println("The venue " + ID + " does not exist.");
				return false;
			}
		}
		return false;
	}
	
	// Save data through PrintWriter
 	private void saveAll() throws FileNotFoundException
	{
		PrintWriter output = new PrintWriter(operation);
		
		courses.forEach((k,v) ->
		{
			output.println("course: \""+v.getID()+"\", \""+v.getName()+"\", \""+v.getPurpose()+"\"");
		});
		output.println();
		
		venues.forEach((k,v) ->
		{
			output.println("venue: \""+v.getLocation()+"\","+v.getCapacity()+",\""+v.getPurpose()+"\"");
		});
		output.println();
		
		staffs.forEach((k,v) ->
		{
			output.println("staff: \""+v.geteNo()+"\",\""+v.getName()+"\",\""+v.getPosition()+"\",\""+v.getOffice()+"\"");
		});
		output.println();
		
		for(Lesson n:lessons)
		{
			output.print("The course ID is: \"" + n.getCourseOffering().getCourse().getID()+"\","+ n.getCourseOffering().getMaxnum()+", ");
			if(n.getVenue()!=null)
				output.print("The location is: \"" + n.getVenue().getLocation() +"\", ");
			else
				output.print("Location: To Be Determined; ");
			if(n.getStaff()!=null)
				output.print("The staff ID is: \"" + n.getStaff().geteNo() +"\", ");
			else
				output.print("Staff: To Be Determined; ");
			output.printf("\""+n.printType()+"\","+n.getDay()+","+"%.2f"+","+"%.2f",n.getStart(),n.getEnd());
			output.println();
		}
		output.println();
		
		for(Student student:students)
		{
			output.print(student.toString());
			output.println();
		}
		
		output.close();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void readAllObject(String input[]) throws FileNotFoundException, IOException, ClassNotFoundException
 	{
		ObjectInputStream[] in = new ObjectInputStream[5];
		for(int i=0;i<5;i++)
		{
			in[i] = new ObjectInputStream(new FileInputStream(input[i]));
			if(input[i].contains("students"))
				students = (ArrayList<Student>)in[i].readObject();
			else if(input[i].contains("lessons"))
				lessons = (ArrayList<Lesson>) in[i].readObject();
			else if(input[i].contains("venues"))
				venues = (HashMap<String, Venue>) in[i].readObject();
			else if(input[i].contains("staffs"))
				staffs = (HashMap<String, Staff>) in[i].readObject();
			else if(input[i].contains("courses"))
				courses = (HashMap<String, Course>) in[i].readObject();
			in[i].close();
		}
 	}
 	
	@SuppressWarnings("unused")
	private void saveAllObject(String output[]) throws FileNotFoundException, IOException 
	{
		ObjectOutputStream[] out = new ObjectOutputStream[5];
		for(int i=0;i<5;i++)
		{
			out[i] = new ObjectOutputStream(new FileOutputStream(output[i]));
			if(output[i].contains("students"))
				out[i].writeObject(students);
			else if(output[i].contains("lessons"))
				out[i].writeObject(lessons);
			else if(output[i].contains("venues"))
			    out[i].writeObject(venues);
			else if(output[i].contains("staffs"))
			    out[i].writeObject(staffs);
			else if(output[i].contains("courses"))
				out[i].writeObject(courses);
			out[i].close();
		}
	}
}
