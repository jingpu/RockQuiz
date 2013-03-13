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
 * Servlet implementation class AppointAdmin
 */
@WebServlet("/ChangeStatus")
public class ChangeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeStatus() {
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
		String userId = request.getParameter("id");
		String user = (String)request.getSession().getAttribute("guest");
		Administrator admin = new Administrator(user);
		if(!admin.getInfo("status").equals("s")){
			response.sendRedirect("home.jsp");
			return;
		}
		if(UserManager.alreadyExist(userId)) {
			String status = request.getParameter("status");
			admin.setStatus(userId, status);
			response.sendRedirect("admin.jsp?id=" + user);
		} else {
			response.sendRedirect("admin.jsp?id=" + user + "&adm=" + userId);
		}
	}

}
