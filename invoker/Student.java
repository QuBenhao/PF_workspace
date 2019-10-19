package invoker;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	
	private String ID;
	private String name;
	private ArrayList<Lesson> lessons;
	
	private final int MAXLESSON =8;
	private final int MAXLECTURE =4;
	
	public Student(String ID, String name)
	{
		this.ID = ID;
		this.name = name;
		this.lessons = new ArrayList<Lesson>();
	}
		
	public String getID()
	{
		return ID;
	}
	
	// To get information that which lessons the student enrolls
	public ArrayList<Lesson> getLessons()
	{
		return lessons;
	}
	
	public boolean addLesson(Lesson lesson) throws ClashException
	{
		ClashException ex = new ClashException(this);
		int count=0,countlecture=0;
		for(int index=0;index<lessons.size();index++)
		{
			if(lessons.get(index).getCourseOffering().getCourse().getID().compareTo(lesson.getCourseOffering().getCourse().getID())==0)
				count++;
			else if(lessons.get(index) instanceof Lecture)
				countlecture++;
		}
		// a student can only enroll 8 lessons OR 4 lectures
		if(lessons.size() == MAXLESSON || countlecture == MAXLECTURE)
			System.out.println("Student can only join four courses within one semester!");
		else if(count>=2)
			System.out.println("Cannot enroll too many lessons for the same course");
		else
		{
			try {
				Lesson n;
				for(int index=0;index<lessons.size();index++)
				{
					n = lessons.get(index);
					if(n.getDay()==lesson.getDay() && ((lesson.getStart()>=n.getStart()&&lesson.getStart()<n.getEnd())||(lesson.getEnd()<=n.getEnd()&&lesson.getEnd()>n.getStart())))
						throw ex;
				}
				lessons.add(lesson);
				return true;
			}
			catch(Exception ex0)
			{
				System.out.println(ex.toString());
			}
		}
		return false;
	}
	
	public void dropLesson(Lesson lesson)
	{
		if(!lessons.remove(lesson))
			System.out.println("Did not enroll in such lesson: "+lesson.toString());
	}
	
	@Override
	public String toString()
	{
		String info ="";
		info += "Student ID: \"" + ID;
		info += "\", Student name: \""+name;
		info += "\"\n";
		for(int index=0;index<lessons.size();index++)
			info += "Enroll in: "+lessons.get(index).getCourseOffering().getCourse().getID()+", "+lessons.get(index).toString() + "\n";
		return info;
	}
}
