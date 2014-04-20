
package server;

import java.util.HashMap;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Merlin
 */
@WebServlet(name = "ClientActionServlet", urlPatterns = {"/client/*"})
public class ClientActionServlet extends ActionServlet {

	public ClientActionServlet() {
		URL_PREFIX = "/PredictIF/client";
		STATIC_PREFIX = URL_PREFIX + "/static";
		JSP_PREFIX = "../WEB-INF/jsp";
		
		routes = new HashMap<String, String>();
		routes.put("", null);
		routes.put("/", null);
		routes.put("/index", null);
		routes.put("/inscription", JSP_PREFIX + "/inscription.jsp");
		routes.put("/inscription-traitement", null);
		routes.put("/choix-medium", JSP_PREFIX + "/choix-medium.jsp");
		routes.put("/choix-medium-traitement", null);
		
		actions = new HashMap<String, Action>();
		actions.put("", new SignupRedirecter());
		actions.put("/", new SignupRedirecter());
		actions.put("/index", new SignupRedirecter());
		actions.put("/inscription-traitement", new SignupHandler());
		actions.put("/choix-medium", new MediumSelector());
		actions.put("/choix-medium-traitement", new MediumSelectionHandler());
		
	}
}
