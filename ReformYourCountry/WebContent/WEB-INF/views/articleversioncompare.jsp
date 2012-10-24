<%@ page language="java" contentType="text/html; charset=UTF-8"%>
    <%@ page import="reformyourcountry.model.ArticleVersion" %>
    <%@ page import="java.util.List" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>

<title>Insert title here</title>

<style type="text/css">

#main .main-area {
    width:100%;  /* Overrides the page template to enable this specific page to take the whole window width */
} 

#sub_nav{
display: none;
}      
#content {
width: 100%;
}
</style>


<script type="text/javascript">

	$(document).ready(function(){
				
		$('.versionarticle').change(function() {
			var selectElmt1 = $("#versionarticle1");
			var selectElmt2 = $("#versionarticle2");
			
			window.location.href ="/article/versioncompare?id="+selectElmt1.val()+"&id2="+selectElmt2.val();
		});
		
	});
</script>

</head>
<body>
	<ryctag:pageheadertitle title="${article.title}">
	</ryctag:pageheadertitle>

	<table>
		<tr>
		    <% int i=1; %>
		    <c:forEach var="selectedArticleVersionAndText" items="${twoArticleVersionAndTexts}">
			  <td>
			
			   <select class="versionarticle" id="versionarticle<%=i%>">
			        <%-- 1. The selected version --%>
					<option value="${selectedArticleVersionAndText.articleVersion.id}">${selectedArticleVersionAndText.articleVersion.versionNumberAndDate}</option>
									
					<%-- 2. All the other versions --%>				
					<c:forEach items="${versionList}" var="version" >
					   <  <c:if test="${version.id != selectedArticleVersionAndText.articleVersion.id}">	
						  <option value="${version.id}">${version.versionNumberAndDate}</option>
						</c:if> 
					</c:forEach>
			    </select>
				<p>
				${selectedArticleVersionAndText.text}
				</p>
			  </td>
			  <% i++; %>
			</c:forEach>
			
		</tr>
		
	</table>
	
</body>
</html>