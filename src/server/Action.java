
package server;

import dao.ClientUtil;
import dao.EmployeUtil;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.service.Service;

/**
 *
 * @author Merlin
 */
public interface Action {
	// TODO : injection de dépendance pour les services ?
	//public void setService(Service service);
	
	public void execute(HttpServletRequest request);
}

class LoginHandler implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("employe", null);
		request.setAttribute("erreur", "");
		request.setAttribute("message", "");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username != null && username.length() > 0) {
			Employe employe = Service.findEmployeByEmail(username);
			// Succès
			if (employe != null) {
				session.setAttribute("employe", employe);
				request.setAttribute("redirect-to", ActionServlet.URL_PREFIX + "/clients");
			}
			else {
				// Échec
				request.setAttribute("erreur", "Mot de passe invalide.");
			}
		}
	}
}

class LogoutHandler implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("employe", null);
		request.setAttribute("erreur", "");
		request.setAttribute("message", "Vous avez été déconnecté avec succès.");
	}
}


class ClientLister implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		List<Client> clients = ClientUtil.getListClient();
		request.setAttribute("clients", clients);
	}
	
}