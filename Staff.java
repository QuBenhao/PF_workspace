package invoker;

public class Staff {

	// Instance variables
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
	
	public void assign(Lesson a, Lesson b)
	{
		a.setStaff(this);
		b.setStaff(this);
	}
	
	
	@Override
	public String toString()
	{
		return "Staff ID: "+geteNo()+", Name: "+getName()+", Position: "+getPosition()+", Office: "+getOffice();
	}
}
