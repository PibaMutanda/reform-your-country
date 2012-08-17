<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/articletreeradio.tld' prefix='ryc'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.li{
list-style-type:none; 
}

</style>

</head>
<body>
<h1>Créer un article</h1>
<form  method="Post" action="articlecreatesubmit">
Title: <input type="text" name="title"> <input type="submit" value="Creer article"><br/>
Parent:
<br/>

<!--  <c:forEach items="${listArticles}" var="article">

		<input type='radio' name='category' value="${article.getId()}"/>${article.getTitle()}
		
			<c:if test="${article.getChildren().isEmpty()==false}">
			    
				<c:forEach items="${article.getChildren()}" var="children">
				 		&nbsp&nbsp<input type='radio' name='category' value="${children.getId()}" />${children.getTitle()}<br/>
							
				</c:forEach>
				
			</c:if>
			
		
		
	</c:forEach>
	-->
<ryc:articlesTreeRadio/>
</form>	
</body>
</html>