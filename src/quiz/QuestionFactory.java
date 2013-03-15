/**
 * 
 */
package quiz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//why here need import???

/**
 * QuestionFactory class is for handling the creation of question object,
 * including printing question creation page
 * 
 * @author jimmy_000
 * 
 */
public class QuestionFactory {

	public static String[] getQuestionTypes() {
		String types[] = new String[7];
		types[0] = QuestionBase.QR;
		types[1] = QuestionBase.FIB;
		types[2] = QuestionBase.MC;
		types[3] = QuestionBase.PR;
		types[4] = QuestionBase.MA;
		types[5] = QuestionBase.MCMA;
		types[6] = QuestionBase.MATCH;
		return types;
	}

	// MyQuiz get a question from database using questionType and questionId
	public static QuestionBase getQuestion(String questionType,
			String questionId) {
		// How can QuestionFactory access QuestionBase's protected
		// variable??????
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
		else if (questionType.equals(QuestionBase.MATCH))
			return new Matching(questionType, questionId);
		return null;
	}

	public static QuestionBase createQuestion(String questionType,
			HttpServletRequest request, int suffix) {
		HttpSession session = request.getSession();
		String creatorId = (String) session.getAttribute("guest");
		int timeLimit = Integer.parseInt(request.getParameter("timeLimit_"
				+ suffix));
		String questionDescription = request
				.getParameter("questionDescription_" + suffix);
		String answer = getCreatedAnswer(questionType, request, suffix);
		String tagString = request.getParameter("tag_" + suffix);
		String tmp = request.getParameter("maxScore_" + suffix);
		int maxScore = Integer.parseInt(tmp);

		if (questionType.equals(QuestionBase.QR)) {
			return new QResponse(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1);

		} else if (questionType.equals(QuestionBase.FIB)) {
			return new FillInBlank(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1);

		} else if (questionType.equals(QuestionBase.MC)) {
			String choices = getCreatedChoices(questionType, request, suffix);
			return new MultiChoice(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					choices);

		} else if (questionType.equals(QuestionBase.PR)) {
			String url = request.getParameter("url_" + suffix);
			return new PResponse(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1, url);

		} else if (questionType.equals(QuestionBase.MA)) {
			String isOrder = request.getParameter("isOrder_" + suffix);
			if (isOrder == null)
				isOrder = "false";
			return new MAQuestion(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					isOrder);

		} else if (questionType.equals(QuestionBase.MCMA)) {
			String choices = getCreatedChoices(questionType, request, suffix);
			return new MCMAQuestion(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					choices);

		} else if (questionType.equals(QuestionBase.MATCH)) {
			String choices = getCreatedChoices(questionType, request, suffix);
			return new Matching(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					choices);
		}
		return null;
	}

	// called by quiz to print html for every question
	// essentially, it is a html-string
	public static String printCreateHtmlSinglePage(String questionType) {
		if (questionType.equals(QuestionBase.QR))
			return QResponse.printCreateHtmlSinglePage();
		else if (questionType.equals(QuestionBase.FIB))
			return FillInBlank.printCreateHtmlSinglePage();
		else if (questionType.equals(QuestionBase.MC))
			return MultiChoice.printCreateHtmlSinglePage();
		else if (questionType.equals(QuestionBase.PR))
			return PResponse.printCreateHtmlSinglePage();
		else if (questionType.equals(QuestionBase.MA))
			return MAQuestion.printCreateHtmlSinglePage();
		else if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.printCreateHtmlSinglePage();
		else if (questionType.equals(QuestionBase.MATCH))
			return Matching.printCreateHtmlSinglePage();
		return "error";
	}

	public static String getCreatedAnswer(String questionType,
			HttpServletRequest request, int suffix) {
		if (questionType.equals(QuestionBase.QR))
			return QResponse.getCreatedAnswer(request, suffix);
		else if (questionType.equals(QuestionBase.FIB))
			return FillInBlank.getCreatedAnswer(request, suffix);
		else if (questionType.equals(QuestionBase.MC))
			return MultiChoice.getCreatedAnswer(request, suffix);
		else if (questionType.equals(QuestionBase.PR))
			return PResponse.getCreatedAnswer(request, suffix);
		else if (questionType.equals(QuestionBase.MA))
			return MAQuestion.getCreatedAnswer(request, suffix);
		else if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.getCreatedAnswer(request, suffix);
		else if (questionType.equals(QuestionBase.MATCH))
			return Matching.getCreatedAnswer(request, suffix);
		return "error";
	}

	public static String getCreatedChoices(String questionType,
			HttpServletRequest request, int suffix) {
		if (questionType.equals(QuestionBase.MC))
			return MultiChoice.getCreatedChoices(request, suffix);
		else if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.getCreatedChoices(request, suffix);
		else if (questionType.equals(QuestionBase.MATCH))
			return Matching.getCreatedChoices(request, suffix);
		return "error";
	}

	public static String printReference() {
		return QuestionBase.printReference();
	}

	public static void main(String[] args) {
		String questionTypes[] = getQuestionTypes();
		// test printSummaryPageHTML method
	}

}
