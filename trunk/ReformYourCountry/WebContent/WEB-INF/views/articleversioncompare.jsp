<%@ page language="java" contentType="text/html; charset=UTF-8"%>
    <%@ page import="reformyourcountry.model.ArticleVersion" %>
    <%@ page import="java.util.List" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>

<style type="text/css">

/* Overrides the page template to enable this specific page to take the whole window width */
#main .main-area {
    width: 100%;  
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
	<ryctag:breadcrumb>
		<c:forEach items="${parentsPath}" var="subarticle">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
		</c:forEach>
		<ryctag:breadcrumbelement label="comparaison de versions" />
	</ryctag:breadcrumb>
   	<ryctag:pageheadertitle title="${article.title}"/>

	<div class="versionCompareSegment" >
		 <% int i=1; %>
		 <c:forEach var="selectedArticleVersionAndText" items="${twoArticleVersionAndTexts}">
			  <div class="versionCompare">
			
			   <select class="versionarticle" id="versionarticle<%=i%>">
			        <%-- 1. The selected version --%>
					<option value="${selectedArticleVersionAndText.articleVersion.id}">${selectedArticleVersionAndText.articleVersion.versionNumberDateAndUser}</option>
									
					<%-- 2. All the other versions --%>				
					<c:forEach items="${versionList}" var="version" >
					    <c:if test="${version.id != selectedArticleVersionAndText.articleVersion.id}">	
						  <option value="${version.id}">${version.versionNumberDateAndUser}</option>
						</c:if> 
					</c:forEach>
			    </select>
			 </div>
			 <% i++;%>
		</c:forEach>	    
	</div>
	
	<h2>Résumé</h2>
	<div class="versionCompareSegment" >
		  <% int j=1; %>
		  <c:forEach var="selectedArticleVersionAndText" items="${twoArticleVersionAndTexts}">
		  	<div class="versionCompare">
			   		${selectedArticleVersionAndText.summary}
		 	</div>
		 	<% j++;%>
		  </c:forEach>	
	</div>
	<hr>

	<h2>Contenu</h2>
 	<div class="versionCompareSegment">
		  <% int k=1; %>
		  <c:forEach var="selectedArticleVersionAndText" items="${twoArticleVersionAndTexts}">
		  	<div class="versionCompare">
			   ${selectedArticleVersionAndText.content}
		 	</div>
		 	<% k++;%>
		  </c:forEach>	
	</div>
	<hr>
	
	<h2>A classer</h2>
	<div class="versionCompareSegment">
		  <% int l=1; %>
		  <c:forEach var="selectedArticleVersionAndText" items="${twoArticleVersionAndTexts}">
		  	<div class="versionCompare">
			   ${selectedArticleVersionAndText.toClassify}
		 	</div>
		 	<% l++;%>
		  </c:forEach>	
	</div>
			 
		
	
	
</body>
</html>