<%-- 
    Document   : login
    Created on : Apr 7, 2014, 2:55:41 PM
    Author     : Merlin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Predict'IF | Identification employé</title>
    </head>
    <body>
        <h1>Predict'IF | Identification employé</h1>
		
		<p>
			<%= request.getAttribute("message") %>
		</p>
		
		<form action="/PredictIF/login" method="post">
			<input type="text" name="username" placeholder="Nom d'utilisateur">
			<input type="password" name="password" placeholder="password">
			
			<span class="erreur"><%= request.getAttribute("erreur") %></span>
			
			<input type="submit" value="Connexion">
		</form>
    </body>
</html>

<%
// Nettoyer les erreurs
request.setAttribute("erreur", "");
%>