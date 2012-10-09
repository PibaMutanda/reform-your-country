<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<meta name="description" lang="fr" content="Articles sur l'enseignement, les problèmes et les réformes du système scolaire."/>
<meta name="robots" content="index, follow"/>	
<meta name="googlebot" content="noarchive" />
</head>
<body>
<ryctag:pageheadertitle title="Liste des articles"></ryctag:pageheadertitle>

<ryc:conditionDisplay privilege="EDIT_ARTICLE">
<div class="article-options">
			    <ul class="list sitemap-list">
					 <li><a href="/article/create">Créer un article</a></li>
					 <li><a href="/article/image">Images d'articles</a></li>
				</ul>	
</div>
</ryc:conditionDisplay>
<ryc:articlesTree link="true" />
</body>
</html>