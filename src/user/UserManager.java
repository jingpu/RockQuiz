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
package user;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** @author: youyuan
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
	private static final String announceTable = "announcements";
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

	public static void setAnnouncement(String announce, String admin){
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE '" + announceTable + "'");
			if(!rs.next()){
				stmt.executeUpdate("CREATE TABLE "+ announceTable +" (Time datetime, content text, admin varchar(20))");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.executeUpdate("INSERT INTO "+announceTable+" VALUES (now(),'"+ announce +"','"+ admin +"')");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	public static Announce getLatestAnnounce(){
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " 
					+ announceTable + " ORDER BY Time DESC");
			if(rs.next()){
				String time = rs.getString("Time");
				Announce ann = new Announce(time.substring(0,time.length()-2), rs.getString("content"), 
						rs.getString("admin"));
				close();
				return ann;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println("exception");
		}
		close();
		return null;
	}

	public static List<Announce> getAllAnnounce(){
		setDriver();
		List<Announce> annList = new LinkedList<Announce>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " 
					+ announceTable + " ORDER BY Time DESC");
			while(rs.next()){
				Announce ann = new Announce(rs.getString("Time"), rs.getString("content"), 
						rs.getString("admin"));
				annList.add(ann);
			}
			close();
			return annList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		close();
		return null;
	}

	public static void deleteAnnouncement(String time, String admin){
		setDriver();
		try {
			stmt.executeUpdate("DELETE FROM " + announceTable 
					+ " WHERE Time LIKE '" + time + "' AND admin LIKE '" + admin + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	public static void deleteAllAnnouncement(){
		setDriver();
		try {
			stmt.executeUpdate("DROP TABLE IF EXISTS " + announceTable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	
	// account management

	/** Method addNewAccount is to add a new user tuple into userstats.sql and create 
	 * all second floor tables of this user.
	 * @param userId - the unique ID of one user
	 * @param password - the encrypted password
	 * @param status - the right of the user: (u)ser/(a)dmin
	 * @return true if this account has been added to the database successfully.
	 * **/
	public static boolean addNewAccount(String userId, String password, 
			String status, String gender, String email, String category){
		if(userId.equals("guest")) return false;
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
						"userId varchar(20), password varchar(40), " +
						"registrationTime datetime, status char(1), gender char(1), email char(50), category text, privacy char(1));");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//System.out.println("Adding new table fails.");
				close();
				return false;
			}
		}

		// userId available


		String hashValue = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			try {
				md.update(password.getBytes());
				MessageDigest tc = (MessageDigest)md.clone();
				byte[] bytes = tc.digest();
				StringBuffer buff = new StringBuffer();
				for (int i=0; i<bytes.length; i++) {
					int val = bytes[i];
					val = val & 0xff;  // remove higher bits, sign
					if (val<16) buff.append('0'); // leading 0
					buff.append(Integer.toString(val, 16));
				}			
				hashValue = buff.toString();
				md.reset();
			} catch (CloneNotSupportedException cnse) {
				try {
					throw new DigestException("couldn't make digest of partial content");
				} catch (DigestException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		userId = userId.toLowerCase();
		try {
			stmt.executeUpdate("INSERT INTO " + userTable + " VALUES ('"
					+ userId + "','" + hashValue + "',now(),'" + status + "','" 
					+ gender + "','" + email + "','" + category + "','0')");
			stmt.executeUpdate("CREATE TABLE " + userId + "_history( Time datetime, " +
					"Type char(33), content varchar(32));");
			stmt.executeUpdate("CREATE TABLE " + userId + "_network( userId varchar(20), " +
					"status char(1));");
			stmt.executeUpdate("CREATE TABLE " + userId + "_inbox( code char(40), Time text, " +
					"fromUser varchar(20), Type char(1), title text, " +
					"content text, ifRead char(1) );");
			stmt.executeUpdate("CREATE TABLE " + userId + "_sent( code char(40), Time text, " +
					"toUser varchar(20), Type char(1), title text, " +
					"content text );");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.out.println("Adding new account fails.");
			close();
			return false;
		}
		close();
		return true;
	}

	public static boolean alreadyExist(String userId){
		if(userId.equals("guest")) return true;
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userTable + " " +
					"WHERE userId = '" + userId + "'");
			if(rs.next()) {
				close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return false;
	}

	public static boolean matchAccount(String userId, String password) {		
		String hashValue = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			try {
				md.update(password.getBytes());
				MessageDigest tc = (MessageDigest)md.clone();
				byte[] bytes = tc.digest();
				StringBuffer buff = new StringBuffer();
				for (int i=0; i<bytes.length; i++) {
					int val = bytes[i];
					val = val & 0xff;  // remove higher bits, sign
					if (val<16) buff.append('0'); // leading 0
					buff.append(Integer.toString(val, 16));
				}			
				hashValue = buff.toString();
				md.reset();
			} catch (CloneNotSupportedException cnse) {
				try {
					throw new DigestException("couldn't make digest of partial content");
				} catch (DigestException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//System.out.println("translate pwd");
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userTable + " " +
					"WHERE userId = '" + userId + "' AND password = '" + hashValue + "'");
			if(!rs.next()) {
				close();
				//System.out.println("not found");
				return false;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
				//System.out.println("This account doesn't exist.");
				close();
				return false;
			}
			rs.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			//System.out.println("Deletion fails (1).");
			close();
			return false;
		}
		userId = userId.toLowerCase();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId + "_network");
			List<String> friends = new LinkedList<String>();
			while(rs.next()){
				friends.add(rs.getString("userId"));
			}
			for(String friend : friends){
				removeFriend(friend, userId);
			}
			setDriver();
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

	public static boolean deleteAll(){
		setDriver();
		try{
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userTable);
			while(rs.next()){
				deleteAccount(rs.getString("userId"));
			}
			stmt.executeUpdate("DROP TABLE IF EXISTS " + userTable);
			close();
			return true;
		} catch(SQLException e){
			//e.printStackTrace();
			//System.out.println("Deletion fails.");
			close();
			return false;
		}
	}

	public static String getAccountInfo(String userId, String column){
		String str = "";
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * from " 
					+ userTable + " WHERE userId LIKE '" + userId + "'");
			if(rs.next() && rs.getString(column) != null){
				str = rs.getString(column);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return str;
	}

	public static void setAccountInfo(String userId, String column, String content){
		if(column.equals("userId") || column.equals("registrationTime")) {
			//System.out.println(column + " is not allowed to be modified.");
			return;
		}
		userId = userId.toLowerCase();
		if(column.equals("password")) {
			String hashValue = "";
			try {
				MessageDigest md = MessageDigest.getInstance("SHA");
				try {
					md.update(content.getBytes());
					MessageDigest tc = (MessageDigest)md.clone();
					byte[] bytes = tc.digest();
					StringBuffer buff = new StringBuffer();
					for (int i=0; i<bytes.length; i++) {
						int val = bytes[i];
						val = val & 0xff;  // remove higher bits, sign
						if (val<16) buff.append('0'); // leading 0
						buff.append(Integer.toString(val, 16));
					}			
					hashValue = buff.toString();
					md.reset();
				} catch (CloneNotSupportedException cnse) {
					try {
						throw new DigestException("couldn't make digest of partial content");
					} catch (DigestException e) {
						e.printStackTrace();
					}
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			content = hashValue;
		}

		setDriver();
		try {
			stmt.executeUpdate("UPDATE " + userTable + " SET " + column +"=" + "'" + content 
					+ "' WHERE userId='" + userId + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	public static List<String> getUserList(String query, String fromTime, String toTime, boolean orderByTime){
		List<String> userList = new LinkedList<String>();
		setDriver();
		toTime += " 23:59:59.9";
		try {
			ResultSet rs = stmt.executeQuery("SELECT * from " 
					+ userTable + " WHERE userId LIKE '%" 
					+ query + "%' AND registrationTime > '"+ fromTime 
					+"' AND registrationTime < '"+ toTime +"' ORDER BY registrationTime");
			while(rs.next()){
				userList.add(rs.getString("userId"));		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		if(!orderByTime){
			Collections.sort(userList, new Comparator<String>() {
				public int compare(String a, String b) {
					return (a.length() - b.length());
				}
			});
		} 
		return userList;
	}

	/** Method getFriendsList is to return all friends of the user.
	 * @param userId - the unique ID of one user
	 * @return List<String> consisting the user's friends
	 * **/
	public static List<String> getFriendsList(String userId){
		setDriver();
		List<String> friends = new LinkedList<String>();
		userId = userId.toLowerCase();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId + "_network" +
					" WHERE status='f'");
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
		userId = userId.toLowerCase();
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

	public static String seeFriendStatus(String me, String other){
		setDriver();
		me = me.toLowerCase();
		other = other.toLowerCase();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + me + "_network" +
					" WHERE userId LIKE '" + other + "'");
			if(!rs.next()){
				close();
				return "x";
			} else {
				String status = rs.getString("status");
				close();
				return status;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("Message failed.");
		}
		close();
		return "x";
	}

	/** Method requestFriend is to update database according to one friend request.
	 * @param from - the user who makes the request
	 * @param to - the user who receives the request
	 * **/
	public static void requestFriend(String from, String to){
		setDriver();
		from = from.toLowerCase();
		to = to.toLowerCase();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + to + "_network" +
					" WHERE userId LIKE '" + from + "'");
			if(!rs.next()){
				stmt.executeUpdate("INSERT INTO " + from + "_network" + " VALUES ('"
						+ to + "','r')");
				stmt.executeUpdate("INSERT INTO " + to + "_network" + " VALUES ('"
						+ from + "','u')");
				Message msg = new Message(from, to, "r", "", "");
				sendMsg(msg);  //r - friend request msg
				setDriver();
			} else if (rs.getString("status").equals("r")){
				stmt.executeUpdate("UPDATE " + from + "_network" + " SET status='f' " +
						"WHERE userId='" + to + "'");
				stmt.executeUpdate("UPDATE " + to + "_network" + " SET status='f' " +
						"WHERE userId='" + from + "'");
				Message msg1 = new Message(from, to, "f", "", "");
				sendMsg(msg1);
				Message msg2 = new Message(to, from, "r", "", "");
				sendMsg(msg2);
				setDriver();
			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("Message failed.");
		}
		close();
	}

	/** Method processUnconfirmedFriend is to update database according to a friend request process.
	 * @param me - the user who process this requests
	 * @param other - the user whose request is to be processed
	 * **/
	public static void confirmFriend(String me, String other){
		setDriver();
		me = me.toLowerCase();
		other = other.toLowerCase();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + me + "_network" +
					" WHERE userId LIKE '" + other + "'");
			if(rs.next() && rs.getString("status").equals("u")) {
				stmt.executeUpdate("UPDATE " + me + "_network" + " SET status='f' " +
						"WHERE userId='" + other + "'");
				stmt.executeUpdate("UPDATE " + other + "_network" + " SET status='f' " +
						"WHERE userId='" + me + "'");
				Message msg1 = new Message(me, other, "f", "", "");
				sendMsg(msg1);
				Message msg2 = new Message(other, me, "f", "", "");
				sendMsg(msg2);
			}
		}catch (SQLException e) {
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
		me = me.toLowerCase();
		other = other.toLowerCase();
		try {
			stmt.executeUpdate("DELETE FROM " + me + "_network WHERE userId='" + other + "'");
			stmt.executeUpdate("DELETE FROM " + other + "_network WHERE userId='" + me + "'");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	public static boolean sendMsg(Message msg){
		if(!alreadyExist(msg.from) || !alreadyExist(msg.to)){
			return false;
		}

		Date now = new Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String currentTime = sdf.format(now);

		String hashValue = "";
		String str = currentTime + msg.from + msg.to + msg.type + msg.title;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			try {
				md.update(str.getBytes());
				MessageDigest tc = (MessageDigest)md.clone();
				byte[] bytes = tc.digest();
				StringBuffer buff = new StringBuffer();
				for (int i=0; i<bytes.length; i++) {
					int val = bytes[i];
					val = val & 0xff;  // remove higher bits, sign
					if (val<16) buff.append('0'); // leading 0
					buff.append(Integer.toString(val, 16));
				}			
				hashValue = buff.toString();
				md.reset();
			} catch (CloneNotSupportedException cnse) {
				try {
					throw new DigestException("couldn't make digest of partial content");
				} catch (DigestException e) {
					e.printStackTrace();
				}
				return false;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}

		setDriver();
		try {
			stmt.executeUpdate("INSERT INTO " + msg.from.toLowerCase() + "_sent" + " VALUES ('" + hashValue + "','" 
					+ currentTime + "','" + msg.to + "','" + msg.type + "','" + msg.title + "','" 
					+ msg.content + "')");

			stmt.executeUpdate("INSERT INTO " + msg.to.toLowerCase() + "_inbox" + " VALUES ('"+ hashValue + "','" 
					+ currentTime + "','" + msg.from + "','" + msg.type + "','" + msg.title + "','" 
					+ msg.content + "','0')");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}

	/** @return the information of the message without reading it.
	 * **/
	public static Message getMsg(String userId, String box, String msgCode){
		userId = userId.toLowerCase();
		String boxTable = userId + "_" + box;

		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + boxTable + " WHERE code='" + msgCode + "'");
			if(rs.next()){
				Message msg = null;
				if(box.equals("inbox")){
					String title = rs.getString("title");
					String content = rs.getString("content");
					title = title == null? "" : title;
					content = content == null? "" : title;
					msg = new Message(rs.getString("fromUser"), userId, rs.getString("type"), 
							title, content);
					msg.setRead(rs.getString("ifRead").equals("1"));
				} else if(box.equals("sent")){
					msg = new Message(userId, rs.getString("toUser"), rs.getString("type"), 
							rs.getString("title"), rs.getString("content"));
				}
				msg.setTime(rs.getString("Time"));
				close();
				return msg;
			} else {
				//System.out.println("Message does not exist.");
			}
			if(rs.next()){
				//System.out.println("Message Hashcode duplicates.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return null;
	}

	/** @return the information of the message by reading it.
	 * **/
	public static Message readMsg(String userId, String box, String msgCode){
		userId = userId.toLowerCase();
		String boxTable = userId + "_" + box;
		Message msg = null;
		setDriver();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + boxTable + " WHERE code='" + msgCode + "'");
			if(rs.next()){
				if(box.equals("inbox")){
					msg = new Message(rs.getString("fromUser"), userId, rs.getString("type"), 
							rs.getString("title"), rs.getString("content"));
					msg.setRead(true);
				} else if(box.equals("sent")){
					msg = new Message(userId, rs.getString("toUser"), rs.getString("type"), 
							rs.getString("title"), rs.getString("content"));
				}
				msg.setTime(rs.getString("Time"));
			} else {
				//System.out.println("Message does not exist.");
			}
			if(rs.next()){
				//System.out.println("Message Hashcode duplicates.");
			}
			if(box.equals("inbox")){
				stmt.executeUpdate("UPDATE " + userId + "_inbox SET ifRead='1' " + "WHERE code='" 
						+ msgCode + "'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return msg;
	}

	public static void deleteMsg(String userId, String box, String msgCode){
		userId = userId.toLowerCase();
		setDriver();
		String boxTable = userId + "_" + box;
		try {
			stmt.executeUpdate("DELETE FROM " + boxTable + " WHERE code='" + msgCode + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	public static List<String> getMessagesInbox(String userId){
		userId = userId.toLowerCase();
		setDriver();
		try {
			List<String> msgs = new LinkedList<String>(); 
			ResultSet rs = stmt.executeQuery("SELECT * from " + userId + "_inbox ORDER BY Time DESC");
			while(rs.next()){
				msgs.add(rs.getString("code"));
			}
			close();
			return msgs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return null;
	}

	public static List<String> getUnreadMessages(String userId){
		userId = userId.toLowerCase();
		setDriver();
		try {
			List<String> msgs = new LinkedList<String>(); 
			ResultSet rs = stmt.executeQuery("SELECT * from " + userId + "_inbox WHERE " +
					"ifRead='0' ORDER BY Time DESC");
			while(rs.next()){
				msgs.add(rs.getString("code"));
			}
			close();
			return msgs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return null;
	}

	public static List<String> getMessagesSent(String userId){
		userId = userId.toLowerCase();
		setDriver();
		try {
			List<String> msgs = new LinkedList<String>(); 
			ResultSet rs = stmt.executeQuery("SELECT * from " + userId + "_sent ORDER BY Time DESC");
			while(rs.next()){
				msgs.add(rs.getString("code"));
			}
			close();
			return msgs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return null;
	}

	public static boolean addQuizTaken(String userId, String quizName, String quizId){
		userId = userId.toLowerCase();
		setDriver();
		try{
			stmt.executeUpdate("INSERT INTO " + userId + "_history" 
					+ " VALUES (now(), 't" + quizId + "', '" + quizName + "')");
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		}
		close();
		return true;
	}

	public static boolean addQuizCreated(String userId, String quizName){
		userId = userId.toLowerCase();
		setDriver();
		try{
			stmt.executeUpdate("INSERT INTO " + userId + "_history" 
					+ " VALUES (now(), 'c', '" + quizName + "')");
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}

	public static boolean containsAchievement(String userId, String achieveId){
		userId = userId.toLowerCase();
		setDriver();
		try{
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId + "_history" 
					+ " WHERE Type LIKE '"+ achieveId +"'");
			if(rs.next()){
				close();
				return true;
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return false;
	}

	public static boolean addAchievement(String userId, String achieveId, String quizName){
		userId = userId.toLowerCase();
		setDriver();
		try{
			stmt.executeUpdate("INSERT INTO " + userId + "_history" 
					+ " VALUES (now(), '"+ achieveId + "', '" + quizName + "')");
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}

	public static int countHistory(String userId, String type){
		setDriver();
		int count = 0;
		try{
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ userId 
					+ "_history WHERE Type LIKE '" + type + "%'");
			Set<String> strs = new HashSet<String>();
			while(rs.next()){
				String news = rs.getString("Type");
				strs.add(news);
			}
			count = strs.size();
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return count;
	}

	public static List<Activity> getAchievements(String userId, int number){
		userId = userId.toLowerCase();
		List<Activity> achieves = new LinkedList<Activity>();
		setDriver();
		try{
			ResultSet rs = stmt.executeQuery("SELECT * from " + userId 
					+ "_history WHERE Type LIKE 'a%' ORDER BY Time DESC");
			int i = 0;
			while(rs.next()){
				if(i == number) break;
				Activity act = new Activity(userId, rs.getString("Time"), 
						rs.getString("Type"), rs.getString("content"));
				achieves.add(act);
				i++;
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return achieves;
	}

	public static List<Activity> getQuizTaken(String userId, int number){
		userId = userId.toLowerCase();
		List<Activity> taken = new LinkedList<Activity>();
		setDriver();
		try{
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + userId 
					+ "_history WHERE Type LIKE 't%' ORDER BY Time DESC");
			int i = 0;
			while(rs.next()){
				if(i == number) break;
				Activity act = new Activity(userId, rs.getString("Time"), 
						rs.getString("Type"), rs.getString("content"));
				taken.add(act);
				i++;
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return taken;
	}

	public static List<Activity> getQuizCreated(String userId, int number){
		userId = userId.toLowerCase();
		List<Activity> created = new LinkedList<Activity>();
		setDriver();
		try{
			int i = 0;
			ResultSet rs = stmt.executeQuery("SELECT * from " + userId 
					+ "_history WHERE Type='c' ORDER BY Time DESC");
			while(rs.next()){
				if(i == number) break;
				Activity act = new Activity(userId, rs.getString("Time"), 
						rs.getString("Type"), rs.getString("content"));
				created.add(act);
				i++;
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return created;
	}


	public static List<Activity> getRecentActivity(String userId, int number){
		userId = userId.toLowerCase();
		List<Activity> recent = new LinkedList<Activity>();
		setDriver();
		try{
			ResultSet rs = stmt.executeQuery("SELECT * from " + userId 
					+ "_history ORDER BY Time DESC");
			int i = 0;
			while(rs.next()){
				if(i == number) break;
				Activity act = new Activity(userId, rs.getString("Time"), 
						rs.getString("Type"), rs.getString("content"));
				recent.add(act);
				i++;
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return recent;
	}
}
