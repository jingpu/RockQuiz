package quiz;

import java.util.List;

import database.MyDB;
import java.sql.Connection;
public class MyQuiz implements Quiz{

	private final String quizName; 
	private final String creatorId;
	private final String totalScore;
	private final String quizDescription;
	private final List<String> tags; 
	private final boolean canPractice;
	private final boolean isRandom;
	private final boolean isOnePage;
	private final boolean isImmCorrection; //jvm optimization
	private final List<QuestionBase> questionList;
	
	//constructor for a quiz from creator	
	public MyQuiz(String quizName, String creatorId, String totalScore,
			String quizDescription, List<String> tags, boolean canPractice,
			boolean isRandom, boolean isOnePage, boolean isImmCorrection,
			List<QuestionBase> questionList) {
		super();
		this.quizName = quizName;
		this.creatorId = creatorId;
		this.totalScore = totalScore;
		this.quizDescription = quizDescription;
		this.tags = tags;
		this.canPractice = canPractice;
		this.isRandom = isRandom;
		this.isOnePage = isOnePage;
		this.isImmCorrection = isImmCorrection;
		this.questionList = questionList;
	}
	
	
	
	//constructor for a quiz from database
	public MyQuiz(String quizName) {
		//TODO
		Connection con = MyDB.getConnection();
		
	}
	
	
	
	public void saveToDatabase() {
		
	}
	
	@Override
	public String getSummaryPage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getScore(String quizId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMaxScore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTimeElapsed(String quizId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
