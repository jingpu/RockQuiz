/**
 * Quiz.java
 */
package quiz;

/**
 * @author Jing Pu
 * 
 */
public interface Quiz {

	// Summary page

	public String getSummaryPage(); // url to summary page

	public String getScore(String quizId); // for history part

	public String getMaxScore();

	public String getTimeElapsed(String quizId);

	public String getQuizName();

}
