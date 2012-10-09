<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ attribute name="label" required="true" type="java.lang.String"%>
<%@ attribute name="link" type="java.lang.String"%>
<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>

 <c:choose>
	 <c:when test="${link != null}">
		<a href="${link}">${label}</a>
	 </c:when>
	 <c:otherwise>
		<span class='current_crumb'>${label}</span>
 	 </c:otherwise>
 </c:choose>
