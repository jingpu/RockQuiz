package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class QuizSearch {
	private static final String MYSQL_USERNAME = "ccs108youyuan";
	private static final String MYSQL_PASSWORD = "ooreoquu";
	private static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	private static final String MYSQL_DATABASE_NAME = "c_cs108_youyuan";

	private static final String quizNameTable = "Global_Quiz_Info_Table";
	private static final String quizEventTable = "Question_Response_Pool";
	private static Connection con;
	private static Statement stmt;
	
	/** Set DriverManager
	 * **/
	private static void setDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + MYSQL_DATABASE_SERVER + "/" + MYSQL_DATABASE_NAME;
			con = DriverManager.getConnection(url, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("CS108 student: Update the MySQL constants to correct values!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("CS108 student: Add the MySQL jar file to your build path!");
		}
	}

	/** Close connection
	 * **/
	public static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** @param query - part of quizName to search
	 * **/
	public static List<MyQuiz> getQuizInfoList(String query){
		List<MyQuiz> quizList = new LinkedList<MyQuiz>();
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * from " 
					+ quizNameTable + " WHERE quizName='%" + query + "%'");
			while(rs.next()){
				MyQuiz queryQuiz = new MyQuiz(rs.getString("quizName"));	
				quizList.add(queryQuiz);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return quizList;
	}
	
	public class myQuizEvent{
		
		private final String quizId;
		public myQuizEvent(String quizId){
			this.quizId = quizId;
		}
	}
	
	/** @param query - quizId to search
	 * **/
	public static List<MyQuizEvent> getQuizEventList(String query){
		List<myQuizEvent> quizEventList = new LinkedList<myQuizEvent>();
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * from " 
					+ quizEventTable + " WHERE quizId='" + query + "'");
			while(rs.next()){
				myQuizEvent queryQuizEvent = new myQuizEvent(rs.getString("quizId"));	
				quizEventList.add(queryQuizEvent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return quizEventList;
	}

}



