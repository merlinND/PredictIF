
package server;

import java.util.HashMap;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Merlin
 */
@WebServlet(name = "CommonActionServlet", urlPatterns = {"", "/index"})
public class CommonActionServlet extends ActionServlet {

	@Override
	public String getUrlPrefix() {
		return "/PredictIF";
	}
	@Override
	public String getJspPrefix() {
		return "WEB-INF/jsp";
	}
	
	public CommonActionServlet() {
		routes = new HashMap<String, String>();
		routes.put("", getJspPrefix() + "/index.jsp");
		routes.put("/", getJspPrefix() + "/index.jsp");
		routes.put("/index", getJspPrefix() + "/index.jsp");
		
		actions = new HashMap<String, Action>();
	}
}
