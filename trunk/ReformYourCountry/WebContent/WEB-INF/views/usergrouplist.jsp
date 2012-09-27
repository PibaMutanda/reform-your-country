﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
					<td><input type="checkbox"   name="groupIds" 
                        <c:if test="${myGroups.contains(currentGroup)}"  >
					     	checked="checked"  
			            </c:if> value="${currentGroup.id}" />
					</td>
					<td>${currentGroup.name}</td>
				</tr>
			</c:forEach>
		<tr><td><input type="hidden" name="id" value="${id}" /></td></tr>	
		</table>
		<input type="submit" name="sauver" value="Sauver" />
	</form>
</body>
</html>