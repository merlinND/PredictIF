<%-- 
    Document   : hello
    Created on : Mar 31, 2014, 5:33:36 PM
    Author     : Merlin
--%>

<%@page import="metier.modele.Medium"%>
<%@page import="dao.MediumUtil"%>
<%@page import="dao.JpaUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dao.ClientUtil"%>
<%@page import="metier.modele.Client"%>
<%@page import="metier.service.Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello World!</title>
    </head>
    <body>
        <h1>Hello World!</h1>
		<p>
			<%
				
				List<Client> clients = ClientUtil.getListClient();
				out.println("Liste des clients :");
				for (Client c : clients)
					out.println(c.getNom() + "<br/>");
				
			%>
		</p>
    </body>
</html>
