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

<%@include file="WEB-INF/jspf/header.jspf" %>
<h2>Choisissez un client à traiter</h2>

<!-- Liste des clients -->
<ul>
	<input type="text" placeholder="Filtrer">
	<select>
		<option value="">Plus ancien</option>
	</select>
	
	<c:forEach items="${clients}" var="client">
		<li>${client.nom} ${client.prenom}</li>
	</c:forEach>
</ul>

<!-- Détails des clients (panneaux affichés dynamiquement) -->
<section>
	<c:forEach items="${clients}" var="client">
		<div id="client-${client.id}">
			<h3>${client.civilite} ${client.prenom} ${client.nom}</h3>
			<ul>
				<li>Date de naissance : <fmt:formatDate value="${client.dateNaissance}" pattern="dd/MM/yyyy"/></li>
				<li>Adresse : ${client.adresse}</li>
				<li>Téléphone : ${client.telephone}</li>
				<li>E-mail : ${client.email}</li>
			</ul>
			<a href="<%=request.getAttribute("URL_PREFIX")%>/horoscope?clientId=${client.id}"><button>Créer un horoscope</button></a>
		</div>
	</c:forEach>
</section>


<%@include file="WEB-INF/jspf/footer.jspf" %>