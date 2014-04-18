
package server;

import dao.ClientUtil;
import dao.HoroscopeUtil;
import dao.PredictionUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.prediction.Prediction;
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
				// Charger la liste des clients dans l'objet (lazy-loading)
				List<Client> clients = employe.getListClient();
				
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
		Employe e = (Employe) request.getSession().getAttribute("employe");
		if (e != null) {
			List<Client> clients = e.getListClient();
			request.setAttribute("clients", clients);
			return;
		}
		
		// Error cases (mising parameters)
		request.setAttribute("redirect-to", ActionServlet.URL_PREFIX + "/login");
		return;
	}
	
}

class HoroscopeCreater implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		String clientIdString = request.getParameter("clientId");
		if (clientIdString != null) {
			Long clientId = Long.decode(clientIdString);
		
			if (clientId != null) {
				Client c = ClientUtil.find(clientId);
				if (c != null) {
					
					request.setAttribute("client", c);
					request.setAttribute("mediums", c.getMediumsFavoris());
					request.setAttribute("horoscopes", HoroscopeUtil.getListHoroFromClient(c));
					
					Map<String, List<Prediction>> predictions = new HashMap<String, List<Prediction>>();
					predictions.put("travail", PredictionUtil.getListTravail());
					predictions.put("amour", PredictionUtil.getListAmour());
					predictions.put("sante", PredictionUtil.getListSante());
					request.setAttribute("predictions", predictions);
					
					return;
				}
			}
		}
		// Error cases (mising parameters)
		request.setAttribute("redirect-to", ActionServlet.URL_PREFIX + "/clients");
		return;
	}
	
}