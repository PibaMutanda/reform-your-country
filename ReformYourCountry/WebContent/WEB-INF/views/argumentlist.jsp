<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/ckeditor/adapters/jquery.js"></script>

<div id="colArg${ispos}" style="
        <c:if test='${ispos}'> width:390px; float:right;</c:if>  <%-- Right column --%>
        <c:if test='${!ispos}'>width:385px; float:left;background: url(images/_global/bg-sidebar.gif) 100% 0 repeat-y; padding-right:15px;</c:if> <%-- Left column + vertical separator line --%>
        ">
    <div class="listArgument">
	  <c:forEach items="${listToShow}" var="argument">
			<%@include file="argumentdetail.jsp"%>
	  </c:forEach>
   	</div>

		
    <div style="border:1px solid black;" >
		<div style="background-color: white; padding:5px; color: #BBB; font-size:0.8em;" id="argumentAddDivFakeEditor${ispos}" onclick="argumentCreateStart('${ispos}',${action.id});">
		    Cliquez ici pour composer un nouvel argument.<br/><br/><br/><br/>
		</div>
		<div id="argumentAddDivRealEditor${ispos}" style="display:none; background-color: #e2e2e2; padding:5px;">
		</div>
	</div>
</div>

