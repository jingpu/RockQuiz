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
		String retUrl = request.getParameter("retUrl");
		if(retUrl.contains("Mailbox_browse.jsp?id="+ fromUser) || retUrl.contains("Mail.jsp?id=yy&box=")) 
			retUrl = "Mailbox_sent.jsp?id="+ fromUser;
		title = title == null? "" : Helper.replaceComma(title);
		String content = request.getParameter("content");
		content = content == null? "" : Helper.replaceComma(content);
		Message msg = new Message(fromUser, toUser, "n", title, content);
		Account user = new Account(fromUser);
		if(user.sendMessage(msg)) {
			response.sendRedirect(retUrl);
			return;
		}
		response.sendRedirect(retUrl);
		return;
	}
}
