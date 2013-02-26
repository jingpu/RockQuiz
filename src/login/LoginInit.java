package login;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class LoginInit
 *
 */
@WebListener
public class LoginInit implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public LoginInit() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
        HttpSession session = arg0.getSession();
        session.setAttribute("guest", "guest");
        session.setAttribute("announce", "[Anncouncement] Let's Rock Quiz!!");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
