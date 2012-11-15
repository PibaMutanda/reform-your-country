<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="js/int/argument.js">
</script>

<div style="
        <c:if test='${ispos}'> width:390px; float:right;</c:if>  <%-- Right column --%>
        <c:if test='${!ispos}'>width:385px; float:left;background: url(images/_global/bg-sidebar.gif) 100% 0 repeat-y; padding-right:15px;</c:if> <%-- Left column + vertical separator line --%>
        ">
	<c:forEach items="${listToShow}" var="arg">
		
 		
			<%@include file="argumentdetail.jsp"%>
 		
	</c:forEach>
   

		<div id="help${ispos}" style="display:none;background-color:#FFFFCC; padding:10px; font-size:0.8em;	">
		<div style="width:100%; text-align: right;">
		<div style="font-weight: bold;" onclick="hideHelp(help${ispos});">
			X
		</div>
		</div>
			Merci de participer en argumentant.<br/>
			Les meilleurs arguments:<br/>
			<ul>
			<li>sont concis, murement réfléchis et clairement énoncés,</li>
			<li>concernent directement la proposition d'action posée,</li>
			<li>son isolés (si vous avez plusieurs arguments, ajoutez-les séparément),</li>
			<li>évitent de répéter des arguments donnés précédemment,</li>
			<li>factuels (plutôt qu'émotionnels).</li>
			</ul><br/>
			Soyez poli et amusez-vous.
		</div>
		<div style="border:3px inset; background-color: white;">
		<div id="hideArgArea${ispos}" onclick="showArea('${ispos}');"style="color: #CCCCCC">Cliquer ici pour composer un nouvel argument.<br/><br/><br/><br/></div>
		<div id="showArgArea${ispos}" style="display:none;">
		<form id="form${ispos}" class="argumentNegForm" action="" method="post">
			<input type="hidden" id="ispos" name="ispos" value="${ispos}" /> 
			<input type="hidden" id="action" name="action"value="${action.id}" />
			<div style="padding-left:5px;">
				Titre: 	<textarea id="titleArg${ispos}"name="title" rows="1" cols="15"></textarea>
			</div>
						
        	<textarea id="contentArg${ispos}"  name="content" ></textarea>
			<div style="padding-left:5px;" onclick="sendNewArg(this,'${ispos }');">
				Ajout de l'argument
			</div>
		</form>
		</div>
		</div>
		</div>

