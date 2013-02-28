package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
		String userName = (String) session.getAttribute("userName");
		if (userName == null)
			userName = "tester";

		// get quizName from session
		String quizName = (String) session.getAttribute("quizName");
		if (quizName == null) {
			// if there is no such attribute, we are just coming into this
			// QuizDisplay page and there should be quizName in HTTP request
			quizName = request.getParameter("quizName");
			session.setAttribute("quizName", quizName);
		}
		// then get the quiz object and the question list
		MyQuiz quiz = new MyQuiz(quizName);
		List<QuestionBase> questionList = quiz.getQuestionList();

		// get questionIndex from session
		Integer questionIndex = (Integer) session.getAttribute("questionIndex");
		if (questionIndex == null) {
			// if there is no such attribute, we are just coming into this
			// QuizDisplay page and we should set it to be zero (first question)
			questionIndex = 0;
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
			// set questionIndex to the next
			questionIndex++;
			session.setAttribute("questionIndex", questionIndex);
		} else {
			/*
			 * already finished all questions, save the results
			 */

			// TODO redirect to result page
			RequestDispatcher dispatch = request.getRequestDispatcher(quiz
					.getSummaryPage());
			dispatch.forward(request, response);
		}

	}
}
