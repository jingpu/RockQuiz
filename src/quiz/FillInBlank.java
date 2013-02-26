/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.SQLException;

import database.MyDB;

/**
 * @author yang
 *
 */
public class FillInBlank extends QuestionBase {

	public FillInBlank(String questionType, String questionId) {
		// TODO Auto-generated constructor stub
		super(questionType, questionId);
		
	}


	@Override
	public void saveToDatabase() {
		// TODO Auto-generated method stub
		queryStmt = "INSERT INTO " + FIB_Table + "VALUES (\"" +
					questionId + "\", \"" +
					creatorId + "\", \"" +
					typeIntro + "\", \"" +
					questionDescription  + "\", \"" +
					answer + "\", \"" +
					maxScore + "\", \"" +
					tagString + "\", \"" +
					correctRatio + "\")";
		
		try {
		rs = stmt.executeQuery(queryStmt);
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
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
