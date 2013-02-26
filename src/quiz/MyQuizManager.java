/**
 * 
 */
package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import database.MyDB;

/**
 * @author jimmy_000
 * 
 */
public final class MyQuizManager implements QuizManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#getPopularQuiz()
	 */
	@Override
	public List<String> getPopularQuiz() {
		int numEntries = 5;
		// below implements a naive sorting way
		// first get all quizzes
		List<MyQuiz> quizzes = getAllQuizzes();
		// sort quizzes by takenTimes
		Collections.sort(quizzes, new Comparator<MyQuiz>() {
			@Override
			public int compare(MyQuiz o1, MyQuiz o2) {
				return o2.getTakenTimes() - o1.getTakenTimes();
			}
		});
		// get the first numEntries of quiz name after sorting
		List<String> names = new ArrayList<String>(numEntries);
		for (int i = 0; i < numEntries && i < quizzes.size(); i++)
			names.add(quizzes.get(i).getQuizName());
		return names;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#getRecentCreateQuiz()
	 */
	@Override
	public List<String> getRecentCreateQuiz() {
		int numEntries = 5;
		List<MyQuiz> quizzes = getRecentCreateQuiz(numEntries);
		List<String> names = new ArrayList<String>(quizzes.size());
		for (MyQuiz q : quizzes) {
			names.add(q.getQuizName());
		}
		return names;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#getQuiz(java.lang.String)
	 */
	@Override
	public Quiz getQuiz(String name) {
		return new MyQuiz(name);
	}

	private List<MyQuiz> getAllQuizzes() {
		List<MyQuiz> list = new ArrayList<MyQuiz>();
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT quizName FROM Global_Quiz_Info_Table");
			while (rs.next()) {
				String quizName = rs.getString("quizName");
				list.add(new MyQuiz(quizName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private List<MyQuiz> getRecentCreateQuiz(int num) {
		List<MyQuiz> list = new ArrayList<MyQuiz>();
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT quizName FROM Global_Quiz_Info_Table"
							+ "ORDER BY createTime DESC" + "LIMIT 0," + num);
			while (rs.next()) {
				String quizName = rs.getString("quizName");
				list.add(new MyQuiz(quizName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
