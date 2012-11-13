<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


	<div class="argument" style="width:340px; margin-bottom:5px;float:right;">
	
		<div style="width:100%;background:url(/images/_global/separator3.gif) 0 100% repeat-x ;">
			
				
			
			<div style="font-weight:bold; font-size:18px;">
				${arg.title}
			</div>
			<p  style="padding:5px;font-size:0.9em;">
				${arg.content}
			</p>
			<div style="display:inline-block;font-size:0.9em;">
				<div>
					<font style="font-style: italic;">${arg.user.lastName} - ${arg.getFormatedCreatedOn()}</font>
				</div>
				<div>
					<c:if test="${arg.isEditable()}">
						<a  onclick="editArg(this,${arg.id},'${arg.title}','${arg.content}');">Editer</a>
						<!--<img src="\images\_global\edit.png" width="16px" onfocus="handle();" onclick="editArg(this,${arg.id},'${arg.title}','${arg.content}');">-->
					</c:if>
				</div>
			
			</div>
		</div>
		<div id="comment${arg.id}" style="width:100%;">
			<%@include file="argumentcomment.jsp"%>
		</div>
	</div>

	<div style=" margin-bottom:5px;width: 45px;text-align:center;font-weight: bold;font-size: 25px;">
			<c:choose>
				<c:when test="${arg.getVoteValueByUser(current.user)>0}">
					<img class="div-align-center" align="middle" src="\images\_global\up_selected.png"/>		
				</c:when>
				<c:otherwise>
					<img class="div-align-center" align="middle" src="\images\_global\up.png" onclick="voteOnArgument(this,${arg.id},1);"/>
				</c:otherwise>
			</c:choose>

		<div style="padding-top:5px; margin-bottom:-8px;">${arg.getTotal()}</div>

			<c:choose>
				<c:when test="${arg.getVoteValueByUser(current.user)<0}">
					<img class="div-align-center" align="middle" src="\images\_global\down_selected.png"/>		
				</c:when>
				<c:otherwise>
					<img class="div-align-center" align="middle" src="\images\_global\down.png" onclick="voteOnArgument(this,${arg.id},-1);"/>
				</c:otherwise>
			</c:choose>
	</div>
	