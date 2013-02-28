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
	public void addQuizTaken(String quizName, String quizId) {
		// TODO Auto-generated method stub
		UserManager.addQuizTaken(userId, quizName, quizId);
		
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
	
	public List<String[]> getQuizTaken(){
		return UserManager.getQuizTaken(userId);
	}
	
	public List<String> getQuizCreated(){
		return UserManager.getQuizCreated(userId);
	}
	
	public boolean sendMessage(Message msg){
		return UserManager.sendMsg(msg);
	}
	
	public Message getMessage(String box, String msgCode){
		return UserManager.getMsg(userId, box, msgCode);
	}
	
	public Message readMessage(String box, String msgCode){
		return UserManager.readMsg(userId, box, msgCode);
	}
	
	public void deleteMessage(String box, String msgCode){
		UserManager.deleteMsg(userId, box, msgCode);
	}
	
	public List<String> getMessageInbox(){
		return UserManager.getMessagesInbox(userId);
	}

	public List<String> getMessageSent(){
		return UserManager.getMessagesSent(userId);
	}
	
	public List<String> getUnreadMessage(){
		return UserManager.getUnreadMessages(userId);
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
	
	public String seeFriendStatus(String other){
		return UserManager.seeFriendStatus(this.userId, other);
	}
}
