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
		// save quiz event to user database
		Account user = new Account(userName);
		user.addQuizTaken(quizName, quizId);

		/*
		 * write html
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// print to result page
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Quiz Results</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Quiz Results</h1>");
		out.println("<p>Score: " + quiz.getScore(quizId) + "/"
				+ quiz.getMaxScore() + "</p>");

		out.println("<p>Time: " + quiz.getTimeElapsed(quizId) + "s </p>");
		out.println("<p><a href=\"" + quiz.getSummaryPage()
				+ "\">Go back to summary page.</p>");
		//*** add chanllenge button
		out.println("</body>");
		out.println("</html>");

		// remove all the session attributes defined in this servlet
		session.removeAttribute("quizStartTime");
	}

}
