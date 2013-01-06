<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ attribute name="path" required="true"%>
<%@ attribute name="label"%>
<%@ attribute name="title" %>
<%@ attribute name="required"%>
<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>

 <c:choose>
 	<c:when test="${title!=null}">
 <tr title="${title}">
 	</c:when>
 	<c:otherwise>
 <tr>
 	</c:otherwise>
 </c:choose>
	<td><form:checkbox path="${path}" label="${label}" required="${required}"/></td>
</tr>