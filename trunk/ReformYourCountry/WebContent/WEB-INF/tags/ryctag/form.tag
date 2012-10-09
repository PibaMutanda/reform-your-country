<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ attribute name="modelAttribute" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="method" required="false" %>
<%@ attribute name="width" required="false" %>
<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>
<form:form modelAttribute="${modelAttribute}" action="${action}" method="${method}">
	<table style="width:${width}">
		<jsp:doBody/>
	</table>
</form:form>