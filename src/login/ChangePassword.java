package login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.Account;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
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
		String userId = (String)request.getSession().getAttribute("guest");
		String currPwd = request.getParameter("currPwd");
		//System.out.println(currPwd);
		String newPwd = request.getParameter("newPwd");
		//System.out.println(newPwd);
		String cnfPwd = request.getParameter("cnfPwd");
		//System.out.println(cnfPwd);
		Account user = new Account(userId);
		if(user.matchPwd(currPwd)){
			//System.out.println("match old pwd");
			if(newPwd.equals(cnfPwd)){
				user.editInfo("password", newPwd);
				response.sendRedirect("password.jsp?err=0");
				return;
			}
			else {
				response.sendRedirect("password.jsp?err=2");
				return;
			}
		}
		else {
			response.sendRedirect("password.jsp?err=1");	
		}

	}

}
