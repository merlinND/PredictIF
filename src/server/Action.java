
package server;

import dao.ClientUtil;
import dao.EmployeUtil;
import dao.HoroscopeUtil;
import dao.MediumUtil;
import dao.PredictionUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import static metier.modele.Client_.id;
import metier.modele.Employe;
import metier.modele.Horoscope;
import metier.modele.Medium;
import metier.modele.prediction.Amour;
import metier.modele.prediction.Prediction;
import metier.modele.prediction.Sante;
import metier.modele.prediction.Travail;
import metier.service.Service;
import util.Aleatoire;

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

/**
 * @warning All verification (done client-side) should be duplicated server-side
 * @author Merlin
 */
class SignupHandler implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		String required[] = {
			"civilite",
			"nom",
			"prenom",
			"date-de-naissance",
			"adresse",
			"ville",
			"code-postal",
			"pays",
			"email",
			"telephone",
			"cgu"
		};
		// + the checkbox "offres-partenaire" doesn't appear if it is left unchecked
		Map<String, String> data = new HashMap<String, String>();
		
		for (String key : required) {
			String s = request.getParameter(key);
			if (s != null) {
				data.put(key, s);
			}
			else {
				// Mising parameters
				System.out.println("Missing parameter : " + key);
				request.setAttribute("redirect-to", ClientActionServlet.URL_PREFIX + "/inscription");
				return;
			}
		}
		
		// Format some fields
		String name = data.get("nom").toUpperCase();
		// Parse birthdate
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(data.get("date-de-naissance"));
		}
		catch (ParseException ex) {
			Logger.getLogger(SignupHandler.class.getName()).log(Level.SEVERE, "Couldn't parse given date : " + data.get("date-de-naissance"), ex);
			return;
		}
		// Concatenate address
		String adresse = data.get("adresse") + ", " + data.get("code-postal") + " " + data.get("ville") + " " + data.get("pays");
		
		Client c = new Client(data.get("civilite"), name, data.get("prenom"), date, adresse, data.get("telephone"), data.get("email"));
		
		String offresPartenaire = request.getParameter("offres-partenaire");
		if (offresPartenaire != null && offresPartenaire.equalsIgnoreCase("on")) {
			c.setPartenaire(true);
			
			// Send e-mail to partners
			System.out.println("===== Sending e-mail to partners =====");
			System.out.println(Service.generateEmailForPartenaires(c));
		}
		
		// Create client
		Service.create(c);
		
		// Assign client to least busy employee
		Employe e = Service.affecterEmployeReferentAuClient(c);
		
		System.out.println("Le nouveau client " + c.getNom() + " a été affecté à l'employé :");
		System.out.println("> " + e.getPrenom() + " " + e.getNom() + " (" + e.getEmail() + ")");
		
		// Success
		request.getSession().setAttribute("client", c);
		request.setAttribute("redirect-to", ClientActionServlet.URL_PREFIX + "/choix-medium");
	}
	
}

class MediumSelector implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		Client c = (Client) request.getSession().getAttribute("client");
		if (c != null) {
			List<Medium> mediums = MediumUtil.getListMedium();
			
			// Pre-select some mediums
			
			
			request.setAttribute("mediums", mediums);
		}
		else
			request.setAttribute("redirect-to", ClientActionServlet.URL_PREFIX + "/inscription");
	}
	
}

class MediumSelectionHandler implements Action {

	@Override
	public void execute(HttpServletRequest request) {
		Client c = (Client) request.getSession().getAttribute("client");
		if (c == null) {
			request.setAttribute("redirect-to", ClientActionServlet.URL_PREFIX + "/inscription");
			return;
		}
		
		String[] selection = request.getParameterValues("mediums-choisis");
		List<Medium> mediums = new ArrayList<Medium>();
		if (selection == null) {
			// TODO: select random mediums for this client
			// Choose n random mediums for this client
			int n = (int)Math.floor(Math.random() * 3) + 3;
			List<Medium> all = MediumUtil.getListMedium();
			List<Integer> indexes = Aleatoire.randomSubList(all.size(), n);
			for (Integer index : indexes)
				mediums.add(all.get(index));
		}
		else {
			for (String s : selection) {
				Long id = Long.decode(s);
				Medium medium = MediumUtil.find(id);
				mediums.add(medium);
			}
		}
		
		for(Medium m : mediums) {
			c.addMedium(m);
		}
		Service.update(c);
		
		request.setAttribute("redirect-to", ClientActionServlet.URL_PREFIX + "/inscription-confirmation");
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
			List<Client> result = new ArrayList<Client>(),
						 other = new ArrayList<Client>(),
						 clients = e.getListClient();
			
			// List only clients that need their horoscope done
			for (Client c : clients) {
				if (HoroscopeUtil.besoinHoroscope(c))
					result.add(c);
				else
					other.add(c);
			}
			
			request.setAttribute("clients", result);
			request.setAttribute("otherClients", other);
			
			List<Client> allClients = new ArrayList<Client>();
			allClients.addAll(clients);
			allClients.addAll(other);
			request.setAttribute("allClients", allClients);
			
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
						
						// List in priority predictions that were not used previously
						List<Prediction> travail = PredictionUtil.getListTravail(),
										amour = PredictionUtil.getListAmour(),
										sante = PredictionUtil.getListSante(),
										usedTravail = HoroscopeUtil.getUsedListTravail(c),
										usedAmour = HoroscopeUtil.getUsedListAmour(c),
										usedSante = HoroscopeUtil.getUsedListSante(c);
										
						
						travail.removeAll(usedTravail);
						travail.addAll(usedTravail);
						amour.removeAll(usedAmour);
						amour.addAll(usedAmour);
						sante.removeAll(usedSante);
						sante.addAll(usedSante);

						predictions.put("travail", travail);
						predictions.put("amour", amour);
						predictions.put("sante", sante);
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