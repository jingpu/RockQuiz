package quiz;

import java.util.List;

import user.Account;

public interface QuizManager {
	
	//Summary page
	public  List<String> getPopularQuiz();
	public  List<String> getRecentCreateQuiz();
	
	//interface needed by both administrator and users 
	public Quiz getQuiz(String name);
	public List<Quiz> getQuizList(String filter);
	
	//interface needed by admistrator
	public List<QuizLog> getHistory(String quizName); 
	public Account getAccount(String userId);
	
	
}
