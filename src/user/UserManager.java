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

	// account management
	/** Method addNewAccount is to add a new user tuple into userstats.sql and create 
	 * all second floor tables of this user.
	 * @param userId - the unique ID of one user
	 * @param password - the encrypted password
	 * @param status - the right of the user: (u)ser/(a)dmin
	 * @return true if this account has been added to the database successfully.
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
					+ userId + "\",\"" + password + "\",now(),\"" + status + "\")");
			stmt.executeUpdate("CREATE TABLE " + userId + "_history( Time datetime, " +
					"Type char(1), content varchar(50));");
			stmt.executeUpdate("CREATE TABLE " + userId + "_network( userId varchar(20), " +
					"status char(1));");
			stmt.executeUpdate("CREATE TABLE " + userId + "_inbox( Time datetime, " +
					"fromUser varchar(20), Type char(1), " +
					"title varchar(50), content text, ifRead char(1) );");
			stmt.executeUpdate("CREATE TABLE " + userId + "_sent( Time datetime, " +
					"toUser varchar(20), Type char(1), title varchar(50), " +
					"content text );");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Adding new account fails.");
			close();
			return false;
		}
		close();
		return true;
	}
	
	/** Method deleteAccount is to delete a tuple in userstats.sql and delete all related 
	 * second floor tables of this user.
	 * @param userId - the unique ID of one user
	 * @return true if successfully delete it
	 * **/
	public static boolean deleteAccount(String userId){
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
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Deletion fails (1).");
			close();
			return false;
		}

		try {
			for(String attr : attributes) {
				stmt.executeUpdate("DROP TABLE IF EXISTS " + userId + "_" + attr);
			}
			stmt.executeUpdate("DELETE FROM " + userTable + " WHERE userId LIKE '" + userId + "'");
		} catch(SQLException e){
			//e.printStackTrace();
			System.out.println("Deletion fails (2).");
			close();
			return false;
		}
		close();
		return true;
	}

	/** Method getFriendsList is to return all friends of the user.
	 * @param userId - the unique ID of one user
	 * @return List<String> consisting the user's friends
	 * **/
	public static List<String> getFriendsList(String userId){
		setDriver();
		List<String> friends = new LinkedList<String>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId + "_network" +
					" WHERE status LIKE 'c'");
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

	/** Method getUnconfirmedFriendsList is to return unprocessed friend requests
	 * @param userId - the unique ID of one user
	 * @return List<String> consisting the user's unconfirmed friends
	 * **/
	public static List<String> getUnconfirmedFriendsList(String userId){
		setDriver();
		List<String> friends = new LinkedList<String>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId + "_network" +
					" WHERE status LIKE 'u'");
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
	
	/** Method requestFriend is to update database according to one friend request.
	 * @param from - the user who makes the request
	 * @param to - the user who receives the request
	 * **/
	public static void requestFriend(String from, String to){
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + to + "_network" +
					" WHERE userId LIKE '" + from + "'");
			if(!rs.next()){
				stmt.executeUpdate("INSERT INTO " + from + "_network" + " VALUES (\""
						+ to + "\",\"r\")");
				stmt.executeUpdate("INSERT INTO " + to + "_network" + " VALUES (\""
						+ from + "\",\"u\")");
				sendMsg(from, to, "r", "1", "1");  //r - friend request msg
			} else if (rs.getString("status").equals("r")){
				stmt.executeUpdate("UPDATE " + from + "_network" + " SET status='c' " +
						"WHERE userId='" + to + "'");
				stmt.executeUpdate("UPDATE " + to + "_network" + " SET status='c' " +
						"WHERE userId='" + from + "'");
				System.out.println(sendMsg(from, to, "c", "2", "2")); //c - friend confirm msg
				System.out.println(sendMsg(to, from, "c", "2", "2"));
			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Message failed.");
		}
		close();
	}

	/** Method processUnconfirmedFriend is to update database according to a friend request process.
	 * @param me - the user who process this requests
	 * @param other - the user whose request is to be processed
	 * **/
	public static void processUnconfirmedFriend(String me, String other, String decision){
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + me + "_network" +
					" WHERE userId LIKE '" + other + "'");
			if(rs.next()) {
				String status = rs.getString("status");
				if(status.equals("u") || status.equals("i")){
					if(decision.equals("c")) {
						stmt.executeUpdate("UPDATE " + other + "_network" + " SET status='c' " +
								"WHERE userId='" + me + "'");
					} 
					stmt.executeUpdate("UPDATE " + me + "_network" + " SET status='" + decision 
							+ "' WHERE userId='" + other + "'");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	/** Method removeFriend is to update database according to friendship removal
	 * @param me - user 1
	 * @param other - user 2
	 * **/
	public static void removeFriend(String me, String other){
		setDriver();
		try {
			stmt.executeUpdate("DELETE FROM " + me + "_network WHERE userId='" + other + "'");
			stmt.executeUpdate("DELETE FROM " + other + "_network WHERE userId='" + me + "'");		
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
			if(box.equals("inbox")){
				stmt.executeUpdate("UPDATE " + tableName + " SET ifRead='1' " +
						"WHERE Time='" + time + "'");
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName +
						" WHERE Time='" + time + "' AND fromUser='" + otherUser);
				rs.next();
				content = rs.getString("content");
				if(!rs.next()) System.out.println("Message Duplicates in Inbox.");
			} else if (box.equals("sent")){
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName +
						" WHERE Time='" + time + "' AND toUser='" + otherUser);
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
					+ " VALUES (now(), \"t\", \"" + quizId + "\")");
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
					+ " VALUES (now(), \"c\", \"" + quizName + "\")");
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
					+ " VALUES (now(), \"a\", \"" + name + "\")");
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}
}
