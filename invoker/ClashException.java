package invoker;

import java.util.ArrayList;

public class ClashException extends Exception {
	
	private ArrayList<Lesson> lessons;
	
	public ClashException(ArrayList<Lesson> lessons) {
		super("Already been taken");
		this.lessons = lessons;
	}
	
	public void getClashLesson() {
		
	}

}
