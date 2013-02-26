package mailbox;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
 * Servlet implementation class Mailbox
 */
@WebServlet("/Mailbox")
public class Mailbox extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mailbox() {
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
		String userId = request.getParameter("username");
		HttpSession session = request.getSession();
		String guest = (String) session.getAttribute("user");
		if(guest.equals("guest")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("index.html");
			dispatch.forward(request, response);
		} else if(!guest.equals(userId)){
			RequestDispatcher dispatch = request.getRequestDispatcher("home.jsp?id=" + guest);
			dispatch.forward(request, response);
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			Account user = new Account(userId);
			out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
					      + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Mailbox - " + userId + "</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<input name=\"username\" type=\"hidden\" value=" + userId + " />");
			out.println("<h1>Mailbox</h1>");
			out.println("<h2>Inbox</h2>");
			out.println("<table frame=\"hsizes\" rules=\"rows\"><tr><th>From</th><th>Title</th><th>Date</th></tr>");
			List<Message> msgsInbox = user.getMessageInbox();
			for(Message msg : msgsInbox){
				if(msg.ifRead) out.println("<b>");
				String msgCode = Integer.toHexString((msg.time + "yy" + msg.from).hashCode());
				out.println("<a href=\"Mailbox.jsp?box=inbox&msg=" + msgCode + "\">" +
						"<tr><td>"+ msg.from + "</td><td>" + msg.title + "</td><td>" + msg.time + "</td></tr></a>");
				if(msg.ifRead) out.println("</b>");
			}
			out.println("</table>");

			out.println("<h2>Sent</h2>");
			out.println("<table frame=\"hsizes\" rules=\"rows\"><tr><th>To</th><th>Title</th><th>Date</th></tr>");
			List<Message> msgsSent = user.getMessageSent();
			for(Message msg : msgsSent){
				String msgCode = Integer.toHexString((msg.time + "yy" + msg.from).hashCode());
				out.println("<a href=\"Mailbox.jsp?box=sent&msg=" + msgCode + "\">" +
						"<tr><td>"+ msg.to + "</td><td>" + msg.title + "</td><td>" + msg.time + "</td></tr></a>");
			}
			out.println("</table>");
			out.println("</body></html>");
		}
	}

}
