/**
 * 
 */
package quiz;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param questionType
	 * @param questionId
	 */
	public Matching(String questionType, String questionId) {
		super(questionType, questionId);
		// TODO Auto-generated constructor stub
	}

	public static String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a multiChoice question</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");

		// Choice options
		html.append("<p>ChoiceA:   <input type=\"text\" name=\"choice0\" ></input></p>");
		html.append("<p>ChoiceB:   <input type=\"text\" name=\"choice1\" ></input></p>");
		html.append("<p>ChoiceC:   <input type=\"text\" name=\"choice2\" ></input></p>");
		html.append("<p>ChoiceD:   <input type=\"text\" name=\"choice3\" ></input></p>");

		// Answer options
		html.append("<p>AnswerA:   <input type=\"text\" name=\"answer0\" ></input></p>");
		html.append("<p>AnswerB:   <input type=\"text\" name=\"answer1\" ></input></p>");
		html.append("<p>AnswerC:   <input type=\"text\" name=\"answer2\" ></input></p>");
		html.append("<p>AnswerD:   <input type=\"text\" name=\"answer3\" ></input></p>");

		// Full Score
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\"0\" ></input></p>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.MATCH + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"numOptions\" value=\"4\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Save\"/></form>");

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
		html.append(super.printReadHtml());

		html.append("<p>This is a question page, please read the question information, and make an answer</p>");
		html.append("<p>" + typeIntro + "</p>\n");
		html.append("<form action=\"QuestionProcessServlet\" method=\"post\">");
		html.append("<p>Question Description: ");
		html.append(questionDescription + "</p>");

		// create choice options
		List<String> choicesList = Helper.parseTags(choices);
		for (int i = 0; i < choicesList.size(); i++) {
			html.append("<span> id=" + "\"choice" + i + "\""
					+ choicesList.get(i) + "</span>");
		}

		// create answer options
		List<String> answersList = Helper.parseTags(answer);
		for (int i = 0; i < answersList.size(); i++) {
			html.append("<span> id=" + "\"answer" + i + "\""
					+ answersList.get(i) + "</span>");
			// html.append("<p><input type=\"hidden\" name=\"answer_"
			// + getQuestionId() + "\" value= \"" + "\" id = \"answer" + i
			// + "\">" + answersList.get(i) + "</input></p>");
		}
		// Hidden information - questionType and questionId information
		html.append("<p>Time Limit:   <input type=\"text\" name=\"timeLimit\" value=\""
				+ timeLimit + "\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"numOption_"
				+ getQuestionId() + "\" value=\"4\"></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\"></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		System.out.println(html.toString());
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

	/**
	 * Create answer in the format: #answer0##answer1##answer2#..#
	 * 
	 * @param request
	 * @return
	 */
	public static String getCreatedAnswer(HttpServletRequest request) {
		StringBuilder answer = new StringBuilder();
		int numAnswers = Integer.parseInt(request.getParameter("numOptions"));
		for (int i = 0; i < numAnswers; i++) {
			answer.append("#");
			// delete "answer=" will get choice index i.e. choice0
			// then use request.getParameter(choice0) to get answerBody
			String answerBody = request.getParameter("answer" + i);
			answer.append(answerBody);
			answer.append("#");
		}
		return answer.toString();
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

}
