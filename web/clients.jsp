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
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
	List<Client> clients = (List<Client>)request.getAttribute("clients");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Predict'IF | Liste des clients</title>
    </head>
    <body>
        <h1>Predict'IF | Choisissez un client à traiter</h1>
		
		<a href="/PredictIF/logout"><button>Déconnexion</button></a>
		
		<!-- Liste des clients -->
		<ul>
			<c:forEach items="${clients}" var="client">
				<li>${client.nom} ${client.prenom}</li>
			</c:forEach>
		</ul>
    </body>
</html>
