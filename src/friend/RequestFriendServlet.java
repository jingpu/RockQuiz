package friend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.Account;

/**
 * Servlet implementation class RequestFriendServlet
 */
@WebServlet("/RequestFriend")
public class RequestFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestFriendServlet() {
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
		String toUser = request.getParameter("to");
		HttpSession session = request.getSession();
		String guest = (String) session.getAttribute("guest");
		String toUsersPage = "userpage.jsp?id=" + toUser;
		if (toUser == null || guest.equals("guest")) {
			response.sendRedirect(toUsersPage);
			return;
		} 
		Account fromUser = new Account(guest);
		fromUser.requestFriend(toUser);
		response.sendRedirect(toUsersPage);
	}
}
