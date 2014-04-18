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

<h2>Choisissez un client � traiter</h2>
TODO: montrer en premier les clients non-trait�s.

<!-- Liste des clients -->
<ul class="liste-clients">
	<input type="text" placeholder="Filtrer">
	<select>
		<option value="">Plus ancien</option>
	</select>
	
	<c:forEach items="${clients}" var="client">
		<li><a href="#client-${client.id}">${client.nom} ${client.prenom}</a></li>
	</c:forEach>
</ul>

<!-- D�tails des clients (panneaux affich�s dynamiquement) -->
<section>
	<c:forEach items="${clients}" var="client">
		<div id="client-${client.id}" class="details-client">
			<h3>${client.civilite} ${client.prenom} ${client.nom}</h3>
			<ul>
				<li>Date de naissance : <fmt:formatDate value="${client.dateNaissance}" pattern="dd/MM/yyyy"/></li>
				<li>Adresse : ${client.adresse}</li>
				<li>T�l�phone : ${client.telephone}</li>
				<li>E-mail : ${client.email}</li>
			</ul>
			<a href="<%=request.getAttribute("URL_PREFIX")%>/horoscope?clientId=${client.id}"><button>Cr�er un horoscope</button></a>
		</div>
	</c:forEach>
</section>

<%@include file="footer.jspf" %>