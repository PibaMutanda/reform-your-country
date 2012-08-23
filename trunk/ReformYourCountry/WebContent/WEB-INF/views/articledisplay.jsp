<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>

<html>
<head>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
<style type="text/css">@import "css/jquery.countdown.css";</style> 
<script type="text/javascript" src="js/jquery.countdown.js"></script>

<title>${article.title}</title>
</head>

<body>

<h2>
<c:forEach items = "${parentsTree}" var ="article">
    <a href ="article?id=${article.id}">${article.title}></a>
</c:forEach></h2>

	<h2>${article.title}</h2>
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

	

	<hr/>
	<c:if test="${article.published}">
		${articleContent}
	</c:if>
	<c:if test="${!article.published}">
	
		<script type="text/javascript">
			$(function () {
					var publishDay = new Date();
					publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
					$('#defaultCountdown').countdown({until: publishDay, format: 'dH',layout: ' {dn} {dl} and {hn} {hl} until the article is published'});
					$('#year').text(austDay.getFullYear());
			});
		</script>
		<div id="defaultCountdown"></div>
			
	<ryc:conditionDisplay privilege="EDIT_ARTICLE">
		
			${articleContent}
		
	</ryc:conditionDisplay>
	</c:if>
</body>
</html>   

