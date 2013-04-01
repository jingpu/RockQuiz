/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 ******************************************************************************/
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
		out.println("<link href='CSS/page_style.css' rel='stylesheet' type='text/css' />");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<link href=\"CSS/style.css\" rel=\"stylesheet\" type=\"text/css\">");
		if(user.sendMessage(msg)) {
			System.out.println("sent");

			out.println("<title>Message Sent Successfully</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Message is sent successfully.</h1>");
		} else {
			out.println("<title>Message Error</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Message fails to be sent.</h1>");
			if(!UserManager.alreadyExist(toUser)){
				out.println("<p>" + toUser + " doesn't exist.<p>");
			}
		}
		out.println("<p><a href='Mailbox_frame.jsp?id=" + fromUser +"'>Back to mailbox.</a></p>");
		out.println("<p><a href='' onclick='self.close();return false;'>Close This window</a></p>");
		out.println("</body>");
		out.println("</html>");
		return;
	}
}
