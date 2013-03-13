package quiz;

import java.util.List;
import java.util.Set;

public interface QuizManager {

	public static int SORT_BY_CREATION_TIME = 1;
	public static int SORT_BY_TAKEN_TIMES = 2;

	/**
	 * Gets get popular quizzes
	 * 
	 * @param numEntries
	 *            the max number of quizzes returned
	 * @return List of Quiz objects
	 */
	public List<Quiz> getPopularQuiz(int numEntries);

	/**
	 * Gets get recent created quizzes
	 * 
	 * @param numEntries
	 *            the max number of quizzes returned
	 * @return List of Quiz objects
	 */
	public List<Quiz> getRecentCreateQuiz(int numEntries);

	/**
	 * interface needed by both administrator and users
	 * 
	 * @param name
	 *            String of the quizName
	 * @return Quiz which has the quizName=name in the database. returns null
	 *         pointer if nothing matched in database.
	 */
	public Quiz getQuiz(String name);

	/**
	 * deletes the quiz from data base
	 * 
	 * @param name
	 *            String of the quizName
	 */
	public void deleteQuiz(String name);

	/**
	 * Search Quizzes which has the certain querying pattern (sub-string) in
	 * QuizCreator field
	 * 
	 * @param pattern
	 *            String of querying pattern
	 * @param numEntries
	 *            the max number of quizzes returned
	 * @param sortMethod
	 *            sorting method for returned quiz. Possible value are those
	 *            static int field defined in QuizManager interface
	 * @return List of Quiz objects
	 */
	public List<Quiz> searchForQuizCreator(String pattern, int numEntries,
			int sortMethod);

	/**
	 * Search Quizzes which has the certain querying pattern (sub-string) in
	 * QuizName field
	 * 
	 * @param pattern
	 *            String of querying pattern
	 * @param numEntries
	 *            the max number of quizzes returned
	 * @param sortMethod
	 *            sorting method for returned quiz. Possible value are those
	 *            static int field defined in QuizManager interface
	 * @return List of Quiz objects
	 */
	public List<Quiz> searchForQuizName(String pattern, int numEntries,
			int sortMethod);

	/**
	 * Search Quizzes which has the certain querying pattern (sub-string) in
	 * Description field
	 * 
	 * @param pattern
	 *            String of querying pattern
	 * @param numEntries
	 *            the max number of quizzes returned
	 * @param sortMethod
	 *            sorting method for returned quiz. Possible value are those
	 *            static int field defined in QuizManager interface
	 * @return List of Quiz objects
	 */
	public List<Quiz> searchForQuizDescription(String pattern, int numEntries,
			int sortMethod);

	/**
	 * Search Quizzes which has the certain querying pattern (sub-string) in
	 * QuizName, QuizCreator, or Description field
	 * 
	 * @param pattern
	 *            String of querying pattern
	 * @param numEntries
	 *            the max number of quizzes returned
	 * @param sortMethod
	 *            sorting method for returned quiz. Possible value are those
	 *            static int field defined in QuizManager interface
	 * @return List of Quiz objects
	 */
	public List<Quiz> searchForQuiz(String pattern, int numEntries,
			int sortMethod);

	public List<Quiz> searchForCategory(String pattern,
			int sortMethod);

	public Set<String> getCategories();

}
