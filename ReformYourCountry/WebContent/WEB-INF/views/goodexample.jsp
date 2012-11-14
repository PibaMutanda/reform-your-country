<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
</head>
<ryctag:pageheadertitle title="goodexamplelist" >
Insert breadcrumb elements here
</ryctag:pageheadertitle>

<c:forEach items="${article.goodExamples}" var="goodExample">
    <form action="/goodexample/edit/updatedescription" method="post">
        <div>${goodExample.title}</div>
        <div id="${goodExample.id}">${goodExample.description}</div>
    </form>
    <c:choose>
        <c:when test="${fn:length(goodExample.articles) gt 1}">
            <c:forEach items="${goodExample.articles}" var="article">
                <ul>
                    <li>${article.title}<a href="/goodexample/edit/deletearticle?goodExampleId=${goodExample.id}&articleId=${article.id}"> /delete/ </a></li>
                </ul>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <c:forEach items="${goodExample.articles}" var="article">
                <ul>
                    <li>${article.title}</li>
                </ul>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <form action="/goodexample/edit/addarticle" method="post">
        <label for="articleUrl">ajout de l'article</label><input type="text" placeholder="url" name="articleUrl" id="articleUrl"/>
        <input type="submit" value="ajouter"/> 
        <input type="hidden" name="goodExampleId" value="${goodExample.id}">
    </form>
    <script type="text/javascript">
    	CKEDITOR.replace('${goodExample.id}', {
       		customConfig : '/js/ext/ckeditor_config.js',
        	toolbar : 'goodExample'
            });
	</script>
</c:forEach>


