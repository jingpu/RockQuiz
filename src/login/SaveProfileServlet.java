package login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.Account;

/**
 * Servlet implementation class SaveProfileServlet
 */
@WebServlet("/SaveProfile")
public class SaveProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveProfileServlet() {
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
		String guest = (String)request.getSession().getAttribute("guest");
		String userId = request.getParameter("id");
		System.out.println(guest+" "+userId);
		System.out.println("m0");
		System.out.println("m1");
		if (guest == null || guest.equals("guest")) {
			response.sendRedirect("index.html");
			return;
		} else if (!guest.equals(userId)) {
			response.sendRedirect("home.jsp?id=" + guest);
			System.out.println(guest+" "+userId);
			System.out.println("m2");
			return;
		}
		System.out.println("m3");
		String gender = request.getParameter("gender");
		System.out.println(gender);
		String email = request.getParameter("email");
		Account user = new Account(userId);
		user.editInfo("gender", gender);
		user.editInfo("email", email);
		response.sendRedirect("profile.jsp?id=" + userId + "&p=s");
	}

}
