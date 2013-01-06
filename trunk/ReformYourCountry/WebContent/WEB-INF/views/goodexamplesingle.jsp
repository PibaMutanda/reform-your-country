<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ryctag:pageheadertitle title="${currentItem.titre}" >
<ryctag:breadcrumbelement label="${currentItem.titre}"></ryctag:breadcrumbelement>
</ryctag:pageheadertitle>

 <%@include file="itemdetail.jsp"%>

