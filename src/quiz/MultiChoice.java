/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import database.MyDB;

/**
 * @author yang
 * 
 */
public class MultiChoice extends QuestionBase {
	// TODO: merge all choices into one field -> enable user add more than 4
	// choices
	private List<String> choices;
	private static final String typeIntro = "MultiChoice question: user should choose one correct answer from choice options"
			+ "Correct answer will get full score, while the wrong answer will get zero";

	public MultiChoice(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, float correctRation, List<String> choices) {
		super(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRation);
		// TODO Auto-generated constructor stub
		this.choices = choices;
	}

	public MultiChoice(String questionType, String questionId) {
		super(questionType, questionId);
		try {
			Connection con = MyDB.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			ResultSet rs = stmt.executeQuery(queryStmt);
			rs.next();

			choices = new ArrayList<String>();
			for (int i = 0; i < 4; i++) {
				choices.add(rs.getString(i + 9));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveToDatabase() {
		// TODO Auto-generated method stub
		queryStmt = "INSERT INTO " + MC_Table + " VALUES (\"" + questionId
				+ "\", \"" + creatorId + "\", \"" + typeIntro + "\", \""
				+ questionDescription + "\", \"" + answer + "\", \"" + maxScore
				+ "\", \"" + tagString + "\", \"" + correctRatio + "\", \""
				+ choices.get(0) + "\", \"" + choices.get(1) + "\", \""
				+ choices.get(2) + "\", \"" + choices.get(3) + "\")";

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
		html.append("<h1>This page will guide you to create a multiChoice question</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");

		// Choice options
		html.append("<p>ChoiceA:   <input type=\"text\" name=\"choiceA\" ></input></p>");
		html.append("<p>ChoiceB:   <input type=\"text\" name=\"choiceB\" ></input></p>");
		html.append("<p>ChoiceC:   <input type=\"text\" name=\"choiceC\" ></input></p>");
		html.append("<p>ChoiceD:   <input type=\"text\" name=\"choiceD\" ></input></p>");

		// Answer and Full Score
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MC + "\" ></input></p>");
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
		html.append("<p><input type=\"radio\" name=\"answer\" value=\"A. "
				+ choices.get(0) + "\">" + choices.get(0) + "</input></p>");
		html.append("<p><input type=\"radio\" name=\"answer\" value=\"B. "
				+ choices.get(1) + "\">" + choices.get(1) + "</input></p>");
		html.append("<p><input type=\"radio\" name=\"answer\" value=\"C. "
				+ choices.get(2) + "\">" + choices.get(2) + "</input></p>");
		html.append("<p><input type=\"radio\" name=\"answer\" value=\"D. "
				+ choices.get(3) + "\">" + choices.get(3) + "</input></p>");

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ getQuestionType() + "\"></input></p>");
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

	/**
	 * Check answer is to check the answer body rather than just an option
	 * index(i.e. A,B,C) TODO: This will enable future shuffle of choices ABCD
	 * 
	 * @return
	 */
	public static String getAnswerString(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String answer = (String) session.getAttribute("answer");
		return answer;
	}

	// TODO: change the multi-choice table structure, and merge different choice
	// options into one field
	public static String getChoicesString(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int numChoices = Integer.parseInt((String) session
				.getAttribute("numChoices"));
		StringBuilder choices = new StringBuilder();
		for (int i = 0; i < numChoices; i++) {
			choices.append("#");
			choices.append((String) session.getAttribute("choice" + i));
			choices.append("#");
		}
		return choices.toString();
	}

}
