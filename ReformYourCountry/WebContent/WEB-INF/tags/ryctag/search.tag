<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>

<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>

 <c:forEach items="${listToSearch}" var="result">
     <a href="/article/${result.article.url}">${result.articleDocument.title}</a>
     <br/>
	     <c:choose>
		      <c:when test="${result.articleDocument.content != null}">
		     		${result.articleDocument.content}
		      </c:when>
		      <c:otherwise>
		      	 	${result.article.description}
		      </c:otherwise>
	     </c:choose>
     <br/> <br/>
</c:forEach> 
		        