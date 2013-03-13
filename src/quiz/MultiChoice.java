/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.Helper;
import database.MyDB;

/**
 * @author yang
 * 
 */
public class MultiChoice extends QuestionBase {
	// TODO: merge all choices into one field -> enable user add more than 4
	// choices
	protected final String choices;
	private static final String typeIntro = "MultiChoice question: user should choose one correct answer from choice options"
			+ "Correct answer will get full score, while the wrong answer will get zero";

	public MultiChoice(String questionType, String creatorId, int timeLimit,
			String questionDescription, String answer, int maxScore,
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
		html.append("<h1>This page will guide you to create a multiChoice question</h1>\n");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\" OnSubmit=\"return checkScore()\">\n");
		html.append("<p> Please enter proposed question description and answer </p>\n");
		html.append("<p>Question Description\n: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>\n");

		// Choice options
		html.append("<div id=\"multi_choice\">\n");
		html.append("<p>Choice0:   <input type=\"text\" name=\"choice0\" ></input><input type=\"radio\" name=\"answer\" value=\"choice0\"></input></p>\n");
		html.append("<p>Choice1:   <input type=\"text\" name=\"choice1\" ></input><input type=\"radio\" name=\"answer\" value=\"choice1\"></input></p>\n");
		html.append("<p>Choice2:   <input type=\"text\" name=\"choice2\" ></input><input type=\"radio\" name=\"answer\" value=\"choice2\"></input></p>\n");
		html.append("<p>Choice3:   <input type=\"text\" name=\"choice3\" ></input><input type=\"radio\" name=\"answer\" value=\"choice3\"></input></p>\n");
		html.append("</div>\n");

		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addChoice();\" />\n");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteChoice();\" />\n");
		// Answer and Full Score
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>\n");
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\"0\" ></input></p>\n");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MC + "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"numChoices\" id=\"numChoices\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>\n");
		html.append("<input type=\"submit\" value = \"Save\"/></form>\n");

		return html.toString();

	}

	public static String printCreateHtmlSinglePage() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h4>This page will guide you to create a multiChoice question</h4>\n");
		html.append("<p> Please enter proposed question description and answer </p>\n");
		html.append("<p>Question Description\n: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>\n");

		// Choice options
		html.append("<div id=\"multi_choice\">\n");
		html.append("<p>Choice0:   <input type=\"text\" name=\"choice0\" ></input><input type=\"radio\" name=\"answer\" value=\"choice0\"></input></p>\n");
		html.append("<p>Choice1:   <input type=\"text\" name=\"choice1\" ></input><input type=\"radio\" name=\"answer\" value=\"choice1\"></input></p>\n");
		html.append("<p>Choice2:   <input type=\"text\" name=\"choice2\" ></input><input type=\"radio\" name=\"answer\" value=\"choice2\"></input></p>\n");
		html.append("<p>Choice3:   <input type=\"text\" name=\"choice3\" ></input><input type=\"radio\" name=\"answer\" value=\"choice3\"></input></p>\n");
		html.append("</div>\n");

		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addChoice();\" />\n");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteChoice();\" />\n");
		// Answer and Full Score
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>\n");
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\"0\" ></input></p>\n");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MC + "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"numChoices\" id=\"numChoices\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>\n");

		return html.toString();

	}

	@Override
	public String printReadHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append(super.printReadHtml());

		html.append("<p>This is a question page, please read the question information, and make an answer</p>\n");
		html.append("<p>" + typeIntro + "</p>\n");
		html.append("<form action=\"QuestionProcessServlet\" method=\"post\" id=\"questionRead\">\n");
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>\n");

		// create choice options
		List<String> choiceList = Helper.parseTags(choices);
		for (int i = 0; i < choiceList.size(); i++) {
			html.append("<p><input type=\"radio\" name=\"answer_"
					+ getQuestionId() + "\" value= \"" + choiceList.get(i)
					+ "\">" + choiceList.get(i) + "</input></p>\n");
		}

		// Hidden information - questionType and questionId information

		html.append("<p>Time Limit:  <input id=\"time_limit\" type=\"hidden\" name=\"timeLimit\" value=\""
				+ timeLimit + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"numChoices_"
				+ getQuestionId() + "\" value=\"4\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\" ></input></p>\n");
		html.append("<input type=\"submit\" value = \"Next\"/></form>\n");

		return html.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public int getScore(String userInput) {
		// TODO Auto-generated method stub
		if (userInput.equals(answer))
			return maxScore;
		return 0;
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

	public static String getCreatedChoices(HttpServletRequest request,
			int suffix) {
		int numChoices = Integer.parseInt(request.getParameter("numChoices"
				+ "_" + suffix));
		StringBuilder choices = new StringBuilder();
		for (int i = 0; i < numChoices; i++) {
			choices.append("#");
			choices.append(request.getParameter("choice" + i + "_" + suffix));
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

		html.append("<p>This is a question page, please read the question information, and make an answer</p>\n");
		html.append("<p>" + typeIntro + "</p>\n");

		// form action should be here
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>\n");

		String choicesList[] = choices.split("#");
		for (int i = 0; i < choicesList.length; i++) {
			if (choicesList[i].isEmpty()) // remove empty string at head/end
				++i;
			html.append("<p><input type=\"radio\" name=\"answer_"
					+ getQuestionId() + "\" value= \"" + choicesList[i] + "\">"
					+ choicesList[i] + "</input></p>\n");
		}

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"numChoices_"
				+ getQuestionId() + "\" value=\"4\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\"  ></input></p>\n");

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

	public static String getCreatedAnswer(HttpServletRequest request, int suffix) {
		return request.getParameter("answer_" + suffix);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quiz.QuestionBase#getUserAnswer(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getUserAnswer(HttpServletRequest request) {
		String userAnswer = request.getParameter("answer_" + getQuestionId());
		if (userAnswer == null)
			userAnswer = "";
		return userAnswer;
	}

	public Element toElement(Document doc) {
		Element questionElem = null;

		questionElem = doc.createElement("question");

		// set question type as attribute to the root
		Attr typeAttr = doc.createAttribute("type");
		typeAttr.setValue("multiple-choice");
		questionElem.setAttributeNode(typeAttr);

		// add question description(query)
		Element query = doc.createElement("query");
		query.appendChild(doc.createTextNode(questionDescription));
		questionElem.appendChild(query);

		// add options (with answer attribute)
		// TODO: change parseTags() function name
		List<String> options = Helper.parseTags(choices);
		for (int i = 0; i < options.size(); i++) {
			Element option = doc.createElement("option");
			option.appendChild(doc.createTextNode(options.get(i)));
			if (options.get(i).equals(answer)) {
				Attr answerAttr = doc.createAttribute("answer");
				answerAttr.setValue("answer");
				option.setAttributeNode(answerAttr);
			}
			questionElem.appendChild(option);
		}

		// add time-limit
		Element timeLimit = doc.createElement("time-limit");
		timeLimit.appendChild(doc.createTextNode(Integer
				.toString(this.timeLimit)));
		questionElem.appendChild(timeLimit);

		// add score
		Element maxScore = doc.createElement("score");
		maxScore.appendChild(doc.createTextNode(Integer.toString(this.maxScore)));
		questionElem.appendChild(maxScore);

		// add tag
		Element tag = doc.createElement("tag");
		tag.appendChild(doc.createTextNode(this.tagString));
		questionElem.appendChild(tag);

		return questionElem;
	}

	/**
	 * @return
	 */
	public static String printReference() {
		return QuestionBase.printReference();
	}
}
