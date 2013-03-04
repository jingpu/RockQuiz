/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import database.MyDB;

/**
 * @author yang
 * 
 */
public class MCMAQuestion extends QuestionBase {
	// choices format: #chioce0#choice1#choice2#...#
	private final String choices;
	private static final String typeIntro = "Multi-Choice-Multi-Answer question: user should choose one or more correct answers from choice options"
			+ "Choosing all correct answer will get full score, and choosing partial correct answer will get partial score"
			+ "while choosing any wrong answer will fix zero at your score. So, be careful!";

	/**
	 * @param questionType
	 * @param questionId
	 */
	public MCMAQuestion(String questionType, String questionId) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		choices = tmpChoices;
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
	public MCMAQuestion(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, float correctRatio, String choices) {
		super(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio);
		this.choices = choices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#saveToDatabase()
	 */
	@Override
	public void saveToDatabase() {
		queryStmt = "INSERT INTO " + MCMA_Table + " VALUES (\"" + questionId
				+ "\", \"" + creatorId + "\", \"" + typeIntro + "\", \""
				+ questionDescription + "\", \"" + choices + "\", \""
				+ maxScore + "\", \"" + tagString + "\", \"" + correctRatio
				+ "\", \"" + choices + "\")";
		try {
			Connection con = MyDB.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(queryStmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Answer format: #answer0#answer1#answer2#...#
	 * 
	 * @return
	 */
	public static String getAnswerString(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int numAnswers = Integer.parseInt((String) session
				.getAttribute("numAnswers"));
		StringBuilder answer = new StringBuilder();
		for (int i = 0; i < numAnswers; i++) {
			answer.append("#");
			answer.append((String) session.getAttribute("answer" + i));
			answer.append("#");
		}
		return answer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public String getScore(String userInput) {
		String[] answerList = parseAnswer(choices);
		String[] inputList = parseAnswer(userInput);

		return Integer.toString(getScore(answerList, inputList));
	}

	// overload
	// TODO: dynamically assign score based on maxScore
	private int getScore(String[] answerList, String[] inputList) {
		int score = 0;
		HashSet<String> answerSet = new HashSet<String>();
		for (String str : answerList) {
			answerSet.add(str);
		}
		for (String str : inputList) {
			if (answerSet.contains(str))
				score += 1; // if correct, score + 1
			else
				return 0; // if incorrect, score = 0
		}
		return score;
	}

	private String[] parseAnswer(String answerString) {
		String[] answerList = answerString.split("#");
		return answerList;
	}

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

	// TODO: change the control type in two printHTML functions
	public static String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a multiChoice-MultiAnswer question</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");

		// Choice options
		html.append("<p>ChoiceA:   <input type=\"text\" name=\"choice0\" ></input></p>");
		html.append("<p>ChoiceB:   <input type=\"text\" name=\"choice1\" ></input></p>");
		html.append("<p>ChoiceC:   <input type=\"text\" name=\"choice2\" ></input></p>");
		html.append("<p>ChoiceD:   <input type=\"text\" name=\"choice3\" ></input></p>");

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
		String choicesList[] = choices.split("#");
		for (int i = 0; i < choicesList.length; i++) {
			html.append("<p><input type=\"radio\" name=\"answer\" + i"
					+ "value= \"" + choicesList[i] + "\">" + choicesList[i]
					+ "</input></p>");
		}

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ getQuestionType() + "\"></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" value=\""
				+ getQuestionId() + "\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		return html.toString();

	}

}
