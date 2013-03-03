package user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author youyuan
 * **/
public class Activity {
	public final String time;
	public final String type;
	public final String content;
	
	public Activity(String time, String type, String content){
		this.time = time;
		this.type = type;
		this.content = content;
	}
	
	public String toString(){
		StringBuilder dscr = new StringBuilder();
		if(type.charAt(0) == 't') {
			dscr.append(" took quiz ");
		} else if(type == "c"){
			dscr.append(" created quiz ");
		} else if(type == "a"){
			dscr.append(" earned a new achievement ");
		} else {
			System.out.println("activity does not exist");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			dscr.append(content).append(" " + TimeTrsf.dscr(date, now));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}
}
