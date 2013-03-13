package user;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**@author youyuan
 * **/
public class UserManagerTest {
/*
	@Test
	public void addNew() {
		UserManager.deleteAll();
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a", "f", "asf@asdf.edu"), true);
		assertEquals(UserManager.addNewAccount("yy1", "dkkdkdkkd1", "u", "m", "asf@asdf.edu"), true);
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a", "f", "asf@asdf.edu"), false);
	}
	
	@Test
	public void delAcc(){
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a","f","asf@asdf.edu"), false);
		assertEquals(UserManager.addNewAccount("yy1", "dkkdkdkkd1", "f","asf@asdf.edu", "u"), false);
		assertEquals(UserManager.deleteAccount("yy"), true);
		assertEquals(UserManager.deleteAccount("yy1"), true);
		assertEquals(UserManager.deleteAccount("yy3"), false);
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd","f","asf@asdf.edu", "a"), false);
	}
	
	@Test
	public void friend(){
		UserManager.addNewAccount("a1", "dkkdkdkkd", "u", "f","asf@asdf.edu");
		UserManager.addNewAccount("a2", "dkkdkdkkd", "u", "f","asf@asdf.edu");
		UserManager.addNewAccount("a3", "dkkdkdkkd", "u", "f","asf@asdf.edu");
		UserManager.addNewAccount("a4", "dkkdkdkkd", "u", "f","asf@asdf.edu");
		UserManager.requestFriend("a1", "a2");
		UserManager.requestFriend("a2", "a3");
		UserManager.requestFriend("a2", "a1");
		UserManager.requestFriend("a4", "a1");
		UserManager.confirmFriend("a1", "a4");
		UserManager.confirmFriend("a3", "a2");
		UserManager.removeFriend("a1", "a2");
		System.out.println(UserManager.getFriendsList("a1")); //a4
		System.out.println(UserManager.getFriendsList("a2"));
		System.out.println(UserManager.getFriendsList("a3"));
		System.out.println(UserManager.getFriendsList("a4")); //a1
		List<String> msgsI = UserManager.getMessagesInbox("a1");
		List<String> msgsS = UserManager.getMessagesSent("a1");
		for(String msg : msgsI){
			System.out.println(msg.toString());
			Message mail = UserManager.readMsg("a1", "inbox", msg);
			System.out.println(mail.getTime());
			System.out.println(mail.from); //
			System.out.println(mail.type); //f r f
		}
		for(String msg : msgsS){
			System.out.println(msg.toString());
			Message mail = UserManager.readMsg("a1", "sent", msg);
			System.out.println(mail.getTime());
			System.out.println(mail.to); //
			System.out.println(mail.type); //f r f
		}
	}
	
	@Test
	public void quiz() {
		UserManager.addAchievement("a1", "level1");
		UserManager.addAchievement("a1", "level2");
		UserManager.addAchievement("a1", "level3");
		UserManager.addQuizCreated("a1", "eva");
		UserManager.addQuizCreated("a1", "gis");
		UserManager.addQuizTaken("a1", "fa", "123");
		UserManager.addQuizTaken("a1", "souleater", "123");
		UserManager.addQuizTaken("a1", "akira", "1232");
		UserManager.addQuizTaken("a1", "clannad", "123");
		assertEquals(3, UserManager.countHistory("a1", "a"));
		assertEquals(2, UserManager.countHistory("a1", "c"));
		assertEquals(4, UserManager.countHistory("a1", "t"));
	}
	*/
	@Test
	public void admin(){
		UserManager.deleteAll();
		UserManager.addNewAccount("yy", "123", "s", "f", "asf@asdf.edu", "Science#Engineering");
		UserManager.addNewAccount("y1", "123", "u", "f", "asf@asdf.edu", "Science#Engineering");
		UserManager.addNewAccount("hj", "123", "s", "f", "asf@asdf.edu", "Science#Engineering");
		UserManager.addNewAccount("zy", "123", "s", "m", "asf@asdf.edu", "Science#Engineering");
		UserManager.addNewAccount("pj", "123", "s", "m", "asf@asdf.edu", "Science#Engineering");
	}
	
	@Test
	public void charProcess(){
		String admin="admin";
		String announce = "\"test\"";
		UserManager.setAnnouncement(announce, admin);
		Announce ann = UserManager.getLatestAnnounce();
		UserManager.deleteAnnouncement(ann.getTime(), ann.getAdmin());
		UserManager.addAchievement("yy", "a2", "imm_test");
	}
}
