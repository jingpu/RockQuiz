package user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import quiz.*;
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
		QuizManager man = new MyQuizManager();
		String quizDisplay = content + "(deprecated)";
		Quiz quiz = man.getQuiz(content);
		if(quiz != null){
			quizDisplay = Helper.displayQuiz(quiz, false);
		}
		StringBuilder dscr = new StringBuilder();
		dscr.append(Helper.displayUser(user));
		if(type.charAt(0) == 't') {
			dscr.append(" took "+ quizDisplay);

		} else if(type.equals("c")){
			dscr.append(" created a quiz " + quizDisplay);

		} else if(type.charAt(0) == 'a'){
			dscr.append(" earned " + "<a href='#' title='"
					+ Helper.getTitleDescription(type) +"' style='font-weight:bold;'>"
					+ Helper.getTitle(type) + "</a> in "
					+ quizDisplay);
			
		} else {
			System.out.println("activity does not exist");
		}
		dscr.append(" | ");
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
		QuizManager man = new MyQuizManager();
		String quizDisplay = content + "(deprecated)";
		Quiz quiz = man.getQuiz(content);
		if(quiz != null){
			quizDisplay = Helper.displayQuiz(quiz, false);
		}
		StringBuilder dscr = new StringBuilder();
		dscr.append("I");
		if(type.charAt(0) == 't') {
			if(quiz == null){
				dscr.append(" took "+ quizDisplay);
			} else {
				String quizId = type.substring(1);
				String score = quiz.getScore(quizId);
				if(score == null){
					dscr.append(" took "+ quizDisplay);
				} else{
					String full = quiz.getMaxScore();
					dscr.append(" got " + score + "/" + full + " in "
							+ quizDisplay);
				}
			}
		} else if(type.equals("c")){
			dscr.append(" created a quiz "+ quizDisplay);

		} else if(type.charAt(0) == 'a'){
			dscr.append(" earned " + "<a href='#' title='"
					+ Helper.getTitleDescription(type) +"' style='font-weight:bold;'>"
					+ Helper.getTitle(type) + "</a> in "
					+ quizDisplay);

		} else {
			System.out.println("activity does not exist");
		}

		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			dscr.append(" | ");
			if(useTimeTrsf) dscr.append(TimeTrsf.dscr(date, now));
			else dscr.append(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}
}
