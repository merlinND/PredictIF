<%-- 
    Document   : hello
    Created on : Mar 31, 2014, 5:33:36 PM
    Author     : Merlin
--%>
<%
	request.setAttribute("title", "Accueil");
%>
<%@include file="WEB-INF/jspf/header.jspf" %>

<h2>Hello index!</h2>
<%@include file="WEB-INF/jspf/footer.jspf" %>