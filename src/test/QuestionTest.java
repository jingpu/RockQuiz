/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import quiz.FillInBlank;
import quiz.MultiChoice;
import quiz.PResponse;
import quiz.QResponse;
import quiz.QuestionBase;

//JUnit test is multi

/**
 * @author yang
 *
 */
public class QuestionTest {
	@Test
	public void testCreateQuestion() {
		String[] questionTypes = QuestionBase.getQuestionTypes();
		List<String> choices = new ArrayList<String>();
		choices.add("aaaaa");
		choices.add("bbbbb");
		choices.add("ccccc");
		choices.add("ddddd");
		
		String questionType = questionTypes[0];
		String creatorId = "Patrick";
		String questionDescription = "aaa";
		String answer = "bbb";
		String maxScore = "5";
		String tagString = "#mix#";
		String correctRatio = "2/4";
		
		String questionWithBlank = "abcdefg#blank#eeeee";
		String url = "http://i.usatoday.net/_common/_notches/666b36b8-b939-4019-9070-7b8f4028a4bf-OscarStatueForOverviews.jpg";
		
		//Test Question-Response
		QuestionBase question = new QResponse(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio);
		printHtml(questionType, question);		
			
		//Test Fill-In-Blank
		questionType = questionTypes[1];
		question = new FillInBlank(questionType, creatorId, questionWithBlank, answer, maxScore,
				tagString, correctRatio);
		printHtml(questionType, question);		
		
		//Test Multi-Choice
		questionType = questionTypes[2];
		question = new MultiChoice(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio, choices);
		printHtml(questionType, question);
		
		//Test Picture-Response
		questionType = questionTypes[3];
		question = new PResponse(questionType, creatorId, questionDescription, answer, maxScore,
				tagString, correctRatio, url);
		printHtml(questionType, question);
		
	}
	
	
	private void printHtml(String questionType, QuestionBase question) {
		String html = QuestionBase.printCreateHtml(questionType);
		System.out.println(html);
		System.out.println("");
		
		html = question.printReadHtml();
		System.out.println(html);
		System.out.println("");
	}
	

}
