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

<form action="<%=request.getAttribute("URL_PREFIX")%>/login" method="post" class="form-horizontal" role="form">
	<div class="form-group">
		<label for="username" class="col-sm-2 control-label">Nom d'utilisateur</label>
		<div class="col-sm-10">
			<input type="text" name="username" placeholder="Nom d'utilisateur">
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Mot de passe</label>
		<div class="col-sm-10">
			<input type="password" name="password" placeholder="Mot de passe">
		</div>
	</div>

	<span class="erreur"><%= request.getAttribute("erreur") %></span>

	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="Connexion">
		</div>
	</div>
</form>

<%
// Nettoyer les erreurs
request.setAttribute("erreur", "");
%>

<%@include file="footer.jspf" %>