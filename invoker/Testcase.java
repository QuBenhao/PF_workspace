package invoker;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Testcase{

	Scanner input = null;
	String error = null;

	// Initialize or read file
	public Testcase(File file,HashMap<String, Course> coursecase, HashMap<String, Venue> venuecase, HashMap<String, Staff> staffcase , ArrayList<Lesson> lessoncase, ArrayList<Student>studentcase) throws FileNotFoundException, CapacityException, PreExistException, ClashException 
	{
		if(file.exists())
			if(file.canRead())
				input = new Scanner(file);
			
		while(input.hasNextLine())
		{
			String info;
			info = input.nextLine();
		
			if(info.split(":")[0].contains("staff") && Array.getLength(info.split(","))==4 && Array.getLength(info.split("\""))==8)
			{
				// staff
				readStaffFile(info,staffcase);
			}
			else if(info.split(":")[0].contains("venue") && Array.getLength(info.split(","))==3 && info.split(",")[1].matches("^[1-9]\\d*$")==true && Array.getLength(info.split("\""))==4)
			{
				// venue
				readVenueFile(info,venuecase);
			}
			else if(info.split(":")[0].contains("course") && Array.getLength(info.split("\""))==6)
			{
				// course
				readCourseFile(info,coursecase);	
			}
			else if(Array.getLength(info.split(":"))==4 && (Array.getLength(info.split("\""))==5||Array.getLength(info.split("\""))==7||Array.getLength(info.split("\""))==9))
			{
				// lesson
				readLessonFile(info, coursecase, venuecase, staffcase, lessoncase);
			}
			else if(info.split(":").length==3)
			{
				Student student;
				student = readStudentFile(info, studentcase);
				if(input.hasNextLine())
					info = input.nextLine();
				while(info.split(":").length==8)
				{
					readStudentFile(info, coursecase, lessoncase, student);
					if(input.hasNextLine())
						info = input.nextLine();
					else
						break;
				}
			}
			else if(info.compareTo("")!=0)
			{
				System.out.println("This input is not in the right format");
			}
		}
		
		input.close();
	}
		
	private void readCourseFile(String info,HashMap<String,Course> coursecase) throws PreExistException
	{
		String[] a = new String[3];
		a[0] = info.split("\"")[1];
		a[1] = info.split("\"")[3];
		a[2] = info.split("\"")[5];
		PreExistException ex = new PreExistException(0,a[0],coursecase);
		try
		{
			if(ex.checkCourse())
				throw ex;
			coursecase.put(a[0], new Course(a[0],a[1],a[2]));
		}
		catch(Exception ex0)
		{
			System.out.println(ex.coursetoString());
		}
	}
	
	private void readVenueFile(String info,HashMap<String,Venue> venuecase) throws PreExistException
	{
		String loc,purp;
		int cap;
		loc = info.split("\"")[1];		
		cap = Integer.valueOf(info.split(",")[1]);
		purp = info.split("\"")[3];
		PreExistException ex = new PreExistException(loc,0,venuecase);
		try
		{
			if(ex.checkVenue())
				throw ex;
			venuecase.put(loc, new Venue(loc,cap,purp));
		}
		catch(Exception ex0)
		{
			System.out.println(ex.venuetoString());
		}
	}
	
	private void readStaffFile(String info,HashMap<String,Staff> staffcase) throws PreExistException
	{
		String[] a = new String[4];
		a[0] = info.split("\"")[1];
		a[1] = info.split("\"")[3];
		a[2] = info.split("\"")[5];
		a[3] = info.split("\"")[7];
		PreExistException ex = new PreExistException(a[0],staffcase,0);
		try
		{
			if(ex.checkStaff())
				throw ex;
			staffcase.put(a[0], new Staff(a[0],a[1],a[2],a[3]));
		}
		catch(Exception ex0)
		{
			System.out.println(ex.stafftoString());
		}
	}
	
	private void readLessonFile(String info,HashMap<String, Course> coursecase, HashMap<String, Venue> venuecase, HashMap<String, Staff> staffcase, ArrayList<Lesson> lessoncase) throws CapacityException,ClashException
	{
		String courseID, location = null, staffID = null, type;
		int maxNum,day;
		double start,end;
		Course c;
		CourseOffering co;
		Venue ven;
		Staff s;
		
		courseID = info.split(":")[1].split("\"")[1];
		if(info.split(",")[1].matches("^[1-9]\\d*$"))
		{
			maxNum = Integer.valueOf(info.split(",")[1]);
			if(!info.split(":")[2].contains("To Be Determined"))
				location = info.split(":")[2].split("\"")[1];
			if(!info.split(":")[3].contains("To Be Determined"))
				staffID = info.split(":")[3].split("\"")[1];
			type = info.split("\"")[Array.getLength(info.split("\""))-2];
			if(info.split(",")[Array.getLength(info.split(","))-3].matches("^[1-7]"))
			{
				day = Integer.valueOf(info.split(",")[Array.getLength(info.split(","))-3]);
				if(info.split(",")[Array.getLength(info.split(","))-2].matches("^\\d+(\\.\\d+)?"))
				{
					start = Double.valueOf(info.split(",")[Array.getLength(info.split(","))-2]);
					if(info.split(",")[Array.getLength(info.split(","))-1].matches("^\\d+(\\.\\d+)?"))
					{
						end = Double.valueOf(info.split(",")[Array.getLength(info.split(","))-1]);
						
						c = coursecase.get(courseID);
						if(c==null)
							System.out.println("No such course!");
						else
						{
							c.createOffering(maxNum, c);
							co = c.getOffering();
							ven = venuecase.get(location);
							Lesson l; 
							s = staffcase.get(staffID);
							if(!venuecase.containsValue(ven))	
							{
								System.out.println("The venue does not exist, set to empty");
								ven = null;
							}
							if(type.compareToIgnoreCase("LECTURE")==0)
							{
								if(c.getOffering().getLecture()!=null)
									System.out.println("The lecture for this course already exist!");
								else
								{
									if(staffcase.containsKey(staffID))
									{
										l = co.addLecture(day,start,end,s,ven,lessoncase);
										lessoncase.add(l);
										if(ven!=null)
											ven.addLesson(lessoncase.get(lessoncase.size()-1));
										System.out.println("Insert lesson: "+ courseID+","+l.toString());
									}
									else
									{
										System.out.println("The staff does not exist, set to empty");
										lessoncase.add(co.addLecture(day, start, end, ven));
										System.out.println("Insert lesson: "+ courseID+","+lessoncase.get(lessoncase.size()-1).toString());
									}	
								}
							}
							else if (type.compareToIgnoreCase("TUTORIAL")==0)
							{
								if(staffcase.containsKey(staffID))
								{
									l = co.addTutorial(day,start,end,s,ven,lessoncase);
									if(l!=null)
									{
										lessoncase.add(l);
										if(ven!=null)
											ven.addLesson(lessoncase.get(lessoncase.size()-1));
										System.out.println("Insert lesson: "+ courseID+","+l.toString());
									}
								}
								else
								{
									System.out.println("The staff does not exist");
									l = co.addTutorial(day,start,end,ven);
									if(l!=null)
									{
										lessoncase.add(l);
										System.out.println("Insert lesson: "+ courseID+","+lessoncase.get(lessoncase.size()-1).toString());
									}
								}
							}
						}
					}
					else
					{
						error = info.split(",")[Array.getLength(info.split(","))-1];
						System.out.println("Input end Hour error: " + error);
					}
				}
				else
				{
					error = info.split(",")[Array.getLength(info.split(","))-2];
					System.out.println("Input start Hour error: " + error);
				}
			}
			else
			{
				error = info.split(",")[Array.getLength(info.split(","))-3];
				System.out.println("Input day error: " + error);
			}
		}
		else
		{
			error = info.split(",")[1];
			System.out.println("Input course offering max number error: " + error);
		}
	}
	
	private Student readStudentFile(String info, ArrayList<Student>studentcase)
	{
		String ID,name;
		ID = info.split("\"")[1];
		name = info.split("\"")[3];
		Student temp = new Student(ID,name);
		boolean found = false;
		for(int index=0;index<studentcase.size();index++)
		{
			if(studentcase.get(index).getID().compareTo(ID)==0)
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			studentcase.add(temp);
			System.out.println("Insert student:"+ID);
		}
		else
			System.out.println("The student already exist");
		return temp;
	}
	
	private void readStudentFile(String info, HashMap<String,Course>coursecase, ArrayList<Lesson> lessoncase, Student student) throws CapacityException, ClashException
	{
		String courseID,type,day0;
		int day,hour,min;
		double startHour,endHour,temp;	
		courseID = info.split(",")[0].split(" ")[2];
		type = info.split(",")[1].split(" ")[2];
		day0 = info.split(",")[2].split(" ")[2];
		day = daytoint(day0);
		hour = Integer.valueOf(info.split(":")[4].split(" ")[1]);
		min = Integer.valueOf(info.split(":")[5].split(",")[0]);
		temp = (double)min;
		startHour = (double)hour + temp/100;
		hour = Integer.valueOf(info.split(":")[6].split(" ")[1]);
		min = Integer.valueOf(info.split(":")[7]);
		temp = (double)min;
		endHour = (double)hour + temp/100;
		
		if(coursecase.containsKey(courseID))
		{
			CourseOffering co = coursecase.get(courseID).getOffering();
			if(co!=null)
			{
				if(type.compareTo("Lecture")==0 && co.getLecture().getDay()==day && co.getLecture().getStart()==startHour && co.getLecture().getEnd()==endHour)
				{
					co.addStudent(student);
				}
				else if(type.compareTo("Tutorial")==0)
				{
					Lesson n;
					for(int index=0;index<co.getLessons().size();index++)
					{
						n = co.getLessons().get(index);
						if(n instanceof Tutorial && n.getDay()==day && n.getStart()==startHour && n.getEnd()==endHour)
							co.joinTutorial(student, n);
					}
				}
			}
		}
	}
	
	private int daytoint(String day)
	{
		if(day.compareToIgnoreCase("Monday")==0)
			return 1;
		else if(day.compareToIgnoreCase("Tuesday")==0)
			return 2;
		else if(day.compareToIgnoreCase("Wednesday")==0)
			return 3;
		else if(day.compareToIgnoreCase("Thursday")==0)
			return 4;
		else if(day.compareToIgnoreCase("Friday")==0)
			return 5;
		else if(day.compareToIgnoreCase("Saturday")==0)
			return 6;
		else if(day.compareToIgnoreCase("Sunday")==0)
			return 7;
		return 0;
	}
}
