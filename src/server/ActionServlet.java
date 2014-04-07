
package server;

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
@WebServlet(name = "ActionServlet", urlPatterns = {"/"})
public class ActionServlet extends HttpServlet {

	public final String URL_PREFIX = "/PredictIF";
	public Map<String, String> routes;
	public Map<String, Action> actions;

	public ActionServlet() {
		routes = new HashMap<String, String>();
		routes.put("/", "index.jsp");
		routes.put("/index", "index.jsp");
		routes.put("/hello", "hello.jsp");
		routes.put("/login", "login.jsp");
		
		actions = new HashMap<String, Action>();
		actions.put("/login", new LoginHandler());
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
		// Set encoding once and for all
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// Extract session and request URI
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		uri = uri.substring(uri.indexOf(URL_PREFIX) + URL_PREFIX.length());
		
		// Execute action if existing
		if (actions.containsKey(uri)) {
			actions.get(uri).execute(request);
		}
		
		// Forward to view
		if (routes.containsKey(uri)) {
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
