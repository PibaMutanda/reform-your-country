<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

</head>
<body>
	<ryctag:pageheadertitle title="${article.title}" />

	<div class="article-options">
		<ul class="list sitemap-list">
			<li><a href="/video/create?idArticle=${article.id}">Ajouter video</a></li>
		</ul>
	</div>

	<table>
		<c:forEach items="${article.videos}" var="video">
			<tr>
				<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${video.updatedOrCreatedOn}" /></td>

				<td><a href="http://www.youtube.com/watch?v=${video.idOnHost}">${video.idOnHost}</a></td>

				<td>
					<a href="/video/edit?idVideo=${video.id}">éditer<a>
				</td>

				<td>
					<a href="/video/delete?idVideo=${video.id}">supprimer<a>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
