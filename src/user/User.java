package user;

import java.util.List;

/**@author youyuan
 * **/
public interface User {
	
	public void addQuizTaken(String quizName, String quizId);
	public void addQuizCreated(String quizName);
	
	/**
	 * @param achieveId
	 * @param quizName
	 */
	void addAchievement(String achieveId, String quizName);
	
}
