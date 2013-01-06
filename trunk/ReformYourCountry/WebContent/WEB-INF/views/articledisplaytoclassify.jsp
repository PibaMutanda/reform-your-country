<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>
<script type="text/javascript" src="js/int/untranslate.js"></script>
</head>
<body>
 <ryctag:breadcrumb>
	 <c:forEach items="${parentsPath}" var="subarticle">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
	 </c:forEach>
	 <ryctag:breadcrumbelement label="${article.title}" link="/article/${article.url}" />
	 <ryctag:breadcrumbelement label="A classer"></ryctag:breadcrumbelement>
 </ryctag:breadcrumb>
 <ryctag:pageheadertitle title="${article.title} - A classer"/>
 

<div style="font-size:12px">
 <ryc:conditionDisplay privilege="MANAGE_ARTICLE" >

	 <%@include file="articlelinkmenu.jsp" %>	
 </ryc:conditionDisplay>	
</div>	
<br/>			
			
	
<div class="article-title" >
	<span title='identifiant de cet article pour utilisation dans la balise [link article="${article.shortName}"]'>${article.shortName}</span>   <!--  Tooltip avec "identifiant de cet article pour utilisation dans la balise [link article="identifiant"]" -->
</div>

<br/>
 <hr/>
<div class="article_content">
${articleToClassify}
</div>
</body>
</html>