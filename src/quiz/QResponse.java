/**
 * 
 */
package quiz;

/**
 * @author yang
 *
 */
public class QResponse extends QuestionBase {

	/**
	 * @param questionType
	 * @param questionId
	 * @param typeIntro
	 * @param questionDescription
	 * @param answer
	 * @param maxScore
	 */
	public QResponse(String questionType, String questionId, String typeIntro,
			String questionDescription, String answer, String maxScore) {
		super(questionType, questionId, typeIntro, questionDescription, answer,
				maxScore);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see quiz.QuestionBase#printHTML()
	 */
	@Override
	public String printHTML() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see quiz.QuestionBase#getScore(java.lang.String)
	 */
	@Override
	public String getScore(String userInput) {
		// TODO Auto-generated method stub
		return null;
	}

}
