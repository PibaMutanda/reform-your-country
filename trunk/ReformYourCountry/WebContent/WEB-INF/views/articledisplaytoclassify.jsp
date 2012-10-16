<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" href="css/ext/jquery-bubble-popup-v3.css"  type="text/css" />
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>
<script type="text/javascript" src="js/int/untranslate.js"></script>
</head>
<body>
 <ryctag:pageheadertitle title="${article.title} - A classer">
 <c:forEach items="${parentsPath}" var="subarticle">
 		<c:if test="${article.title != subarticle.title}">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
		</c:if>
	</c:forEach>
	<ryctag:breadcrumbelement label="${article.title} - A classer" />
 </ryctag:pageheadertitle>
<div style="display:inline-block"><!-- DO NOT REMOVE OTHERWISE TITLE AND MENU ARE UPSIDE DOWN	 -->
	<ul style="float:left">
		<li><a href="/article/${article.url}">Retour a l'article</a></li>
	</ul>
	<div class="article-options">
		<ul class="list sitemap-list">
			<li><a href="/article/edit?id=${article.id}">Editer l'article</a></li>
			<li><a href="/article/parentedit?id=${article.id}">Editer l'article parent</a></li>
			<li><a href="/article/contentedit?id=${article.id}">Editer le contenu de l'article</a></li>
		</ul>	
	</div>
	<div class="article-title" style>
		<span class="tooltip" data-tooltip='identifiant de cet article pour utilisation dans la balise [link article="${article.shortName}"]'>${article.shortName}</span>   <!--  Tooltip avec "identifiant de cet article pour utilisation dans la balise [link article="identifiant"]" -->
	</div>
</div>
 <hr/>
<div class="article_content">
${articleToClassify}
</div>
</body>
</html>