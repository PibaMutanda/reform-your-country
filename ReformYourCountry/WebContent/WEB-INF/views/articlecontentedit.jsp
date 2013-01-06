<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
<script type="application/javascript" src="js/ext/jquery.textarea-expander.js"></script>
<script type="application/javascript" src="js/int/help.js"></script>
<script type="application/javascript" src="js/int/autosave.js"></script>
</head>
<body>
	<ryctag:breadcrumb>
		<c:forEach items="${article.path}" var="subarticle">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
		</c:forEach>
		<ryctag:breadcrumbelement label="${pageName}" />
    </ryctag:breadcrumb>
    
   	<ryctag:pageheadertitle title="${article.title}"/>
    <%-- Help handle --%>
    <div id="helphandle"><span>?</span><div><%--contains the helptext--%></div></div>
		
	<div>
		<div>
			<form action="/article/${submitUrl}" method="post">
    			<input class="save" type="submit" value="Sauver" /><span class="saving" style="font-family: tahoma; font-size: 9px;"></span>
    		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
				<textarea name="value" class="expand autosaveable" cols="60" rows="3" style="width:100%">${thingToEdit}</textarea>
				<input type="hidden" value="${article.id}" name="id" />
				<input class="save" type="submit" value="Sauver" /><span class="saving" style="font-family: tahoma; font-size: 9px;"></span>
			</form>
		</div>
	</div>
</body>
</html>