package mailbox;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.Account;
import user.Message;
import util.Helper;

/**
 * Servlet implementation class ChallengeLetterSent
 */
@WebServlet("/ChallengeLetterSent")
public class ChallengeLetterSent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeLetterSent() {
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
		System.out.println("about to send1");
		HttpSession session = request.getSession();
		String fromUser = (String) session.getAttribute("guest");
		String toUser = request.getParameter("toUser");
		String title = request.getParameter("quizName");
		String content = request.getParameter("content");
		String retUrl = request.getHeader("referer"); 
		content = content == null? "" : Helper.replaceComma(content);
		Message msg = new Message(fromUser, toUser, "c", title, content);
		Account user = new Account(fromUser);
		System.out.println("about to send2");
		if(user.sendMessage(msg)) {
			System.out.println("sent");
			response.sendRedirect(retUrl);
			return;
		}
		response.sendRedirect(retUrl);
		return;
	}

}
