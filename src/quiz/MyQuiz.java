package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import util.Helper;
import database.MyDB;

public class MyQuiz implements Quiz {

	public final class QuizEvent {
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
			Timestamp submitTime = new Timestamp(0);
			long timeElapsed = 0;
			int score = 0;

			Connection con = MyDB.getConnection();
			try {
				Statement stmt = con.createStatement();
				// query quizName_Event_Table
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
						+ " is already in " + quizName + "_Event_Table";

				// update quizName_Event_Table -- insert a row
				String contentRow = "\"" + getQuizId() + "\",\""
						+ getUserName() + "\",\"" + getSubmitTime() + "\", "
						+ getTimeElapsed() + ", " + getScore();
				stmt.executeUpdate("INSERT INTO " + quizName
						+ "_Event_Table VALUES (" + contentRow + ")");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public final String getQuizId() {
			return quizId;
		}

		public final String getUserName() {
			return userName;
		}

		public final Timestamp getSubmitTime() {
			return submitTime;
		}

		public final long getTimeElapsed() {
			return timeElapsed;
		}

		public final int getScore() {
			return score;
		}

	}

	private final String quizName;
	private final String creatorId;
	private final Timestamp createTime;
	private final int totalScore;
	private final String quizDescription;
	private final List<String> tags;
	private final boolean canPractice;
	private final boolean isRandom;
	private final boolean isOnePage;
	private final boolean isImmCorrection; // jvm optimization
	private final List<QuestionBase> questionList;

	private static final String CREATECONTENTTABLEPARAMS = "questionNum CHAR(32), "
			+ "questionType CHAR(32), " + "questionId CHAR(32)";

	private static final String CREATEEVENTTABLEPARAMS = "quizId CHAR(32), "
			+ "userName CHAR(32), " + "submitTime TIMESTAMP, "
			+ "timeElapsed BIGINT, " + "score INT ";

	public MyQuiz(String quizName, String creatorId, String quizDescription,
			List<String> tags, boolean canPractice, boolean isRandom,
			boolean isOnePage, boolean isImmCorrection,
			List<QuestionBase> questionList, Timestamp createTime) {
		super();
		this.quizName = quizName;
		this.creatorId = creatorId;
		this.quizDescription = quizDescription;
		this.tags = tags;
		this.canPractice = canPractice;
		this.isRandom = isRandom;
		this.isOnePage = isOnePage;
		this.isImmCorrection = isImmCorrection;
		this.questionList = questionList;
		this.createTime = createTime;
		int totalScore = 0;
		for (QuestionBase q : questionList) {
			totalScore += Integer.parseInt(q.getMaxScore());
		}
		this.totalScore = totalScore;
	}

