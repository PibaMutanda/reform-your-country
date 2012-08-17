<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/security.tld' prefix='ryc'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserPage</title>
</head>
<body>
<h1>${user.userName }</h1>
<ryc:security privilege="MANAGE_USERS">
<form action="editprivilege" method="post">
<input type="hidden" value="${user.id}">
<input type="submit" value="Editer privilèges">
</form>
</ryc:security>
</body>
</html>