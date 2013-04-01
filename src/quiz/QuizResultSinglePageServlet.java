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
package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.Account;

/**
 * Servlet implementation class QuizResultSinglePageServlet
 */
@WebServlet("/QuizResultSinglePageServlet")
public class QuizResultSinglePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizResultSinglePageServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get session attributes
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("guest");
		if (userName == null) {
			// TODO remove it and do error checking instead
			userName = "guest";
			session.setAttribute("guest", userName);
		}

		// get quiz info
		String quizName = request.getParameter("quizName");
		MyQuiz quiz = new MyQuiz(quizName);
		// return to summary page if this quiz is not for one page display
		if (!quiz.isOnePage()) {
			response.sendRedirect(quiz.getSummaryPage());
			return;
		}
		// get the question list
		List<QuestionBase> questionList = quiz.getQuestionList();

		/*
		 * Do answer Checking here
		 */

		// Need to use factory to preprocess answer string
		int currentScore = 0;
		for (QuestionBase q : questionList) {
			String answer = q.getUserAnswer(request);
			currentScore += q.getScore(answer);
		}

		/*
		 * already finished all questions, save the quizEvent
		 */
		long startTime = (Long) session.getAttribute("quizStartTime");
		long timeElapsed = new Date().getTime() - startTime;
		String quizId = quiz.saveQuizEvent(userName, timeElapsed, currentScore);

		/*
		 *  save quiz event and achievement to user database
		 *  */
		boolean newAchieve1 = false;
		boolean newAchieve2 = false;
		Account user = new Account(userName);
		if(quizName != null){
			user.addQuizTaken(quizName, quizId);
			if(user.countHistory("t") == 10){
				newAchieve1 = true;
				user.addAchievement("a4", quizName);
			}

			if (!user.containsAchievement("a5") && currentScore >= quiz.getBestScore()) {
				newAchieve2 = true;
				user.addAchievement("a5", quizName);
			}
		}

		/*
		 * write html
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// print to result page
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<script type=\"text/javascript\" src=\"challenge-msg.js\"></script>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Quiz Results</title>");
		out.println("</head>");
		if(newAchieve1 && newAchieve2){
			out.println("<body onload=\"javascript:Auto_both()\"");
		} else if(newAchieve1 && !newAchieve2){
			out.println("<body onload=\"javascript:Auto_1()\"");
		} else if(!newAchieve1 && newAchieve2){
			out.println("<body onload=\"javascript:Auto_2()\"");
		} else {
			out.println("<body>");
		}
		out.println("<h1>Quiz Results</h1>");
		out.println("<p>Score: " + quiz.getScore(quizId) + "/"
				+ quiz.getMaxScore() + "</p>");

		out.println("<p>Time: " + quiz.getTimeElapsed(quizId) + "s </p>");

		//*** add challenge button

		out.println("<input name='' type='button' value='Challenge my friends!'onclick='AddElement()'>");
		out.println("<form action='ChallengeLetterSent' target='hidFrame' method='post' id='letter'>");
		out.println("<input type='hidden' name='quizName' value=" + quizName + ">");
		out.println("<div id='msg'></div>");
		out.println("</form>");
		out.println("<iframe name='hidFrame' style='display: none'></iframe>");

		out.println("<p><a href=\"" + quiz.getSummaryPage()
				+ "\">Go back to summary page.</p>");

		out.println("</body>");
		out.println("</html>");



		// remove all the session attributes defined in this servlet
		session.removeAttribute("quizStartTime");
	}

}
