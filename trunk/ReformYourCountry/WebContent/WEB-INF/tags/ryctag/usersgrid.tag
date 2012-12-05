<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

<%@ attribute name="userList" type="java.util.List"%>

<table style='width:100%'>    
	    <% int i = 0;%>   <%-- i to manage the colunms --%>
   		<c:forEach items="${userList}" var="user">
    	    <c:set var="u" value="<%=i%>"/>
    	    <c:if test="${(u mod 4) == 0}"><tr></c:if>
   		    <td><ryctag:user user="${user}"/></td>
       	    <c:if test="${(u mod 4) == 3}"></tr></c:if>
   		    <%  i++; %>
		</c:forEach>
</table>
