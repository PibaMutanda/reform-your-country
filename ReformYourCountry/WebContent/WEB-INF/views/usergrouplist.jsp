<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

<html>
<head>

<title>Mes groupes</title>
</head>
<body> 

<ryctag:pageheadertitle title="liste de mes groupes" breadcrumb="true">
	<ryctag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="user/${user.userName}" />
	<ryctag:breadcrumbelement label="Mes groupes" />
</ryctag:pageheadertitle>


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