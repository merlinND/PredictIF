
package server;

import dao.ClientUtil;
import dao.HoroscopeUtil;
import dao.MediumUtil;
import dao.PredictionUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Horoscope;
import metier.modele.Medium;
import metier.modele.prediction.Amour;
import metier.modele.prediction.Prediction;
import metier.modele.prediction.Sante;
import metier.modele.prediction.Travail;
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

class SignupRedirecter implements Action {
	@Override
	public void execute(HttpServletRequest request) {
		request.setAttribute("redirect-to", ClientActionServlet.URL_PREFIX + "/inscription");
	}
}

class SignupHandler implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}

class LoginRedirecter implements Action {
	@Override
	public void execute(HttpServletRequest request) {
		Employe e = (Employe) request.getSession().getAttribute("employe");
		if (e != null)
			request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/clients");
		else
			request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/login");
	}
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
				request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/clients");
			}
			else {
				// Échec
				request.setAttribute("erreur", "Nom d'utilisateur ou mot de passe invalide.");
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
		request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/login");
		return;
	}
	
}

class HoroscopeCreater implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		Employe e = (Employe) request.getSession().getAttribute("employe");
		if (e != null) {
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
			request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/clients");
			return;
		}
		else {
			request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/login");
			return;
		}
	}
	
}

/**
 * Note: very few error cases are handled!
 * @author Merlin
 */
class HoroscopeHandler implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		String required[] = {
			"clientId",
			"prediction-travail",
			"prediction-amour",
			"prediction-sante",
			"choix-medium"
		};
		Map<String, Long> data = new HashMap<String, Long>();
		
		for (String key : required) {
			String s = request.getParameter(key);
			if (s != null) {
				data.put(key, Long.decode(s));
			}
			else {
				// Mising parameters
				System.out.println("Missing parameter : " + key);
				request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/clients");
				return;
			}
		}
		
		Client c = ClientUtil.find(data.get("clientId"));
		if (c != null) {
			Medium m = MediumUtil.find(data.get("choix-medium"));
			Travail t = (Travail)PredictionUtil.find(data.get("prediction-travail"));
			Amour a = (Amour)PredictionUtil.find(data.get("prediction-amour"));
			Sante s = (Sante)PredictionUtil.find(data.get("prediction-sante"));
			
			if (m != null && t != null && a != null && s != null) {
				Horoscope h = new Horoscope(c, m);
				h.setTravailPrediction(t);
				h.setAmourPrediction(a);
				h.setSantePrediction(s);
				
				Service.create(h);
				
				// Success : send confirmation e-mail
				System.out.println("===== Sending e-mail to client =====");
				System.out.println(Service.generateEmailForClient(h));
				return;
			}
		}
		
		// Error cases (IDs not found)
		request.setAttribute("redirect-to", EmployeActionServlet.URL_PREFIX + "/clients");
		return;
	}
	
}