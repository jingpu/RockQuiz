/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import database.MyDB;

/**
 * @author yang
 * 
 */
public class MultiChoice extends QuestionBase {
	// TODO: merge all choices into one field -> enable user add more than 4
	// choices
	private String choices;
	private static final String typeIntro = "MultiChoice question: user should choose one correct answer from choice options"
			+ "Correct answer will get full score, while the wrong answer will get zero";

	public MultiChoice(String questionType, String creatorId, int timeLimit,
			String questionDescription, String answer, String maxScore,
			String tagString, float correctRation, String choices) {
		super(questionType, creatorId, timeLimit, questionDescription, answer,
				maxScore, tagString, correctRation);
		// TODO Auto-generated constructor stub
		this.choices = choices;
	}

	public MultiChoice(String questionType, String questionId) {
		super(questionType, questionId);
		String tmpChoices = "error";

		try {
			Connection con = MyDB.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			ResultSet rs = stmt.executeQuery(queryStmt);
			rs.next();
			tmpChoices = rs.getString(9);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		choices = tmpChoices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getQuerySaveString()
	 */
	@Override
	public String getQuerySaveString() {
		return "INSERT INTO " + MC_Table + " VALUES (\""
				+ super.getBaseQuerySaveString() + ", \"" + choices + "\")";
	}

	public static String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a multiChoice question</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");

		// Choice options
		html.append("<p>ChoiceA:   <input type=\"text\" name=\"choice0\" ></input> <input type=\"radio\" name=\"answer\" value=\"choice0\"></input></p>");
		html.append("<p>ChoiceB:   <input type=\"text\" name=\"choice1\" ></input> <input type=\"radio\" name=\"answer\" value=\"choice1\"></input></p>");
		html.append("<p>ChoiceC:   <input type=\"text\" name=\"choice2\" ></input> <input type=\"radio\" name=\"answer\" value=\"choice2\"></input></p>");
		html.append("<p>ChoiceD:   <input type=\"text\" name=\"choice3\" ></input> <input type=\"radio\" name=\"answer\" value=\"choice3\"></input></p>");

		// Answer and Full Score
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\"0\" ></input></p>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MC + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"numChoices\" value=\"4\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>");
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

		// create choice options
		String choicesList[] = choices.split("#");
		for (int i = 0; i < choicesList.length; i++) {
			if (choicesList[i].isEmpty()) // remove empty string at head/end
				++i;
			html.append("<p><input type=\"radio\" name=\"answer_"
					+ getQuestionId() + "\" value= \"" + choicesList[i] + "\">"
					+ choicesList[i] + "</input></p>");
		}

		// Hidden information - questionType and questionId information
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\""
				+ timeLimit + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"numChoices_"
				+ getQuestionId() + "\" value=\"4\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\"></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\" ></input></p>");
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

	// TODO: change the multi-choice table structure, and merge different choice
	// options into one field
	public static String getCreatedChoices(HttpServletRequest request) {
		int numChoices = Integer.parseInt(request.getParameter("numChoices"));
		StringBuilder choices = new StringBuilder();
		for (int i = 0; i < numChoices; i++) {
			choices.append("#");
			choices.append(request.getParameter("choice" + i));
			choices.append("#");
		}
		return choices.toString();
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

		String choicesList[] = choices.split("#");
		for (int i = 0; i < choicesList.length; i++) {
			if (choicesList[i].isEmpty()) // remove empty string at head/end
				++i;
			html.append("<p><input type=\"radio\" name=\"answer_"
					+ getQuestionId() + "\" value= \"" + choicesList[i] + "\">"
					+ choicesList[i] + "</input></p>");
		}

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"numChoices_"
				+ getQuestionId() + "\" value=\"4\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\"  ></input></p>");

		return html.toString();
	}

	/**
	 * Create answer in the format: #answer0##answer1##answer2#..#
	 * 
	 * @param request
	 * @return
	 */
	public static String getCreatedAnswer(HttpServletRequest request) {
		// str is a string, i.e. "choice0"
		String str = request.getParameter("answer");
		return request.getParameter(str);
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
