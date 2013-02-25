package quiz;

import java.sql.Connection;
import java.util.List;

import database.MyDB;

public class MyQuiz implements Quiz {

	private final class QuizEvent {
		private final String quizId;
		private final String userName;
		private final String submittime;
		private final String timeElapsed;
		private final String score;

		private QuizEvent(String quizId, String userName, String submittime,
				String timeElapsed, String score) {
			super();
			this.quizId = quizId;
			this.userName = userName;
			this.submittime = submittime;
			this.timeElapsed = timeElapsed;
			this.score = score;
		}

		private QuizEvent(String quizId) {
			// load from DB
		}

		private void saveToDatabase() {

		}

		/**
		 * @return the quizId
		 */
		private final String getQuizId() {
			return quizId;
		}

		/**
		 * @return the userName
		 */
		private final String getUserName() {
			return userName;
		}

		/**
		 * @return the submittime
		 */
		private final String getSubmittime() {
			return submittime;
		}

		/**
		 * @return the timeElapsed
		 */
		private final String getTimeElapsed() {
			return timeElapsed;
		}

		/**
		 * @return the score
		 */
		private final String getScore() {
			return score;
		}

	}

	private final String quizName;
	private final String creatorId;
	private final String totalScore;
	private final String quizDescription;
	private final List<String> tags;
	private final boolean canPractice;
	private final boolean isRandom;
	private final boolean isOnePage;
	private final boolean isImmCorrection; // jvm optimization
	private final List<QuestionBase> questionList;

	public MyQuiz(String quizName, String creatorId, String totalScore,
			String quizDescription, List<String> tags, boolean canPractice,
			boolean isRandom, boolean isOnePage, boolean isImmCorrection,
			List<QuestionBase> questionList) {
		super();
		this.quizName = quizName;
		this.creatorId = creatorId;
		this.totalScore = totalScore;
		this.quizDescription = quizDescription;
		this.tags = tags;
		this.canPractice = canPractice;
		this.isRandom = isRandom;
		this.isOnePage = isOnePage;
		this.isImmCorrection = isImmCorrection;
		this.questionList = questionList;
	}

	public MyQuiz(String quizName) {
		// TODO
		Connection con = MyDB.getConnection();

	}

	public void saveToDatabase() {

	}

	/**
	 * Print HTML for Quiz Summary Page Each quiz should have a summary page
	 * which includes:
	 * 
	 * The text description of the quiz.
	 * 
	 * The creator of the quiz(hot linked to the creator’s user page).
	 * 
	 * A list of the user’s past performance on this specific quiz. Consider
	 * allowing the user to order this by date, by percent correct, and by
	 * amount of time the quiz took.
	 * 
	 * A list of the highest performers of all time.
	 * 
	 * A list of top performers in the last day.
	 * 
	 * A list showing the performance of recent test takers (both good and bad).
	 * 
	 * Summary statistics on how well all users have performed on the quiz.
	 * 
	 * A way to initiate taking the quiz.
	 * 
	 * A way to start the quiz in practice mode, if available.
	 * 
	 * A way to start editing the quiz, if the user is the quiz owner.
	 * 
	 * @param userName
	 *            the name of the user who are accessing this page
	 * 
	 * @return String of page in HTML
	 */
	public String printSummaryPageHTML(String userName) {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>\n");
		html.append("<html>\n");
		html.append("<head>\n");
		html.append("<meta charset=\"UTF-8\">\n");
		html.append("<title>Quiz Summary - " + quizName + "</title>\n");
		html.append("</head>\n");
		html.append("<body>\n");
		html.append("<h1>Quiz Summary - " + quizName + "</h1>\n");

		// The text description of the quiz.
		html.append("<h2>Quiz Description</h2>\n");
		html.append("<p>" + quizDescription + "</p>\n");

		// The creator of the quiz(hot linked to the creator’s user page).
		html.append("<h2>Quiz Creator</h2>\n");
		html.append("<p>" + creatorId + "</p>\n"); // TODO: should be a hyper
													// link here

		// TODO A list of the user’s past performance on this specific quiz.

		// A list of the highest performers of all time.
		List<QuizEvent> highScores = highScoreEvents(5);
		html.append("<h2>High Scores</h2>\n");
		html.append("<ol>\n");
		for (QuizEvent e : highScores) {
			html.append("<li>" + e.getUserName() + ": " + e.score + "</li>\n");
		}
		html.append("</ol>\n");

		// A list of top performers in the last day.
		List<QuizEvent> highScoresLastday = highScoreEvents(5);
		html.append("<h2>High Scores in the Last Day</h2>\n");
		html.append("<ol>\n");
		for (QuizEvent e : highScoresLastday) {
			html.append("<li>" + e.getUserName() + ": " + e.score + "</li>\n");
		}
		html.append("</ol>\n");

		// A list showing the performance of recent test takers
		List<QuizEvent> recentEvents = recentTakenEvents(5);
		html.append("<h2>Recent Taken Log</h2>\n");
		html.append("<ol>\n");
		for (QuizEvent e : recentEvents) {
			html.append("<li>" + e.getUserName() + ": " + e.score + "</li>\n");
		}
		html.append("</ol>\n");

		// TODO Summary statistics on how well all users have performed on the
		// quiz.

		// A way to initiate taking the quiz.
		// A way to start the quiz in practice mode, if available.
		html.append("<form action=\"" + getQuizStartPage() + "\">\n");
		html.append("<input type=\"hidden\" name=\"quizName\" value=\""
				+ quizName + "\">\n");
		String disabledAttr = "";
		if (!canPractice) // if cannot practice, set disabled attribute
			disabledAttr = "disabled";
		html.append("<input type=\"checkbox\" name=\"practiceMode\" value=\"true\" "
				+ disabledAttr + ">Start in practice mode<br>\n");
		html.append("<input type=\"submit\" value=\"Start Quiz\" >\n");
		html.append("</form>\n");

		// A way to start editing the quiz, if the user is the quiz owner.
		if (userName.equals(creatorId)) {
			html.append("<form action=\"" + getQuizEditPage() + "\">\n");
			html.append("<input type=\"hidden\" name=\"quizName\" value=\""
					+ quizName + "\">\n");
			html.append("<input type=\"submit\" value=\"Edit Quiz\" >\n");
			html.append("</form>\n");
		}

		html.append("</body>\n");
		html.append("</html>\n");
		return html.toString();
	}

	@Override
	public String getSummaryPage() {
		String url = "/QuizSummayPage?quizName=" + quizName;
		return url;
	}

	/**
	 * 
	 * @return String of the hyper link to start a quiz
	 */
	public String getQuizStartPage() {
		return null;
	}

	/**
	 * 
	 * @return String of the hyper link to edit a quiz
	 */
	public String getQuizEditPage() {
		return null;
	}

	@Override
	public String getScore(String quizId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMaxScore() {
		return totalScore;
	}

	@Override
	public String getTimeElapsed(String quizId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param num
	 *            The number of events returned
	 * @return List of QuizEvent
	 */
	public List<QuizEvent> highScoreEvents(int num) {
		// TODO
		return null;
	}

	/**
	 * 
	 * @param num
	 *            The number of events returned
	 * @return List of QuizEvent
	 */
	public List<QuizEvent> highScoreLastDayEvents(int num) {
		// TODO
		return null;
	}

	/**
	 * 
	 * @param num
	 *            The number of events returned
	 * @return List of QuizEvent
	 */
	public List<QuizEvent> recentTakenEvents(int num) {
		// TODO
		return null;
	}
}
