<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html>

<body>
   <ryctag:breadcrumb>
                <ryctag:breadcrumbelement label="${article.title}" link="/article/${article.url}" />
                <ryctag:breadcrumbelement label="vidéos de ${article.title}" link="/video/manager?id=${article.id}" />
   </ryctag:breadcrumb>                
	<c:choose>
		<c:when test="${video.id == null}">
			<ryctag:pageheadertitle title="Ajouter une vidéo" />
		</c:when>
		<c:otherwise>
			<ryctag:pageheadertitle title="Editer une vidéo" />
		</c:otherwise>
	</c:choose>
        
    <ryctag:form action="/video/editsubmit" modelAttribute="video">
         <ryctag:input path="idOnHost" label="identifiant video"/>
		 <input type="hidden" name="idVideo" value="${video.id}"/>
		 <input type="hidden" name="idArticle" value="${video.article.id}"/>
		 <input type="submit" value="sauver" />
    </ryctag:form>
</body>
</html>