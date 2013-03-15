package quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Helper;

/**
 * Servlet implementation class QuizEditServle
 */
@WebServlet("/QuizEditServlet")
public class QuizEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizEditServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("guest");
		if (userName == null || userName.equals("guest")) {
			response.sendRedirect("index.html");
			return;
		}

		// get parameters from HTTP request
		String quizName = request.getParameter("quizName");
		assert quizName != null;
		MyQuiz quiz = new MyQuiz(quizName);

		// Take care of space, escape to "_"
		quizName = Helper.replaceSpace(quizName);
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

		String categoryType = request.getParameter("category_type");
		String category;
		if (categoryType.equals("existing_categories"))
			category = request.getParameter("existing_categories");
		else
			category = request.getParameter("new_category");


		// construct question list and store them to database
		int numQuestions = quiz.getQuestionList().size();
		List<QuestionBase> questionList = new ArrayList<QuestionBase>(
				numQuestions);
		for (int i = 0; i < numQuestions; i++) {
			String questionType = request.getParameter("questionType_" + i);
			if (questionType != null) {
				// create the question and store it into the database
				QuestionBase question = QuestionFactory.createQuestion(
						questionType, request, i);
				assert question != null;
				question.saveToDatabase();
				questionList.add(question);
			}
		}

		assert !questionList.isEmpty();
		// edit the quiz class and update it in database
		List<String> tags = Helper.parseTags(tagString);
		quiz.edit(quizDescription, tags, Boolean.parseBoolean(canPractice),
				Boolean.parseBoolean(isRandom),
				Boolean.parseBoolean(isOnePage),
				Boolean.parseBoolean(isImmCorrection), questionList, category);
		quiz.updateDatabase();


		// redirect to the quiz summary page
		RequestDispatcher dispatch = request.getRequestDispatcher(quiz
				.getSummaryPage());
		dispatch.forward(request, response);
	}


}
