package user;

import quiz.MyQuizManager;
import quiz.Quiz;
import quiz.QuizManager;

/**
 * @author youyuan
 * **/

public class Message {

	public final String from;
	public final String to;
	public final String type;
	public final String title;
	public final String content;
	private String time;
	private boolean ifRead;
	private static final String challengeTitle = "**CHALLENGE Letter**";
	private static final String friendRequestTitle = "**Friend Request**";
	private static final String friendConfirmTitle = " Are Friends Now";
	private static final String challengeContent = " wants to challenge you with this quiz ";
	private static final String friendRequestContent = " want to add you as friend. <br>";
	private static final String friendConfirmContent = "Say hello to ";
	
	public Message(String from, String to, String type, String title, String content){
		this.from = from;
		this.to = to;
		this.type = type;
		this.title = title;
		this.content = content;
		this.ifRead = false;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public void setRead(boolean ifRead){
		this.ifRead = ifRead;
	}
	
	public boolean getRead(){
		return this.ifRead;
	}
	
	public String getTitle(){
		if(type.equals("c")){
			return challengeTitle;
		} else if(type.equals("r")){
			return friendRequestTitle;
		} else if(type.equals("f")){
			return "You and " + from + friendConfirmTitle;
		} else if(type.equals("n")){
			if(title.equals("")) return "(no subject)";
			return title;
		} else {
			System.out.println("Invalid Type");
		}
		return "(no subject)";
	}
	
	public String getContent(){
		String fromDisp = "<a href='userpage.jsp?id=" + from + "' target='_top'>" + from + "</a>";
		if(type.equals("c")){
			QuizManager man = new MyQuizManager();
			Quiz quiz = man.getQuiz(title);
			String quizUrl = quiz.getSummaryPage();
			String quizDisp = "<a href='" + quizUrl + "' target='_top'>" + title + "</a>";
			return fromDisp + challengeContent + quizDisp + "<br>\""+ content +"\"";
		} else if(type.equals("r")){
			StringBuilder sb = new StringBuilder();
			sb.append(fromDisp).append(friendRequestContent);
			sb.append("<a href='RespondFriend?to="+ from +"'>Accept</a>");
			return sb.toString();
		} else if(type.equals("f")){
			return friendConfirmContent + fromDisp;
		} else if(type.equals("n")){
			return content;
		} else {
			System.out.println("Invalid Type");
			return "";
		}
	}
}
