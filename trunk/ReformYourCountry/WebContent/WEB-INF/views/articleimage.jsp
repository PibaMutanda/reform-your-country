<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Librairie d'images pour les articles</title>
</head>
<body>


	<!-- If there is a error message, show it! -->
	<c:choose>
		<c:when test="${!empty errorMsg}">
			<div class="error">Error:${errorMsg}</div>
		</c:when>
	</c:choose>

	
	<ryc:conditionDisplay privilege="MANAGE_USERS">
			<!-- Uploader form -->
			<div class="">(Attention l'image doit faire moins de 1.5Mo)</div>
			<form method="post" action="articleimageadd"
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
				<ryc:conditionDisplay privilege="MANAGE_USERS">
						<a href="articleimagedel?fileName=${image.getName()}">remove</a>
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
