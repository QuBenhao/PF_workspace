package invoker;

import java.util.ArrayList;

public class CourseOffering {

	private int maxNum;
	private Course course;
	ArrayList<Lesson> lessons = new ArrayList<Lesson>();	
	Lecture a = null;	
	
	public CourseOffering(int maxNum,Course course)
	{
		this.maxNum = maxNum;
		this.course = course;
	}
	
	public int getMaxnum()
	{
		return maxNum;
	}
	
	public Course getCourse()
	{
		return course;
	}
	
	public Lecture addLecture(int day, double start, double end , Venue ven)
	{
		if(a==null)
		{
			if(day==6||day==7||start>end)
				System.out.println("Something wrong with the input.");
			else
			{
				a = new Lecture(day, start, end, null, ven, this.course.getOffering());
				lessons.add(a);
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
	
	public Tutorial addTutorial(int day, double start, double end , Venue ven)
	{
		Tutorial t = null;
		if(day==6||day==7||start>end)
			System.out.println("Something wrong with the input.");
		else
		{
			t = new Tutorial(day, start, end, null, ven, this.course.getOffering());
			lessons.add(t);
		}
		return t;
	}
	
	public Lesson getLesson(int day, double start)
	{
		Lesson l = null;
		int index = 0;
		for(;index<lessons.size();index++)
		{
			if(lessons.get(index).getDay()==day)
				if(lessons.get(index).getStart()==start)
				{
					l = lessons.get(index);
					// what if there are two tutorial at the same time for the same course
					break;
				}
		}
		if(index == lessons.size())
			System.out.print("Do not have such lesson.");	
		return l;		
	}
	
	
}
