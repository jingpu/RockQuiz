package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.Account;

/**
 * Servlet implementation class QuizDisplayServlet
 */
@WebServlet("/QuestionProcessServlet")
public class QuestionProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestionProcessServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("guest");
		if (userName == null) {
			// TODO remove it and do error checking instead
			userName = "guest";
			session.setAttribute("guest", userName);
		}

		// get quizName questionIndex currentScore from session
		String quizName = (String) session.getAttribute("quizName");
		Integer questionIndex = (Integer) session.getAttribute("questionIndex");
		Integer currentScore = (Integer) session.getAttribute("currentScore");
		boolean comingFromLastQuestionPage = false;
		MyQuiz quiz;
		if (quizName == null) {
			// if there is no such attribute, we are just coming into this
			// QuizDisplay page and there should be quizName in HTTP request
			quizName = request.getParameter("quizName");
			quiz = new MyQuiz(quizName);
			if (quiz.isOnePage()) {
				// redirect to quizDisplayOnePage.jsp and never return to this
				// page
				RequestDispatcher dispatch = request
						.getRequestDispatcher("quizDisplayOnePage.jsp");
				dispatch.forward(request, response);
				return;
			}
			if (quiz.isRandom())
				quiz.shuffleQuestionListAndSave();
			session.setAttribute("quizName", quizName);
			// set questionIndex to be zero (first question)
			questionIndex = 0;
			session.setAttribute("questionIndex", questionIndex);
			// set currentScore
			currentScore = 0;
			session.setAttribute("currentScore", currentScore);
			// set start time
			session.setAttribute("quizStartTime", new Date().getTime());
		} else {
			// otherwise we are coming from last question
			comingFromLastQuestionPage = true;
			quiz = new MyQuiz(quizName);
		}

		// then get the question list
		List<QuestionBase> questionList = quiz.getQuestionList();

		if (comingFromLastQuestionPage) {
			/*
			 * Do answer Checking here
			 */

			// Need to use factory to preprocess answer string
			QuestionBase lastQuestion = questionList.get(questionIndex - 1);
			String answer = lastQuestion.getUserAnswer(request);
			currentScore += lastQuestion.getScore(answer);
			session.setAttribute("currentScore", currentScore);
		}

		// write html
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (questionIndex != questionList.size()) {
			/*
			 * Display the question
			 */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Question " + (questionIndex + 1) + "</title>");
			out.println("</head>");
			out.println("<body>");

			QuestionBase question = questionList.get(questionIndex);
			out.println(question.printReadHtml());
			out.println("</body>");
			out.println("</html>");

			// set questionIndex to the next question
			questionIndex++;
			session.setAttribute("questionIndex", questionIndex);
		} else {
			/*
			 * already finished all questions, save the quizEvent
			 */
			long startTime = (Long) session.getAttribute("quizStartTime");
			long timeElapsed = new Date().getTime() - startTime;
			String quizId = quiz.saveQuizEvent(userName, timeElapsed,
					currentScore);
			// save quiz event to user database
			Account user = new Account(userName);
			user.addQuizTaken(quizName, quizId);

			// print to result page
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<script type=\"text/javascript\" src=\"challenge-msg.js\"></script>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Quiz Results</title>");

			out.println("</head>");
			out.println("<body>");
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
			session.removeAttribute("quizName");
			session.removeAttribute("questionIndex");
			session.removeAttribute("currentScore");
			session.removeAttribute("quizStartTime");
		}

	}

}
