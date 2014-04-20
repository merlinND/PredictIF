<%-- 
    Document   : inscription
    Created on : Apr 20, 2014, 12:35:12 PM
    Author     : Merlin
--%>
<%@page import="metier.modele.Client"%>
<%@page import="metier.modele.Medium"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	request.setAttribute("title", "Confirmation de votre inscription");
	
	Client client = (Client)request.getSession().getAttribute("client");
%>
<%@include file="head.jspf" %>

	<div class="container-fluid">
		<!-- Header -->
		<header class="page-header">
			<h1>
				<a href="<%=request.getAttribute("URL_PREFIX")%>/">Predict'IF</a>
				<small>Confirmation de votre inscription</small>
			</h1>
		</header>
		
		<div class="alert alert-success">
			${client.civilite} ${client.nom}, votre inscription a bien été prise en compte.
		</div>
				
<%@include file="footer.jspf" %>