<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="reformyourcountry.web.UrlUtil"%>
<html>
<head>
<!-- code used for "i like facebook" -->
<meta property="og:title" content="${action.title}" />
<meta property="og:type" content="action" />
<meta property="og:url"	content="http://<%= UrlUtil.getProdAbsoluteDomainName()%>/action/${action.title}" />
<meta property="og:image"	content="http://<%= UrlUtil.getProdAbsoluteDomainName()%>/images/logo/2-logo.png" />
<meta property="og:site_name"	content="<%= UrlUtil.getProdAbsoluteDomainName()%>" />
<meta property="fb:admins" content="${user.id}" />
<meta property="og:description" content="${action.shortDescription}" />

<meta name="description" lang="fr" content="${action.shortDescription}" />
<meta name="robots" content="index, follow" />

<script type="text/javascript" src="/js/int/voteaction.js"></script>
<script type="text/javascript" src="/js/ext/d3.v2.min.js"></script>
<script type="text/javascript" src="/js/int/argument.js"></script>
<script type="text/javascript" src="/js/int/CKeditorManager.js"></script>
<script type="text/javascript" src="/js/int/general.form.js"></script>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
  var addthis_config = {"data_track_clickback":true};
</script>
<script type="text/javascript"
	src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-509a829c59a66215">
</script>
</head>

<body>

<ryctag:breadcrumb>
		<ryctag:breadcrumbelement label="actions"  link="/action"/>
		<ryctag:breadcrumbelement label="cette action" />
</ryctag:breadcrumb>		

<ryc:conditionDisplay privilege="MANAGE_ACTION">
	   <div class="page-menu-links">
			<a href="/action/edit?id=${action.id}">éditer</a>
	   </div>
</ryc:conditionDisplay>

<%-- Form apparently needed for ajax actions --%>
<ryctag:form action="/action/edit" modelAttribute="action"	method="get" width="50px;">
		<input type="hidden" value="${action.id}" name="id" id="idAction" />
</ryctag:form> 


<div style="float:right;">
  <a href="/specialuserslist/${action.url}" title="voir ce que les partis et associations ont voté"><img src="/images/partis.png"/></a>
</div>  

<ryctag:pageheadertitle title="${action.title}" />

	

<%-- Action text --%>
<div style="width: 100%;">

	<div style="font-size: 1em;">

	  <%-- List of articles within the action --%>
	  <div class="action-list" style="font-size: 0.9em; float:right; color:#7D92B9; margin: -16px 0 15px 15px;">
	    <ryctag:frame1tape>
			<div class="action-goodexample-title" style="margin-top:-30px;" >articles liés</div>
			<c:forEach items="${action.articles}" var="article">
					<div style="margin-bottom:10px;">
						<a href="/article/${article.url}" class="list-title">
						    <c:choose>
								<c:when test="${article.description != null}">
									<span title="${article.description}">${article.title}</span>
								</c:when>
								<c:otherwise>
									${article.title}
								</c:otherwise>
							</c:choose>
						</a>
					</div>
			</c:forEach>
	     </ryctag:frame1tape>
 	   </div>
		
	    ${action.content}
   </div>

   <div>
			<!-- AddThis Button BEGIN -->
			<div class="addthis_toolbox addthis_default_style"
				style="padding-top: 30px; padding-bottom: 10px;">
				<div style="float: left">
					<a class="addthis_button_facebook_like"
						fb:like:layout="button_count"></a> <a class="addthis_button_tweet"></a>
					<a class="addthis_button_google_plusone" g:plusone:size="medium"></a>
					<a class="addthis_counter addthis_pill_style"></a>
				</div>
			</div>
			<!-- AddThis Button END -->
			
   </div>
</div>

<ryctag:separator/>

<%-- Chart --%>
<div id="voteGraph"
	style="width: 500px; margin-left: 150px; padding-top: 10px;">
</div>
<div id="voteContainer">
	<%-- Will be re-filled through Ajax too--%>
	<%@include file="voteaction.jsp"%>
</div>

<%-- Arguments --%>
<div id="argContainer">
	<%-- Will be re-filled through Ajax--%>
	<%@include file="argument.jsp"%>
</div>
<%-- <%@include file="argumentcomment.jsp"%> --%>
</body>
</html>
