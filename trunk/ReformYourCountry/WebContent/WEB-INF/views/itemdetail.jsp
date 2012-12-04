<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="reformyourcountry.model.User"%>
<%@ page import="java.util.*"%>


<div id="item${currentItem.id}" style="width:100%; display: inline-block;background:url(/images/_global/separator3.gif) 0 0 repeat-x ;">

	<div style="width:340px; margin-bottom:5px;float:right;">
		<div style="width:100%;background:url(/images/_global/separator3.gif) 0 100% repeat-x ;padding-top:10px;">

			<div style="font-weight:bold; font-size:18px;">
				${currentItem.title}
			</div>
			<div style="margin-top:5px;font-size:0.9em;">
				${currentItem.content}
			</div>
			<ryctag:user user="${currentItem.user}"/>
			<div style="display:inline-block;font-size:0.8em;width:100%">
				<div>
					<span style="font-style: italic;">crée le ${currentItem.formatedCreatedOn}</span> 
					<span class="commentLink" onclick="ItemEditStart(this,${currentItem.id});">Editer</span>
					<span onclick="deleteItem(this,${currentItem.id})" class="divButton" style="color:red; font-size:1.3em;font-weight: bold;" title="Supprimer">×</span>
				</div>
				<div>
					<c:if test="${currentItem.editable}">
						<a href="/argument?id=${currentItem.id}">Administrer les commentaires</a> 
						
						
						<!--<img src="\images\_global\edit.png" width="16px" onfocus="handle();" onclick="editArg(this,${arg.id},'${arg.title}','${arg.content}');">-->
					</c:if>
				</div>
			</div>
		</div>
		<c:set var="divId" value="arg${currentItem.id}" scope="request"/>
		<c:set var="helpContent" value="${p_comment_help}" scope="request"/>
		<%@include file="comment.jsp"%>
	</div>
	
    <%-- VOTES --%>
	<%@include file="vote.jsp"%>
	

</div>