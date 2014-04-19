<%-- 
    Document   : hello
    Created on : Mar 31, 2014, 5:33:36 PM
    Author     : Merlin
--%>
<%
	request.setAttribute("title", "Accueil");
%>
<%@include file="header.jspf" %>

<h2>Hello index!</h2>

<a href="<%=request.getAttribute("URL_PREFIX")%>/clients">Liste des clients</a>

<%@include file="footer.jspf" %>