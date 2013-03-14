package mailbox;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.Account;
import user.Message;
import user.UserManager;
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
		title = title == null? "" : Helper.replaceComma(title);
		String content = request.getParameter("content");
		content = content == null? "" : Helper.replaceComma(content);
		Message msg = new Message(fromUser, toUser, "n", title, content);
		Account user = new Account(fromUser);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<script type=\"text/javascript\">");
		out.println("<!--setTimeout('self.close()',5000);");
		out.println("//--></script>");
		out.println("<meta charset=\"UTF-8\">");
		if(user.sendMessage(msg)) {
			System.out.println("sent");

			out.println("<title>Message Sent Successfully</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Message is sent successfully.<h1>");
		} else {
			out.println("<title>Message Error</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Message fails to be sent.<h1>");
			if(!UserManager.alreadyExist(toUser)){
				out.println("<p>" + toUser + " doesn't exist.<p>");
			}
		}
		out.println("<a href='Mailbox_frame.jsp?id=" + fromUser +"'>Back to mailbox.</a>");
		out.println("</body>");
		out.println("</html>");
		return;
	}
}
