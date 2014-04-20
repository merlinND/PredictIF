<%-- 
    Document   : inscription
    Created on : Apr 20, 2014, 12:35:12 PM
    Author     : Merlin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	request.setAttribute("title", "Inscription client");
	
	String message = (String)request.getAttribute("message");
	String erreur = (String)request.getAttribute("erreur");
	// Nettoyer les erreurs et messages
	request.setAttribute("erreur", "");
	request.setAttribute("message", "");
%>
<%@include file="head.jspf" %>

	<div class="container-fluid">
		<!-- Header -->
		<header class="page-header">
			<h1>
				<a href="<%=request.getAttribute("URL_PREFIX")%>/">Predict'IF</a>
				<small>Inscription client</small>
			</h1>
		</header>
		
		<c:if test="${!empty message}">
			<div class="alert alert-info">${message}</div>
		</c:if>

		<form action="<%=request.getAttribute("URL_PREFIX")%>/inscription-traitement" method="post" class="form-horizontal" role="form">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="civilite" class="col-sm-2 control-label">Civilité</label>
						<div class="col-sm-4">
							<select name="civilite" class="form-control">
								<option value="M.">M.</option>
								<option value="MME.">Mme.</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="nom" class="col-sm-2 control-label">Nom</label>
						<div class="col-sm-10">
							<input type="text" name="nom" class="form-control" placeholder="Nom" required>
						</div>
					</div>
					<div class="form-group">
						<label for="prenom" class="col-sm-2 control-label">Prénom</label>
						<div class="col-sm-10">
							<input type="text" name="prenom" class="form-control" placeholder="Prénom" required>
						</div>
					</div>
					<div class="form-group">
						<label for="date-de-naissance" class="col-sm-2 control-label">Date de naissance</label>
						<div class="col-sm-6">
							<input type="date" name="date-de-naissance" class="form-control" placeholder="Date de naissance" required>
						</div>
					</div>
				</div> <!-- end column -->

				<div class="col-sm-6">
					<div class="form-group">
						<label for="adresse" class="col-sm-2 control-label">Adresse</label>
						<div class="col-sm-10">
							<input type="text" name="adresse" class="form-control" placeholder="Adresse" required>
						</div>
					</div>
					<div class="form-group">
						<label for="ville" class="col-sm-2 control-label">Ville</label>
						<div class="col-sm-10">
							<input type="text" name="ville" class="form-control" placeholder="Ville" required>
						</div>
					</div>
					<div class="form-group">
						<label for="code-postal" class="col-sm-2 control-label">Code postal</label>
						<div class="col-sm-6">
							<input type="number" min="1000" name="code-postal" class="form-control" placeholder="69100" required>
						</div>
					</div>
					<div class="form-group">
						<label for="pays" class="col-sm-2 control-label">Pays</label>
						<div class="col-sm-10">
							<select name="pays" class="form-control">
								<option value="france">TODO</option>
								<option value="TODO">TODO</option>
							</select>
						</div>
					</div>
				</div> <!-- end column -->
			</div> <!-- end .row -->
			
			<hr>
			
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">E-mail</label>
						<div class="col-sm-10">
							<input type="email" name="email" class="form-control" placeholder="votre-adresse@domaine.fr" required>
						</div>
					</div>
					<div class="form-group">
						<label for="telephone" class="col-sm-2 control-label">Téléphone</label>
						<div class="col-sm-4">
							<input type="tel" name="telephone" class="form-control" placeholder="01 23 45 67 89" required>
						</div>
					</div>
				</div> <!-- end column -->
				
				
				<div class="col-sm-6">
					<div class="checkbox">
						<label class="col-sm-12">
							<input type="checkbox" name="cgu" required>
							J'accepte les <a href="http://rickrolled.fr/" target="_blank">conditions générales d'utilisation</a>
						</label>
					</div>
					<div class="checkbox">
						<label class="col-sm-12">
							<input type="checkbox" name="offres-partenaire" required>
							Je souhaite recevoir des offres des partenaires de Predict'IF
						</label>
					</div>
				</div> <!-- end column -->
			</div> <!-- end .row -->
			
			<c:if test="${!empty erreur}">
				<div class="alert alert-warning">${erreur}</div>
			</c:if>

			<div class="form-group">
				<div class="col-sm-12">
					<input type="submit" value="S'inscrire" class="btn btn-success center-block">
				</div>
			</div>
		</form>
				
	</div>
				
<%@include file="footer.jspf" %>