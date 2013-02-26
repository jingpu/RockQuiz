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
 * Servlet implementation class QuizCreationServlet
 */
@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizCreationServlet() {
		super();
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

		// set default values for form inputs
		String quizName = "";
		String tagString = "";
		String quizDescription = "";
		String canPractice = "checked";
		String isRandom = "checked";
		String isOnePage = "";
		String isImmCorrection = "";

		// get form input values from session
		String tmp;
		HttpSession session = request.getSession();
		tmp = (String) session.getAttribute("quizName");
		if (tmp != null)
			quizName = tmp;
		tmp = (String) session.getAttribute("tagString");
		if (tmp != null)
			tagString = tmp;
		tmp = (String) session.getAttribute("quizDescription");
		if (tmp != null)
			quizDescription = tmp;
		tmp = (String) session.getAttribute("canPractice");
		if (tmp != null && tmp.equals("false"))
			canPractice = "";
		tmp = (String) session.getAttribute("isRandom");
		if (tmp != null && tmp.equals("false"))
			isRandom = "";
		tmp = (String) session.getAttribute("isOnePage");
		if (tmp != null && tmp.equals("true"))
			isOnePage = "checked";
		tmp = (String) session.getAttribute("isImmCorrection");
		if (tmp != null && tmp.equals("true"))
			isImmCorrection = "checked";

		// write html
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Create Quiz</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("<h1>Create Quiz</h1>");
		out.println("<form action=\"QuestionCreationServlet\" method=\"post\">");
		out.println("<h2>Quiz Information</h2>");
		out.println("<p>");
		out.println("Quiz Name: <input type=\"text\" name=\"quizName\" value=\""
				+ quizName + "\" required></input><br>");
		out.println("Tags: <input type=\"text\" name=\"tagString\" value=\""
				+ tagString + "\"></input><br>");
		out.println("Quiz Description:<br>");
		out.println("<textarea name=\"quizDescription\" rows=\"10\" cols=\"50\" required>"
				+ quizDescription + "</textarea><br>");
		out.println("<input type=\"checkbox\" name=\"canPractice\" value=\"true\" "
				+ canPractice + ">Allow practice mode</input><br>");
		out.println("<input type=\"checkbox\" name=\"isRandom\" value=\"true\" "
				+ isRandom
				+ ">Automatically randomized question order</input><br>");
		out.println("<input type=\"checkbox\" name=\"isOnePage\" value=\"true\" "
				+ isOnePage + ">Displays in one page</input><br>");
		out.println("<input type=\"checkbox\" name=\"isImmCorrection\" value=\"true\" "
				+ isImmCorrection
				+ ">Allow immediate Correction if Multi-Page mode enabled</input><br>");
		out.println("</p>");

		out.println("<h2>Create new Questions</h2>");
		out.println("<p>TODO: display the number of questions already created.</p>");
		out.println("<h4>List of supported question types:</h4>");

		String[] questionTypes = QuestionBase.getQuestionTypes();
		for (int i = 0; i < questionTypes.length; i++) {
			String typeName = questionTypes[i];
			out.println("<input type=\"radio\" name=\"questionType\" value=\""
					+ typeName + "\">" + typeName + "</input><br>");
		}
		out.println("<input type=\"radio\" name=\"questionType\" value=\"finish\" required><b>Finish</b></input><br>");
		out.println("<input type=\"submit\" value=\"Submit\" >");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
}
