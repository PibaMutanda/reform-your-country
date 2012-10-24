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
			<li><a href="/video/edit">Ajouter video</a></li>
		</ul>
	</div>

	<table>
		<c:forEach items="${article.videos}" var="video">
			<tr>
				<td><a href="${video.idOnHost}">${video.idOnHost}</a></td>
				<td>
					<form action="/article/videodel" method="get">
						<input type="submit" name="videoremove" value="supprimer video" />
						<input type="hidden" name="videoId" value="${video.id}" /> <input
							type="hidden" name="articleId" value="${article.id}" />
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
