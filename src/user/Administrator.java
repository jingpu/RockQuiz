/**
 * 
 */
package user;

import quiz.MyQuiz;
import quiz.MyQuizManager;
import quiz.Quiz;
import quiz.QuizManager;

/**
 * @author youyuan
 *
 */
public class Administrator extends Account implements User{
	public Administrator(String userId){
		super(userId);
		// TODO Auto-generated constructor stub
	}

	public void deleteAccount(String userId) {
		if(UserManager.deleteAccount(userId)) return;
		System.out.println("Deleting Account Failed");
	}
	
	public boolean canFindQuiz(String quizName){
		QuizManager man = new MyQuizManager();
		if(man.getQuiz(quizName) == null) return false;
		return true;
	}
	public void deleteQuiz(String quizName) {
		QuizManager man = new MyQuizManager();
		man.deleteQuiz(quizName);
	}
	
	public void clearQuizHistory(String quizName) {
		Quiz quiz = new MyQuiz(quizName);
		quiz.clearQuizEvents();
	}
	
	public void setAnnouncement(String annoucement){
		UserManager.setAnnouncement(annoucement, this.userId);
	}
	
	public void setStatus(String userId, String status){
		UserManager.setAccountInfo(userId, "status", status);
	}
}
