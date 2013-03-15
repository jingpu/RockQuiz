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

	public static String printCreateHtmlSinglePage() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h4>This section will guide you to create a multiChoice question</h4>\n");
		html.append("Please enter proposed question description and answer, and label the right answer. Add and delete button could allow user to customize the number of choices.<br>");
		html.append("<p class='notice'> Notice: User should first create a certain number of choices, and label the right answer right behind the choice.");
		html.append("<br>One Multi-Choice question could contain only one right answer</p>");
		html.append("<span class= 'description'>Question Description:</span><br>");
		html.append("<textarea name=\"questionDescription\" rows=\"10\" cols=\"50\" required></textarea><br>");

		html.append("<div class=\"MC_div\">");
		// Choice options
		html.append("<div class=\"choices\">");
		for (int i = 0; i < 4; i++) {
			html.append("<div class=\"combo\">");
			html.append("<span class='option'>Choice"
					+ i
					+ ":</span><input type=\"text\" name=\"choice"
					+ i
					+ "\" required ></input><input type=\"radio\" name=\"answer\" value=\"choice"
					+ i + "\"></input>");
			html.append("</div>");
		}
		html.append("</div>"); // choices div

		// hidden choice option template
		html.append("<div class=\"choice_template\" hidden=\"hidden\">");
		html.append("<span class='option'></span> <input type=\"text\" name=\"choice\" class=\"requiredField\"></input><input type=\"radio\" name=\"answer\" value=\"choice\"></input>");
		html.append("</div>");

		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addChoice(this);\" />");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteChoice(this);\" /><br>");

		html.append("<input class=\"numChoices\" type=\"hidden\" name=\"numChoices\" value =\"4\" ></input>");
		html.append("</div>"); // for MC_div

		// Answer and Full Score
		html.append("Score:   <input class=\"max_score\" type=\"text\" name=\"maxScore\" ></input><br>");
		// add timeLimit field
		html.append("<div class=time_limit_div>Time Limit:   ");
		html.append("<input class=\"time_limit\" type=\"text\" name=\"timeLimit\" value=\"0\" ></input><br>");
		html.append("</div>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MC + "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>\n");

		return html.toString();

	}

	@Override
	public String printEditHtml(int suffix) {
		StringBuilder html = new StringBuilder();
		html.append("<h4>MultiChoice Question</h4>\n");
		html.append("Please enter proposed question description and answer, and label the right answer. Add and delete button could allow user to customize the number of choices.<br>\n");
		html.append("<p class='notice'> Notice: User should first create a certain number of choices, and label the right answer right behind the choice.\n");
		html.append("One Multi-Choice question could contain only one right answer</p>");
		html.append("<span class= 'description'>Question Description:</span><br>");
		html.append("<textarea name=\"questionDescription_" + suffix
				+ "\"  rows=\"10\" cols=\"50\" required>"
				+ getQuestionDescription() + "</textarea><br>");

		html.append("<div class=\"MC_div\">");

		// Choice options
		html.append("<div class=\"choices\">");
		List<String> choiceList = Helper.parseTags(choices);

		for (int i = 0; i < choiceList.size(); i++) {
			String checked = "";
			if (answer.equals(choiceList.get(i)))
				checked = "checked";
			html.append("<div class=\"combo\">");
			html.append("<span class='option'>Choice"
					+ i
					+ ":</span><input type=\"text\" name=\"choice"
					+ i
					+ "_"
					+ suffix
					+ "\" value=\""
					+ choiceList.get(i)
					+ "\" required ></input><input type=\"radio\" name=\"answer_"
					+ suffix + "\" value=\"choice" + i + "\"" + checked
					+ "></input>");
			html.append("</div>");
		}
		html.append("</div>"); // choices div

		// hidden choice option template
		html.append("<div class=\"choice_template\" hidden=\"hidden\">");
		html.append("<span class='option'></span> <input type=\"text\" name=\"choice_"
				+ suffix
				+ "\" class=\"requiredField\"></input><input type=\"radio\" name=\"answer_"
				+ suffix + "\" value=\"choice\"></input>");
		html.append("</div>");

		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addChoice(this);\" />");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteChoice(this);\" /><br>");

		html.append("<input class=\"numChoices\" type=\"hidden\" name=\"numChoices_"
				+ suffix + "\" value =\"" + choiceList.size() + "\" ></input>");
		html.append("</div>"); // for MC_div

		// Answer and Full Score
		html.append("Score:   <input class=\"max_score\" type=\"text\" name=\"maxScore_"
				+ suffix + "\" value=\"" + getMaxScore() + "\" required><br>");
		// add timeLimit field
		html.append("<div class=time_limit_div>Time Limit:   ");
		html.append("<input class=\"time_limit\" type=\"text\" name=\"timeLimit_"
				+ suffix
				+ "\" value=\""
				+ getTimeLimit()
				+ "\" required></input><br>");
		html.append("</div>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType_" + suffix
				+ "\" value=\"" + QuestionBase.MC + "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag_" + suffix
				+ "\" value=\"not_implemeted\" ></input></p>\n");

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

		html.append("<p><input id=\"time_limit\" type=\"hidden\" name=\"timeLimit\" value=\""
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

	public static String getCreatedAnswer(HttpServletRequest request, int suffix) {
		String str = request.getParameter("answer_" + suffix);
		return request.getParameter(str + "_" + suffix);
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

}
