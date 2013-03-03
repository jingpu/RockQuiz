/**
 * 
 */
package quiz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * QuestionFactory class is for handling the creation of question object,
 * including printing question creation page
 * 
 * @author jimmy_000
 * 
 */
public class QuestionFactory {

	public static String[] getQuestionTypes() {
		String types[] = new String[4];
		types[0] = QuestionBase.QR;
		types[1] = QuestionBase.FIB;
		types[2] = QuestionBase.MC;
		types[3] = QuestionBase.PR;
		return types;
	}

	// MyQuiz get a question from database using questionType and questionId
	public static QuestionBase getQuestion(String questionType,
			String questionId) {

		if (questionType.equals(QuestionBase.QR))
			return new QResponse(questionType, questionId);
		else if (questionType.equals(QuestionBase.FIB))
			return new FillInBlank(questionType, questionId);
		else if (questionType.equals(QuestionBase.MC))
			return new MultiChoice(questionType, questionId);
		else if (questionType.equals(QuestionBase.PR))
			return new PResponse(questionType, questionId);
		else
			return null;
	}

	// MyQuiz create a question from a HTTP request
	public static QuestionBase createQuestion(String questionType,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String creatorId = (String) session.getAttribute("userName");
		if (questionType.equals(QuestionBase.QR)) {
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = request.getParameter("answer");
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			return new QResponse(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted");
			
		} else if (questionType.equals(QuestionBase.FIB)) {
			String questionDescription = request
			.getParameter("questionDescription");
			String answer = request.getParameter("answer");
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			return new FillInBlank(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted");
			
		} else if (questionType.equals(QuestionBase.MC)) {
			String questionDescription = request
			.getParameter("questionDescription");
			String answer = request.getParameter("answer");
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			
			String choiceA = request.getParameter("choiceA");  //TODO: better way to handle this
			String choiceB = request.getParameter("choiceB");
			String choiceC = request.getParameter("choiceC");
			String choiceD = request.getParameter("choiceD");
			
			List<String> choices = new ArrayList<String>();
			choices.add(choiceA);
			choices.add(choiceB);
			choices.add(choiceC);
			choices.add(choiceD);
			return new MultiChoice(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted", choices);
			
		} else if (questionType.equals(QuestionBase.PR)) {
			String questionDescription = request
			.getParameter("questionDescription");
			String answer = request.getParameter("answer");
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			String url = request.getParameter("url");
			return new PResponse(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted", url);
		}
		
			return null;
	}

	// called by quiz to print html for every question
	// essentially, it is a html-string
	public static String printCreateHtml(String questionType) {
		if (questionType.equals(QuestionBase.QR))
			return QResponse.printCreateHtml();
		else if (questionType.equals(QuestionBase.FIB))
			return FillInBlank.printCreateHtml();
		else if (questionType.equals(QuestionBase.MC))
			return MultiChoice.printCreateHtml();
		else if (questionType.equals(QuestionBase.PR))
			return PResponse.printCreateHtml();
		else
			return "error";
	}

	public static void main(String[] args) {
		String questionTypes[] = getQuestionTypes();
		// test printSummaryPageHTML method
		System.out.print(printCreateHtml(questionTypes[0]));
	}
}
