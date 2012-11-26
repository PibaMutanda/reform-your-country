<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="js/int/votelistaction.js"></script>

<c:forEach items="${actionItems}" var="actionItem">
		<div style="text-align:center;">
			<a href="/action/${actionItem.action.url}" style="margin:10px;">
				<span class="tooltip" data-tooltip='${actionItem.action.shortDescription}'>${actionItem.action.title}</span>
			</a>
			<div id="voteContainer" style="margin:10px;">
  					<c:set var="vote" value="${actionItem.voteAction}" scope="request"/>
				    <c:set var="resultNumbers" value="${actionItem.resultNumbers}" scope="request" />
				    <c:set var="id" value="${actionItem.action.id}" scope="request" />
		            <span style="width:130px; margin:0 auto;"><%@ include file="/WEB-INF/views/voteactionwidget.jsp" %></span>
       		</div>
		</div>
</c:forEach>