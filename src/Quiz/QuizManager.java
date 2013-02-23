package quiz;

import java.util.List;

public interface QuizManager {
	
	//Summary page
	public  List<String> getPopularQuiz();
	public  List<String> getRecentCreateQuiz();
	
	//
	public Quiz getQuiz(String name);
	
	
}
