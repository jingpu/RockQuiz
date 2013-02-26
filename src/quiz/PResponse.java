/**
 * 
 */
package quiz;

import java.sql.SQLException;

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
		super(questionType, questionId);
		try {
			stmt = con.createStatement();
			stmt.executeQuery("USE c_cs108_yzhao3");
			rs = stmt.executeQuery(queryStmt);
			rs.next();
			url = rs.getString(9);
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
	
		StringBuilder html = new StringBuilder();
		html.append(super.printCreateHtml());
		html.append("<h1>This page will guide you to create a picture-response question</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> Please enter proposed question description and answer </p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");
		html.append("<p>Picture URL: <img src=\" + url + \"></p>\n");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionType\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Save\"/></form>");
		return html.toString();
		
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
