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
		return null;
	}

	// MyQuiz create a question from a HTTP request
	/**
	 * create a question from webpage
	 * 
	 * @param questionType
	 * @param request
	 * @return
	 */
	public static QuestionBase createQuestion(String questionType,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String creatorId = (String) session.getAttribute("guest");
		if (questionType.equals(QuestionBase.QR)) {
			int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getCreatedAnswer(questionType, request);
			int maxScore = Integer.parseInt(request.getParameter("maxScore"));
			String tagString = request.getParameter("tag");
			return new QResponse(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1);

		} else if (questionType.equals(QuestionBase.FIB)) {
			int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getCreatedAnswer(questionType, request);
			int maxScore = Integer.parseInt(request.getParameter("maxScore"));
			String tagString = request.getParameter("tag");
			return new FillInBlank(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1);

		} else if (questionType.equals(QuestionBase.MC)) {
			int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getCreatedAnswer(questionType, request);
			int maxScore = Integer.parseInt(request.getParameter("maxScore"));
			String tagString = request.getParameter("tag");
			String choices = getCreatedChoices(questionType, request);
			return new MultiChoice(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					choices);

		} else if (questionType.equals(QuestionBase.PR)) {
			int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getCreatedAnswer(questionType, request);
			int maxScore = Integer.parseInt(request.getParameter("maxScore"));
			String tagString = request.getParameter("tag");
			String url = request.getParameter("url");
			return new PResponse(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1, url);

		} else if (questionType.equals(QuestionBase.MA)) {
			int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getCreatedAnswer(questionType, request);
			int maxScore = Integer.parseInt(request.getParameter("maxScore"));
			String tagString = request.getParameter("tag");
			String isOrder = request.getParameter("isOrder");
			if (isOrder == null)
				isOrder = "false";
			return new MAQuestion(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					isOrder);

		} else if (questionType.equals(QuestionBase.MCMA)) {
			int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getCreatedAnswer(questionType, request);
			int maxScore = Integer.parseInt(request.getParameter("maxScore"));
			String tagString = request.getParameter("tag");
			String choices = getCreatedChoices(questionType, request);
			return new MCMAQuestion(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					choices);

		} else if (questionType.equals(QuestionBase.MATCH)) {
			int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			String questionDescription = request
					.getParameter("questionDescription");
			String answer = getCreatedAnswer(questionType, request);
			int maxScore = Integer.parseInt(request.getParameter("maxScore"));
			String tagString = request.getParameter("tag");
			String choices = getCreatedChoices(questionType, request);
			return new Matching(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, -1,
					choices);
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
		else if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.printCreateHtml();
		else if (questionType.equals(QuestionBase.MATCH))
			return Matching.printCreateHtml();
		return "error";
	}

	/**
	 * Used by quiz servlet when creating multi-answer for a question
	 * 
	 * @param questionType
	 * @param request
	 * @return
	 */
	public static String getCreatedAnswer(String questionType,
			HttpServletRequest request) {
		if (questionType.equals(QuestionBase.QR))
			return QResponse.getCreatedAnswer(request);
		else if (questionType.equals(QuestionBase.FIB))
			return FillInBlank.getCreatedAnswer(request);
		else if (questionType.equals(QuestionBase.MC))
			return MultiChoice.getCreatedAnswer(request);
		else if (questionType.equals(QuestionBase.PR))
			return PResponse.getCreatedAnswer(request);
		else if (questionType.equals(QuestionBase.MA))
			return MAQuestion.getCreatedAnswer(request);
		else if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.getCreatedAnswer(request);
		else if (questionType.equals(QuestionBase.MATCH))
			return Matching.getCreatedAnswer(request);
		return "error";
	}

	/**
	 * Used by quiz servlet to wrap answers for MCMA and MultiChoice
	 * 
	 * @param questionType
	 * @param request
	 * @return
	 */
	public static String getCreatedChoices(String questionType,
			HttpServletRequest request) {
		if (questionType.equals(QuestionBase.MC))
			return MultiChoice.getCreatedChoices(request);
		else if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.getCreatedChoices(request);
		else if (questionType.equals(QuestionBase.MATCH))
			return Matching.getCreatedChoices(request);
		return "error";
	}

	public static void main(String[] args) {
		String questionTypes[] = getQuestionTypes();
		// test printSummaryPageHTML method
		System.out.print(printCreateHtml(questionTypes[0]));
	}

	/**
	 * @param questionType
	 * @return
	 */
	public static String printReference(String questionType) {
		if (questionType.equals(QuestionBase.QR))
			return QResponse.printReference();
		else if (questionType.equals(QuestionBase.FIB))
			return FillInBlank.printReference();
		else if (questionType.equals(QuestionBase.MC))
			return MultiChoice.printReference();
		else if (questionType.equals(QuestionBase.PR))
			return PResponse.printReference();
		else if (questionType.equals(QuestionBase.MA))
			return MAQuestion.printReference();
		else if (questionType.equals(QuestionBase.MCMA))
			return MCMAQuestion.printReference();
		else if (questionType.equals(QuestionBase.MATCH))
			return Matching.printReference();
		return "error";
	}

	public static String printReference() {
		return QuestionBase.printReference();
	}

}
