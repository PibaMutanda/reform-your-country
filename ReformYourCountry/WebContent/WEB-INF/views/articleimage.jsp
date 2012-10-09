<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<title>Librairie d'images pour les articles</title>
</head>
<body>


	<!-- If there is a error message, show it! -->
	<c:choose>
		<c:when test="${!empty errorMsg}">
			<div class="error">Error:${errorMsg}</div>
		</c:when>
	</c:choose>

	
	<ryc:conditionDisplay privilege="EDIT_ARTICLE">
			<!-- Uploader form -->
			<div class="">(Attention l'image doit faire moins de 1.5Mo)</div>
			<form method="post" action="article/imageadd"
				enctype="multipart/form-data">
				<input type="file" name="file" value="${totalFiles.size()}" /><br>
				<input type="submit" value="Ajouter" /><br>
			</form>
	</ryc:conditionDisplay>


	<%--need a scriplet because didn't to get a way to display an array length in EL --%>
	<div>La librairie contient ${listFiles.size()} images</div>


	<!-- For each filename, create an img tag, every 3 pics, create a new line -->
	<%
	    int i = 0;
	%>
	<table cellspacing="10">
		<c:forEach items="${listFiles}" var="image">
			<c:choose>
				<c:when test="<%=i == 0%>">
					<tr>
				</c:when>
				<c:when test="<%=i % 3 == 0%>">
					</tr>
					<tr>
				</c:when>
			</c:choose>
			<td align="center" valign="top"><img
				src="gen/article/${image.getName()}" width="200" /> <br>
				${image.getName()}<br>
				<ryc:conditionDisplay privilege="EDIT_ARTICLE">
						<a href="article/imagedel?fileName=${image.getName()}">remove</a>
				</ryc:conditionDisplay>
			</td>
			<%
			    i++;
			%>
		</c:forEach>
		</tr>
	</table>

</body>
</html>
