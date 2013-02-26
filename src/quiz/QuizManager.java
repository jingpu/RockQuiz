package quiz;

import java.util.List;

public interface QuizManager {

	// Summary page
	public List<String> getPopularQuiz();

	public List<String> getRecentCreateQuiz();

	// interface needed by both administrator and users
	public Quiz getQuiz(String name);

}
