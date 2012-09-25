<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<link rel="stylesheet" href="css/jquery-bubble-popup-v3.css"  type="text/css" />
<link rel="stylesheet" href="css/jquery.countdown.css" type="text/css"/>
<script type="text/javascript" src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>
<script type="text/javascript" src="js/ext/jquery.countdown.js"></script>
<script type="text/javascript" src="js/ext/jquery.countdown-fr.js"></script>
<title>${article.title}</title>
</head>

<body>


 <ryctag:pageheadertitle title="${article.title}" breadcrumb="true">
 	<c:forEach items="${parentsPath}" var="subarticle">
 		<c:if test="${article.title != subarticle.title}">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="article/${subarticle.url}" />
		</c:if>
	</c:forEach>
	<ryctag:breadcrumbelement label="${article.title}" />
 </ryctag:pageheadertitle>





<div  class="dialog"  > Loading ...</div>


	<div class="article-info">
		${article.shortName}<br/>  
		publish date: ${displayDate}
	</div>
	<div class="article-options">
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
	</div>
    <!-- COUNT DOWN -->

	<c:if test="${!article.published&&article.publicView}">
	<div  class="opener" id="defaultCountdown"></div>
		<script type="text/javascript">
		$(document).ready(function () {
					var publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
					function reload() { 
						window.location.reload(); 
					} 
					$('#defaultCountdown').countdown({until: publishDay, onExpiry:reload, format: 'dHMS',layout: ' {dn} {dl} , {hn} {hl} , {mn} {ml} et {sn} {sl} jusqu\'à ce que l\'article soit publié   <<<<<<<<<< DESIGNER, PLEASE IMPROVE (discret si droit de voir le texte, en grand sinon)'});
			});
		</script>
		
	</c:if>
	

	<div class="">
	<!-- ARTICLE CONTENT -->
	<c:if test="${showContent}">
		${articleContent}
	</c:if>
	<c:if test="${!article.publicView}">
		Cet article n'est pas disponible au public.
	</c:if>
	</div>
</body>
</html>   

