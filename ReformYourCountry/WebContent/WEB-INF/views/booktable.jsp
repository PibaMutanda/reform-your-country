<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ page import="java.util.*"%>
<%@ page import="reformyourcountry.model.Book"%>


<h2>${tablename}</h2>
<table>
	<c:forEach items="${bookList}" var="book" varStatus="stat">
		<c:choose>
			<c:when test="${stat.index % 2 == 0}">
				<tr>
					<td><ryctag:book book="${book}" /></td>
			</c:when>
			<c:otherwise>
   					<td><ryctag:book book="${book}" /></td>
				</tr>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</table>
