
package server;

import java.util.HashMap;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Merlin
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/employe/*"})
public class EmployeActionServlet extends ActionServlet {

	public EmployeActionServlet() {
		URL_PREFIX = "/PredictIF/employe";
		STATIC_PREFIX = URL_PREFIX + "/static";
		JSP_PREFIX = "../WEB-INF/jsp";
		
		routes = new HashMap<String, String>();
		routes.put("", null);
		routes.put("/", null);
		routes.put("/index", null);
		routes.put("/login", JSP_PREFIX + "/login.jsp");
		routes.put("/logout", JSP_PREFIX + "/login.jsp");
		routes.put("/clients", JSP_PREFIX + "/clients.jsp");
		routes.put("/horoscope", JSP_PREFIX + "/horoscope.jsp");
		routes.put("/horoscope-traitement", null);
		
		// TODO: require login on most pages
		
		actions = new HashMap<String, Action>();
		actions.put("", new LoginRedirecter());
		actions.put("/", new LoginRedirecter());
		actions.put("/index", new LoginRedirecter());
		actions.put("/login", new LoginHandler());
		actions.put("/logout", new LogoutHandler());
		actions.put("/clients", new ClientLister());
		actions.put("/horoscope", new HoroscopeCreater());
		actions.put("/horoscope-traitement", new HoroscopeHandler());
		
	}
}
