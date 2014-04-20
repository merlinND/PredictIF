
package server;

import java.util.HashMap;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Merlin
 */
@WebServlet(name = "ClientActionServlet", urlPatterns = {"/client/*"})
public class ClientActionServlet extends ActionServlet {

	@Override
	public String getUrlPrefix() {
		return "/PredictIF/client";
	}
	
	public ClientActionServlet() {
		routes = new HashMap<String, String>();
		routes.put("", null);
		routes.put("/", null);
		routes.put("/index", null);
		routes.put("/inscription", getJspPrefix() + "/inscription.jsp");
		routes.put("/inscription-traitement", null);
		routes.put("/choix-medium", getJspPrefix() + "/choix-medium.jsp");
		routes.put("/choix-medium-traitement", null);
		routes.put("/inscription-confirmation", getJspPrefix() + "/inscription-confirmation.jsp");
		
		actions = new HashMap<String, Action>();
		actions.put("", new SignupRedirecter());
		actions.put("/", new SignupRedirecter());
		actions.put("/index", new SignupRedirecter());
		actions.put("/inscription-traitement", new SignupHandler());
		actions.put("/choix-medium", new MediumSelector());
		actions.put("/choix-medium-traitement", new MediumSelectionHandler());
		
	}
}
