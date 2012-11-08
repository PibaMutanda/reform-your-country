<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div  id="comment${arg.id}">
	<c:forEach items="${arg.commentList}" var="lst">
		<div>
			${lst.content}<br />
		</div>
	</c:forEach>
</div>