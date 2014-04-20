<%-- 
    Document   : clients
    Created on : Apr 7, 2014, 3:36:38 PM
    Author     : Merlin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="dao.ClientUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="metier.modele.Client"%>
<%@page import="java.util.List"%>

<%
	request.setAttribute("title", "Liste des clients");
	List<Client> clients = (List<Client>)request.getAttribute("clients");
%>

<%@include file="header.jspf" %>

<h2>Choisissez un client à traiter</h2>
TODO: montrer en premier les clients non-traités.

<!-- Liste des clients -->
<section class="col-xs-12">
	<div class="row">
		<div class="col-xs-8">
			<input type="text" placeholder="Filtrer" class="form-control">
		</div>
		<div class="col-xs-4">
			<select class="form-control">
				<option value="">Plus ancien</option>
			</select>
		</div>
	</div>
	
	<ul class="liste-clients list-group" style="margin-top:15px">
		<c:forEach items="${clients}" var="client">
			<li class="list-group-item">
				<a href="#client-${client.id}" data-container="body" data-toggle="popover" data-placement="right" data-content="TOOD : infos client">
					${client.nom} ${client.prenom}
				</a>
			</li>
		</c:forEach>
	</ul>
</section>
<!-- Détails des clients (panneaux affichés dynamiquement) -->
<section>
	<c:forEach items="${clients}" var="client">
		<div id="client-${client.id}" class="details-client holder">
			<h3>${client.civilite} ${client.prenom} ${client.nom}</h3>
			<ul>
				<li>Date de naissance : <fmt:formatDate value="${client.dateNaissance}" pattern="dd/MM/yyyy"/></li>
				<li>Adresse : ${client.adresse}</li>
				<li>Téléphone : ${client.telephone}</li>
				<li>E-mail : ${client.email}</li>
			</ul>
			<a href="<%=request.getAttribute("URL_PREFIX")%>/horoscope?clientId=${client.id}"><button class="btn btn-primary">Créer un horoscope</button></a>
		</div>
	</c:forEach>
</section>

<%@include file="footer.jspf" %>