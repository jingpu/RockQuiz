/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class MCMAQuestion extends QuestionBase {
	// choices format: #chioce0#choice1#choice2#...#
	protected final String choices;
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
			tmpChoices = rs.getString("choices");
		} catch (SQLException e) {
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
	public MCMAQuestion(String questionType, String creatorId, int timeLimit,
			String questionDescription, String answer, int maxScore,
			String tagString, float correctRatio, String choices) {
		super(questionType, creatorId, timeLimit, questionDescription, answer,
				maxScore, tagString, correctRatio);
		this.choices = choices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getQuerySaveString()
	 */
	@Override
	public String getQuerySaveString() {
		return "INSERT INTO " + MCMA_Table + " VALUES (\""
				+ super.getBaseQuerySaveString() + ", \"" + choices + "\")";
	}

	/**
	 * Create answer in the format: #answer0##answer1##answer2#..#
	 * 
	 * @param request
	 * @return
	 */
	public static String getCreatedAnswer(HttpServletRequest request) {
		String answerList[] = request.getParameterValues("answer");
		StringBuilder answer = new StringBuilder();
		for (String str : answerList) {
			answer.append("#");
			// delete "answer=" will get choice index i.e. choice0
			// then use request.getParameter(choice0) to get answerBody
			String answerBody = request.getParameter(str);
			answer.append(answerBody);
			answer.append("#");
		}
		return answer.toString();
	}

	public static String getCreatedAnswer(HttpServletRequest request, int suffix) {
		String answerList[] = request.getParameterValues("answer" + "_"
				+ suffix);
		StringBuilder answer = new StringBuilder();
		for (String str : answerList) {
			answer.append("#");
			// delete "answer=" will get choice index i.e. choice0
			// then use request.getParameter(choice0) to get answerBody
			String answerBody = request.getParameter(str + "_" + suffix);
			answer.append(answerBody);
			answer.append("#");
		}
		return answer.toString();
	}

	/**
	 * Get Choices string from several distinct choice(input) fields Since input
	 * fields are different from checkbox field, here we have to use multiple
	 * fields(parameters) rather than a single parameter
	 * 
	 * @param request
	 * @return
	 */
	@Deprecated
	public static String getCreatedChoices(HttpServletRequest request) {
		// TODO: changeable numChoices
		// int numChoices = request.getParameter("numChoices"));
		int numChoices = 4;
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
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public int getScore(String userInput) {
		Set<String> answerSet = getAnswerSet();
		String[] inputList = parseAnswer(userInput);

		return getScore(answerSet, inputList);
	}

	// overload
	// TODO: dynamically assign score based on maxScore
	private int getScore(Set<String> answerSet, String[] inputList) {
		int score = 0;
		for (String str : inputList) {
			if (answerSet.contains(str))
				score += maxScore; // if correct, score + 1
			else
				return 0; // if incorrect, score = 0
		}
		return score;
	}

	private String[] parseAnswer(String answerString) {
		String[] answerList = answerString.split("#");
		return answerList;
	}

	public static String printCreateHtml() {
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a multiChoice-MultiAnswer question</h1>\n");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\" OnSubmit=\"return checkScore()\">\n");
		html.append("<p> Please enter proposed question description here: </p>\n");
		html.append("<p class=\"description\">Question Description:</p>\n");
		html.append("<p><textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>\n");
		html.append("<p> Please enter proposed choices, and tick the checkbox if it is one of the answers </p>\n");

		// Choice options and answers
		html.append("<div id=\"MCMA\"");
		html.append("<p>Choice0:   <input type=\"text\" name=\"choice0\" ></input><input type=\"checkbox\" name=\"answer\" value=\"choice0\"></input></p>\n");
		html.append("<p>Choice1:   <input type=\"text\" name=\"choice1\" ></input><input type=\"checkbox\" name=\"answer\" value=\"choice1\"></input></p>\n");
		html.append("<p>Choice2:   <input type=\"text\" name=\"choice2\" ></input><input type=\"checkbox\" name=\"answer\" value=\"choice2\"></input></p>\n");
		html.append("<p>Choice3:   <input type=\"text\" name=\"choice3\" ></input><input type=\"checkbox\" name=\"answer\" value=\"choice3\"></input></p>\n");
		html.append("</div>\n");

		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addMCMAChoice();\" />\n");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteMCMAChoice();\" />\n");

		// Full Score
		html.append("<p>Score per correct answer:   <input class=\"max_score\" type=\"text\" name=\"maxScore\" ></input></p>\n");
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\"0\" ></input></p>\n");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"numChoices\" id=\"numChoices\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MCMA + "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>\n");
		html.append("<input type=\"submit\" value = \"Save\"/></form>\n");

		return html.toString();

	}

	public static String printCreateHtmlSinglePage() {
		StringBuilder html = new StringBuilder();
		html.append("<h4>This page will guide you to create a multiChoice-MultiAnswer question</h4>\n");
		html.append("<p> Please enter proposed question description here: </p>\n");
		html.append("<p class=\"description\">Question Description:</p>\n");
		html.append("<p><textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>\n");
		html.append("<p> Please enter proposed choices, and tick the checkbox if it is one of the answers </p>\n");

		html.append("<div class=\"MCMA_div\">");
		// Choice options and answers
		html.append("<div class=\"choices\">");
		for (int i = 0; i < 4; i++) {
			html.append("<div class=\"combo\">");
			html.append("<span class='option'>Choice"
					+ i
					+ "</span>: <input type=\"text\" name=\"choice"
					+ i
					+ "\" ></input><input type=\"checkbox\" name=\"answer\" value=\"choice"
					+ i + "\"></input>");
			html.append("</div>");
		}
		html.append("</div>"); // div for choices

		// hidden choice option template
		html.append("<div class=\"choice_template\" hidden=\"hidden\">");
		html.append("<span class='option'></span> <input type=\"text\" name=\"choice\"></input><input type=\"checkbox\" name=\"answer\" value=\"choice\"></input>");
		html.append("</div>");

		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addMCMAChoice(this);\" /><br>");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteMCMAChoice(this);\" /><br>");

		html.append("<input class=\"numChoices\" type=\"hidden\" name=\"numChoices\" value =\"4\"></input>");
		html.append("</div>"); // div for MCMA_div

		// Full Score
		html.append("Score per correct answer:   <input class=\"max_score\" type=\"text\" name=\"maxScore\" ></input><br>");

		// add timeLimit field
		html.append("<div class=time_limit_div>Time Limit:   ");
		html.append("<input class=\"time_limit\" type=\"text\" name=\"timeLimit\" value=\"0\" ></input><br>");
		html.append("</div>");

		// Hidden information - question Type and tag information

		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MCMA + "\" ></input></p>\n");
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
		String choicesList[] = choices.split("#");
		for (int i = 0; i < choicesList.length; i++) {
			if (choicesList[i].isEmpty()) // remove empty string at head/end
				++i;
			html.append("<p><input type=\"checkbox\" name=\"answer_"
					+ getQuestionId() + "\" value= \"" + choicesList[i] + "\">"
					+ choicesList[i] + "</input></p>\n");
		}

		// Hidden information - questionType and questionId information
		html.append("<p><input id=\"time_limit\" type=\"hidden\" name=\"timeLimit\" value=\""
				+ timeLimit + "\" ></input></p>");

		html.append("<p><input type=\"hidden\" name=\"numChoices_"
				+ getQuestionId() + "\" value=\"" + choicesList.length
				+ "\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\" ></input></p>\n");
		html.append("<input type=\"submit\" value = \"Next\"/></form>\n");
		System.out.println(html.toString());
		return html.toString();

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

		// every form field will be renamed as xx_questionId
		// create choice options
		String choicesList[] = choices.split("#");
		for (int i = 0; i < choicesList.length; i++) {
			if (choicesList[i].isEmpty()) // remove empty string at head/end
				++i;
			html.append("<p><input type=\"checkbox\" name=\"answer_"
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quiz.QuestionBase#getUserAnswer(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getUserAnswer(HttpServletRequest request) {
		String answerList[] = request.getParameterValues("answer_"
				+ getQuestionId());
		if (answerList == null)
			return "";
		StringBuilder answer = new StringBuilder();
		for (String str : answerList) {
			answer.append("#");
			answer.append(str);
			answer.append("#");
		}
		return answer.toString();
	}

	private Set<String> getAnswerSet() {
		String[] answerList = parseAnswer(answer);
		HashSet<String> answerSet = new HashSet<String>();
		for (String str : answerList) {
			answerSet.add(str);
		}
		return answerSet;
	}

	public Element toElement(Document doc) {
		Element questionElem = null;
		questionElem = doc.createElement("question");

		// set question type as attribute to the root
		Attr typeAttr = doc.createAttribute("type");
		typeAttr.setValue("multi-choice-multi-answer");
		questionElem.setAttributeNode(typeAttr);

		// add question descritpion(query)
		Element query = doc.createElement("query");
		query.appendChild(doc.createTextNode(questionDescription));
		questionElem.appendChild(query);

		// add choices and answers
		List<String> options = Helper.parseTags(choices);
		Set<String> answerSet = getAnswerSet();
		for (int i = 0; i < options.size(); i++) {
			Element option = doc.createElement("option");
			option.appendChild(doc.createTextNode(options.get(i)));
			if (answerSet.contains(options.get(i))) {
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
