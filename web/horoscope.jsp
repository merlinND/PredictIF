<%-- 
    Document   : horoscope.jsp
    Created on : Mar 31, 2014, 5:33:36 PM
    Author     : Merlin
--%>
<%@page import="metier.modele.Client"%>
<%
	request.setAttribute("title", "Création d'horoscope");
	Client client = (Client)request.getAttribute("client");
%>
<%@include file="WEB-INF/jspf/header.jspf" %>

<h2>Création d'un horoscope pour ${client.civilite} ${client.nom}</h2>

<section>
	<h3>Infos clients</h3>
</section>

<section>
	<h3>Anciens horoscopes</h3>
</section>

<section>
	<section>
		<h3>Nouvel horoscope</h3>

		<form action="<%=request.getAttribute("URL_PREFIX")%>/traitementHorosocope" method="post">
			<label>Travail</label>
			<label>Amour</label>
			<label>Santé</label>
			<label>Medium</label>

			<input type="submit" value="Envoyer">
			<a href="<%=request.getAttribute("URL_PREFIX")%>/clients">Annuler</a>
		</form>
	</section>
	
	<section>
		<h3>Prévisualisation</h3>
	</section>
</section>

<%@include file="WEB-INF/jspf/footer.jspf" %>