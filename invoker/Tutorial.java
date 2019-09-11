package invoker;

class Tutorial extends Lesson {

	public Tutorial(int d, double sh, double eh, Staff s, Venue v, CourseOffering co) {
		super(d,sh,eh,s,v,co);
		// TODO Auto-generated constructor stub
	}
	
	String printType()
	{
		return "Tutorial";
	}

}
