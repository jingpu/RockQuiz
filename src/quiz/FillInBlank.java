/**
 * 
 */
package quiz;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author yang
 * 
 */
public class FillInBlank extends QuestionBase {
	private static final String typeIntro = "In this type of question, given a question with a blank, user needs to fill in the blank"
			+ " Correct answer will get full score, while the wrong answer will get zero";

	public FillInBlank(String questionType, String creatorId, int timeLimit,
			String questionDescription, String answer, int maxScore,
			String tagString, float correctRatio) {
		super(questionType, creatorId, timeLimit, questionDescription, answer,
				maxScore, tagString, correctRatio);

		// TODO Auto-generated constructor stub
	}

	public FillInBlank(String questionType, String questionId) {
		// TODO Auto-generated constructor stub
		super(questionType, questionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuestionBase#getQuerySaveString()
	 */
	@Override
	public String getQuerySaveString() {
		return "INSERT INTO " + FIB_Table + " VALUES (\""
				+ super.getBaseQuerySaveString() + ")";
	}

	private String parsePrefix() {
		return questionDescription.split("#blank#")[0];
	}

	private String parseSuffix() {
		return questionDescription.split("#blank#")[1];
	}

	public static String printCreateHtmlSinglePage() {
		StringBuilder html = new StringBuilder();
		html.append("<h4>This page will guide you to create a Fill-In-Blank question</h4>\n");
		html.append("Please enter proposed question description and answer. In order to insert a blank, please press the button \"add a blank\"<br>");

		html.append("<span class='example'>i.e. In order to express a question: I think _____ is awesome. The correct input is \"I think #blank# is awesome\"</span><br>");
		html.append("<p class='notice'> Notice: one question should and only contain one blank</p>");
		html.append("<span class=\"description\">Question Description:<br></span>");

		// textarea and add button
		html.append("<div class=\"combo\">");
		html.append("<textarea class=\"FIB\" name=\"questionDescription\" rows=\"10\" cols=\"50\""
				+ "\" required></textarea>");
		html.append("<input type=\"button\" value=\"add a blank\" onclick=\"addBlank(this);\" /><br>");
		html.append("</div>");

		// add answer field
		html.append("Answer:<input type=\"text\" name=\"answer\""
				+ "\" required></input><br>");
		html.append("Score:<input class=\"max_score\" type=\"text\" name=\"maxScore\""
				+ "\" required></input><br>");

		// add timeLimit field
		html.append("<div class=time_limit_div>Time Limit:   ");
		html.append("<input class=\"time_limit\" type=\"text\" name=\"timeLimit\" value=\"0\" ></input><br>");
		html.append("</div>");

		// Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" value=\""
				+ QuestionBase.FIB + "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"tag\" value=\"not_implemeted\"></input></p>\n");

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

		// create prefix, blank, suffix
		html.append("<p>" + parsePrefix()
				+ "<input type=\"text\" name=\"answer_" + getQuestionId()
				+ "\" >" + parseSuffix() + "</input></p>\n");

		// Hidden information - questionType and questionId information
		// TODO: timeLimit pass to javascript
		html.append("<p><input id=\"time_limit\" type=\"hidden\" name=\"timeLimit\" value=\""
				+ timeLimit + "\" ></input></p>");

		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\" ></input></p>\n");
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
		// create prefix, blank, suffix
		html.append("<p>" + parsePrefix()
				+ "<input type=\"text\" name=\"answer_" + getQuestionId()
				+ "\" >" + parseSuffix() + "</input></p>\n");

		// Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType_"
				+ getQuestionId() + "\" value=\"" + getQuestionType()
				+ "\" ></input></p>\n");
		html.append("<p><input type=\"hidden\" name=\"questionId_"
				+ getQuestionId() + "\" value=\"" + getQuestionId()
				+ "\"  ></input></p>\n");

		return html.toString();
	}

	public static String getCreatedAnswer(HttpServletRequest request) {
		return request.getParameter("answer");
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
		typeAttr.setValue("fill-in-blank");
		questionElem.setAttributeNode(typeAttr);

		// add question descritpion(query)
		Element query = doc.createElement("blank-query");

		Element pre = doc.createElement("pre");
		pre.appendChild(doc.createTextNode(parsePrefix()));
		query.appendChild(pre);

		Element blank = doc.createElement("blank");
		// pre.appendChild(doc.createTextNode(""));
		query.appendChild(blank);

		Element post = doc.createElement("post");
		post.appendChild(doc.createTextNode(parseSuffix()));
		query.appendChild(post);

		questionElem.appendChild(query);
		// TODO: questionDescription change to <pre><blank><post> format

		// add answer
		Element answer = doc.createElement("answer");
		answer.appendChild(doc.createTextNode(this.answer));
		questionElem.appendChild(answer);

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
