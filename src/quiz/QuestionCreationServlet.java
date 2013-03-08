package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.Account;
import util.Helper;

/**
 * Servlet implementation class QuestionCreationServlet
 */
@WebServlet("/QuestionCreationServlet")
public class QuestionCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestionCreationServlet() {
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
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("guest");
		if (userName == null) {
			// TODO remove it and do error checking instead
			userName = "guest";
			session.setAttribute("guest", userName);
		}
		String creatorId = userName;

		// get parameters from HTTP request
		String questionType = request.getParameter("questionType");
		String quizName = request.getParameter("quizName");
		String tagString = request.getParameter("tagString");
		String quizDescription = request.getParameter("quizDescription");

		String canPractice = request.getParameter("canPractice");
		if (canPractice == null || !canPractice.equals("true"))
			canPractice = "false";

		String isRandom = request.getParameter("isRandom");
		if (isRandom == null || !isRandom.equals("true"))
			isRandom = "false";

		String isOnePage = request.getParameter("isOnePage");
		if (isOnePage == null || !isOnePage.equals("true"))
			isOnePage = "false";

		String isImmCorrection = request.getParameter("isImmCorrection");
		if (isImmCorrection == null || !isImmCorrection.equals("true"))
			isImmCorrection = "false";

		if (questionType.equals("finish")) {
			/*
			 * if finished quiz creation
			 */

			// also get questionList from session
			@SuppressWarnings("unchecked")
			List<QuestionBase> questionList = (List<QuestionBase>) session
					.getAttribute("questionList");
			// get a QuizManager
			QuizManager quizManager = new MyQuizManager();
			if (questionList == null) {
				// if there is no questionList created, it should redirect
				// to quiz creation page and display some message

				// we are not finishing quiz creation, so save all params to
				// session
				saveToSession(session, quizName, tagString, quizDescription,
						canPractice, isRandom, isOnePage, isImmCorrection);

				// write html
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				String message = "No question has been created in the new quiz. "
						+ "Please go back and add a new question.";
				out.print(printCreationFailPage(message));

			} else if (quizManager.getQuiz(quizName) != null) {
				// quizName already exists. It should
				// redirect to quiz creation page and display some message

				// write html
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				String message = "Quiz name \"" + quizName
						+ "\" already exists. "
						+ "Please go back and use a valid quiz name.";
				out.print(printCreationFailPage(message));

				// reset quizName
				quizName = "";
				// we are not finishing quiz creation, so save all params to
				// session
				saveToSession(session, quizName, tagString, quizDescription,
						canPractice, isRandom, isOnePage, isImmCorrection);

			} else {
				// construct the quiz class and store it to database
				Timestamp createTime = new Timestamp(
						new java.util.Date().getTime());
				List<String> tags = Helper.parseTags(tagString);
				MyQuiz quiz = new MyQuiz(quizName, creatorId, quizDescription,
						tags, Boolean.parseBoolean(canPractice),
						Boolean.parseBoolean(isRandom),
						Boolean.parseBoolean(isOnePage),
						Boolean.parseBoolean(isImmCorrection), questionList,
						createTime, "Not_Implemented_Category");
				quiz.saveToDatabase();
				// add quiz to user's database
				Account user = new Account(userName);
				user.addQuizCreated(quizName);

				// remove all session attributes
				session.removeAttribute("questionList");
				session.removeAttribute("quizName");
				session.removeAttribute("tagString");
				session.removeAttribute("quizDescription");
				session.removeAttribute("canPractice");
				session.removeAttribute("isRandom");
				session.removeAttribute("isOnePage");
				session.removeAttribute("isImmCorrection");

				// redirect to the quiz summary page
				RequestDispatcher dispatch = request.getRequestDispatcher(quiz
						.getSummaryPage());
				dispatch.forward(request, response);
			}
		} else {
			/*
			 * otherwise create a question
			 */
			// hack: save all quiz info related request params to session
			// context so that when finishing question creation and back to quiz
			// creating page all form fields keep their values

			session.setAttribute("quizName", quizName);
			session.setAttribute("tagString", tagString);
			session.setAttribute("quizDescription", quizDescription);
			session.setAttribute("canPractice", canPractice);
			session.setAttribute("isRandom", isRandom);
			session.setAttribute("isOnePage", isOnePage);
			session.setAttribute("isImmCorrection", isImmCorrection);

			// write html
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Create Question</title>");
			out.println("</head>");
			out.println("<body>");

			out.println(QuestionFactory.printCreateHtml(questionType));
			out.println("</body>");
			out.println("</html>");
		}

	}

	private void saveToSession(HttpSession session, String quizName,
			String tagString, String quizDescription, String canPractice,
			String isRandom, String isOnePage, String isImmCorrection) {
		session.setAttribute("quizName", quizName);
		session.setAttribute("tagString", tagString);
		session.setAttribute("quizDescription", quizDescription);
		session.setAttribute("canPractice", canPractice);
		session.setAttribute("isRandom", isRandom);
		session.setAttribute("isOnePage", isOnePage);
		session.setAttribute("isImmCorrection", isImmCorrection);
	}

	private String printCreationFailPage(String message) {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>");
		html.append("<html>");
		html.append("<head>");
		html.append("<meta charset=\"UTF-8\">");
		html.append("<title>Quiz Creation Fails</title>");
		html.append("</head>");
		html.append("<body>");
		html.append("<h1>Quiz Creation Fails</h1>");
		html.append("<p>" + message + "</p>");
		html.append("<form action=\"QuizCreationServlet\" method=\"post\">\n");
		html.append("<input type=\"submit\" value=\"Back\" >\n");
		html.append("</form>\n");
		html.append("</body>");
		html.append("</html>");

		return html.toString();
	}
}
