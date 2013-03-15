package login;

import java.io.IOException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import user.UserManager;

/**
 * @author huijie
 *
 */


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		String saveCookie = request.getParameter("savecookie");
		if (UserManager.matchAccount(usrname, pwd)) {
			HttpSession session = request.getSession();
			session.setAttribute("guest", usrname);
			String homepage = "home.jsp?id=" + usrname;
			if("on".equals(saveCookie)) {
				Cookie[] cookies = request.getCookies();
				boolean foundCookie = false;
				if(cookies != null){
					for(int i = 0; i < cookies.length; i++) { 
						Cookie cookie1 = cookies[i];
						System.out.println(cookie1.getName());
						System.out.println(cookie1.getValue());
						if (cookie1.getName().equals("username")) {
							foundCookie = true;
							cookie1.setValue(usrname);
							cookie1.setMaxAge(90*24*60*60);
							System.out.println(cookie1.getName());
							System.out.println(cookie1.getValue());
						}
					}  
				}
				System.out.println(foundCookie);
				if (!foundCookie) {
					Cookie cookie1 = new Cookie("username",usrname);
					cookie1.setMaxAge(30*24*60*60);
					response.addCookie(cookie1); 
				}
				System.out.println(cookies);
			}
			
			response.sendRedirect(homepage);
			return;

		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("infoIncorrect.html");
			dispatch.forward(request, response);
			return;
		}

	}

}
