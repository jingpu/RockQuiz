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
public class PResponse extends QuestionBase {
	private String url;
	
	public PResponse(String questionType, String questionId, String creatorId,
			String typeIntro, String questionDescription, String answer,
			String maxScore, String tagString, String correctRation, String url) {
		super(questionType, questionId, creatorId, typeIntro, questionDescription,
				answer, maxScore, tagString, correctRation);
		// TODO Auto-generated constructor stub
		this.url = url;
	}


	public PResponse(String questionType, String questionId) {
		questionType = PR;
		queryStmt = "SELECT * FROM Picture_Response_Pool WHERE question_id = " + questionId;
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
				url = rs.getString(9);
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
		url + "\")";

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
	public String printCreateHtml() {
		// TODO Auto-generated method stub
		String html = super.printCreateHtml();
		return html + "<p><img src=" + url + "></p>\n";
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
