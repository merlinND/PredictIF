<%-- 
    Document   : clients
    Created on : Apr 7, 2014, 3:36:38 PM
    Author     : Merlin
--%>

<%@page import="dao.ClientUtil"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<c:forEach items="${clients}" var="client">
		<li>${client.nom} ${client.prenom}</li>
	</c:forEach>
</ul>
<%@include file="WEB-INF/jspf/footer.jspf" %>