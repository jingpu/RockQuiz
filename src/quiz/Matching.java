/**
 * 
 */
package quiz;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.Helper;

/**
 * @author yang
 * 
 */
public class Matching extends MCMAQuestion {
	private static final String typeIntro = "Matching question: user should try to match every option and answer"
			+ "Correct answer will get positive points, while the wrong answer will get negative points";

	// private final String choices;

	/**
	 * @param questionType
	 * @param creatorId
	 * @param timeLimit
	 * @param questionDescription
	 * @param answer
	 * @param maxScore
	 * @param tagString
	 * @param correctRation
	 * @param choices
	 */
	public Matching(String questionType, String creatorId, int timeLimit,
			String questionDescription, String answer, int maxScore,
			String tagString, float correctRation, String choices) {
		super(questionType, creatorId, timeLimit, questionDescription, answer,
				maxScore, tagString, correctRation, choices);
		// this.choices = choices;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param questionType
	 * @param questionId
	 */
	public Matching(String questionType, String questionId) {
		super(questionType, questionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getQuerySaveString()
	 */
	@Override
	public String getQuerySaveString() {
		return "INSERT INTO " + MATCH_Table + " VALUES (\""
				+ super.getBaseQuerySaveString() + ", \"" + choices + "\")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public int getScore(String userInput) {
		List<String> inputList = Helper.parseTags(userInput);
		List<String> solutionList = Helper.parseTags(answer);
		int score = 0;
		for (int i = 0; i < solutionList.size(); i++) {
			if (inputList.get(i).equals(solutionList.get(i)))
				score += maxScore;
			else
				score -= 1;
		}
		return score > 0 ? score : 0;
	}

	@Override
	public String getUserAnswer(HttpServletRequest request) {
		int numAnswers = Integer.parseInt(request.getParameter("numChoices_"
				+ getQuestionId()));
		StringBuilder answer = new StringBuilder();
		for (int i = 0; i < numAnswers; i++) {
			answer.append("#");
			// if there is no input in answer field, it should be null
			// In matching problem, answer is inserted into the value field of
			// choice
			// since choice order(solution order) is fixed, while presented
			// answer is shuffled
			String userAnswer = request.getParameter("choice" + i + "_"
					+ getQuestionId());
			if (userAnswer == null)
				userAnswer = "";

			answer.append(userAnswer);
			answer.append("#");
		}
		return answer.toString();
	}

	public static String getCreatedAnswer(HttpServletRequest request, int suffix) {
		int numAnswers = Integer.parseInt(request.getParameter("numChoices"
				+ "_" + suffix));
		StringBuilder answer = new StringBuilder();
		for (int i = 0; i < numAnswers; i++) {
			answer.append("#");
			// if there is no input in answer field, it should be null
			answer.append(request.getParameter("answer" + i + "_" + suffix));
			answer.append("#");
		}
		return answer.toString();
	}

	@Deprecated
	public static String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a Matching question</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\" OnSubmit=\"return checkScore()\">");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p class=\"description\">Question Description:</p>\n");
		html.append("<p><textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");

		// Choice options
		html.append("<div id='options'>");
		html.append("<p>Choice0:   <input type=\"text\" name=\"choice0\" ></input></p>");
		html.append("<p>Choice1:   <input type=\"text\" name=\"choice1\" ></input></p>");
		html.append("<p>Choice2:   <input type=\"text\" name=\"choice2\" ></input></p>");
		html.append("<p>Choice3:   <input type=\"text\" name=\"choice3\" ></input></p>");
		html.append("</div>");

		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addMatchOption();\" />\n");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteMatchOption();\" />\n");

		// Answer options
		html.append("<div id='results'>");
		html.append("<p>Answer0:   <input type=\"text\" name=\"answer0\" ></input></p>");
		html.append("<p>Answer1:   <input type=\"text\" name=\"answer1\" ></input></p>");
		html.append("<p>Answer2:   <input type=\"text\" name=\"answer2\" ></input></p>");
		html.append("<p>Answer3:   <input type=\"text\" name=\"answer3\" ></input></p>");
		html.append("</div>");

		// Full Score
		html.append("<div id='option'>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\"0\" ></input></p>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MATCH + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"numOptions\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>");
		html.append("<input id = \"submit\" type=\"submit\" value = \"Save\"/></form>");
		html.append("</div>");

		return html.toString();

	}

	public static String printCreateHtmlSinglePage() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h4>This page will guide you to create a Matching question</h4>");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p class=\"description\">Question Description:</p>\n");
		html.append("<p><textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");

		html.append("<div class=\"Match_div\">");
		// add/delete choices
		html.append("<input type=\"button\" value=\"add\" onclick=\"addMatchOption(this);\" />\n");
		html.append("<input type=\"button\" value=\"delete\" onclick=\"deleteMatchOption(this);\" />\n");

		// Choice options
		html.append("<div class=\"choices\">");
		for (int i = 0; i < 4; i++) {
			html.append("<div class=\"combo\">");
			html.append("<span class='option'>Choice" + i + " & Answer" + i
					+ ": </span><input type=\"text\" name=\"choice" + i
					+ "\" ></input><input type=\"text\" name=\"answer" + i
					+ "\"></input>");
			html.append("</div>");
		}
		html.append("</div>"); // for choices div

		// hidden choice option template
		html.append("<div class=\"choice_template\" hidden=\"hidden\">");
		html.append("<span class='option'></span> <input type=\"text\" name=\"choice\"></input><input type=\"text\" name=\"answer\"></input>");
		html.append("</div>");

		html.append("<input type=\"hidden\" name=\"numChoices\" value=\"4\"></input>");
		html.append("</div>"); // for Match_div

		// Full Score
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\"0\" ></input></p>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MATCH + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>");

		return html.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.MultiChoice#printReadHtml()
	 */
	@Override
	public String printReadHtml() {
		StringBuilder html = new StringBuilder();
		html.append("<h2>Question Type Introduction</h2>\n");
		html.append("<p>Question Creator: " + creatorId + "</p>\n");
		html.append("<p>This is a question page, please read the question information, and make an answer</p>");
		html.append("<p>" + typeIntro + "</p>\n");
		html.append("<form action=\"QuestionProcessServlet\" method=\"post\" id=\"questionRead\"> onsubmit=false");
		html.append("<span class= 'description'>Question Description:</span><br>");
		html.append(questionDescription + "<br>");

		html.append("<div id=\"match\"></div>");
		html.append("<div id=\"results\"></div>");
		// create choice options
		List<String> choiceList = Helper.parseTags(choices);
		for (int i = 0; i < choiceList.size(); i++) {
			String choiceSpanId = "cs" + i;
			String choiceId = "choice" + i;
			String choiceName = "choice" + i + "_" + getQuestionId();
			html.append("<span id=" + "'" + choiceSpanId
					+ "' hidden=\"hidden\">" + "<input id=" + "'" + choiceId
					+ "' type=\"hidden\" name=\"" + choiceName + "\"></input>"
					+ choiceList.get(i) + "</span>");
		}

		// create answer options
		List<String> answerList = Helper.parseTags(answer);
		Collections.shuffle(answerList);
		for (int i = 0; i < answerList.size(); i++) {
			String answerSpanId = "as" + i;
			String answerId = "answer" + i;
			String answerName = "answer" + i + "_" + getQuestionId();
			html.append("<span id=" + "'" + answerSpanId
					+ "' hidden=\"hidden\">" + "<input id=" + "'" + answerId
					+ "' type=\"hidden\" name=\"" + answerName + "\"></input>"
					+ answerList.get(i) + "</span>");
		}

		// Hidden information - questionType and questionId information
		html.append("<p><input id=\"time_limit\" type=\"hidden\" name=\"timeLimit\" value=\""
				+ timeLimit + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"numChoices_"
				+ getQuestionId() + "\"  value=\"" + answerList.size()
				+ "\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\"></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\" ></input></p>");

		// html.append("<button id=\"vals\">Results</button>");
		html.append("<button id=\"clear\">Clear</button>");
		html.append("<input id=\"result\" type=\"submit\" value = \"Next\"/></form>");

		return html.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.MultiChoice#printReadHtmlForSingle()
	 */
	@Override
	public String printReadHtmlForSingle() {
		// TODO Auto-generated method stub
		return super.printReadHtmlForSingle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.MultiChoice#toElement(org.w3c.dom.Document)
	 */
	@Override
	public Element toElement(Document doc) {
		// TODO Auto-generated method stub
		return super.toElement(doc);
	}

	/**
	 * @return
	 */
	public static String printReference() {
		return QuestionBase.printReference();
	}
}
