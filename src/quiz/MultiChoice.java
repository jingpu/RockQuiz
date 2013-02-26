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

	public MultiChoice(String questionType, String questionId) {
		super(questionType, questionId);
		try {
			stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			rs = stmt.executeQuery(queryStmt);
			rs.next();
		
			choices = new ArrayList<String>();	
			for (int i = 0; i < 3; i++) {
				choices.add(rs.getString(i + 9));
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

	
	@Override
	public List<String> getRadioIds() {
		// TODO Auto-generated method stub
		return choices;
	}
	
	
	/* (non-Javadoc)
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public String getScore(String userInput) {
		// TODO Auto-generated method stub
		if (userInput.equals(answer)) return  maxScore;
		return null;
	}

}
