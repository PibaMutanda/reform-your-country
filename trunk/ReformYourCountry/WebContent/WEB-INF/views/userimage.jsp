<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<h1>Page pour uploader une image d'un user</h1>

			<!-- If there is a error message, show it! -->
	<c:choose>
		<c:when test="${!empty errorMsg}">
			<div class="error">Error:${errorMsg}</div>
		</c:when>
	</c:choose>
	
	<div class="">(Attention l'image doit faire moins de 1.5Mo)</div>
		<form method="post" action="userimageadd" enctype="multipart/form-data">
				<input type="file" name="file" value="${totalFiles.size()}" /><br>
				<input type="submit" value="Ajouter" /><a href="redirect:user?username=${user.userName}">Annuler</a><br>
				<input type="hidden" value="${user.id}"/>
		</form>
	
		
</body>
</html>