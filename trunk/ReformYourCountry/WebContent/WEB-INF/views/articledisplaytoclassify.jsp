<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
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
		<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
 </c:forEach>
 <ryctag:breadcrumbelement label="${article.title}" link="/article/${article.url}" />
 <ryctag:breadcrumbelement label="A classer"></ryctag:breadcrumbelement>
 </ryctag:pageheadertitle>
 

<div style="font-size:12px">
 <ryc:conditionDisplay privilege="MANAGE_ARTICLE" >

	<a href="/article/edit?id=${article.id}">éditer l'article</a>&nbsp-&nbsp
	<a href="/article/parentedit?id=${article.id}">éditer l'article parent</a>&nbsp-&nbsp
	<a href="/article/contentedit?id=${article.id}">éditer le contenu de l'article</a>
 </ryc:conditionDisplay>	
</div>	
<br/>			
			
	
<div class="article-title" >
	<span class="tooltip" data-tooltip='identifiant de cet article pour utilisation dans la balise [link article="${article.shortName}"]'>${article.shortName}</span>   <!--  Tooltip avec "identifiant de cet article pour utilisation dans la balise [link article="identifiant"]" -->
</div>

<br/>
 <hr/>
<div class="article_content">
${articleToClassify}
</div>
</body>
</html>