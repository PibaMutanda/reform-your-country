<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ attribute name="path" required="true"%>
<%@ attribute name="label"%>
<%@ attribute name="tooltip" %>
<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>

 <c:choose>
 	<c:when test="${tooltip!=null}">
 <tr class="tooltip" data-tooltip="${tooltip}">
 	</c:when>
 	<c:otherwise>
 <tr>
 	</c:otherwise>
 </c:choose>
 	<td><label for="${path}">${label}</label></td>
	<td><form:password path="${path}" style="width:100%;"/></td>
	<td><form:errors path="${path}" cssClass="error" /></td>
</tr>