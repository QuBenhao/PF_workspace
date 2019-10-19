package invoker;

public class Menu {
	
	public static void mainmenu()
	{
		System.out.println("**************** Welcome to the Timetabling and Enrolment System ****************");
		System.out.println("1: To do actions related to courses");
		System.out.println("2: To do actions related to venues");
		System.out.println("3: To do actions related to staffs");
		System.out.println("4: To do actions related to lessons");
		System.out.println("5: To print timetable");
		System.out.println("6: To input data from other files");
		System.out.println("7: To enroll student");
		System.out.println("8: To show all the students");
		System.out.println("9: To show menu again");
		System.out.println("0 or letters: To exit");
		System.out.println("*********************************************************************************");
	}
	
	public static void studentmenu()
	{
		System.out.println("**************** Welcome to the Timetabling and Enrolment System ****************");
		System.out.println("1: To list all the courses");
		System.out.println("2: To list all the lessons");
		System.out.println("3: To join or quit a course offering");
		System.out.println("4: To join or quit a tutorial");
		System.out.println("5: To list all the lessons of a student");
		System.out.println("6: To register students");
		System.out.println("7: To exit");
	}
	
	public static void coursemenu()
	{
		System.out.println("Enter 1: To add course");
		System.out.println("Enter 2: To add course Offering");
		System.out.println("Enter 3: To find course information");
		System.out.println("Enter 4: To list all the course");
		System.out.println("Enter 5: To remove a course");
		System.out.println("Enter 6: To find course offering information");
		System.out.println("Enter 7: To list all the lessons of a course");
	}
	
	public static void venuemenu()
	{
		System.out.println("Enter 1: To add venue");
		System.out.println("Enter 2: To find venue information");
		System.out.println("Enter 3: To list all the venue");
		System.out.println("Enter 4: To add lesson to the venue");
		System.out.println("Enter 5: To list all the lessons of a venue");
	}
	
	public static void staffmenu() 
	{
		System.out.println("Enter 1: To add staff");
		System.out.println("Enter 2: To find staff information");
		System.out.println("Enter 3: To list all the staff");
		System.out.println("Enter 4: To assign lesson to the staff");
		System.out.println("Enter 5: To list all the lessons of a staff");
	}

}
