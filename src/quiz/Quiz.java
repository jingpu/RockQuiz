/**
 * Quiz.java
 */
package quiz;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Jing Pu
 * 
 */
public interface Quiz {


	/**
	 * get a Quiz Summary Page URL. Usage: <a
	 * href="<%= quiz.getSummaryPage() %>"><%= quiz.getQuizName() %></a>
	 * 
	 * @return String of page url
	 */
	public String getSummaryPage(); // url to summary page

	public String getScore(String quizId); // for history part

	public String getMaxScore();

	public String getTimeElapsed(String quizId);

	public String getQuizName();

	public String getCreatorId();

	public int getTakenTimes();

	public Timestamp getCreateTime();
	
	public String getQuizDescription();

	public void clearQuizEvents();

	public int getBestScore();
	
	public String getCategory();
	
	public List<String> getTags();

}
