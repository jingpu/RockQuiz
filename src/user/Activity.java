package user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import quiz.MyQuiz;
import quiz.Quiz;
import util.Helper;

/**
 * @author youyuan
 * **/
public class Activity {
	public final String user;
	public final String time;
	public final String type;
	public final String content;

	public Activity(String user, String time, String type, String content){
		this.user = user;
		this.time = time;
		this.type = type;
		this.content = content;
	}

	public String toString(boolean useTimeTrsf){
		StringBuilder dscr = new StringBuilder();
		dscr.append(Helper.displayUser(user));
		if(type.charAt(0) == 't') {
			Quiz quiz = new MyQuiz(content);
			dscr.append(" took "+ Helper.displayQuiz(quiz, false));

		} else if(type.equals("c")){
			Quiz quiz = new MyQuiz(content);
			dscr.append(" created a quiz " + Helper.displayQuiz(quiz, false));

		} else if(type.charAt(0) == 'a'){
			Quiz quiz = new MyQuiz(content);
			dscr.append(" earned " + Helper.getTitle(type) + " in quiz "
					+ Helper.displayQuiz(quiz, false));

		} else {
			System.out.println("activity does not exist");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			if(useTimeTrsf) dscr.append(TimeTrsf.dscr(date, now));
			else dscr.append(date);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}

	public String toStringMe(boolean useTimeTrsf){
		System.out.println(type);
		StringBuilder dscr = new StringBuilder();
		dscr.append("You");
		if(type.charAt(0) == 't') {
			Quiz quiz = new MyQuiz(content);
			String quizId = type.substring(1);
			String score = quiz.getScore(quizId);
			String full = quiz.getMaxScore();
			dscr.append(" got " + score + "/" + full + " in "
					+ Helper.displayQuiz(quiz, false));

		} else if(type.equals("c")){
			Quiz quiz = new MyQuiz(content);
			dscr.append(" created a quiz "+ Helper.displayQuiz(quiz, false));

		} else if(type.charAt(0) == 'a'){
			Quiz quiz = new MyQuiz(content);
			dscr.append(" earned " + Helper.getTitle(type) + " in "
					+ Helper.displayQuiz(quiz, false));
			
		} else {
			System.out.println("activity does not exist");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			dscr.append(" ");
			if(useTimeTrsf) dscr.append(TimeTrsf.dscr(date, now));
			else dscr.append(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}
}
