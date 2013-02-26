/**
 * 
 */
package quiz;

import java.sql.SQLException;

/**
 * @author yang
 *
 */
public class FillInBlank extends QuestionBase {
	private static final String typeIntro = "In this type of question, given a question with a blank, user need to fill in the blank";
	
	
	
	
	public FillInBlank(String questionType, String creatorId,
			String questionDescription, String answer, String maxScore,
			String tagString, String correctRatio) {
		super(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio);
			
		// TODO Auto-generated constructor stub
	}

	
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
		return "0";
	}
	
	
	private String parsePrefix() {
		return questionDescription.split("#blank#")[0];
	}
	
	private String parseSuffix() {
		return questionDescription.split("#blank#")[1];
	}
	
	
	public static String printCreateHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append("<h1>This page will guide you to create a Fill-In-Blank question</h1>");
		html.append("<p> Please enter proposed question description and answer. In order to insert a blank, please follow the format #blank# </p>");
		
		//create the form 
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p> i.e. In order to express a question: I think _____ is awesome. You should type it as \"I think #blank# is awesome\"</p>");
		html.append("<p>Question Description: <textarea name=\"questionDescription\" rows=\"10\" cols=\"50\"></textarea></p>");
		html.append("<p>Answer:   <input type=\"text\" name=\"answer\" ></input></p>");
		html.append("<p>Score:   <input type=\"text\" name=\"maxScore\" ></input></p>");
		
		//Hidden information - question Type and tag information
		html.append("<p><input type=\"hidden\" name=\"questionType\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"tag\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Save\"/></form>");
		html.append("");

		return html.toString();
	}

	
	
	@Override
	public String printReadHtml() {
		// TODO Auto-generated method stub
		StringBuilder html = new StringBuilder();
		html.append(super.printReadHtml());
		
		html.append("<h1>This is a question page, please read the question information, and make an answer</h1>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">");
		html.append("<p>Question Description: ");
		
		//create prefix, blank, suffix
		html.append(parsePrefix());
		html.append("<p><input type=\"text\" name=\"answer\" ></input></p>");
		html.append(parseSuffix());
		html.append("</p>");
		
		//Hidden information - questionType and questionId information
		html.append("<p><input type=\"hidden\" name=\"questionType\" ></input></p>");
		html.append("<p><input type=\"hidden\" name=\"questionId\" ></input></p>");
		html.append("<input type=\"submit\" value = \"Next\"/></form>");

		return html.toString();
		
	}


}
