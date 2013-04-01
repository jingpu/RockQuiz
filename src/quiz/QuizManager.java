/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 ******************************************************************************/
package quiz;

import java.util.List;
import java.util.Set;

public interface QuizManager {

	public static int SORT_BY_CREATION_TIME = 1;
	public static int SORT_BY_TAKEN_TIMES = 2;
	public static int SORT_BY_RELATIVITY = 3;

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
	public List<Quiz> searchForQuizCreator(String pattern, 
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
	public List<Quiz> searchForQuizName(String pattern, 
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
	public List<Quiz> searchForQuizDescription(String pattern, 
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
	public List<Quiz> searchForQuiz(String pattern, 
			int sortMethod);

	public List<Quiz> searchForCategory(String pattern,
			int sortMethod);

	public Set<String> getCategories();

	public List<Quiz> searchForTag(String pattern, int sortMethod);

}
