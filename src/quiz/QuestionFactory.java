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
		String types[] = new String[6];
		types[0] = QuestionBase.QR;
		types[1] = QuestionBase.FIB;
		types[2] = QuestionBase.MC;
		types[3] = QuestionBase.PR;
		types[4] = QuestionBase.MA;
		types[5] = QuestionBase.MCMA;
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
		else if (questionType.equals(QuestionBase.MA))
			return new MAQuestion(questionType, questionId);
		else if (questionType.equals(QuestionBase.MCMA))
			return new MCMAQuestion(questionType, questionId);
		return null;
	}

	// MyQuiz create a question from a HTTP request
	// questionType is not stored in the session??
	public static QuestionBase createQuestion(String questionType,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String creatorId = (String) session.getAttribute("userName");
		if (questionType.equals(QuestionBase.QR)) {
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getAnswerString(questionType, request);
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			return new QResponse(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted");

		} else if (questionType.equals(QuestionBase.FIB)) {
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getAnswerString(questionType, request);
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			return new FillInBlank(questionType, creatorId,
					questionDescription, answer, maxScore, tagString,
					"not_implemeted");

		} else if (questionType.equals(QuestionBase.MC)) {
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getAnswerString(questionType, request);
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");

			// TODO: better way to handle this
			String choiceA = request.getParameter("choiceA");
			String choiceB = request.getParameter("choiceB");
			String choiceC = request.getParameter("choiceC");
			String choiceD = request.getParameter("choiceD");

			List<String> choices = new ArrayList<String>();
			choices.add(choiceA);
			choices.add(choiceB);
			choices.add(choiceC);
			choices.add(choiceD);
			return new MultiChoice(questionType, creatorId,
					questionDescription, answer, maxScore, tagString,
					"not_implemeted", choices);

		} else if (questionType.equals(QuestionBase.PR)) {
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getAnswerString(questionType, request);
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			String url = request.getParameter("url");
			return new PResponse(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted", url);

		} else if (questionType.equals(QuestionBase.MA)) {
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getAnswerString(questionType, request);
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			String isOrder = request.getParameter("isOrder");
			return new MAQuestion(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted", isOrder);

		} else if (questionType.equals(QuestionBase.MCMA)) {
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getAnswerString(questionType, request);
			String maxScore = request.getParameter("maxScore");
			String tagString = request.getParameter("tag");
			String choices = getChoicesString(questionType, request);
			return new MAQuestion(questionType, creatorId, questionDescription,
					answer, maxScore, tagString, "not_implemeted", choices);
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
		else if (questionType.equals(QuestionBase.MA))
			return MAQuestion.printCreateHtml();
		else
			return "error";
	}

	/**
	 * Used by quiz servlet when creating multi-answer for a question OR when
	 * user inputs multi-answer for a question: get a formated answer string
	 * from multi-answer fields, and pass to QuestionBase constructor or
	 * getScore function
	 * 
	 * @param questionType
	 * @param request
	 * @return
	 */
	public static String getAnswerString(String questionType,
			HttpServletRequest request) {
		if (questionType.equals(QuestionBase.QR))
			return QResponse.getAnswerString(request);
		else if (questionType.equals(QuestionBase.FIB))
			return FillInBlank.getAnswerString(request);
		else if (questionType.equals(QuestionBase.MC))
			return MultiChoice.getAnswerString(request);
		else if (questionType.equals(QuestionBase.PR))
			return PResponse.getAnswerString(request);
		else if (questionType.equals(QuestionBase.MA))
			return MAQuestion.getAnswerString(request);
		return "error";
	}

	/**
	 * Used by quiz servlet to wrap answers for MCMA and MultiChoice TODO:
	 * MultiChoice's table needs modification
	 * 
	 * @param questionType
	 * @param request
	 * @return
	 */
	public static String getChoicesString(String questionType,
			HttpServletRequest request) {
		if (questionType.equals(QuestionBase.MC))
			return MultiChoice.getChoicesString(request);
		if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.getChoicesString(request);
		return "error";
	}

	public static void main(String[] args) {
		String questionTypes[] = getQuestionTypes();
		// test printSummaryPageHTML method
		System.out.print(printCreateHtml(questionTypes[0]));
	}
}
