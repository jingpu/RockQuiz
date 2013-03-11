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

	public MyQuizManager() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#getQuiz(java.lang.String)
	 */
	@Override
	public Quiz getQuiz(String name) {
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT quizName FROM Global_Quiz_Info_Table"
							+ " WHERE quizName = \"" + name + "\"");
			if(rs.isBeforeFirst())
				return new MyQuiz(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Quiz> getAllQuizzes() {
		List<Quiz> list = new ArrayList<Quiz>();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#getPopularQuiz(int)
	 */
	@Override
	public List<Quiz> getPopularQuiz(int numEntries) {
		// below implements a naive sorting way
		// first get all quizzes
		List<Quiz> list = getAllQuizzes();
		// sort quizzes by takenTimes
		sortQuizList(list, QuizManager.SORT_BY_TAKEN_TIMES);
		// return sublist of the first numEntries elements
		return list.subList(0, Math.min(list.size(), numEntries));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#getRecentCreateQuiz(int)
	 */
	@Override
	public List<Quiz> getRecentCreateQuiz(int numEntries) {
		List<Quiz> list = new ArrayList<Quiz>();
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT quizName FROM Global_Quiz_Info_Table"
							+ " ORDER BY createTime DESC " + "LIMIT 0,"
							+ numEntries);
			while (rs.next()) {
				String quizName = rs.getString("quizName");
				list.add(new MyQuiz(quizName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#searchForQuizCreator(java.lang.String, int)
	 */
	@Override
	public List<Quiz> searchForQuizCreator(String pattern, 
			int sortMethod) {
		List<Quiz> list = new ArrayList<Quiz>();
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT quizName FROM Global_Quiz_Info_Table"
							+ " WHERE creatorId LIKE '%" + pattern + "%' ORDER BY createTime DESC");
			while (rs.next()) {
				String quizName = rs.getString("quizName");
				list.add(new MyQuiz(quizName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// sort list
		Collections.sort(list, new Comparator<Quiz>() {
			@Override
			public int compare(Quiz o1, Quiz o2) {
				return o2.getCreatorId().length() - o1.getCreatorId().length();
			}
		});
		// return sublist of the first numEntries elements
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#searchForQuizName(java.lang.String, int)
	 */
	@Override
	public List<Quiz> searchForQuizName(String pattern, 
			int sortMethod) {
		List<Quiz> list = new ArrayList<Quiz>();
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT quizName FROM Global_Quiz_Info_Table"
							+ " WHERE quizName LIKE '%" + pattern + "%' ORDER BY createTime DESC");
			while (rs.next()) {
				String quizName = rs.getString("quizName");
				list.add(new MyQuiz(quizName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// sort list
		Collections.sort(list, new Comparator<Quiz>() {
			@Override
			public int compare(Quiz o1, Quiz o2) {
				return o1.getQuizName().length() - o2.getQuizName().length();
			}
		});
		// return sublist of the first numEntries elements
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#searchForQuizDescription(java.lang.String, int)
	 */
	@Override
	public List<Quiz> searchForQuizDescription(String pattern, 
			int sortMethod) {
		List<Quiz> list = new ArrayList<Quiz>();
		Connection con = MyDB.getConnection();
		try {
			Statement stmt = con.createStatement();
			// query Global_Quiz_Info_Table
			ResultSet rs = stmt
					.executeQuery("SELECT quizName FROM Global_Quiz_Info_Table"
							+ " WHERE quizDescription LIKE '%" + pattern
							+ "%' ORDER BY createTime DESC");
			while (rs.next()) {
				String quizName = rs.getString("quizName");
				list.add(new MyQuiz(quizName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return sublist of the first numEntries elements
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quiz.QuizManager#searchForQuiz(java.lang.String, int)
	 */
	@Override
	public List<Quiz> searchForQuiz(String pattern, 
			int sortMethod) {
		List<Quiz> list = searchForQuizCreator(pattern, sortMethod);
		List<Quiz> list1 = searchForQuizDescription(pattern, 
				sortMethod);
		List<Quiz> list2 = searchForQuizName(pattern,  sortMethod);
		// merge list1 and list2 to list
		list1.addAll(list);
		for(Quiz quiz1 : list1){
			boolean exist = false;
			for(Quiz quiz2 : list2){
				if(quiz1.getQuizName().equals(quiz2.getQuizName())){
					exist = true;
					break;
				}
			}
			if(!exist) list2.add(quiz1);
		}
		// sort list
		if(sortMethod == SORT_BY_RELATIVITY) return list2;
		sortQuizList(list2, sortMethod);
		// return sublist of the first numEntries elements
		return list2;
	}

	private void sortQuizList(List<Quiz> list, int sortMethod) {
		switch (sortMethod) {
		case QuizManager.SORT_BY_CREATION_TIME:
			Collections.sort(list, new Comparator<Quiz>() {
				@Override
				public int compare(Quiz o1, Quiz o2) {
					return o1.getCreateTime().compareTo(o2.getCreateTime());
				}
			});
			break;
		case QuizManager.SORT_BY_TAKEN_TIMES:
			Collections.sort(list, new Comparator<Quiz>() {
				@Override
				public int compare(Quiz o1, Quiz o2) {
					return o1.getTakenTimes() - o2.getTakenTimes();
				}
			});
			break;
		}
	}

}
