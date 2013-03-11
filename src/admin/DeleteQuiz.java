package admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.Administrator;
import user.UserManager;

/**
 * Servlet implementation class DeleteQuiz
 */
@WebServlet("/DeleteQuiz")
public class DeleteQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteQuiz() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String quizName = request.getParameter("quiz");
		String user = (String)request.getSession().getAttribute("guest");
		Administrator admin = new Administrator(user);
		if(!admin.getInfo("status").equals("s")){
			response.sendRedirect("home.jsp");
			return;
		}
		if(admin.canFindQuiz(quizName)) {
			String op = request.getParameter("operation");
			if(op.equals("1")) admin.deleteQuiz(quizName);
			else if(op.equals("2")) admin.clearQuizHistory(quizName);
			response.sendRedirect("admin.jsp?id=" + user);
		} else {
			response.sendRedirect("admin.jsp?id=" + user + "&delq=" + quizName);
		}
	}

}
