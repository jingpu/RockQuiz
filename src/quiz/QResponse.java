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
public class QResponse extends QuestionBase {

	/**
	 * Constructor for connecting database
	 * @param questionId
	 */
	public QResponse(String questionType, String questionId) {
		super(questionType, questionId);
	}

	@Override
	public void saveToDatabase() {
		// TODO Auto-generated method stub
		queryStmt = "INSERT INTO " + QR_Table + "VALUES (\"" +
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

	
	@Override
	public String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append(super.printCreateHtml());
		html.append("<h1>This page will guide you to create a question-response question</h1>");
		html.append("<form action=\"QuestionCreationServlet\" method=\"post\">");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionType\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Save\"/></form>");
		return html.toString();
	}

	@Override
	public String printReadHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append(super.printReadHtml());
		
		html.append("<h1>This is a question page, please read the question information, and make an answer</h1>");
		html.append("<form action=\"QuestionCreationServlet\" method=\"post\">");
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionType\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		return html.toString();
		
	}
	
	


}
