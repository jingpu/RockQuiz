package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import util.Helper;
import database.MyDB;

public class MyQuiz implements Quiz {

	private final class QuizEvent {
		private final String quizId;
		private final String userName;
		private final Timestamp submitTime;
		private final long timeElapsed;
		private final int score;

		private QuizEvent(String quizId, String userName, Timestamp submitTime,
				long timeElapsed, int score) {
			super();
			this.quizId = quizId;
			this.userName = userName;
			this.submitTime = submitTime;
			this.timeElapsed = timeElapsed;
			this.score = score;
		}

		private QuizEvent(String quizId) {
			// below declare a set of non-final variable, which is a hack to get
			// around the exception issue
			String userName = "error";
			Timestamp submitTime = null;
			long timeElapsed = 0;
			int score = 0;

			Connection con = MyDB.getConnection();
			try {
				Statement stmt = con.createStatement();
				// query quizExample0_Event_Table
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + quizName
						+ "_Event_Table WHERE quizId = \"" + quizId + "\"");
				rs.next();
				userName = rs.getString("userName");
				submitTime = rs.getTimestamp("submitTime");
				timeElapsed = rs.getLong("timeElapsed");
				score = rs.getInt("score");

			} catch (SQLException e) {
				e.printStackTrace();
			}
			// do the real initialization now
			this.quizId = quizId;
			this.userName = userName;
			this.submitTime = submitTime;
			this.timeElapsed = timeElapsed;
			this.score = score;
		}

		private void saveToDatabase() {
			Connection con = MyDB.getConnection();
			try {
				Statement stmt = con.createStatement();
				// DEBUG: check if QuizId is already in database
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + quizName
						+ "_Event_Table WHERE quizId = \"" + quizId + "\"");
				assert rs.isBeforeFirst() : "ERROR: quizId = " + quizId
						+ " is already in " + quizName + "_Content_Table";

				// update quizName_Event_Table -- insert a row
				String contentRow = "\"" + getQuizId() + "\",\""
						+ getUserName() + "\",\"" + getSubmittime() + "\", "
						+ getTimeElapsed() + ", " + getScore();
				stmt.executeUpdate("INSERT INTO " + quizName
						+ "_Content_Table VALUES(" + contentRow + ")");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		private final String getQuizId() {
			return quizId;
		}

		private final String getUserName() {
			return userName;
		}

		private final Timestamp getSubmittime() {
			return submitTime;
		}

		private final long getTimeElapsed() {
			return timeElapsed;
		}

		private final int getScore() {
			return score;
		}

	}

	private final String quizName;
	private final String creatorId;
	private final int totalScore;
	private final String quizDescription;
	private final List<String> tags;
	private final boolean canPractice;
	private final boolean isRandom;
	private final boolean isOnePage;
	private final boolean isImmCorrection; // jvm optimization
	private final List<QuestionBase> questionList;

	private static final String CREATECONTENTTABLEPARAMS = "questionNum CHAR(32), "
			+ "questionType CHAR(32), " + "questionId CHAR(32 )";

	private static final String CREATEEVENTTABLEPARAMS = "quizId CHAR(32), "
			+ "userName CHAR(32), " + "submitTime TIMESTAMP, "
			+ "timeElapsed BIGINT, " + "score INT ";

	public MyQuiz(String quizName, String creatorId, int totalScore,
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
		// below declare a set of non-final variables, which is a hack to get
		// around the exception issue
		String creatorId = "error";
		int totalScore = 0;
		String quizDescription = "error";
		List<String> tags = null;
		boolean canPractice = false;
		boolean isRandom = false;
		boolean isOnePage = false;
		boolean isImmCorrection = false;
		List<QuestionBase> questionList = null;

		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Global_Quiz_Info_Table WHERE quizName = \""
							+ quizName + "\"");
			rs.next();
			creatorId = rs.getString("creatorId");
			quizDescription = rs.getString("quizDescription");
			canPractice = rs.getBoolean("canPractice");
			isRandom = rs.getBoolean("isRandom");
			isOnePage = rs.getBoolean("isOnePage");
			isImmCorrection = rs.getBoolean("isImmediateCorrection");
			String tagString = rs.getString("tagString");
			tags = Helper.parseTags(tagString);

			// query quizName_Content_Table
			int score = 0;
			questionList = new ArrayList<QuestionBase>();
			rs = stmt.executeQuery("SELECT * FROM " + quizName
					+ "_Content_Table");
			while (rs.next()) {
				String questionType = rs.getString("questionType");
				String questionId = rs.getString("questionId");
				QuestionBase question = QuestionBase.getQuestion(questionType,
						questionId);
				if (question != null)
					score += Integer.parseInt(question.getMaxScore());
				questionList.add(question);
			}
			totalScore = score;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// do the real initialization now
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

