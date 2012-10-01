<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<link rel="stylesheet" href="css/ext/jquery-bubble-popup-v3.css"  type="text/css" />
<link rel="stylesheet" href="css/ext/jquery.countdown.css" type="text/css"/>
<script type="text/javascript" src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>
<script type="text/javascript" src="js/ext/jquery.countdown.js"></script>
<script type="text/javascript" src="js/ext/jquery.countdown-fr.js"></script>
<meta name="robots" content="index, follow"/>
<meta name="description" content="${article.description}"/>
<title>${article.title}</title>
</head>

<body>


 <ryctag:pageheadertitle title="${article.title}" breadcrumb="true">
 	<c:forEach items="${parentsPath}" var="subarticle">
 		<c:if test="${article.title != subarticle.title}">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="article/${subarticle.url}" />
		</c:if>
	</c:forEach>
	<ryctag:breadcrumbelement label="${article.title}" />
 </ryctag:pageheadertitle>

 <div style="display:block;">
  <div style="float: left;">
 <c:if test="${!article.publicView}">
		<p>Cet article n'est pas disponible au public.
     	<c:choose>
  	       <c:when test="${displayDate != null}">
  	          Il sera publié le ${displayDate}.
		   </c:when>
		   <c:otherwise>
		      Sa date de publication est inconnue.
		   </c:otherwise>
		 </c:choose>
		 </p>
		 <br/>
</c:if>
</div>
<div class="article-options">
<ryc:conditionDisplay privilege="EDIT_ARTICLE">
	    <ul class="list sitemap-list">
			 <li><a href="articleedit?id=${article.id}">Editer l'article</a></li>
			 <li><a href="articleparentedit?id=${article.id}">Editer l'article parent</a></li>
		</ul>	
      <span class="tooltip" data-tooltip='identifiant de cet article pour utilisation dans la balise [link article="identifiant"]'>${article.shortName}</span>   <!--  Tooltip avec "identifiant de cet article pour utilisation dans la balise [link article="identifiant"]" -->

</ryc:conditionDisplay>
</div>


</div>
<div  style="display:inline-block;"	>

<ryc:conditionDisplay privilege="EDIT_ARTICLE">
  <hr/>
</ryc:conditionDisplay>  


<!-- ARTICLE CONTENT -->
<c:choose>
  	  <c:when test="${showContent}">
		${articleContent}
	  </c:when>
	  
	  <c:otherwise> <%-- We do not show the text, but the countdown --%>
		<c:if test="${displayDate != null}">
  		  <br/><br/><br/>
          <!-- COUNT DOWN -->
		  <div   id="defaultCountdown" style="margin-left:100px;"></div>
			<script type="text/javascript">
			    $(document).ready(function () {
						var publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
						function reload() { 
							window.location.reload(); 
						} 
						$('#defaultCountdown').countdown(
								{until: publishDay, onExpiry:reload, format: 'dHMS',
								 layout: '<span style="font-size:100px;">{dn}</span> {dl} ,  &nbsp; &nbsp; &nbsp; <span style="font-size:100px;">{hn}</span> {hl}, &nbsp; &nbsp; &nbsp; <span style="font-size:100px;">{mn}</span> {ml} et  &nbsp; &nbsp; &nbsp;  <span style="font-size:100px;">{sn}</span> {sl}'}); 
				});
			</script>
			<br/><br/><br/> avant publication.
		</c:if>
	  
	  </c:otherwise>
</c:choose>
</div>
</body>
</html>   

