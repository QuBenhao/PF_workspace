package invoker;

import java.util.ArrayList;

public class Tutorial extends Lesson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 41L;
	// allow how many students
	private int count;
	private ArrayList<Student> students;
	
	public Tutorial(int d, double sh, double eh, Venue v, CourseOffering co) throws ClashException 
	{
		super(d,sh,eh,v,co);
		this.count = 0;
		this.students = new ArrayList<Student>();
	}
	
	public Tutorial(int d, double sh, double eh, Staff s, Venue v, CourseOffering co, ArrayList<Lesson> lessons) throws ClashException
	{
		super(d,sh,eh,s,v,co,lessons);
		this.count = 0;
		this.students = new ArrayList<Student>();
	}
	
	public boolean addStudent(Student student) throws CapacityException
	{
		if(super.getVenue()==null)
		{
			System.out.println("No venue information! Cannot enroll");
			return false;
		}
		CapacityException ex = new CapacityException(super.getVenue());
		try
		{
			if(count>=super.getVenue().getCapacity())
				throw ex;
			count++;
			students.add(student);
			System.out.println("Student join tutorial successfully " + student.getID());
		}
		catch(Exception ex0)
		{
			System.out.println(ex.toString());
			return false;
		}
		return true;
	}
	
	public boolean removeStudent(Student student)
	{
		if(students.contains(student))
		{
			students.remove(student);
			return true;
		}
		return false;
	}
	
	@Override
	public void listStudents() 
	{
		System.out.println(super.toString());
		for(int index=0;index<students.size();index++)
			System.out.println(students.get(index).toString());
	}
	
	@Override
	String printType()
	{
		return "Tutorial";
	}

}
