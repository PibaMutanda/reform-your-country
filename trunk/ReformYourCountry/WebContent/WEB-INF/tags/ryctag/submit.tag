<%@ tag body-content="empty" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ attribute name="entity" required="true" type="reformyourcountry.model.BaseEntity"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="value" required="true"%>

<form action="${action}" method="POST">
<input type="hidden" name="id" value="${entity.getId()}"/>
<input type="submit" value="${value}"/>
</form>			