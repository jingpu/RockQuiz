package quiz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String questionType = request.getParameter("questionType");
		HttpSession session = request.getSession();
		if (questionType.equals("finish")) {
			// remove all session attributes
			session.removeAttribute("quizName");
			session.removeAttribute("tagString");
			session.removeAttribute("quizDescription");
			session.removeAttribute("canPractice");
			session.removeAttribute("isRandom");
			session.removeAttribute("isOnePage");
			session.removeAttribute("isImmCorrection");
		} else {
			// save all request params to session
			String string;
			session.setAttribute("quizName", request.getParameter("quizName"));
			session.setAttribute("tagString", request.getParameter("tagString"));
			session.setAttribute("quizDescription",
					request.getParameter("quizDescription"));

			string = request.getParameter("canPractice");
			if (string != null && string.equals("true"))
				session.setAttribute("canPractice", "true");
			else
				session.setAttribute("canPractice", "false");

			string = request.getParameter("isRandom");
			if (string != null && string.equals("true"))
				session.setAttribute("isRandom", "true");
			else
				session.setAttribute("isRandom", "false");

			string = request.getParameter("isOnePage");
			if (string != null && string.equals("true"))
				session.setAttribute("isOnePage", "true");
			else
				session.setAttribute("isOnePage", "false");

			string = request.getParameter("isImmCorrection");
			if (string != null && string.equals("true"))
				session.setAttribute("isImmCorrection", "true");
			else
				session.setAttribute("isImmCorrection", "false");
		}

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

		out.println("<h1>Create Question</h1>");
		out.println("<a href=\"QuizCreationServlet\">Return to Create Quiz</a>");
		out.println("</body>");
		out.println("</html>");
	}
}
