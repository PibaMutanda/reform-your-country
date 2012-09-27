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
<meta name="robots" content="index, follow"/>
<meta name="description" content="${article.description}"/>
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
<div style="display:block;margin:0;">

	<div class="article-info">
		${article.shortName}<br/>  
		publish date: ${displayDate}
	</div>
	<div class="article-options">
	<ul class="list sitemap-list">
			 <li><a href="articleedit?id=${article.id}">Editer l'article</a></li>
			 <li><a href="articleparentedit?id=${article.id}">Editer l'article parent</a></li>
		</ul>	

	</div>
</div>
	<div style="position:relative;display: inline-block; margin:0;">
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
	

	<div>
	<!-- ARTICLE CONTENT -->
	<c:if test="${showContent}">
		${articleContent}
	</c:if>
	<c:if test="${!article.publicView}">
		Cet article n'est pas disponible au public.
	</c:if>
	</div>
	</div>
</body>
</html>   

