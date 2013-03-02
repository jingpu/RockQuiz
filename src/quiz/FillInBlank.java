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
public class FillInBlank extends QuestionBase {
	private static final String typeIntro = "In this type of question, given a question with a blank, user need to fill in the blank"
			+ " Correct answer will get full score, while the wrong answer will get zero";

	public FillInBlank(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, String correctRatio) {
		super(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio);

		// TODO Auto-generated constructor stub
	}

	public FillInBlank(String questionType, String questionId) {
		// TODO Auto-generated constructor stub
		super(questionType, questionId);
	}

	@Override
	public void saveToDatabase() {
		// TODO Auto-generated method stub
		String queryStmt = "INSERT INTO " + FIB_Table + " VALUES (\""
				+ questionId + "\", \"" + creatorId + "\", \"" + typeIntro
				+ "\", \"" + questionDescription + "\", \"" + answer + "\", \""
				+ maxScore + "\", \"" + tagString + "\", \"" + correctRatio
				+ "\")";

		try {
			Connection con = MyDB.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(queryStmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String parsePrefix() {
		return questionDescription.split("#blank#")[0];
	}

	private String parseSuffix() {
		return questionDescription.split("#blank#")[1];
	}

	public static String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a Fill-In-Blank question</h1>");
		html.append("<p> Please enter proposed question description and answer. In order to insert a blank, please follow the format #blank# </p>");

		// create the form
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> i.e. In order to express a question: I think _____ is awesome. You should type it as \"I think #blank# is awesome\"</p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.FIB + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\"></input></p>");
		html.append("<input type=\"submit\" value = \"Save\"/></form>");
		html.append("");

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

		// create prefix, blank, suffix
		html.append("<p>" + parsePrefix()
				+ "<input type=\"text\" name=\"answer\" >" + parseSuffix()
				+ "</input></p>");

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ getQuestionType() + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" value=\""
				+ getQuestionId() + "\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		return html.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public String getScore(String userInput) {
		// TODO Auto-generated method stub
		if (userInput.equals(answer))
			return maxScore;
		return "0";
	}

}
