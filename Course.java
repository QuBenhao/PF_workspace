package invoker; 

public class Course {

	// Instance variables
	private String ID;
	private String name;
	private String purpose;
	
	// Constructor Declaration of Class
	public Course(String ID, String name, String purpose)
	{
		this.ID = ID;
		this.name = name;
		this.purpose = purpose;
	}
	
	// method to get the ID of the course
	public String getID()
	{
		return ID;
	}
	
	// method to get the name of the course
	public String getName()
	{
		return name;
	}
	
	// method to get the objective of the course
	public String getPurpose()
	{
		return purpose;
	}
	
	
	CourseOffering co = null;
	// create
	public boolean createOffering(int max,Course c)
	{
		if(co==null)
		{
			co = new CourseOffering(max,c);
			return true;
		}
		else
		{
			System.out.print("The course offering already exists!");
			return false;
		}
	}
	
	//
	public CourseOffering getOffering()
	{
		return co;
	}
	
	@Override
	public String toString()
	{
		return "Course ID: "+ getID() +", Name: "+ getName() +", Purpose: "+getPurpose();
	}
	
}
