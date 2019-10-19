package invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StudentApp extends Application {
	
	Scanner input;
	String error;
	
	private HashMap<String,Course>courses;
	private HashMap<String,Venue> venues;
	private HashMap<String,Staff> staffs;
	private ArrayList<Lesson> lessons;
	private ArrayList<Student>students;
	
	public StudentApp(HashMap<String,Course>courses,HashMap<String,Venue> venues, HashMap<String,Staff> staffs,ArrayList<Lesson> lessons,ArrayList<Student>students)
	{
		this.input = super.input;
		this.courses = courses;
		this.venues = venues;
		this.staffs = staffs;
		this.lessons = lessons;
		this.students = students;
		this.error = "";
	}
	
	public void enroll() throws Exception
	{
		Menu.studentmenu();
		String read;
		int choice = 0;
		String studentID;
		String courseID;
		
		do
		{
			System.out.println("Please enter your choices:");
			if(input.hasNextInt())
				choice = input.nextInt();
			else
				break;
			if(choice==1)
			{
				CourseApp temp = new CourseApp(courses,lessons);
				temp.listAllCourse();
			}
			else if(choice==2)
			{
				LessonApp temp = new LessonApp(courses,venues,staffs,lessons);
				temp.listAllLessons();
			}
			else if(choice<7 && choice>2)
			{
				System.out.print("Please enter your student ID:");
				input.useDelimiter("\n");
				studentID = input.next();
				Student student=null;
				int index;
				for(index=0;index<students.size();index++)
				{
					if(students.get(index).getID().compareTo(studentID)==0)
					{
						student = students.get(index);
						break;
					}
				}
				
				if(index!=students.size() && student!=null)
				{
					if(choice == 6)
						System.out.println("Student already exists");
					else if(choice == 5)
						System.out.println(student.toString());
					else
					{
						System.out.print("To join or quit? J/Q:");
						read = input.next();
						System.out.print("Enter the course ID:");
						courseID = input.next();
						if(super.check("course", courseID, courses, null, null))
						{
							if(choice==3) 
							{
								if(read.charAt(0)=='J' || read.charAt(0)=='j')
								{
									if(courses.get(courseID).getOffering()!=null)
										courses.get(courseID).getOffering().addStudent(student);
									else
										System.out.println("No course offering!");
								}								
								else if(read.charAt(0)=='Q' || read.charAt(0)=='q')
								{
									if(courses.get(courseID).getOffering()!=null)
										courses.get(courseID).getOffering().dropStudent(student);
								}
							}
							if(choice==4)
							{
								int day;
								double start;
								System.out.print("Enter the day of the tutorial:");
								if(input.hasNextInt())
								{
									day = input.nextInt();
									System.out.print("Enter the start hour of the tutorial:");
									if(input.hasNextDouble())
									{
										start = input.nextDouble();
										Lesson n = null; 
										if(courses.get(courseID).getOffering()!=null)
										{
											for(int i=0;i<courses.get(courseID).getOffering().getLessons().size();i++)
											{
												if(courses.get(courseID).getOffering().getLessons().get(i) instanceof Tutorial)
													if(courses.get(courseID).getOffering().getLessons().get(i).getDay()==day)
														if(courses.get(courseID).getOffering().getLessons().get(i).getStart()==start)
														{
															n = courses.get(courseID).getOffering().getLessons().get(i);
															break;
														}
											}
											if(n!=null)
											{
												if(read.charAt(0)=='J' || read.charAt(0)=='j')
												{
													n.getCourseOffering().joinTutorial(student,n);
												}
												else if(read.charAt(0)=='Q' || read.charAt(0)=='q')
												{
													n.getCourseOffering().dropTutorial(student);
												}
											}
										}
										else
											System.out.println("No course offering!");
									}
									else
										error = input.next();
								}
								else
									error = input.next();
							}
						}
					}
				}
				else
				{
					if(choice!=6)
						System.out.println("No such a student");
					else
					{
						System.out.print("Enter the name of the student:");
						String name = input.next();
						students.add(new Student(studentID,name));
						System.out.println("Student added:"+studentID);
					}
				}
			}
		}while(choice<7 && choice>0);
	}
}
