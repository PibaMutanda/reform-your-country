<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<meta name="description" lang="${p_lang}" content="${p_articlelist_description}"/>
<meta name="robots" content="index, follow"/>	
<meta name="googlebot" content="noarchive" />

</head>
<body>
<ryctag:pageheadertitle title="Liste des articles"></ryctag:pageheadertitle>

<ryc:conditionDisplay privilege="MANAGE_ARTICLE">
<div style="display:inline-block">
<div class="article-options">
<!-- 			    <ul class="list sitemap-list"> -->
					 <a href="/article/create">Créer un article</a>
					 - <a href="/article/image">Images d'articles</a>
					 - <a href="/article/version/changelog">Dernières modifications</a>
<!-- 				</ul>	 -->
</div>
</div>
<hr/>
</ryc:conditionDisplay>
<img class="generatepdflist" style="cursor:pointer;" src="images/pdf_button.png"></img>
<div class="article_content">
<ryc:articlesList/>
</div>
</body>
</html>