package user;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserManagerTest {

	@Test
	public void addNew() {
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a"), false);
		assertEquals(UserManager.addNewAccount("yy1", "dkkdkdkkd1", "u"), true);
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a"), false);
	}
	
	@Test
	public void del(){
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a"), false);
		assertEquals(UserManager.addNewAccount("yy1", "dkkdkdkkd1", "u"), false);
		assertEquals(UserManager.deleteAccount("yy"), true);
		assertEquals(UserManager.deleteAccount("yy1"), true);
		assertEquals(UserManager.deleteAccount("yy3"), false);
		assertEquals(UserManager.addNewAccount("yy", "dkkdkdkkd", "a"), true);
	}
	
	@Test
	public void friend(){
		UserManager.deleteAccount("a1");
		UserManager.deleteAccount("a2");
		UserManager.deleteAccount("a3");
		UserManager.addNewAccount("a1", "dkkdkdkkd", "u");
		UserManager.addNewAccount("a2", "dkkdkdkkd", "u");
		UserManager.addNewAccount("a3", "dkkdkdkkd", "u");
		UserManager.requestFriend("a1", "a2");
		UserManager.requestFriend("a2", "a3");
		UserManager.requestFriend("a2", "a1");
		UserManager.processUnconfirmedFriend("a3", "a2", "i");
		UserManager.removeFriend("a1", "a2");
	}
}
