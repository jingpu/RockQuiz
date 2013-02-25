package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/** author: youyuan
 *  A UserManager implemented based on interface User.
 *  Its mission is to modify/update users' information 
 *  in database. 
 * **/
public class UserManager{
	private static final String MYSQL_USERNAME = "ccs108youyuan";
	private static final String MYSQL_PASSWORD = "ooreoquu";
	private static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	private static final String MYSQL_DATABASE_NAME = "c_cs108_youyuan";

	private static final String userTable = "userstats";
	private static Connection con;
	private static Statement stmt;

	private static final List<String> attributes = new LinkedList<String>(
			Arrays.asList("history", "network", "inbox", "sent"));

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

	public static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// account management
	/** Method addNewAccount is to add a new user tuple into userstats.sql and create all second floor tables
	 * of this user.
	 * @param userId - the unique ID of one user
	 * @param password - the encrypted password
	 * @param status - the right of the user: "norm"/"admin"/"del"
	 * @return Return true if this account has been added to the database successfully.
	 * **/
	public static boolean addNewAccount(String userId, String password, String status){
		setDriver();
		// information invalid - too short
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userTable + " " +
					"WHERE userId LIKE '" + userId + "'");
			if(rs.next()) {
				close();
				return false;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				stmt.executeUpdate("CREATE TABLE " + userTable + " ( " +
						"userId varchar(20), password varchar(50), " +
						"registrationTime datetime, status char(1));");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("Adding new table fails.");
				close();
				return false;
			}
		}

		// userId available
		try {
			stmt.executeUpdate("INSERT INTO " + userTable + " VALUES (\""
					+ userId + "\",\"" + password + "\",\"now()\",\"" + status + ")");
			stmt.executeUpdate("CREATE TABLE " + userId + "_history( time datetime, " +
					"type char(1), content varchar(50));");
			stmt.executeUpdate("CREATE TABLE " + userId + "_network( userId varchar(20), " +
					"status char(1));");
			stmt.executeUpdate("CREATE TABLE " + userId + "_inbox( time datetime, " +
					"from varchar(20), type char(1), " +
					"title varchar(50) + content text, read char(1) );");
			stmt.executeUpdate("CREATE TABLE " + userId + "_sent( time datetime, " +
					"to varchar(20), type varchar(3), " +
					"content text );");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Adding new account fails.");
			close();
			return false;
		}
		close();
		return true;
	}

	public boolean deleteAccount(String userId){
		setDriver();
		// check whether the userId exists
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userTable + " " +
					"WHERE userId LIKE '" + userId + "'");
			if(!rs.next()) {
				System.out.println("This account doesn't exist.");
				close();
				return false;
			}
			rs.close();
		} catch (SQLException ignore) {
			System.out.println("Deletion fails (1).");
			close();
			return false;
		}

		try {
			for(String attr : attributes) {
				stmt.executeUpdate("DROP TABLE IF EXISTS " + userId + "_" + attr);
			}
			stmt.executeUpdate("DELETE FROM userTable WHERE userId=" + userId );
		} catch(SQLException ignore){
			System.out.println("Deletion fails (2).");
			close();
			return false;
		}
		close();
		return true;
	}

	// networking
	public static List<String> getFriendsList(String userId){
		setDriver();
		List<String> friends = new LinkedList<String>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId + "_network" +
					" WHERE status LIKE 'cnf'");
			while(rs.next()){
				friends.add(rs.getString("userId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return friends;
	}

	public static List<String> getUnconfirmedFriendsList(String userId){
		setDriver();
		List<String> friends = new LinkedList<String>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId + "_network" +
					" WHERE status LIKE 'unc'");
			while(rs.next()){
				friends.add(rs.getString("userId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return friends;
	}

	public static void requestFriend(String from, String to){
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + to + "_network" +
					" WHERE userId LIKE '" + from + "'");
			if(!rs.next()){
				stmt.executeUpdate("INSERT INTO " + from + "_network" + " VALUES (\""
						+ to + "\",\"req\")");
				stmt.executeUpdate("INSERT INTO " + to + "_network" + " VALUES (\""
						+ from + "\",\"unc\")");
				sendMsg(from, to, "frq", "", "");  //frq - friend request msg
			} else if (rs.getString("status") == "req"){
				stmt.executeUpdate("UPDATE " + from + "_network" + " SET status='cnf' " +
						"WHERE userId='" + to + "'");
				stmt.executeUpdate("UPDATE " + to + "_network" + " SET status='cnf' " +
						"WHERE userId='" + from + "'");
				sendMsg(from, to, "fco", "", ""); //frq - friend confirm msg
				sendMsg(to, from, "fco", "", "");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	public static void processUnconfirmedFriend(String me, String other, String decision){
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + me + "_network" +
					" WHERE status LIKE 'unc'");
			while(rs.next()){
				rs.updateString("status", decision);
				if(decision == "cnf") {
					stmt.executeUpdate("UPDATE " + other + "_network" + " SET status='cnf' " +
							"WHERE userId='" + me + "'");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	public static String sendMsg(String from, String to, String type, String title, String content){
		setDriver();
		Date now = new Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(now);

		try {
			stmt.executeUpdate("INSERT INTO " + from + "_sent" + " VALUES (\"" + currentTime + "\", \""
					+ to + "\",\"" + type + "\",\"" + title + "\",\"" + content + "\")");

			stmt.executeUpdate("INSERT INTO " + to + "_inbox" + " VALUES (\""+ currentTime + "\", \""
					+ from + "\",\"" + type + "\",\"" + title + "\",\"" + content + "\",\"0\")");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return currentTime;
	}

	public static String readMsg(String userId, String box, String otherUser, String time){
		setDriver();
		String tableName = userId + "_" + box;
		String content = "";
		try {
			if(box == "inbox"){
				stmt.executeUpdate("UPDATE " + tableName + " SET read='1' " +
						"WHERE time='" + time + "'");
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName +
						" WHERE time='" + time + "' AND from='" + otherUser);
				rs.next();
				content = rs.getString("content");
				if(!rs.next()) System.out.println("Message Duplicates in Inbox.");
			} else if (box == "sent"){
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName +
						" WHERE time='" + time + "' AND to='" + otherUser);
				rs.next();
				content = rs.getString("content");
				if(!rs.next()) System.out.println("Message Duplicates in Sent.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return content;
	}
	
	public static boolean addQuizTaken(String userId, String quizId){
		setDriver();
		try{
			stmt.executeUpdate("INSERT INTO " + userId + "_history" 
		+ " VALUES (\"now()\", \"t\", \"" + quizId + "\")");
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}
	
	public static boolean addQuizCreated(String userId, String quizName){
		setDriver();
		try{
			stmt.executeUpdate("INSERT INTO " + userId + "_history" 
		+ " VALUES (\"now()\", \"c\", \"" + quizName + "\")");
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}
	public static boolean addAchievement(String userId, String name){
		setDriver();
		try{
			stmt.executeUpdate("INSERT INTO " + userId + "_history" 
		+ " VALUES (\"now()\", \"a\", \"" + name + "\")");
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}
}
