
package server;

import dao.JpaUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Merlin
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/employe/*"})
public class ActionServlet extends HttpServlet {

	public static final String URL_PREFIX = "/PredictIF/employe";
	public static final String STATIC_PREFIX = URL_PREFIX + "/static";
	public static final String JSP_PREFIX = "../WEB-INF/jsp";
	
	public Map<String, String> routes;
	public Map<String, Action> actions;

	public ActionServlet() {
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

	@Override
	public void init() throws ServletException {
		super.init();
		JpaUtil.init();
	}

	@Override
	public void destroy() {
		super.destroy();
		JpaUtil.destroy();
	}
	
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();

		// Store useful constants
		request.setAttribute("URL_PREFIX", URL_PREFIX);
		
		// Extract session and request URI
		HttpSession session = request.getSession();
		uri = uri.substring(uri.indexOf(URL_PREFIX) + URL_PREFIX.length());
		
		// Execute action if existing
		if (actions.containsKey(uri)) {
			actions.get(uri).execute(request);
		}
		
		// Execute resulting forward if needed
		String forwardTo = (String)request.getAttribute("forward-to");
		if (forwardTo != null) {
			request.setAttribute("forward-to", "");
			request.getRequestDispatcher(forwardTo).forward(request, response);
			return;
		}

		// Execute resulting redirect if needed
		String redirectTo = (String)request.getAttribute("redirect-to");
		if (redirectTo != null) {
			request.setAttribute("redirect-to", "");
			response.sendRedirect(redirectTo);
			return;
		}
		
		// Forward to view
		if (routes.containsKey(uri)) {
			if (routes.get(uri) != null)
				request.getRequestDispatcher(routes.get(uri)).forward(request, response);
		}
		else {
			response.sendError(404);
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Main action servlet";
	}// </editor-fold>

}
