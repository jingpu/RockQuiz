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

/**
 * Servlet implementation class MsgSent
 * @author youyuan
 */
@WebServlet("/SendMessage")
public class MsgSent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MsgSent() {
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
		HttpSession session = request.getSession();
		String fromUser = (String) session.getAttribute("guest");
		String toUser = request.getParameter("toUser");
		String title = request.getParameter("title");
		title = title == null? "" : title;
		String content = request.getParameter("content");
		content = content == null? "" : content;
		Message msg = new Message(fromUser, toUser, "n", title, content);
		Account user = new Account(fromUser);
		if(user.sendMessage(msg)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("Mailbox_sent.jsp?id="+ fromUser);
			dispatch.forward(request, response);
			return;
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("Mailbox_inbox.jsp?id="+ fromUser);
		dispatch.forward(request, response);
		return;
	}
}
