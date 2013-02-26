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
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletContext;

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
		
		String hashValue = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			try {
				md.update(pwd.getBytes());
				MessageDigest tc = (MessageDigest)md.clone();
				byte[] bytes = tc.digest();
				StringBuffer buff = new StringBuffer();
				for (int i=0; i<bytes.length; i++) {
					int val = bytes[i];
					val = val & 0xff;  // remove higher bits, sign
					if (val<16) buff.append('0'); // leading 0
					buff.append(Integer.toString(val, 16));
				}			
				hashValue = buff.toString();
				md.reset();
			} catch (CloneNotSupportedException cnse) {
			     try {
					throw new DigestException("couldn't make digest of partial content");
				} catch (DigestException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		if (UserManager.matchAccount(usrname, hashValue)) {
			//String usrpage = "userpage.jsp?id=" + usrname;
			String usrpage = "home.jsp?id=" + usrname;
			RequestDispatcher dispatch = request.getRequestDispatcher(usrpage);
			dispatch.forward(request, response);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("infoIncorrect.html");
			dispatch.forward(request, response);
		}
		
	}

}
