/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 ******************************************************************************/
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
			return fromDisp + challengeContent + quizDisp + ".<br><br>\""+ content +"\"";
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
