<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>

<html>
<head>
<script type="text/javascript" src="/js/int/CKeditorManager.js"></script>
<script type="text/javascript" src="/js/int/general.form.js"></script>
<script type="text/javascript" src="/js/int/goodexample.js"></script>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
</head>
<body>
	<ryctag:pageheadertitle title="100 derniers bon exemples"/>
	
	<c:forEach items="${goodExamples}" var="currentItem">
    		<%@include file="itemdetail.jsp" %>
	</c:forEach>
</body>
</html>