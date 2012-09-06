<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

</body>
</html>