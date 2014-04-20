<%-- 
    Document   : horoscope.jsp
    Created on : Mar 31, 2014, 5:33:36 PM
    Author     : Merlin
--%>
<%@page import="metier.modele.prediction.Prediction"%>
<%@page import="java.util.Map"%>
<%@page import="metier.modele.Horoscope"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="metier.modele.Medium"%>
<%@page import="java.util.List"%>
<%@page import="metier.modele.Client"%>

<%!
	// Helper function
	String listerPredictions(List<Prediction> predictions, String type) {
		String html = "";
		
		html += "<div class=\"col-sm-10\">";
			html += "<select name = \"prediction-" + type + "\" id = \"prediction-" + type + "\" class = \"choix-prediction form-control\">";
			for(Prediction p : predictions) {
					html += "<option value = \"" + p.getId() + "\">";
						html += p.getChance() + " | " + p.getDescription();
					html += "</option>";
			}
			html += "</select>";
		
			for(Prediction p : predictions) {
				html += "<span class = \"details-prediction\" id = \"prediction-"+ p.getId() +"\">";
					html += p.getDescription();
				html += "</span>";
			}
		html += "</div>";
		return html;
	}
%>

<%
	request.setAttribute("title", "Création d'horoscope");
	Client client = (Client)request.getAttribute("client");
	List<Medium> mediums = (List<Medium>)request.getAttribute("mediums");
	List<Horoscope> horoscopes = (List<Horoscope>)request.getAttribute("horoscopes");
	
	Map<String, List<Prediction>> predictions = (Map<String, List<Prediction>>)request.getAttribute("predictions");
	List<Prediction> predictionsTravail = predictions.get("travail"),
					predictionsAmour = predictions.get("amour"),
					predictionsSante = predictions.get("sante");
%>
<%@include file="header.jspf" %>

<h2>Création d'un horoscope pour ${client.civilite} ${client.nom}</h2>

<div class="col-xs-12">
	<div class="row">
		<div class="col-sm-4">
			<div id="client-${client.id}" class="panel panel-default details-client">
				<div class="panel-heading">
					<h3>Infos clients</h3>
				</div>
				<div class="panel-body">
					<ul>
						<li>Date de naissance : <fmt:formatDate value="${client.dateNaissance}" pattern="dd/MM/yyyy"/></li>
						<li>Adresse : ${client.adresse}</li>
						<li>Téléphone : ${client.telephone}</li>
						<li>E-mail : ${client.email}</li>
						<li>
							Mediums favoris&nbsp;:&nbsp;
							<ul>
								<c:forEach items="${mediums}" var="medium">
									<li>${medium.civilite} ${medium.nom}</li>
								</c:forEach>
							</ul>
						</li>
					</ul>
				</div> <!-- end .panel-body -->
			</div> <!-- end .panel -->
		</div> <!-- end column -->
		
		<div class="col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3>Anciens horoscopes</h3>
				</div>
				<div class="panel-body">
					<select class="choix-horoscope form-control">
						<option value="-1">Choisir un horoscope à consulter</option>
						<c:forEach items = "${horoscopes}" var = "horoscope">
							<option value="${horoscope.id}">
								Horoscope du <fmt:formatDate value="${horoscope.creationDate}" pattern="dd/MM/yyyy"/>
							</option>
						</c:forEach>
					</select>

					<c:forEach items = "${horoscopes}" var = "horoscope">
					<div id="horoscope-${horoscope.id}" class="details-horoscope">
						<h4>Prédictions</h4>
						<p>
							<strong>Travail</strong>&nbsp;:
							${horoscope.travailPred.chance}&nbsp;|&nbsp;${horoscope.travailPred.description}
						</p>
						<p>
							<strong>Amour</strong>&nbsp;:
							${horoscope.amourPred.chance}&nbsp;|&nbsp;${horoscope.amourPred.description}
						</p>
						<p>
							<strong>Santé</strong>&nbsp;:
							${horoscope.santePred.chance}&nbsp;|&nbsp;${horoscope.santePred.description}
						</p>
						<h4>Médium</h4>
						<p>
							${horoscope.medium.civilite}&nbsp;${horoscope.medium.nom}
						</p>
					</div>
					</c:forEach>

				</div> <!-- end .panel-body -->
			</div> <!-- end .panel -->
		</div> <!-- end column -->
		
	</div> <!-- end .row -->

	<div class="row">
		<section class="panel panel-default">
			<div class="panel-heading">
				<h3>Nouvel horoscope (TODO: ne montrer que les prédictions non-utilisées ?)</h3>
			</div>
			<div class="panel-body">

				<form action="<%=request.getAttribute("URL_PREFIX")%>/horoscope-traitement?clientId=${client.id}" method="post" id="creer-horoscope" class="form-horizontal" role="form">
					<div class="col-sm-6">
						<div class="form-group row">
							<label for="prediction-travail" class="col-sm-2 control-label">Travail</label>
							<%= listerPredictions(predictionsTravail, "travail") %>
						</div>
						<div class="form-group row">
							<label for="prediction-amour" class="col-sm-2 control-label">Amour</label>
							<%= listerPredictions(predictionsAmour, "amour") %>
						</div>
						<div class="form-group row">
							<label for="prediction-sante" class="col-sm-2 control-label">Santé</label>
							<%= listerPredictions(predictionsSante, "sante") %>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group row">
							<label for="choix-medium" class="col-sm-2 control-label">Medium</label>
							<div class="col-sm-10">
								<select name = "choix-medium" class = "choix-medium form-control">
									<c:forEach items = "${mediums}" var = "medium">
										<option value = "${medium.id}">${medium.civilite} ${medium.nom} (${medium.talent})</option>
									</c:forEach>
								</select>
								<c:forEach items="${mediums}" var="medium">
									<span id="medium-${medium.id}" class = "details-medium">
										${medium.biographie}
									</span>
								</c:forEach>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" value="Envoyer" class="btn btn-primary">
								<a href="<%=request.getAttribute("URL_PREFIX")%>/clients" class="btn">Annuler</a>
							</div>
						</div>
					</div>
				</form>

			</div> <!-- end panel-body -->
		</section>
	</div> <!-- end .row -->
</div> <!-- end column -->
<%@include file="footer.jspf" %>