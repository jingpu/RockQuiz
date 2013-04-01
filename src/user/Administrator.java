/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 ******************************************************************************/
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
