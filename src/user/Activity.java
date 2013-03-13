package user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import quiz.MyQuizManager;
import quiz.Quiz;
import quiz.QuizManager;

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

	public String toString(){
		StringBuilder dscr = new StringBuilder();
		dscr.append("<a href=\"userpage.jsp?id=" + user + "\">" + user + "</a>");
		if(type.charAt(0) == 't') {
			QuizManager man = new MyQuizManager();
			Quiz quiz = man.getQuiz(content);
			String url = quiz.getSummaryPage();
			dscr.append(" took <a href=\"" + url + "\">" + content + "</a> ");

		} else if(type.equals("c")){
			QuizManager man = new MyQuizManager();
			Quiz quiz = man.getQuiz(content);
			String url = quiz.getSummaryPage();
			dscr.append(" created a quiz <a href=\"" + url + "\">" + content + "</a> ");

		} else if(type.equals("a")){
			dscr.append(" earned the " + content + " achievement ");
		} else {
			System.out.println("activity does not exist");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			dscr.append(TimeTrsf.dscr(date, now));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}

	public String toStringMe(){
		System.out.println(type);
		StringBuilder dscr = new StringBuilder();
		dscr.append("You");
		if(type.charAt(0) == 't') {
			QuizManager man = new MyQuizManager();
			Quiz quiz = man.getQuiz(content);
			String quizId = type.substring(1);
			System.out.println(quizId);
			String url = quiz.getSummaryPage();
			String score = quiz.getScore(quizId);
			String full = quiz.getMaxScore();
			dscr.append(" got " + score + "/" + full + " in quiz <a href=\"" 
			+ url + "\">" + content + "</a> ");

		} else if(type.equals("c")){
			QuizManager man = new MyQuizManager();
			Quiz quiz = man.getQuiz(content);
			String url = quiz.getSummaryPage();
			dscr.append(" created a quiz <a href=\"" + url + "\">" + content + "</a>");

		} else if(type.equals("a")){
			dscr.append(" earned the " + content + " achievement ");
		} else {
			System.out.println("activity does not exist");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			dscr.append(" " + TimeTrsf.dscr(date, now));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}
}
