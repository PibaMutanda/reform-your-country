<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="colArg${ispos}" style="
        <c:if test='${!ispos}'> width:395px; float:right;</c:if>  <%-- Right column --%>
        <c:if test='${ispos}'>width:390px; float:left;background: url(images/_global/bg-sidebar.gif) 100% 0 repeat-y; padding-right:15px;</c:if> <%-- Left column + vertical separator line --%>
        ">
    <div style="text-align:center; width:100%; margin-bottom:-20px;"><h2>${colTitle}</h2></div>
	<c:set var="canNegativeVote" value="true" scope="request"/>
	<c:set var="isArgument" value="true" scope="request"/>
    <div class="listArgument">
	  <c:forEach items="${listToShow}" var="currentItem">
			<%@include file="itemdetail.jsp"%>
	  </c:forEach>
   	</div>

		
    <div style="border:1px solid #999;" >
		<div style="background-color: white; padding:5px; color: #BBB; font-size:0.8em;" id="argumentAddDivFakeEditor${ispos}" onclick="argumentCreateStart('${ispos}',${action.id});">
		    Cliquez ici pour composer un nouvel argument.<br/><br/><br/><br/>
		</div>
		<div id="argumentAddDivRealEditor${ispos}" style="display:none; background-color: #e2e2e2; padding:5px;">
		</div>
	</div>
</div>



