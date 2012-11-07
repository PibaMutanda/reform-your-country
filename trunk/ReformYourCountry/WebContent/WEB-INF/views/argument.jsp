<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript" src="js/int/argument.js">
</script>

<!-- First we had the column with the negatives args -->
<c:set var="color" value="#8B0000" scope="request"/>
<c:set var="listToShow" value="${listNegArgs }" scope="request"/>
<c:set var="ispos" value="false" scope="request"/>
<%@include file="argumentlist.jsp"%>

<!-- Then we had the column with the positives args -->

<c:set var="color" value="#008B00" scope="request"/>
<c:set var="listToShow" value="${listPosArgs }" scope="request"/>
<c:set var="ispos" value="true" scope="request"/>
<%@include file="argumentlist.jsp"%>