/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.MyDB;

/**
 * @author yang
 * 
 */
public class MAQuestion extends QuestionBase {

	private static final String typeIntro = "In this type of question, given a question, "
			+ "users need to answer all the answer fields. Every answer field is just part of the answer."
			+ "Only correctly answering all the question fields could a user get full score"
			+ "Correctly answering an answer field will get a positive 3 points"
			+ "Otherwise, user would get a negative 1 point."
			+ "The final grade is the sum of postive and negative points. Lowest score is 0";

	/**
	 * @param questionType
	 * @param questionId
	 */
	public MAQuestion(String questionType, String questionId) {
		super(questionType, questionId);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param questionType
	 * @param creatorId
	 * @param questionDescription
	 * @param answer
	 * @param maxScore
	 * @param tagString
	 * @param correctRatio
	 */
	public MAQuestion(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, String correctRatio) {
		super(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#saveToDatabase()
	 */
	@Override
	public void saveToDatabase() {
		queryStmt = "INSERT INTO " + MA_Table + " VALUES (\"" + questionId
				+ "\", \"" + creatorId + "\", \"" + typeIntro + "\", \""
				+ questionDescription + "\", \"" + answer + "\", \"" + maxScore
				+ "\", \"" + tagString + "\", \"" + correctRatio + "\")";
		try {
			Connection con = MyDB.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(queryStmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a question-response question</h1>\n");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> Please enter proposed question description and answer </p>\n");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>\n");

		// TODO: javascript to dynamically expand the number of answers
		html.append("<p>Answer:   <input type=\"text\" name=\"answer1\" ></input></p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer2\" ></input></p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer3\" ></input></p>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");

		// Hidden information - questionType and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\"  value=\""
				+ QuestionBase.QR + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\"></input></p>\n");
		html.append("<input type=\"submit\" value = \"Save\"/></form>");
		return html.toString();
	}

	@Override
	public String printReadHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append(super.printReadHtml());

		html.append("<p>This is a question page, please read the question information, and make an answer</p>");
		html.append("<p>" + typeIntro + "</p>\n");
		html.append("<form action=\"QuestionProcessServlet\" method=\"post\">");
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ getQuestionType() + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" value=\""
				+ getQuestionId() + "\"  ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		return html.toString();
	}
}