	public void saveToDatabase() {
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// update Global_Quiz_Info_Table -- insert a row
			String quizRow = "\"" + quizName + "\",\"" + creatorId + "\",\""
					+ quizDescription + "\",\"" + Helper.generateTags(tags)
					+ "\"," + canPractice + ", " + isRandom + ", " + isOnePage
					+ ", " + isImmCorrection;
			stmt.executeUpdate("INSERT INTO Global_Quiz_Info_Table VALUES("
					+ quizRow + ")");

			// create quizName_Content_Table
			stmt.executeUpdate("CREATE TABLE " + quizName + "_Content_Table ( "
					+ CREATECONTENTTABLEPARAMS + ")");
			// populate quizName_Content_Table
			for (int i = 0; i < questionList.size(); i++) {
				QuestionBase q = questionList.get(i);
				String contentRow = "\"" + i + "\",\"" + q.getQuestionType()
						+ "\",\"" + q.getQuestionId() + "\"";
				stmt.executeUpdate("INSERT INTO " + quizName
						+ "_Content_Table VALUES(" + contentRow + ")");
			}

			// create quizName_Event_Table
			stmt.executeUpdate("CREATE TABLE " + quizName + "_Event_Table ( "
					+ CREATEEVENTTABLEPARAMS + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

		html.append("<h2>Tags</h2>\n");
		html.append("<p>\n");
		for (String tag : tags) {
			html.append("#" + tag + ", \n");
		}
		html.append("</p>\n");

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
		List<QuizEvent> highScoresLastday = highScoreLastDayEvents(5);
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
		QuizEvent event = new QuizEvent(quizId);
		return "" + event.getScore();
	}

	@Override
	public String getMaxScore() {
		return "" + totalScore;
	}

	@Override
	public String getTimeElapsed(String quizId) {
		QuizEvent event = new QuizEvent(quizId);
		long timeElapsed = event.getTimeElapsed();
		return String.format(
				"%d:%d",
				TimeUnit.MILLISECONDS.toMinutes(timeElapsed),
				TimeUnit.MILLISECONDS.toSeconds(timeElapsed)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(timeElapsed)));

	}

	/**
	 * Returns List of at most num of QuizEvents ordered by the scores
	 * 
	 * @param num
	 *            The number of events returned
	 * @return List of QuizEvent
	 */
	public List<QuizEvent> highScoreEvents(int num) {
		Connection con = MyDB.getConnection();
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			// query quizExample0_Event_Table
			rs = stmt.executeQuery("SELECT quizId FROM " + quizName
					+ "_Event_Table " + "ORDER BY score DESC, timeElapsed ASC "
					+ "LIMIT 0," + num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getQuizEventList(rs);
	}

	/**
	 * Returns List of at most num of QuizEvents submitted within a day and
	 * ordered by the scores
	 * 
	 * @param num
	 *            The number of events returned
	 * @return List of QuizEvent
	 */
	public List<QuizEvent> highScoreLastDayEvents(int num) {
		Connection con = MyDB.getConnection();
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			// query quizExample0_Event_Table
			rs = stmt
					.executeQuery("SELECT quizId FROM "
							+ quizName
							+ "_Event_Table "
							+ "WHERE submitTime >= cast((now() - interval 1 day) as date) "
							+ "ORDER BY score DESC, timeElapsed ASC "
							+ "LIMIT 0," + num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getQuizEventList(rs);
	}

	/**
	 * Returns List of at most num of recent taken QuizEvents
	 * 
	 * @param num
	 *            The number of events returned
	 * @return List of QuizEvent
	 */
	public List<QuizEvent> recentTakenEvents(int num) {

		Connection con = MyDB.getConnection();
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			// query quizExample0_Event_Table
			rs = stmt.executeQuery("SELECT quizId FROM " + quizName
					+ "_Event_Table " + "ORDER BY submitTime DESC "
					+ "LIMIT 0," + num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getQuizEventList(rs);
	}

	private List<QuizEvent> allEvents() {
		Connection con = MyDB.getConnection();
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			// query quizExample0_Event_Table
			rs = stmt.executeQuery("SELECT quizId FROM " + quizName
					+ "_Event_Table");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getQuizEventList(rs);
	}

	/**
	 * Parse from SQL resultsSet to a List of QuizEvent
	 * 
	 * @param rs
	 *            SQL ResultSet from a query to quizName_Event_Table. It must
	 *            have the column of 'quizId'
	 * @return List of QuizEvent
	 */
	private List<QuizEvent> getQuizEventList(ResultSet rs) {
		List<QuizEvent> events = new ArrayList<MyQuiz.QuizEvent>();
		try {
			while (rs.next()) {
				String quizId = rs.getString("quizId");
				QuizEvent event = new QuizEvent(quizId);
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}

	public static void main(String[] args) {
		MyQuiz quiz = new MyQuiz("quizExample0");
		// test printSummaryPageHTML method
		System.out.print(quiz.printSummaryPageHTML("Patrick"));
	}
}
