package invoker;

import java.util.Scanner;

public class Mainclass {

	public static void main(String args[]) throws Exception
	{
		Scanner scan = new Scanner(System.in);
		String choice;
		do
		{
			System.out.print("Login in as admin or student? Please enter:");
			scan.useDelimiter("\n");
			choice = scan.next();
			Application user = new Application();
			user.start(choice);
			System.out.print("Enter Q to quit");
			choice = scan.next();	
		}while(choice.compareToIgnoreCase("Q")!=0);
		scan.close();
		System.exit(0);
	}
}
