<%-- 
    Document   : index
    Created on : Mar 31, 2014, 5:33:36 PM
    Author     : Merlin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "Accueil");
%>
<%@include file="head.jspf" %>

		<div class="container-fluid">
			<!-- Header -->
			<div class="jumbotron">
				<h1>
					<a href="/PredictIF/">Predict'IF</a>
					<small>Pour un avenir serein</small>
				</h1>
				
				<div class="col-sm-8 col-sm-push-2">
					<img src="/PredictIF/static/star-trails.jpg" alt="Predict'IF" class="img-responsive img-rounded">
					
					<div class="button-holder">
						<a href="client/" class="btn btn-primary btn-lg">Enregistrement client</a>
					</div>
				</div>
				
				<div class="clearfix"></div>
			</div>
			

			<footer>
				<a href="employe/" class="btn btn-default btn-xs">Connexion employés</a>
				<p>© Predict'IF 2014</p>
			</footer>

		</div> <!-- end .container-fluid -->
	</body>
</html>