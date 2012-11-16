<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="reformyourcountry.model.User"%>
<%@ page import="java.util.*"%>


<div id="arg${argument.id}" style="width:100%; display: inline-block;background:url(/images/_global/separator3.gif) 0 0 repeat-x ;">

	<div class="argument" style="width:340px; margin-bottom:5px;float:right;">
		<div style="width:100%;background:url(/images/_global/separator3.gif) 0 100% repeat-x ;padding-top:10px;">

			<div style="font-weight:bold; font-size:18px;">
				${argument.title}
			</div>
			<div style="margin-top:5px;font-size:0.9em;">
				${argument.content}
			</div>
			<ryctag:user user="${argument.user}"/>
			<div style="display:inline-block;font-size:0.9em;">
				<div>
					<font style="font-style: italic;">crée le ${argument.formatedCreatedOn}</font> 
<%-- 					${arg.user.lastName} -  --%>
				</div>
				<div>
					<c:if test="${argument.editable}">
						<div  onclick="argumentEditStart(this,${argument.id});">Editer</div>
						<!--<img src="\images\_global\edit.png" width="16px" onfocus="handle();" onclick="editArg(this,${arg.id},'${arg.title}','${arg.content}');">-->
					</c:if>
				</div>
			
			</div>
		</div>
		<%@include file="argumentcomment.jsp"%>
	</div>

    <%-- VOTES --%>
	<div style=" margin-bottom:5px;width: 45px;text-align:center;font-weight: bold;font-size: 25px;">
			<c:choose>
				<c:when test="${argument.getVoteValueByUser(current.user)>0}">
					<img class="div-align-center" align="middle" src="\images\_global\up_selected.png"/>		
				</c:when>
				<c:otherwise>
					<img class="div-align-center" align="middle" src="\images\_global\up.png" onclick="voteOnArgument(this,${argument.id},1);"/>
				</c:otherwise>
			</c:choose>

		    <div style="padding-top:5px; margin-bottom:-8px;">${argument.getTotal()}</div>

			<c:choose>
				<c:when test="${argument.getVoteValueByUser(current.user)<0}">
					<img class="div-align-center" align="middle" src="\images\_global\down_selected.png"/>		
				</c:when>
				<c:otherwise>
					<img class="div-align-center" align="middle" src="\images\_global\down.png" onclick="voteOnArgument(this,${argument.id},-1);"/>
				</c:otherwise>
			</c:choose>
 </div>
</div>