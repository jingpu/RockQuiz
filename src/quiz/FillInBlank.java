/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.SQLException;

import database.MyDB;

/**
 * @author yang
 *
 */
public class FillInBlank extends QuestionBase {

	public FillInBlank(String questionType, String questionId) {
		// TODO Auto-generated constructor stub
		super(questionType, questionId);
		
	}

	@Override
	public void saveToDatabase() {
		// TODO Auto-generated method stub
		queryStmt = "INSERT INTO " + FIB_Table + "VALUES (\"" +
					questionId + "\", \"" +
					creatorId + "\", \"" +
					typeIntro + "\", \"" +
					questionDescription  + "\", \"" +
					answer + "\", \"" +
					maxScore + "\", \"" +
					tagString + "\", \"" +
					correctRatio + "\")";
		
		try {
		rs = stmt.executeQuery(queryStmt);
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public String getScore(String userInput) {
		// TODO Auto-generated method stub
		if (userInput.equals(answer)) return  maxScore;
		return null;
	}
	
	
	@Override
	public String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append(super.printCreateHtml());
		html.append("<h1>This page will guide you to create a Fill-In-Blank question</h1>");
		html.append("<p> Please enter proposed question description and answer. In order to insert a blank, please follow the format #blank# </p>");
		
		//create the form 
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> i.e. In order to express a question: I think _____ is awesome. You should type it as \"I think #blank# is awesome\"</p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionType\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Save\"/></form>");
		html.append("");

		return html.toString();
	}

	
	  
	
	
	
	@Override
	public String printReadHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append(super.printReadHtml());
		
		html.append("<h1>This is a question page, please read the question information, and make an answer</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionType\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		return html.toString();
		
	}


}
