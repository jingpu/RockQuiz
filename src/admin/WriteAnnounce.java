package admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.Administrator;

/**
 * Servlet implementation class WriteAnnounce
 */
@WebServlet("/WriteAnnounce")
public class WriteAnnounce extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteAnnounce() {
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
		String user = (String)request.getSession().getAttribute("guest");
		Administrator admin = new Administrator(user);
		System.out.println(admin.getInfo("status"));
		if(!admin.getInfo("status").equals("s")){
			response.sendRedirect("home.jsp");
			return;
		}
		String content = request.getParameter("content");
		System.out.println(content);
		admin.setAnnouncement(content);
		response.sendRedirect("admin.jsp?id=" + user + "&ann=1");
	}

}
