<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

<title>Insert title here</title>
</head>
<body>
	<form action="usergrouplistsubmit">
		<table>
			<c:forEach items="${allGroups}" var="currentGroup">
				<tr>
					<td><input type="checkbox" name="group-${currentGroup.id}"
                        <c:if test="${myGroups.contains(currentGroup)}" >
					     	checked="checked"
			            </c:if> />
					</td>
					<td>${currentGroup.name}</td>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" name="??????? submit..." value="Sauver" />
	<form>
</body>
</html>