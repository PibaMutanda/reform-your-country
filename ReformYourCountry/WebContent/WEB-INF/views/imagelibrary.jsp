<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Librairie d'images pour les articles</title>
</head>
<body>


<!-- If there is a error message, show it! -->
<c:choose>
<c:when test="${!empty errorMsg}"> <div class="error">Error:${errorMsg}</div></c:when>
</c:choose>

<!-- Uploader form -->
<div class="">(Attention l'image doit faire moins de 1.5Mo)</div>
<form method="post" action="add-article-images" enctype="multipart/form-data">
    <input type="file" name="file" value="${file.getInputStream()}"/><br>
    <input type="submit" value="Ajouter" /><br>
    <input type="hidden" name="type" value="article\"/>
</form>

<%--need a scriplet because didn't to get a way to display an array length in EL --%>
<div>La librairie contient <%Object[] array=(Object[])request.getAttribute("listFiles"); 
							out.print(array.length); %> images </div>


<!-- For each filename, create an img tag, every 3 pics, create a new line -->
    <%int id=0; %>
	<table  cellspacing="10" >
	
	<c:forEach items="${listFiles}" var="image">
	<c:choose>
	<c:when test="<%=id==0%>">
		<tr>
	</c:when>
	<c:when test="<%=id % 3==0%>">
		</tr>
		<tr>
	</c:when>
	</c:choose>
	<td align="center" valign="top">
		<img src="gen/article/${image.getName()}" width="200"/>
		<br>
		${image.getName()}<br>
		<a href="deleteimage?path=${image.getName()}">remove</a>
	</td>
	<%id=id+1; %>
	</c:forEach>
	</tr>
	</table>

</body>
</html>	