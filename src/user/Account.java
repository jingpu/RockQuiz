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

	public boolean matchPwd(String password){
		return UserManager.matchAccount(userId, password);
	}
	/**Get account's basic information from userStats table 
	 * @param column:{userId, password, registrationTime, status, gender, email}
	 * see details in sum.sql
	 * **/
	public String getInfo(String column){
		return UserManager.getAccountInfo(userId, column);
	}
	
	/**Edit account's basic information in userStats table 
	 * @param column:{"password", "status", "gender", "email"}
	 * userId and registrationTime cannot be modified.
	 * @param content
	 * 
	 * see details in sum.sql
	 * **/
	public void editInfo(String column, String content){
		UserManager.setAccountInfo(userId, column, content);
	}
	
	@Override
	/**Add new taken quiz's information into userId_history table 
	 * @param quizName
	 * @param quizId
	 * see details about format in sum.sql
	 * **/
	public void addQuizTaken(String quizName, String quizId) {
		// TODO Auto-generated method stub
		UserManager.addQuizTaken(userId, quizName, quizId);
		
	}
	
	@Override
	/**Add new created quiz's information into userId_history table 
	 * @param quizName
	 * see details about format in sum.sql
	 * **/
	public void addQuizCreated(String quizName) {
		// TODO Auto-generated method stub
		UserManager.addQuizCreated(userId, quizName);
		
	}
	
	@Override
	public void addAchievement(String achieveId, String quizName) {
		// TODO Auto-generated method stub
		UserManager.addAchievement(userId, achieveId, quizName);
	}
	
	public boolean containsAchievement(String achieveId) {
		// TODO Auto-generated method stub
		return UserManager.containsAchievement(userId, achieveId);
	}
	
	/**Get certain number achievements list in desc time order
	 * If number == -1, take out all pieces.
	 * @return achievement List<String>
	 * **/
	public List<Activity> getAchievements(int number){
		return UserManager.getAchievements(userId, number);
	}
	
	/**Get certain number taken quizzes list in desc time order
	 * If number == -1, take out all pieces.
	 * @return taken quizzes List<String>
	 * **/
	public List<Activity> getQuizTaken(int number){
		return UserManager.getQuizTaken(userId, number);
	}
	
	/**Get certain number created quizzes list in desc time order
	 * If number == -1, take out all pieces.
	 * @return created quizzes List<String>
	 * **/
	public List<Activity> getQuizCreated(int number){
		return UserManager.getQuizCreated(userId, number);
	}
	
	/**Count the number of certain type of activities in userId_history table.
	 * @param userId
	 * @param type - type of activities:
	 * 		{"a"(achievement), "t"(quiz taken), "c"(quiz created)}
	 * @return count
	 * **/
	public int countHistory(String type){
		return UserManager.countHistory(userId, type);
	}
	
	/**Send message, and display it in inbox/sent.
	 * @param message
	 * @return true if sent successfully
	 * **/
	public boolean sendMessage(Message msg){
		return UserManager.sendMsg(msg);
	}
	
	/**Send challenge message, and display it in inbox/sent.
	 * @param to - the user you want to challenge
	 * @param content - the content of the challenge letter
	 * @return true if sent successfully
	 * **/
	public boolean sendChallengeMessage(String to, String quiz, String content){
		return UserManager.sendMsg(new Message(userId, to, "c", quiz, content));
	}
	
	/**Get the content of a message without activate "ifRead" flag.
	 * @param box:{"inbox","sent"}
	 * @param msgCode - SHA encrypted code of this message
	 * @return message
	 * **/
	public Message getMessage(String box, String msgCode){
		return UserManager.getMsg(userId, box, msgCode);
	}
	
	/**Read message, and activate "ifRead" flag.
	 * @param box:{"inbox","sent"}
	 * @param msgCode - SHA encrypted code of this message
	 * @return message
	 * **/
	public Message readMessage(String box, String msgCode){
		return UserManager.readMsg(userId, box, msgCode);
	}
	
	/**Delete message.
	 * @param box:{"inbox","sent"}
	 * @param msgCode - SHA encrypted code of this message
	 * **/
	public void deleteMessage(String box, String msgCode){
		UserManager.deleteMsg(userId, box, msgCode);
	}
	
	/**Get all the messages in the inbox.
	 * @return a list of message's encrypted code
	 * **/
	public List<String> getMessageInbox(){
		return UserManager.getMessagesInbox(userId);
	}

	/**Get all the messages in the sent box.
	 * @return a list of message's encrypted code
	 * **/
	public List<String> getMessageSent(){
		return UserManager.getMessagesSent(userId);
	}
	
	/**Get all unread messages in the inbox.
	 * @return a list of message's encrypted code
	 * **/
	public List<String> getUnreadMessage(){
		return UserManager.getUnreadMessages(userId);
	}
	
	public List<String> getFriendsList(){
		return UserManager.getFriendsList(userId);
	}
	
	public List<String> getFriendRequests(){
		return UserManager.getUnconfirmedFriendsList(userId);
	}
	
	public List<Activity> getRecentActivity(int number){
		return UserManager.getRecentActivity(userId, number);
	}

	/**
	 * @param number - the maximum number of pieces of news that a friend should contribute
	 * **/
	public List<Activity> getFriendsRecentActivity(int number){
		List<String> friends = getFriendsList();
		List<Activity> friendsActivity = new LinkedList<Activity>();
		for(String friend : friends){
			Account friendAcc = new Account(friend);
			friendsActivity.addAll(friendAcc.getRecentActivity(number));
		}
		Collections.sort(friendsActivity, new Comparator<Activity>() {
			public int compare(Activity a, Activity b) {
				return (b.time.compareTo(a.time));
			}
		});
		return friendsActivity;
	}
	
	public String seeFriendStatus(String other){
		return UserManager.seeFriendStatus(this.userId, other);
	}
	
	public void requestFriend(String other){
		UserManager.requestFriend(this.userId, other);
	}
	
	public void confirmFriend(String other){
		UserManager.confirmFriend(this.userId, other);
	}
	
	public void removeFriend(String other){
		UserManager.removeFriend(this.userId, other);
	}
	
	public Announce getLatestAnnounce(){
		return UserManager.getLatestAnnounce();
	}
	
	public List<Announce> getAllAnnounce(){
		return UserManager.getAllAnnounce();
	}
}