	public MyQuiz(String quizName) {
		// below declare a set of non-final variables, which is a hack to get
		// around the exception issue
		String creatorId = "error";
		int totalScore = 0;
		String quizDescription = "error";
		List<String> tags = new ArrayList<String>();
		boolean canPractice = false;
		boolean isRandom = false;
		boolean isOnePage = false;
		boolean isImmCorrection = false;
		List<QuestionBase> questionList = new ArrayList<QuestionBase>();
		Timestamp createTime = new Timestamp(0);

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
			createTime = rs.getTimestamp("createTime");

			// query quizName_Content_Table
			int score = 0;
			rs = stmt.executeQuery("SELECT * FROM " + quizName
					+ "_Content_Table");
			while (rs.next()) {
				String questionType = rs.getString("questionType");
				String questionId = rs.getString("questionId");
				QuestionBase question = QuestionFactory.getQuestion(
						questionType, questionId);
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
		this.createTime = createTime;
	}

	public void saveToDatabase() {
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// update Global_Quiz_Info_Table -- insert a row
			String quizRow = "\"" + quizName + "\",\"" + creatorId + "\",\""
					+ quizDescription + "\",\"" + Helper.generateTags(tags)
					+ "\"," + canPractice + ", " + isRandom + ", " + isOnePage
					+ ", " + isImmCorrection + ", \"" + createTime + "\"";
			stmt.executeUpdate("INSERT INTO Global_Quiz_Info_Table VALUES("
					+ quizRow + ")");

			// create quizName_Content_Table
			stmt.executeUpdate("DROP TABLE IF EXISTS " + quizName
					+ "_Content_Table");
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
			stmt.executeUpdate("DROP TABLE IF EXISTS " + quizName
					+ "_Event_Table");
			stmt.executeUpdate("CREATE TABLE " + quizName + "_Event_Table ( "
					+ CREATEEVENTTABLEPARAMS + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shuffleQuestionListAndSave() {
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// delete all rows from quizName_Content_Table
			stmt.executeUpdate("DELETE FROM " + quizName
					+ "_Content_Table");
			// shuffle current question list
			Collections.shuffle(questionList);
			// populate quizName_Content_Table
			for (int i = 0; i < questionList.size(); i++) {
				QuestionBase q = questionList.get(i);
				String contentRow = "\"" + i + "\",\"" + q.getQuestionType()
						+ "\",\"" + q.getQuestionId() + "\"";
				stmt.executeUpdate("INSERT INTO " + quizName
						+ "_Content_Table VALUES(" + contentRow + ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getSummaryPage() {
		return "quiz_summary.jsp?quizName=" + quizName;
	}

	/**
	 * 
	 * @return String of the hyper link to start a quiz
	 */
	public String getQuizStartPage() {
		return "QuestionProcessServlet?quizName=" + quizName;
	}

	/**
	 * 
	 * @return String of the hyper link to edit a quiz
	 */
	public String getQuizEditPage() {
		return null;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public String getQuizDescription() {
		return quizDescription;
	}

	public List<String> getTags() {
		return tags;
	}

	public boolean isCanPractice() {
		return canPractice;
	}

	public boolean isRandom() {
		return isRandom;
	}

	public boolean isOnePage() {
		return isOnePage;
	}

	public boolean isImmCorrection() {
		return isImmCorrection;
	}

	@Override
	public String getScore(String quizId) {
		QuizEvent event = new QuizEvent(quizId);
		return "" + event.getScore();
	}

	@Override
	public String getCreatorId() {
		return creatorId;
	}

	@Override
	public String getMaxScore() {
		return "" + totalScore;
	}

	@Override
	public String getQuizName() {
		return quizName;
	}

	public List<QuestionBase> getQuestionList() {
		return questionList;
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

	public int getTakenTimes() {
		int num = 0;
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query quizName_Event_Table
			ResultSet rs = stmt.executeQuery("SELECT quizId FROM " + quizName
					+ "_Event_Table");
			// get the number of rows
			rs.last();
			num = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	private String generateId() {
		// get the first hash
		String quizId = Helper.getMD5ForTime();

		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query quizName_Event_Table
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + quizName
					+ "_Event_Table WHERE quizId = \"" + quizId + "\"");
			// try another hash until it is not used already
			while (rs.isBeforeFirst()) {
				quizId = Helper.getMD5ForTime();
				rs = stmt.executeQuery("SELECT * FROM " + quizName
						+ "_Event_Table WHERE quizId = \"" + quizId + "\"");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizId;
	}

	/**
	 * Constructs a QuizEvent from the input and save it to database, and
	 * returns QuizId String
	 * 
	 * @param userName
	 *            name string of the user who takes the quiz
	 * @param timeElapsed
	 *            the time elapsed for the user to take the quiz
	 * @param score
	 *            the score get it by the user
	 * 
	 * @return QuizId String
	 * 
	 */
	public String saveQuizEvent(String userName, long timeElapsed, int score) {
		Timestamp submitTime = new Timestamp(new java.util.Date().getTime());
		String quizId = generateId();
		QuizEvent event = new QuizEvent(quizId, userName, submitTime,
				timeElapsed, score);
		event.saveToDatabase();
		return quizId;
	}


}
