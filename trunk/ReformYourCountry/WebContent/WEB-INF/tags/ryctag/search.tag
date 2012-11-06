<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>


<%-- Used by search.jsp to prevent copy/pasting code. --%>
<div class=listToSearchTitle>
     <a  href="/article/${result.article.url}">${result.articleDocument.title}</a>
</div>
<span class="datepublication"> <ryc:publishDate id="${result.article.id}"/> </span>
<div class=listToSearch>
    <c:choose>
      <c:when test="${result.articleDocument.content != null}">
     		${result.articleDocument.content}
      </c:when>
      <c:otherwise>
      	 	${result.article.description}
      </c:otherwise>
    </c:choose>
    <br/> <br/>
</div>
		        