/**
 * 
 */
package user;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author youyuan
 *
 */
public class Account implements User{
	public final String userId;
	
	public Account(String userId){
		this.userId = userId;
	}

	public String getInfo(String column){
		return UserManager.getAccountInfo(userId, column);
	}
	
	public void editInfo(String column, String content){
		UserManager.setAccountInfo(userId, column, content);
	}
	
	@Override
	public void addQuizTaken(String quizId) {
		// TODO Auto-generated method stub
		UserManager.addQuizTaken(userId, quizId);
		
	}
	@Override
	public void addQuizCreated(String quizName) {
		// TODO Auto-generated method stub
		UserManager.addQuizCreated(userId, quizName);
		
	}
	@Override
	public void addAchievement(String name) {
		// TODO Auto-generated method stub
		UserManager.addAchievement(userId, name);
	}
		
	public List<String> getAchievements(){
		return UserManager.getAchievements(userId);
	}
	
	public List<String> getQuizTaken(){
		return UserManager.getQuizTaken(userId);
	}
	
	public List<String> getQuizCreated(){
		return UserManager.getQuizCreated(userId);
	}
	
	public List<Message> getMessageInbox(){
		return UserManager.getMessagesInbox(userId);
	}

	public List<Message> getMessageSent(){
		return UserManager.getMessagesInbox(userId);
	}
	
	public List<Message> getUnreadMessage(){
		List<Message> msgs = getMessageInbox();
		List<Message> unreadMsgs = new LinkedList<Message>();
		for(Message msg : msgs){
			if(!msg.ifRead){
				unreadMsgs.add(msg);
			}
		}
		return unreadMsgs;
	}
	
	public List<String> getFriendsList(){
		return UserManager.getFriendsList(userId);
	}
	
	public List<Activity> getRecentActivity(){
		return UserManager.getRecentActivity(userId);
	}

	public List<Activity> getFriendsRecentActivity(){
		List<String> friends = getFriendsList();
		List<Activity> friendsActivity = new LinkedList<Activity>();
		for(String friend : friends){
			Account friendAcc = new Account(friend);
			friendsActivity.addAll(friendAcc.getRecentActivity());
		}
		Collections.sort(friendsActivity, new Comparator<Activity>() {
			public int compare(Activity a, Activity b) {
				return (a.time.compareTo(b.time));
			}
		});
		return friendsActivity;
	}
}
