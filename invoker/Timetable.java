package invoker;

// package from https://github.com/davejm/SwingCalendar
import com.davidmoodie.SwingCalendar.Calendar;
import com.davidmoodie.SwingCalendar.CalendarEvent;
import com.davidmoodie.SwingCalendar.WeekCalendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Timetable {
	
	// my own idea
	private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
	// to fit my idea in the owners' code
	LocalDate current = LocalDate.now();
	LocalDate a = current.with(DayOfWeek.MONDAY);
    JFrame frm = new JFrame();
	
	public Timetable(ArrayList<Lesson> lessons)
	{
		this.lessons = lessons;
	}
	
	
    public void print(String type,String ID) {
        
        // calculate the lessons: int day, double start, double end into localdate format
        LocalDate d;
        // According to the format, need these variables
        int year,month,day,hour1,hour2,min1,min2;
        // to transform double start to int hour and int minute
        double temp;
        ArrayList<CalendarEvent> events = new ArrayList<>();
        
        for(int index=0;index<lessons.size();index++)
        {
        	Lesson n = lessons.get(index);
        	Course c;
        	Color color = Color.YELLOW;
        	c = n.getCourseOffering().getCourse();
        	
        	// use different color for different type
        	if(type.compareToIgnoreCase("Course")==0)
        		color = Color.PINK;
        	else if(type.compareToIgnoreCase("Staff")==0)
        			color = Color.LIGHT_GRAY;
        	else
        		color = Color.YELLOW;
        	
        	if((type.compareToIgnoreCase("Course")==0 && ID.compareTo(c.getID())==0 ) || (type.compareToIgnoreCase("Staff")==0 && n.getStaff()!=null && ID.compareTo(n.getStaff().geteNo())==0) ||(type.compareToIgnoreCase("Venue")==0 && n.getVenue()!=null && ID.compareTo(n.getVenue().getLocation())==0) ||type.compareToIgnoreCase("Student")==0)        		
        	{
        		// a = Monday
        		d = a.plusDays(n.getDay()-1);
        		year = d.getYear();
        		month = d.getMonthValue();
        		day = d.getDayOfMonth();	
        		hour1 = (int)n.getStart();
        		temp = n.getStart()*100;
        		min1 = (int)temp - 100*hour1;
        		hour2 = (int)n.getEnd();
        		temp = n.getEnd()*100;	
        		min2 = (int)temp - 100*hour2;
        		events.add(new CalendarEvent(LocalDate.of(year, month, day),LocalTime.of(hour1, min1),LocalTime.of(hour2, min2),c.getName()+" "+n.printType(), color));
        	}
        }        

        WeekCalendar cal = new WeekCalendar(events);

        cal.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
        cal.addCalendarEmptyClickListener(e -> {
            System.out.println(e.getDateTime());
            System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
        });
        
        frm.add(cal, BorderLayout.CENTER);
        frm.setSize(1000, 900);
        frm.setVisible(true);
        
        // Change to HIDE_ON_CLOSE
    }
    
    
    // To exist
    public void close()
    {
    	frm.dispatchEvent(new WindowEvent(frm, WindowEvent.WINDOW_CLOSING));
    }
    
    
    // Code from https://blog.csdn.net/A694543965/article/details/73332934
    public void savePic(String type,String filename){
		
		System.out.println("Processing - Saving "+type+" timetable to PNG file...");
		
		Container content=frm.getContentPane();
		
		BufferedImage img=new BufferedImage(
				frm.getWidth(),frm.getHeight(),BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = img.createGraphics();
		
		content.printAll(g2d);
		
		type = type.toLowerCase();
		// mkdir()
		File dir = new File(type);
		if(!dir.exists())
			dir.mkdir();
		File f = new File(dir.getAbsolutePath()+"/"+type+"_"+filename+".png");
		System.out.println("Saved in: "+dir.getAbsolutePath());
		try {
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g2d.dispose();

		// print the date or not
		System.out.println("Succeed!"/*+current*/);
    }
}
