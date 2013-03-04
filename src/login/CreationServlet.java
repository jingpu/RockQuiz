package login;

import java.io.IOException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import user.UserManager;

/**
 * @author huijie
 *
 */

/**
 * Servlet implementation class CreationServlet
 */
@WebServlet("/CreationServlet")
public class CreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationServlet() {
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
		
		response.setContentType("text/html");
		String usrname = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String gender = request.getParameter("gender");
		gender = gender.substring(0, 1);
		String email = request.getParameter("email");

		if (usrname == "" || pwd == "") {
			RequestDispatcher dispatch = request.getRequestDispatcher("createAccount.html");
			dispatch.forward(request, response);
			return;
		} 
		
		if (UserManager.alreadyExist(usrname)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("nameInUse.jsp");
			dispatch.forward(request, response);
		} else {
			UserManager.addNewAccount(usrname, pwd, "u", gender, email);
			String usrpage = "home.jsp?id=" + usrname;
			//String usrpage = "userWelcome.jsp";
			RequestDispatcher dispatch = request.getRequestDispatcher(usrpage);
			dispatch.forward(request, response);
		}
			
		
	}

}
