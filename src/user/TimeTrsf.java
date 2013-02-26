package user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTrsf {
	public static String dscr(Date date1, Date date2){
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy");
		StringBuilder description = new StringBuilder();
		if(fmt1.format(date1).equals(fmt1.format(date2))){
			long difference = date2.getTime() - date1.getTime();
			Date diffTime = new Date(difference);
			Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
			calendar.setTime(diffTime);   // assigns calendar to given date 
			int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
			int min = calendar.get(Calendar.MINUTE);
			int sec = calendar.get(Calendar.SECOND);
			if(hour > 1) {
				description.append(hour).append(" hours ago");
			} else if(hour == 1) {
				description.append(hour).append(" hour ago");
			} else if(min > 1) {
				description.append(min).append(" mins ago");
			} else if(min == 1) {
				description.append(min).append(" min ago");
			} else if(sec > 1) {
				description.append(sec).append(" secs ago");
			} else {
				description.append(" just now");
			} 
		} else if(fmt2.format(date1).equals(fmt2.format(date2))){
			SimpleDateFormat fmt3 = new SimpleDateFormat("MMM dd 'at' hh:mm");
			description.append(fmt3.format(date1));
		} else {
			SimpleDateFormat fmt4 = new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm");
			description.append(fmt4.format(date1));
		}
		return description.toString();
	}
}
