<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


	
	<div class="argument" style="width:300px;border-radius: 10px;border:3px solid ${color}; margin:5px;float:left;">
		<c:if test="${arg.user eq current.user}">
			<div  onclick="editArg(this,${arg.id},${arg.title},${arg.content})">Editer</div>
		</c:if>
		<p id="title" align="center" style="text-align:center;font-weight:bold; font-style: italic;padding:5px;">

			${arg.title}
		</p>
		<p id="content" style="padding:5px;">
			${arg.content}
		</p>
		<p align="right" style="text-align: right; font-style: italic;padding:5px;">
			${arg.user.firstName} ${arg.user.lastName}
		</p>
	</div>

	<div style="float:right; margin:5px;width: 75px;text-align:center;font-weight: bold;font-size: 25px;">
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