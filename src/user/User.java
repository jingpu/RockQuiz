package user;

import java.util.List;

/**@author youyuan
 * **/
public interface User {
	
	public String getInfo(String column);
	public void editInfo(String column, String content);
	public List<String> getAchievements();
	public List<String[]> getQuizTaken();
	public List<String> getQuizCreated();
	public int countHistory(String userId, String type);
	public boolean sendMessage(Message msg);
	public boolean sendChallengeMessage(String to, String content);
	public Message getMessage(String box, String msgCode);
	public Message readMessage(String box, String msgCode);
	public void deleteMessage(String box, String msgCode);
	public List<String> getMessageInbox();
	public List<String> getMessageSent();
	public List<String> getUnreadMessage();
	public List<String> getFriendsList();
	public List<Activity> getRecentActivity();
	public List<Activity> getFriendsRecentActivity();
	public String seeFriendStatus(String other);
	public void requestFriend(String other);
	public void confirmFriend(String other);
	public void removeFriend(String other);
	public void addQuizTaken(String quizName, String quizId);
	public void addQuizCreated(String quizName);
	public void addAchievement(String name);
	
}
