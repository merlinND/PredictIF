
package server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Employe;

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
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username != null && username.length() > 0) {
			// TODO
			Employe employe = null; //Service.findEmployeByEmail(username);

			// Succès
			session.setAttribute("employe", employe);
			// TODO: rediriger vers l'accueil des employés ?

			// Échec
			request.setAttribute("erreur", "Mot de passe invalide.");
		}
	}
}