package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.Helper;
import database.MyDB;

/**
 * QuestionBase class Served as the base class of all question types Several
 * Common attributes of all question types are initialized inside the base class
 * (i.e. questionType, questionId, creatorId, ...) Static final string for
 * questionType name and questionTable name are also defined in base class
 * 
 * @author yang
 * 
 */
public abstract class QuestionBase { // abstract class cannot be instantiated,
										// but only its subclass
	protected final String questionType;
	protected final String questionId;
	protected final String creatorId;
	protected final String questionDescription;
	protected final String answer;
	protected final String maxScore;
	protected final String tagString;
	protected final float correctRatio;

	protected static final String QR = "Question_Response";
	protected static final String FIB = "Fill_In_Blank";
	protected static final String MC = "Multi_Choice";
	protected static final String PR = "Picture_Response";
	protected static final String MA = "Multi_Answer";
	protected static final String MCMA = "Multi_Choice_Multi_Answer";

	protected static final String QR_Table = "Question_Response_Pool";
	protected static final String FIB_Table = "Fill_In_Blank_Pool";
	protected static final String MC_Table = "Multi_Choice_Pool";
	protected static final String PR_Table = "Picture_Response_Pool";
	protected static final String MA_Table = "Multi_Answer_Pool";
	protected static final String MCMA_Table = "Multi_Choice_Multi_Answer_Pool";

	protected String queryStmt;

	/**
	 * Constructor used in mode1: construct a question from database
	 * 
	 * @param questionType
	 * @param questionId
	 */
	public QuestionBase(String questionType, String questionId) {
		this.questionType = questionType;
		this.questionId = questionId;

		String questionTable = getQuestionTable(questionType);

		// if not successfully get the attribute for the question, "error" is
		// presented in that field
		String tmpCreatorId = "error";
		String tmpQuestionDescription = "error";
		String tmpAnswer = "error";
		String tmpMaxScore = "error";
		String tmpTagString = "error";
		float tmpCorrectRatio = -1; // error flag

		queryStmt = "SELECT * FROM " + questionTable
				+ " WHERE question_id = \"" + questionId + "\"";

		try {
			Connection con = MyDB.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			ResultSet rs = stmt.executeQuery(queryStmt);
			rs.next();

			tmpCreatorId = rs.getString(2);
			tmpQuestionDescription = rs.getString(4);
			tmpAnswer = rs.getString(5);
			tmpMaxScore = rs.getString(6);
			tmpTagString = rs.getString(7);
			tmpCorrectRatio = rs.getInt(8);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		creatorId = tmpCreatorId;
		questionDescription = tmpQuestionDescription;
		answer = tmpAnswer;
		maxScore = tmpMaxScore;
		tagString = tmpTagString;
		correctRatio = tmpCorrectRatio;
	};

	/**
	 * Constructor used in mode 2: construct a question from webpage
	 * 
	 * @param questionType
	 * @param creatorId
	 * @param questionDescription
	 * @param answer
	 * @param maxScore
	 * @param tagString
	 * @param correctRatio
	 */
	public QuestionBase(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, float correctRatio) {
		super();
		this.questionType = questionType;
		this.creatorId = creatorId;
		this.questionDescription = questionDescription;
		this.maxScore = maxScore;
		this.tagString = tagString;
		this.correctRatio = correctRatio;

		this.answer = answer;
		this.questionId = generateId(questionType);
	}

	/**
	 * Generate a new questionId for a question
	 * 
	 * @param questionType
	 * @return questionId
	 */
	private String generateId(String questionType) {
		// Jing's advice taken, has changed to use MD5 hash to get the question
		// Id

		String id = Helper.getMD5ForTime();
		String questionTable = getQuestionTable(questionType);

		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query questionTable
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + questionTable
					+ " WHERE question_id = \"" + id + "\"");
			// try another hash until it is not used already
			while (rs.isBeforeFirst()) {
				id = Helper.getMD5ForTime();
				rs = stmt.executeQuery("SELECT * FROM " + questionTable
						+ " WHERE question_d = \"" + id + "\"");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private String getQuestionTable(String questionType) {
		String questionTable = null;
		if (questionType.equals(QR))
			questionTable = QR_Table;
		else if (questionType.equals(FIB))
			questionTable = FIB_Table;
		else if (questionType.equals(MC))
			questionTable = MC_Table;
		else if (questionType.equals(PR))
			questionTable = PR_Table;

		return questionTable;
	}

	// when clicking submit
	public abstract void saveToDatabase();

	// protected abstract String getAnswerString(HttpServletRequest request);

	public String printReadHtml() {
		StringBuilder html = new StringBuilder();

		// TODO: may be integrated into jsp
		html.append("<h2>Question Type Introduction</h2>\n");
		// html.append("<p>" + typeIntro + "</p>\n");

		// The creator of the question TODO: link to User's profile page
		html.append("<p>Question Creator: " + creatorId + "</p>\n");

		return html.toString();
	}

	public String getScore(String userInput) {
		if (userInput.equals(answer))
			return maxScore;
		return "0";
	}

	public String getMaxScore() {
		return maxScore;
	}

	public String getQuestionType() {
		return questionType;
	}

	public String getQuestionId() {
		return questionId;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public String getAnswer() {
		return answer;
	}

}