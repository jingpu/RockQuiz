package login;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.*;

/**
 * Application Lifecycle Listener implementation class accntListener
 *
 */
@WebListener
public class accntListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public accntListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	AccountManager accnt = new AccountManager();
    	ServletContext servContext = arg0.getServletContext();
    	servContext.setAttribute("accntManager", accnt);

    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        
    	ServletContext servContext = arg0.getServletContext();
    	servContext.removeAttribute("accntManager");
    }
	
}
