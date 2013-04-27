<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="reformyourcountry.web.UrlUtil"%>
<head>
<!-- code used for "i like facebook" -->
<meta property="og:title" content="${action.title}" />
<meta property="og:type" content="action" />
<meta property="og:url"	content="http://<%= UrlUtil.getProdAbsoluteDomainName()%>/action/${action.title}" />
<meta property="og:image"	content="http://<%= UrlUtil.getProdAbsoluteDomainName()%>/images/logo/2-logo.png" />
<meta property="og:site_name"	content="<%= UrlUtil.getProdAbsoluteDomainName()%>" />
<meta property="fb:admins" content="${user.id}" />
<meta property="og:description" content="${action.shortDescription}" />

<script type="text/javascript" src="/js/int/CKeditorManager.js"></script>
<script type="text/javascript" src="/js/int/general.form.js"></script>
<script type="text/javascript" src="/js/int/goodexample.js"></script>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
 var addthis_config = {"data_track_clickback":true};
 </script>
<script type="text/javascript"
	src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-509a829c59a66215"></script>
</head>

<body>

<ryctag:breadcrumb>
	<ryctag:breadcrumbelement label="${article.title}" link="/article/${article.url}"/>
	<ryctag:breadcrumbelement label="Bons exemples"/>
</ryctag:breadcrumb>
<ryctag:pageheadertitle title="Bons exemples pour l'article ${article.title}" />


<!-- AddThis Button BEGIN -->
<div class="addthis_toolbox addthis_default_style"
	style="padding-top: 30px; padding-bottom: 10px;">
	<div style="float: left">
		<a class="addthis_button_facebook_like" fb:like:layout="button_count"></a>
		<a class="addthis_button_tweet"></a> <a
			class="addthis_button_google_plusone" g:plusone:size="medium"></a> <a
			class="addthis_counter addthis_pill_style"></a>
	</div>
</div>
<!-- AddThis Button END -->

<div id="list">
      <c:forEach items="${article.goodExamples}" var="currentItem">
            <%@include file="itemdetail.jsp"%>
      </c:forEach>
</div>

<hr/>
<div style="border: 1px solid black;">
	<div style="background-color: white; padding: 5px; color: #BBB; font-size: 0.8em;" id="createDivFakeEditor" onclick="createStart(${article.id});">
		Cliquez ici pour composer un nouveau bon exemple.<br />
		<br />
		<br />
		<br />
	</div>
	<div id="createDivRealEditor" style="display: none; background-color: #e2e2e2; padding: 5px;">
	</div>
</div>

</body>