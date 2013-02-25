package user;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserManagerTest {

	@Test
	public void addNew() {
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a", "f", "asf@asdf.edu"), false);
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
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd","f","asf@asdf.edu", "a"), true);
	}
	
	@Test
	public void friend(){
		UserManager.deleteAccount("a1");
		UserManager.deleteAccount("a2");
		UserManager.deleteAccount("a3");
		UserManager.addNewAccount("a1", "dkkdkdkkd", "u", "f","asf@asdf.edu");
		UserManager.addNewAccount("a2", "dkkdkdkkd", "u", "f","asf@asdf.edu");
		UserManager.addNewAccount("a3", "dkkdkdkkd", "u", "f","asf@asdf.edu");
		UserManager.requestFriend("a1", "a2");
		UserManager.requestFriend("a2", "a3");
		UserManager.requestFriend("a2", "a1");
		UserManager.processUnconfirmedFriend("a3", "a2", "i");
		UserManager.removeFriend("a1", "a2");
	}
}
