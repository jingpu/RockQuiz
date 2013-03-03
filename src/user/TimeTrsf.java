package user;

import java.text.SimpleDateFormat;
import java.util.Date;

/**@author youyuan
 * **/

public class TimeTrsf {
	 
	private static long ahour = 1000*60*60;
	private static long amin = 1000*60;
	private static long asec = 1000;
	
	public static String dscr(Date date1, Date date2){
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy");
		StringBuilder description = new StringBuilder();
		if(fmt1.format(date1).equals(fmt1.format(date2))){
			long difference = date2.getTime() - date1.getTime();
			
			if(difference > ahour) {
				description.append(difference/ahour).append(" hours ago");
			} else if(difference == ahour) {
				description.append("1 hour ago");
			} else if(difference > amin) {
				description.append(difference/amin).append(" mins ago");
			} else if(difference == amin) {
				description.append("1 min ago");
			} else if(difference > asec) {
				description.append(difference/asec).append(" secs ago");
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
