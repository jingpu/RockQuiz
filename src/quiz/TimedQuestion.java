/**
 * 
 */
package quiz;

/**
 * @author yang
 * 
 */
public class TimedQuestion {
	private final float timeLimit;

	protected static final String TQR = "Timed_Question_Response";
	protected static final String TFIB = "Timed_Fill_In_Blank";
	protected static final String TMC = "Timed_Multi_Choice";
	protected static final String TPR = "Timed_Picture_Response";
	protected static final String TMA = "Timed_Multi_Answer";
	protected static final String TMCMA = "Timed_Multi_Choice_Multi_Answer";

	TimedQuestion(float timeBudget) {
		timeLimit = timeBudget;
	}

	/**
	 * Static class -- have to use this??
	 * 
	 * @author yang
	 * 
	 */
	public static class TimedQResponse extends QResponse {

		/**
		 * @param questionType
		 * @param creatorId
		 * @param questionDescription
		 * @param answer
		 * @param maxScore
		 * @param tagString
		 * @param correctRatio
		 */
		public TimedQResponse(String questionType, String creatorId,
				String questionDescription, String answer, String maxScore,
				String tagString, float correctRatio) {
			super(questionType, creatorId, questionDescription, answer,
					maxScore, tagString, correctRatio);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param questionType
		 * @param questionId
		 */
		public TimedQResponse(String questionType, String questionId) {
			super(questionType, questionId);
			// TODO Auto-generated constructor stub
		}

		public static String printCreateHtml() {
			StringBuilder html = new StringBuilder();
			html.append(QResponse.printCreateHtml().toString());
			html.append("<p>TODO: Add time_budget here, use javascript to avoid multiple form problem </p>");
			return html.toString();
		}
	}

	public static class TimedFillInBlank extends FillInBlank {

		/**
		 * @param questionType
		 * @param creatorId
		 * @param questionDescription
		 * @param answer
		 * @param maxScore
		 * @param tagString
		 * @param correctRatio
		 */
		public TimedFillInBlank(String questionType, String creatorId,
				String questionDescription, String answer, String maxScore,
				String tagString, float correctRatio) {
			super(questionType, creatorId, questionDescription, answer,
					maxScore, tagString, correctRatio);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param questionType
		 * @param questionId
		 */
		public TimedFillInBlank(String questionType, String questionId) {
			super(questionType, questionId);
			// TODO Auto-generated constructor stub
		}

		public static String printCreateHtml() {
			StringBuilder html = new StringBuilder();
			html.append(FillInBlank.printCreateHtml().toString());
			html.append("<p>TODO: Add time_budget here, use javascript to avoid multiple form problem </p>");
			return html.toString();
		}

	}

	public static class TimedMultiChoice extends MultiChoice {

		/**
		 * @param questionType
		 * @param questionId
		 */
		public TimedMultiChoice(String questionType, String questionId) {
			super(questionType, questionId);
			// TODO Auto-generated constructor stub
		}

		public static String printCreateHtml() {
			StringBuilder html = new StringBuilder();
			html.append(MultiChoice.printCreateHtml().toString());
			html.append("<p>TODO: Add time_budget here, use javascript to avoid multiple form problem </p>");
			return html.toString();
		}

	}

	public static class TimedPResponse extends PResponse {

		/**
		 * @param questionType
		 * @param creatorId
		 * @param questionDescription
		 * @param answer
		 * @param maxScore
		 * @param tagString
		 * @param correctRation
		 * @param url
		 */
		public TimedPResponse(String questionType, String creatorId,
				String questionDescription, String answer, String maxScore,
				String tagString, float correctRation, String url) {
			super(questionType, creatorId, questionDescription, answer,
					maxScore, tagString, correctRation, url);
			// TODO Auto-generated constructor stub
		}

		public static String printCreateHtml() {
			StringBuilder html = new StringBuilder();
			html.append(PResponse.printCreateHtml().toString());
			html.append("<p>TODO: Add time_budget here, use javascript to avoid multiple form problem </p>");
			return html.toString();
		}
	}

	public static class TimedMAQuestion extends MAQuestion {

		/**
		 * @param questionType
		 * @param creatorId
		 * @param questionDescription
		 * @param answer
		 * @param maxScore
		 * @param tagString
		 * @param correctRatio
		 * @param isOrder
		 */
		public TimedMAQuestion(String questionType, String creatorId,
				String questionDescription, String answer, String maxScore,
				String tagString, float correctRatio, String isOrder) {
			super(questionType, creatorId, questionDescription, answer,
					maxScore, tagString, correctRatio, isOrder);
			// TODO Auto-generated constructor stub
		}

		public static String printCreateHtml() {
			StringBuilder html = new StringBuilder();
			html.append(MAQuestion.printCreateHtml().toString());
			html.append("<p>TODO: Add time_budget here, use javascript to avoid multiple form problem </p>");
			return html.toString();
		}
	}

	public static class TimedMCMAQuestion extends MCMAQuestion {

		/**
		 * @param questionType
		 * @param creatorId
		 * @param questionDescription
		 * @param answer
		 * @param maxScore
		 * @param tagString
		 * @param correctRatio
		 * @param choices
		 */
		public TimedMCMAQuestion(String questionType, String creatorId,
				String questionDescription, String answer, String maxScore,
				String tagString, float correctRatio, String choices) {
			super(questionType, creatorId, questionDescription, answer,
					maxScore, tagString, correctRatio, choices);
			// TODO Auto-generated constructor stub
		}

		public static String printCreateHtml() {
			StringBuilder html = new StringBuilder();
			html.append(MCMAQuestion.printCreateHtml().toString());
			html.append("<p>TODO: Add time_budget here, use javascript to avoid multiple form problem </p>");
			return html.toString();
		}

	}

}
