<%-- 
    Document   : inscription
    Created on : Apr 20, 2014, 12:35:12 PM
    Author     : Merlin
--%>
<%@page import="metier.modele.Client"%>
<%@page import="metier.modele.Medium"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	request.setAttribute("title", "Sélection des mediums");
	
	Client client = (Client)request.getSession().getAttribute("client");
	List<Medium> mediums = (List<Medium>)request.getAttribute("mediums");
	
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
				<small>Sélection de vos mediums favoris</small>
			</h1>
		</header>
		
		<div class="alert alert-info">
			${client.civilite} ${client.nom}, veuillez choisir vos mediums favoris.<br>
			<em>Si vous ne souhaitez pas les choisir, nous le ferons pour vous.</em>
		</div>
				
		<c:if test="${!empty message}">
			<div class="alert alert-info">${message}</div>
		</c:if>

		<form action="<%=request.getAttribute("URL_PREFIX")%>/choix-medium-traitement" method="post" class="form-horizontal" role="form">
			<div class="row">
				<div class="col-sm-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							Mediums disponibles
						</div>
						<div class="panel-body">
							<select name = "choix-medium" class = "choix-medium form-control">
								<c:forEach items = "${mediums}" var = "medium">
									<option value = "${medium.id}">${medium.civilite} ${medium.nom} (${medium.talent})</option>
								</c:forEach>
							</select>
							<c:forEach items="${mediums}" var="medium">
								<div id="medium-${medium.id}" class = "details-medium">
									<blockquote style="min-height:110px;">
										<p>${medium.biographie}</p>
									</blockquote>
									
									<button type="button" class="choisir-medium btn btn-default">
										Choisir
									</button>
									<button type="button" class="retirer-medium btn btn-default">
										Retirer
									</button>
								</div>
							</c:forEach>
						</div>
					</div>
				</div> <!-- end column -->

				<div class="col-sm-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							Vos mediums
						</div>
						<div class="panel-body">
							<select name="mediums-choisis" id="mediums-choisis" multiple class="form-control" style="min-height:200px;">
								<c:forEach items = "${mediums}" var = "medium">
									<option value = "${medium.id}">${medium.civilite} ${medium.nom} (${medium.talent})</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div> <!-- end column -->
			</div> <!-- end .row -->
			
			<c:if test="${!empty erreur}">
				<div class="alert alert-warning">${erreur}</div>
			</c:if>

			<div class="form-group">
				<div class="col-sm-12">
					<input type="submit" value="Valider" class="btn btn-success center-block">
				</div>
			</div>
		</form>
				
	</div>
				
<%@include file="footer.jspf" %>