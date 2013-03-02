package quiz;

import java.util.List;

public interface QuizManager {

	// Summary page
	public List<String> getPopularQuiz();

	public List<String> getRecentCreateQuiz();

	/**
	 * interface needed by both administrator and users
	 * 
	 * @param name
	 *            String of the quizName
	 * @return Quiz which has the quizName=name in the database. returns null
	 *         pointer if nothing matched in database.
	 */
	public Quiz getQuiz(String name);

}
