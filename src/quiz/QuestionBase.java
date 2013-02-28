package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import database.MyDB;

public abstract class QuestionBase { // abstract class cannot be instantiated,
										// but only its subclass
	protected final String questionType; // how to use final here?
	protected final String questionId;
	protected final String creatorId;
	protected final String questionDescription;
	protected final String answer;
	protected final String maxScore;
	protected final String tagString;
	protected final String correctRatio;

	// Jing: why we need these object variables?
	protected Connection con;
	protected Statement stmt;
	protected ResultSet rs;
	protected String queryStmt;

	public static final String QR = "Question_Response";
	public static final String FIB = "Fill_In_Blank";
	public static final String MC = "Multi_Choice";
	public static final String PR = "Picture_Response";

	protected static final String QR_Table = "Question_Response_Pool";
	protected static final String FIB_Table = "Fill_In_Blank_Pool";
	protected static final String MC_Table = "Multi_Choice_Pool";
	protected static final String PR_Table = "Picture_Response_Pool";

	// have to have this, otherwise subclass (i.e. QResponse(String questionId))
	// will cause error
	public QuestionBase(String questionType, String questionId) {
		this.questionType = questionType;
		this.questionId = questionId;

		String questionTable = getQuestionTable(questionType);

		String tmpCreatorId = "error";
		String tmpQuestionDescription = "error";
		String tmpAnswer = "error";
		String tmpMaxScore = "error";
		String tmpTagString = "error";
		String tmpCorrectRatio = "error";

		queryStmt = "SELECT * FROM " + questionTable + " WHERE question_id = "
				+ questionId;
		Connection con = MyDB.getConnection();
		try {
			stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			rs = stmt.executeQuery(queryStmt);
			rs.next();

			tmpCreatorId = rs.getString(2);
			tmpQuestionDescription = rs.getString(4);
			tmpAnswer = rs.getString(5);
			tmpMaxScore = rs.getString(6);
			tmpTagString = rs.getString(7);
			tmpCorrectRatio = rs.getString(8);

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

	// Create a question from webpage -- cannot be used like: QuestionBase base
	// = new QuestionBase();
	public QuestionBase(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, String correctRatio) {
		super();
		this.questionType = questionType;
		this.creatorId = creatorId;
		this.questionDescription = questionDescription;
		this.answer = answer;
		this.maxScore = maxScore;
		this.tagString = tagString;
		this.correctRatio = correctRatio;

		this.questionId = generateId(questionType);
	}

	private String generateId(String questionType) {
		Integer id = 0;
		String questionTable = getQuestionTable(questionType);
		queryStmt = "SELECT * FROM " + questionTable
				+ " ORDER BY question_id DESC LIMIT 1";
		Connection con = MyDB.getConnection();
		try {
			stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			rs = stmt.executeQuery(queryStmt);
			rs.next();

			id = Integer.parseInt(rs.getString(1)) + 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id.toString();
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

	// This method is deprecated because it is moved to QuestionFactory class
	@Deprecated
	public static String[] getQuestionTypes() {
		String types[] = new String[4];
		types[0] = QR;
		types[1] = FIB;
		types[2] = MC;
		types[3] = PR;
		return types;
	}

	// This method is deprecated because it is moved to QuestionFactory class
	@Deprecated
	public static QuestionBase getQuestion(String questionType,
			String questionId) {
		// switch questionType -- factory?

		if (questionType.equals(QR))
			return new QResponse(questionType, questionId);
		if (questionType.equals(FIB))
			return new FillInBlank(questionType, questionId);
		if (questionType.equals(MC))
			return new MultiChoice(questionType, questionId);
		if (questionType.equals(PR))
			return new PResponse(questionType, questionId);
		return null;
	}

	// when clicking submit
	public abstract void saveToDatabase();

	// This method is deprecated because it is moved to QuestionFactory class
	@Deprecated
	public static String printCreateHtml(String questionType) {
		if (questionType.equals(QR))
			return QResponse.printCreateHtml();
		else if (questionType.equals(FIB))
			return FillInBlank.printCreateHtml();
		else if (questionType.equals(MC))
			return MultiChoice.printCreateHtml();
		else if (questionType.equals(PR))
			return PResponse.printCreateHtml();
		else
			return "error";
	}

	public String printReadHtml() {
		StringBuilder html = new StringBuilder();

		// The type introduction of the question //TODO: may be integrated into
		// jsp
		html.append("<h2>Question Type Introduction</h2>\n");
		// html.append("<p>" + typeIntro + "</p>\n");

		// The creator of the question TODO: link to User's profile page
		html.append("<p>Question Creator: " + creatorId + "</p>\n"); // TODO:
																		// should
																		// be a
																		// hyper

		return html.toString();
	}

	public String getScore(String userInput) {
		if (userInput.equals(answer))
			return maxScore;
		return "0";
	}

	// get prompt for JSP to show on the webpage
	// TODO more types...
	public String getPrompt() {
		if (questionType.equals(QR))
			return "Your Answer:";
		return null;
	}

	// TODO control string: "text", "hidden", "radio"
	public String getCtrlType() {
		if (questionType.equals(MC))
			return "radio";
		else
			return "text";
	}

	// Will be overridden by MultiChoice
	public List<String> getRadioIds() {
		return null;
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

	// public String getTypeIntro() {
	// return typeIntro;
	// }
	public String getQuestionDescription() {
		return questionDescription;
	}

	public String getAnswer() {
		return answer;
	}

}
