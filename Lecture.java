package invoker;

class Lecture extends Lesson{

	public Lecture(int d, double sh, double eh, Staff s, Venue v, CourseOffering co) {
		super(d,sh,eh,s,v,co);
		// TODO Auto-generated constructor stub
	}
	
	String printType()
	{
		return "Lecture";
	}
	
}
