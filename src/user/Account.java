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
	
	/**Add new achievemetn into userId_history table 
	 * @param name
	 * see details about format in sum.sql
	 * **/
	@Override
	public void addAchievement(String name) {
		// TODO Auto-generated method stub
		UserManager.addAchievement(userId, name);
	}
	
	/**Get achievements list in desc time order
	 * @return achievement List<String>
	 * **/
	public List<Activity> getAchievements(){
		return UserManager.getAchievements(userId);
	}
	
	/**Get taken quizzes list in desc time order
	 * @return taken quizzes List<String>
	 * **/
	public List<Activity> getQuizTaken(){
		return UserManager.getQuizTaken(userId);
	}
	
	/**Get created quizzes list in desc time order
	 * @return created quizzes List<String>
	 * **/
	public List<Activity> getQuizCreated(){
		return UserManager.getQuizCreated(userId);
	}
	
	/**Count the number of certain type of activities in userId_history table.
	 * @param userId
	 * @param type - type of activities:
	 * 		{"a"(achievement), "t"(quiz taken), "c"(quiz created)}
	 * @return count
	 * **/
	public int countHistory(String userId, String type){
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
	public boolean sendChallengeMessage(String to, String content){
		return UserManager.sendMsg(new Message(userId, to, "c", content));
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
