<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
<title>Editer le contenu d'un article</title>
<script type="application/javascript" src="js/ext/jquery.textarea-expander.js"></script>
<script type="application/javascript" src="js/int/help.js"></script>
<script type="application/javascript" src="js/int/autosave.js"></script>
<script>
    $(function() {
        $( "#tabs" ).tabs();
    });
</script>
</head>
<body>
    <ryctag:pageheadertitle title="Éditer le contenu de l'article">
    <c:forEach items="${parentsPath}" var="subarticle">
 		<c:if test="${article.title != subarticle.title}">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
		</c:if>
	</c:forEach>
	<ryctag:breadcrumbelement label="${article.title} - Edition" />
 </ryctag:pageheadertitle>
    <%-- Help handle --%>
    <div id="helphandle"><span>?</span><div><%--contains the helptext--%></div></div>
<%--     <form:form modelAttribute="article" action="article/contenteditsubmit"> --%>
		
		<div id="tabs">
		<ul>
        	<li><a href="#tabs-1">Contenu</a></li>
        	<li><a href="#tabs-2">Résumé</a></li>
        	<li><a href="#tabs-3">A classer</a></li>
    	</ul>
    	<div id="tabs-1">
    		<form action="article/contenteditsubmit">
    			<input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span>
    		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
				<textarea name="content" class="expand autosaveable" cols="60" rows="3" style="width:100%" >${article.content}</textarea>
				<input type="hidden" value="${article.id}" name="id" />
				<input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span>
			</form>
		</div>
		<div id="tabs-2">
			<form action="article/contenteditsubmit">
    			<input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span>
    		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
				<textarea name="summary" class="expand autosaveable" cols="60" rows="3" style="width:100%">${article.summary}</textarea>
				<input type="hidden" value="${article.id}" name="id" />
				<input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span>
			</form>
		</div>
		<div id="tabs-3">
			<form action="article/contenteditsubmit">
    			<input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span>
    		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
				<textarea name="toClassify" class="expand autosaveable" cols="60" rows="3" style="width:100%">${article.toClassify}</textarea>
				<input type="hidden" value="${article.id}" name="id" />
				<input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span>
			</form>
		</div>
		</div>
</body>
</html>