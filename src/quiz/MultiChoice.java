/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MyDB;

/**
 * @author yang
 *
 */
public class MultiChoice extends QuestionBase {
	private List<String> choices;

	public MultiChoice(String questionType, String questionId,
			String creatorId, String typeIntro, String questionDescription,
			String answer, String maxScore, String tagString,
			String correctRation, List<String> choices) {
		super(questionType, questionId, creatorId, typeIntro, questionDescription,
				answer, maxScore, tagString, correctRation);
		// TODO Auto-generated constructor stub
		this.choices = choices;
	}

	public MultiChoice(String questionId) {
		questionType = MC;
		queryStmt = "SELECT * FROM Multi_Choice_Pool WHERE question_id = " + questionId;
		Connection con = MyDB.getConnection();
		try {
			stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			rs = stmt.executeQuery(queryStmt);
			while(rs.next()) {
				questionId = rs.getString(1);
				creatorId = rs.getString(2);
				typeIntro = rs.getString(3);
				questionDescription = rs.getString(4);
				answer = rs.getString(5);
				maxScore = rs.getString(6);
				tagString = rs.getString(7);
				correctRatio = rs.getString(8);
				choices = new ArrayList<String>();
				for (int i = 0; i < 3; i++) {
					choices.add(rs.getString(i + 9));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveToDatabase() {
		// TODO Auto-generated method stub
		queryStmt = "INSERT INTO " + PR_Table + "VALUES (\"" +
		questionId + "\", \"" +
		creatorId + "\", \"" +
		typeIntro + "\", \"" +
		questionDescription  + "\", \"" +
		answer + "\", \"" +
		maxScore + "\", \"" +
		tagString + "\", \"" +
		correctRatio + "\", \"" +
		choices.toString() + "\")";

		try {
		rs = stmt.executeQuery(queryStmt);
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see quiz.QuestionBase#printHTML()
	 */
	@Override
	public String printHTML() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public String getScore(String userInput) {
		// TODO Auto-generated method stub
		return null;
	}

}
