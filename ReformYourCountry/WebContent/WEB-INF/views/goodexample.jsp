<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
<script type="text/javascript" src="/js/int/CKeditorManager.js"></script>
<script type="text/javascript" src="/js/int/general.form.js"></script>
<script type="text/javascript" src="/js/int/goodexample.js"></script>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
</head>
<ryctag:pageheadertitle title="goodexamplelist" >
Insert breadcrumb elements here
</ryctag:pageheadertitle>
<script type="text/javascript">
   var idUser = "${current.user.id}";
</script>
<div id="list">
<c:forEach items="${article.goodExamples}" var="goodExample">
<div id="${goodExample.id}">
    <%@include file="goodexampledisplay.jsp" %>
</div>
</c:forEach>
</div>

<hr/>
<div style="border: 1px solid black;">
	<div style="background-color: white; padding: 5px; color: #BBB; font-size: 0.8em;" id="argumentAddDivFakeEditor" onclick="createStart(${article.id});">
		Cliquez ici pour composer un nouvel argument.<br />
		<br />
		<br />
		<br />
	</div>
	<div id="argumentAddDivRealEditor" style="display: none; background-color: #e2e2e2; padding: 5px;">
	</div>
</div>

