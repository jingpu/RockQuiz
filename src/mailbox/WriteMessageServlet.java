package mailbox;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WriteMessageServlet
 */
@WebServlet("/WriteMessageServlet")
public class WriteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteMessageServlet() {
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
		String guest = (String) session.getAttribute("guest");
		if (userId == null || guest.equals("guest")) {
			response.sendRedirect("index.html");
			return;
		} else if (!guest.equals(userId)) {
			response.sendRedirect("home.jsp?id=" + guest);
			return;
		}
		String mailBoxUrl = "Mailbox.jsp?id="+ userId;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
				      + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>Composing Message</title>");
		out.println("</head>");
		out.println("<body>");
		out.println(new Date());
		out.println("<form action=\"MsgSent\" method=\"post\">");
		out.println("<p>To <input type=\"text\" name=\"toUser\"></p>");
		out.println("<p><input type=\"textarea\" name=\"title\" placeholder=\"Subject\"></p>");
		out.println("<textarea rows=\"10\" cols=\"50\" " + "name=\"content\" " +
				"form=\"MsgSent\" placeholder=\"Composing message here\"></textarea>");
		out.println("<p><input type=\"submit\" value=\"Send\"></form>");
		out.println("<a href=\"" + mailBoxUrl + "\"><input type=\"submit\" value=\"Cancel\"></a></p>");
	}
}
