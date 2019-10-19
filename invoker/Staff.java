package invoker;

import java.io.Serializable;
import java.util.ArrayList;

public class Staff implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String eNo;
	private String name;
	private String position;
	private String office;
	
	public Staff(String eNo, String name, String position, String office)
	{
		this.eNo = eNo;
		this.name = name;
		this.position = position;
		this.office = office;
	}
	
	public String geteNo()
	{
		return eNo;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPosition()
	{
		return position;
	}
	
	public String getOffice()
	{
		return office;
	}
	
	// assign staff, throws staff clash exception
	public void assign(Lesson a, ArrayList<Lesson> lessons) throws ClashException
	{
		a.setStaff(this,lessons);
	}	
	
	@Override
	public String toString()
	{
		return "Staff ID: "+geteNo()+", Name: "+getName()+", Position: "+getPosition()+", Office: "+getOffice();
	}
}
