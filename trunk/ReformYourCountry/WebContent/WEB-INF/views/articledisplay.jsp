<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link href="css/jquery-bubble-popup-v3.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery.countdown.css"/>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
     <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
     <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<script type="text/javascript" src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>

<script type="text/javascript" src="js/ext/jquery.countdown.js"></script>


<title>${article.title}</title>
</head>

<body>

<div  class="dialog"  > Loading ...</div>

	<%-- BREADCRUMB --%>
	<h2>
		<c:forEach items="${parentsPath}" var="article">
			<a href="article?id=${article.id}">${article.title}></a>
		</c:forEach>
	</h2>

	<h2>${article.title}</h2>
	release date: ${article.releaseDate}<br/>

	<ryc:conditionDisplay privilege="EDIT_ARTICLE">
	
		<ryctag:form action="articleedit" modelAttribute="article">
			<form:hidden path="id" value="${article.id}"/>
			<input	type="submit" value="Editer" />
		</ryctag:form>
		<ryctag:form action="articleparentedit" modelAttribute="article">
			<form:hidden path="id" value="${article.id}"/>
			<input	type="submit" value="Editer parent" />
		</ryctag:form>
	</ryc:conditionDisplay>

	
    <!-- COUNT DOWN -->
	<hr/>
	<c:if test="${!article.published}">
	<div id="defaultCountdown"></div>
		<script type="text/javascript">
		$(document).ready(function () {
					var publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
					function reload() { 
						window.location.reload(); 
					} 
					$('#defaultCountdown').countdown({until: publishDay, onExpiry:reload, format: 'dHMS',layout: ' {dn} {dl} , {hn} {hl} , {mn} {ml} and {sn} {sl} until the article is published   <<<<<<<<<< DESIGNER, PLEASE IMPROVE (discret si droit de voir le texte, en grand sinon)'});
			});
		</script>
		
	</c:if>
	
	<hr/>
	
	<!-- ARTICLE CONTENT -->
	<c:if test="${showContent}">
		${articleContent}
	</c:if>
	

</body>
</html>   

