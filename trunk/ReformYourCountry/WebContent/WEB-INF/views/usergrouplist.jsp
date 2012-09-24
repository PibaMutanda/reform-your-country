<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

<title>Insert title here</title>
</head>
<body>
	<form action="usergrouplistsubmit" method="post">
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
		<input type="submit" name="sauver" value="Sauver" />
	</form>
</body>
</html>