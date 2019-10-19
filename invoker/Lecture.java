package invoker;

import java.util.ArrayList;

public class Lecture extends Lesson{

	/**
	 * 
	 */
	private static final long serialVersionUID = 40L;

	public Lecture(int d, double sh, double eh, Venue v, CourseOffering co) throws ClashException {
		super(d,sh,eh,v,co);
		// check capacity when add lecture
		this.checkCap();
	}
	public Lecture(int d, double sh, double eh, Staff s, Venue v, CourseOffering co, ArrayList<Lesson> lessons) throws ClashException
	{
		super(d,sh,eh,s,v,co,lessons);
		// check capacity when add lecture
		this.checkCap();
	}
	
	@Override
	String printType()
	{
		return "Lecture";
	}
		
	@Override
	public void listStudents() 
	{
		System.out.println(super.toString());
		for(int index=0;index<super.getCourseOffering().getStudents().size();index++)
			System.out.println(super.getCourseOffering().getStudents().toString());
	}
	
	// Check the venue is enough or not for the lecture
	private void checkCap()
	{
		if(super.getVenue()!=null)
		{
			CapacityException cex = new CapacityException(super.getVenue());
			try {
				// if the venue cannot provide enough seats for the lecture, throw exception
				if(super.getCourseOffering().getMaxnum()>super.getVenue().getCapacity())
					throw cex;
			}
			catch(Exception ex0){
				System.out.println(cex.toString()+" Reset venue to empty");
				super.resetVenue();
			}
		}
	}
	
}
