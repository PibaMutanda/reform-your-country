<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Créer un article</h1>
<form action="articlecreate" method="POST">
Nom:<input type="text" name="name"> <input type="submit" value="Creer article"> <input type="submit" value="Annuler">

</form>

<br/>
Parent:
<br/>
<c:forEach items="${listArticles}" var="article">
		<input type='radio' name='category'/>${article.getTitle()}<br/>
		<c:choose>
		
			<c:when test="${article.getChildren().isEmpty()}">
				<c:out value="no sub-category"></c:out><br/>
			</c:when>
			<c:otherwise>
			   <c:forEach items="${article.getChildren()}" var="children">
			    &nbsp&nbsp&nbsp&nbsp<input type='radio' name='sub-category'/>${children.getTitle()}<br/>
			   </c:forEach>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</body>
</html>