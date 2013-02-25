/**
 * 
 */
package user;

import java.util.List;

/**
 * @author youyuan
 *
 */
public class Account implements User{
	private String userId;
	
	public Account(String userId){
		this.userId = userId;
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
	
	//comment
}
