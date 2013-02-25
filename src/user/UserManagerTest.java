package user;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class UserManagerTest {

	@Test
	public void addNew() {
		UserManager.deleteAll();
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a", "f", "asf@asdf.edu"), true);
		assertEquals(UserManager.addNewAccount("yy1", "dkkdkdkkd1", "u", "m", "asf@asdf.edu"), true);
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a", "f", "asf@asdf.edu"), false);
	}
	
	@Test
	public void del(){
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
		UserManager.processUnconfirmedFriend("a3", "a2", "i");
		UserManager.processUnconfirmedFriend("a1", "a4", "f");
		UserManager.removeFriend("a1", "a2");
		System.out.println(UserManager.getFriendsList("a1")); //a4
		System.out.println(UserManager.getFriendsList("a2"));
		System.out.println(UserManager.getFriendsList("a3"));
		System.out.println(UserManager.getFriendsList("a4")); //a1
		List<Message> msgs = UserManager.getMessagesInbox("a1");
		for(Message msg : msgs){
			System.out.println(msg.time);
			System.out.println(msg.from); //
			System.out.println(msg.type); //f r f
		}
	}
	
	
}
