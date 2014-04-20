
package server;

import java.util.HashMap;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Merlin
 */
@WebServlet(name = "EmployeActionServlet", urlPatterns = {"/employe/*"})
public class EmployeActionServlet extends ActionServlet {

	@Override
	public String getUrlPrefix() {
		return "/PredictIF/employe";
	}
	
	public EmployeActionServlet() {
		routes = new HashMap<String, String>();
		routes.put("", null);
		routes.put("/", null);
		routes.put("/index", null);
		routes.put("/login", getJspPrefix() + "/login.jsp");
		routes.put("/logout", getJspPrefix() + "/login.jsp");
		routes.put("/clients", getJspPrefix() + "/clients.jsp");
		routes.put("/horoscope", getJspPrefix() + "/horoscope.jsp");
		routes.put("/horoscope-traitement", null);
		
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
