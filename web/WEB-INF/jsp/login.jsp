<%-- 
    Document   : login
    Created on : Apr 7, 2014, 2:55:41 PM
    Author     : Merlin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setAttribute("title", "Identification employ�");
	
	String message = (String)request.getAttribute("message");
	String erreur = (String)request.getAttribute("erreur");
%>
<%@include file="header.jspf" %>

<h2>Identification employ�</h2>

<c:if test="${!empty message}">
	<div class="alert alert-info">${message}</div>
</c:if>

<form action="<%=request.getAttribute("URL_PREFIX")%>/login" method="post" class="form-horizontal" role="form">
	<c:if test="${!empty erreur}">
		<div class="alert alert-warning">${erreur}</div>
	</c:if>
	
		<div class="form-group">
		<label for="username" class="col-sm-2 control-label">E-mail</label>
		<div class="col-sm-10">
			<input type="text" name="username" class="form-control" placeholder="E-mail" required>
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Mot de passe</label>
		<div class="col-sm-10">
			<input type="password" name="password" class="form-control" placeholder="Mot de passe">
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="Connexion" class="btn btn-success">
		</div>
	</div>
</form>

<%@include file="footer.jspf" %>