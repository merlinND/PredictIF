<%-- 
    Document   : index
    Created on : Mar 31, 2014, 5:33:36 PM
    Author     : Merlin
--%>
<%
	request.setAttribute("title", "Accueil");
%>
<%@include file="WEB-INF/jsp/head.jspf" %>

	<h2>Hello index!</h2>

	<p>
		<a href="client/">Enregistrement client</a>
	</p>
	<p>
		<a href="employe/">Connexion employés</a>
	</p>

<%@include file="WEB-INF/jsp/footer.jspf" %>