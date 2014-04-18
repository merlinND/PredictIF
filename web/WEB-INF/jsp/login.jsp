<%-- 
    Document   : login
    Created on : Apr 7, 2014, 2:55:41 PM
    Author     : Merlin
--%>
<%
	request.setAttribute("title", "Identification employé");
%>
<%@include file="header.jspf" %>

<h2>Identification employé</h2>

<p>
	<%= request.getAttribute("message") %>
</p>

<form action="<%=request.getAttribute("URL_PREFIX")%>/login" method="post">
	<input type="text" name="username" placeholder="Nom d'utilisateur">
	<input type="password" name="password" placeholder="password">

	<span class="erreur"><%= request.getAttribute("erreur") %></span>

	<input type="submit" value="Connexion">
</form>

<%
// Nettoyer les erreurs
request.setAttribute("erreur", "");
%>

<%@include file="footer.jspf" %>