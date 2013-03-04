/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import database.MyDB;

/**
 * @author yang
 * 
 */
public class QResponse extends QuestionBase {

	// TODO: add partial score feature here -> be handed by human grader
	private static final String typeIntro = "In this type of question, given a question, "
			+ "user need to answer the question in the answer area. Correct answer will get full score, "
			+ "while the wrong answer will get zero";

	public QResponse(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, float correctRatio) {
		super(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for connecting database
	 * 
	 * @param questionId
	 */
	public QResponse(String questionType, String questionId) {
		super(questionType, questionId);
	}

	@Override
	public void saveToDatabase() {
		queryStmt = "INSERT INTO " + QR_Table + " VALUES (\"" + questionId
				+ "\", \"" + creatorId + "\", \"" + typeIntro + "\", \""
				+ questionDescription + "\", \"" + answer + "\", \"" + maxScore
				+ "\", \"" + tagString + "\", " + correctRatio + ")";
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
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
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
		StringBuilder html = new StringBuilder();
		html.append(super.printReadHtml());

		html.append("<p>This is a question page, please read the question information, and make an answer</p>");
		html.append("<p>" + typeIntro + "</p>\n");
		html.append("<form action=\"QuestionProcessServlet\" method=\"post\">");
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer_"
				+ getQuestionId() + "\" ></input></p>");

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\"  ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		return html.toString();

	}

	/**
	 * @return
	 */
	public static String getAnswerString(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String answer = request.getParameter("answer");
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#printReadHtmlForSingle()
	 */
	@Override
	public String printReadHtmlForSingle() {
		StringBuilder html = new StringBuilder();
		html.append(super.printReadHtml());

		html.append("<p>This is a question page, please read the question information, and make an answer</p>");
		html.append("<p>" + typeIntro + "</p>\n");

		// form action should be here
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>");

		// every form field will be renamed as xx_questionId
		html.append("<p>Answer:   <input type=\"text\" name=\"answer_"
				+ getQuestionId() + "\" ></input></p>");

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\"  ></input></p>");

		return html.toString();
	}

	public static String getCreatedAnswer(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter("answer");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quiz.QuestionBase#getUserAnswer(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getUserAnswer(HttpServletRequest request) {
		return request.getParameter("answer_" + getQuestionId());
	}

}
