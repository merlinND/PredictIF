<%-- 
    Document   : login
    Created on : Apr 7, 2014, 2:55:41 PM
    Author     : Merlin
--%>
<%
	request.setAttribute("title", "Identification employé");
	
	String message = (String)request.getAttribute("message");
	String erreur = (String)request.getAttribute("erreur");
%>
<%@include file="header.jspf" %>

<h2>Identification employé</h2>

<c:if test="${!empty message}">
	<div class="alert alert-info">${message}</div>
</c:if>

<form action="<%=request.getAttribute("URL_PREFIX")%>/login" method="post" class="form-horizontal" role="form">
	<div class="form-group">
		<label for="username" class="col-sm-2 control-label">Nom d'utilisateur</label>
		<div class="col-sm-10">
			<input type="text" name="username" class="form-control" placeholder="Nom d'utilisateur">
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Mot de passe</label>
		<div class="col-sm-10">
			<input type="password" name="password" class="form-control" placeholder="Mot de passe">
		</div>
	</div>

	<c:if test="${!empty erreur}">
		<div class="alert alert-warning">${erreur}</div>
	</c:if>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="Connexion" class="btn btn-success">
		</div>
	</div>
</form>

<%
// Nettoyer les erreurs et messages
request.setAttribute("erreur", "");
request.setAttribute("message", "");
%>

<%@include file="footer.jspf" %>