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
		if (quizName == null) {
			// if there is no such attribute, we are just coming into this
			// QuizDisplay page and there should be quizName in HTTP request
			quizName = request.getParameter("quizName");
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
		}

		// then get the quiz object and the question list
		MyQuiz quiz = new MyQuiz(quizName);
		List<QuestionBase> questionList = quiz.getQuestionList();

		if (comingFromLastQuestionPage) {
			/*
			 * Do answer Checking here
			 */

			// Need to use factory to preprocess answer string
			String questionType = request.getParameter("questionType");
			String answer = QuestionFactory.getAnswerString(questionType,
					request);
			QuestionBase lastQuestion = questionList.get(questionIndex - 1);
			currentScore += Integer.parseInt(lastQuestion.getScore(answer));
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
			out.println("</body>");
			out.println("</html>");

			// remove all the session attributes defined in this servlet
			session.removeAttribute("quizName");
			session.removeAttribute("questionIndex");
			session.removeAttribute("currentScore");
		}

	}
}
