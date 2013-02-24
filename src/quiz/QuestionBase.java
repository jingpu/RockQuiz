package quiz;


public abstract class QuestionBase {
	protected final String questionType;
	protected final String questionId;
	protected final String typeIntro;
	protected final String questionDescription;
	protected final String answer;
	protected final String maxScore;
	
	

	public QuestionBase(String questionType, String questionId,
			String typeIntro, String questionDescription, String answer,
			String maxScore) {
		super();
		this.questionType = questionType;
		this.questionId = questionId;
		this.typeIntro = typeIntro;
		this.questionDescription = questionDescription;
		this.answer = answer;
		this.maxScore = maxScore;
	}
	
	public static QuestionBase getQuestion(String questionType, String questionId){
		//switch questionType
		// case A: call questionAConstructor();
		//return QuestionBase   //factory?/
		switch(questionType) {
			case A: return new typeA(questionId);
			case B: return new typeB(questionId);
		}
	}

	//when clicking submit
	public void saveToDatabase(){
	}
	
	
	public abstract String printHTML();

    
	
	public abstract String getScore(String userInput);
	
	
	public String getMaxScore() {
		return maxScore;
	}
	
	public String getQuestionType() {
		return questionType;
	}
	public String getQuestionId() {
		return questionId;
	}
	public String getTypeIntro() {
		return typeIntro;
	}
	public String getQuestionDescription() {
		return questionDescription;
	}
	public String getAnswer() {
		return answer;
	}
	
	
	
	
	
}
