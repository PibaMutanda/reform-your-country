<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div style="width:100%; font-size:0.7em;">
	<c:forEach items="${arg.commentList}" var="lst">
		<div style="margin:0px; width:100%;">
				${lst.content} - <a id="underlineUser" href="/user/${lst.user.userName}">${lst.user.userName}</a> - 
				${lst.getFormatedCreatedOn()}
		</div>
	</c:forEach>
		<form action="" method="post">
			<input type="hidden" id="idArg" value="${arg.id}" />
			<div>
				<div id="addcom${arg.id}">
					<input type="button" value="ajout de com" onclick="showText(this,${arg.id})"/>
				</div>
				<div id="commentArea${arg.id}" style="display: none;">
					<textarea id="comm${arg.id}" rows="1" cols="20"  onkeyup="maxlength_textarea(this,${arg.id},50,10);"></textarea>
					<div id="nbrCaract${arg.id}"> </div>...
	<!-- 				<script type="text/javascript"> -->
	<%-- 						  var comId = "comm"+${arg.id}; --%>
	<%-- 						  var carRest = "carac_reste_textarea_1"+${arg.id}; --%>
	<!--                        	  maxlength_textarea(comId, carRest, 50, 10);  -->
	<!--                	</script>  -->
					<input id = "sendArgComm${arg.id}" type="button" disabled="disabled" value="commenter" onclick="sendNewComment(this, comm.value,${arg.id});">
				</div>
			</div>
		</form>
</div>