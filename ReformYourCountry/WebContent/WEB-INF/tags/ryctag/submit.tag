<%@ tag body-content="empty" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ attribute name="entity" required="true" type="reformyourcountry.model.BaseEntity"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="method" required="false" %>

<c:if test="${method == null}"><c:set var="method" value="get" /></c:if>

<form action="${action}" method="${method}">
<input type="hidden" name="id" value="${entity.getId()}"/>
<input type="submit" value="${value}"/>
</form>			