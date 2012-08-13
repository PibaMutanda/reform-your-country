<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Edit an article</h1>
	Edition
	<form action="articleeditsubmit">
		<input type="submit" value="Sauver">A publier le<br />
		 Title :<input type="text" name="title" value="${a.getTitle()}"><br/>
		 Content:<br/><textarea name="content" cols=50 rows=60 >${a.getContent()}</textarea>
		<br /><br />
	</form>
</body>
</html>