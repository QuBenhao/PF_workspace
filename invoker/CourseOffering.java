package invoker;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseOffering implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	
	private int maxNum;
	private Course course;
	private ArrayList<Lesson> lessons;
	private ArrayList<Student> students;
	private Lecture a;	
	private int count;
	
	public CourseOffering(int maxNum,Course course)
	{
		this.maxNum = maxNum;
		this.course = course;
		// All the lessons of the course
		this.lessons = new ArrayList<Lesson>();
		// All the students of the course
		this.students = new ArrayList<Student>();
		// A course only offers one lecture
		this.a = null;
		this.count = 0;
	}
	
	public int getMaxnum()
	{
		return maxNum;
	}
	
	public Course getCourse()
	{
		return course;
	}
	
	public ArrayList<Lesson> getLessons()
	{
		return lessons;
	}
	
	public ArrayList<Student> getStudents()
	{
		return students;
	}
	
	public Lecture getLecture()
	{
		return a;
	}
	
	public Lecture addLecture(int day, double start, double end , Venue ven) throws CapacityException, ClashException
	{
		// A course can only have one lecture
		if(a==null)
		{
			if(day==6||day==7||start>=end)
				System.out.println("Something wrong with the input.");
			else
			{
				a = new Lecture(day, start, end, ven, this);
				lessons.add(a);
			}
		}
		else
			System.out.println("The lecture for the course already exist.");
		return a;		
	}
	
	// Assign staff when adding the lecture
	public Lecture addLecture(int day, double start, double end , Staff staff, Venue ven, ArrayList<Lesson> lessons) throws CapacityException, ClashException
	{
		if(a==null)
		{
			if(day==6||day==7||start>=end)
				System.out.println("Something wrong with the input.");
			else
			{
				a = new Lecture(day, start, end, staff, ven, this, lessons);
				this.lessons.add(a);
			}
		}
		else
			System.out.println("The lecture for the course already exist.");
		return a;		
	}
	
	public void removeLecture()
	{
		lessons.remove(a);
		a=null;
	}
	
	public Tutorial addTutorial(int day, double start, double end , Venue ven) throws ClashException
	{
		Tutorial t = null;
		if(day==6||day==7||start>end)
			System.out.println("Something wrong with the input.");
		else
		{
			t = new Tutorial(day, start, end, ven, this.course.getOffering());
			// Lessons cannot at the same time
			if(this.checkClash(t))
				lessons.add(t);
			else
				t = null;
		}
		return t;
	}
	
	// Assign staff when adding the tutorial
	public Tutorial addTutorial(int day, double start, double end , Staff staff, Venue ven,ArrayList<Lesson> lessons) throws ClashException
	{
		Tutorial t = null;
		if(day==6||day==7||start>end)
			System.out.println("Something wrong with the input.");
		else
		{
			t = new Tutorial(day, start, end,staff, ven, this.course.getOffering(),lessons);
			if(this.checkClash(t))
				this.lessons.add(t);
			else
				t = null;
		}
		return t;
	}
	
	public Lesson getLesson(int day, double start)
	{
		Lesson l = null;
		int index;
		for(index=0;index<lessons.size();index++)
		{
			if(lessons.get(index).getDay()==day)
				if(lessons.get(index).getStart()==start)
				{
					l = lessons.get(index);
					break;
				}
		}
		if(index == lessons.size())
			System.out.print("Do not have such lesson.");	
		return l;		
	}

	// Throws Lesson ClashException, a course cannot offer two lessons at the same time
	public boolean checkClash(Lesson lesson) throws ClashException
	{
		ClashException ex = new ClashException(lesson);
		Lesson check = null;
		try
		{
			for(int index=0;index<lessons.size();index++)
			{
				check = lessons.get(index);
				if(check==lesson)
					continue;
				if(check.getDay()==lesson.getDay())
				{
					if((lesson.getStart()>=check.getStart() && lesson.getStart()<check.getEnd())||(lesson.getEnd()<=check.getEnd() && lesson.getEnd()>check.getStart()))
						throw ex;
				}
			}
			return true;
		}
		catch(Exception ex0)
		{
			if(check!=null)
				System.out.println(ex.toString()+", "+check.toString());
			return false;
		}
	}
	
	// Check the remain Capacity
	public int checkCapacity()
	{
		System.out.println("allow:"+this.getMaxnum()+", now:"+this.count);
		return count;
	}
	
	// Throw Course Offering CapacityException if it meets its max number already
	public boolean addStudent(Student student) throws CapacityException
	{
		CapacityException ex = new CapacityException(this);
		try 
		{
			if(this.count >= this.getMaxnum())
				throw ex;
			if(!students.contains(student))
			{
				if(student.addLesson(a))
				{
					students.add(student);
					System.out.println("Student join course offering successfully " + student.getID());
					count++;
					System.out.print(this.course.getID()+" ");
					this.checkCapacity();
					return true;
				}
			}
			else
				System.out.println("Already joined before");
			return false;
		}
		catch(Exception ex0)
		{
			System.out.println(ex.toString());
			return false;
		}
	}
	
	public boolean dropStudent(Student student)
	{
		for(int index=0;index<students.size();index++)
		{
			if(students.contains(student))
			{
				// Drop tutorial as well when dropping the course
				this.dropTutorial(student);
				students.remove(student);
				student.dropLesson(a);
				System.out.println("Student "+student.getID()+" drops the course successfully");
			}
		}
		return true;
	}
	
	// Cannot join a certain tutorial if the venue is full, Venue CapacityException
	// Throw ClashException if the student has other courses at that time
	public boolean joinTutorial(Student student, Lesson tutorial) throws CapacityException, ClashException
	{
		if(this.lessons.contains(tutorial))
		{
			if(students.contains(student))
			{
				boolean temp = false;
				// Casting , throw CapacityException
				if(((Tutorial)tutorial).addStudent(student))
				// Throw student lessons ClashException 
					temp = student.addLesson(tutorial);	
				return temp;
			}
			else
				System.out.println("The student has not enroll in this course offering yet:"+student.getID());
		}
		else
			System.out.println("No such a tutorial");
		return false;
	}
	
	public void dropTutorial(Student student)
	{
		for(int index=0;index<lessons.size();index++)
		{
			// skip lecture
			if(lessons.get(index) instanceof Lecture)
				continue;
			if(((Tutorial)lessons.get(index)).removeStudent(student))
			{
				student.dropLesson(lessons.get(index));
				System.out.println("Student "+student.getID()+" drops the tutorial successfully.");
			}
		}
	}
}
