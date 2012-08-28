<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>

<html>
<head>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.countdown.css"/>
<script type="text/javascript" src="js/jquery.countdown.js"></script>

<title>${article.title}</title>
</head>

<body>
	<!-- BREADCRUMB -->
	<h2>
		<c:forEach items="${parentsPath}" var="article">
			<a href="article?id=${article.id}">${article.title}></a>
		</c:forEach>
	</h2>


	<h1>${article.title}</h1>
	release date: ${article.releaseDate}<br/>


	<ryc:conditionDisplay privilege="EDIT_ARTICLE">
		<form action=articleedit method="GET">
			<input type="hidden" name="id" value="${article.id}"> <input
				type="submit" value="Editer" />
		</form>
		<form action="articleparentedit" method="GET">
		<input type="hidden" name="id" value="${article.id}" /> <input type="submit"
			value="Editer parent" />
		</form>
	</ryc:conditionDisplay>

	
    <!-- COUNT DOWN -->
	<hr/>
	<c:if test="${!article.published}">
	
		<script type="text/javascript">
			$(function () {
					var publishDay = new Date();
					publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
					function reload() { 
						window.location.reload(); 
					} 
					$('#defaultCountdown').countdown({until: publishDay, onExpiry:reload, format: 'dHMS',layout: ' {dn} {dl} , {hn} {hl} , {mn} {ml} and {sn} {sl} until the article is published   <<<<<<<<<< DESIGNER, PLEASE IMPROVE (discret si droit de voir le texte, en grand sinon)'});
			});
		</script>
		<div id="defaultCountdown"></div>

	</c:if>
	
	<!-- ARTICLE CONTENT -->
	<c:if test="${showContent}">
		${articleContent}
	</c:if>
</body>
</html>   

