<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

<div id="help" style="display:none;background-color:#FFFFCC; padding:10px; font-size:0.8em;	">
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

<form id='argEditorForm' class='argumentNegFor' action='' method='post'>
    <div id="errors" style="color:red;"><%--Error messages inserted by JavaScript --%></div>
    <input type='hidden' id='ispos' name='ispos' value='${positiveArg}'/>
    <input type='hidden' id='actionId' name='actionId' value='${actionId}'/>
    <label for='titleArg' style="padding: 0px 5px 0 0;">Titre</label><input type='text' id='titleArg'name='title' style='width:330px;' value="  "/>
    <textarea id='contentArg'  name='content' >${argument.content }</textarea>
    <input type='submit' value='<c:choose><c:when test="${argument.id == null}">Ajouter</c:when><c:otherwise>Sauver</c:otherwise></c:choose>'
	                     onclick="return argumentEditSubmit(${positiveArg});" style='margin:5px;'/>
	<input type="hidden" name="argumentId" value="${argument.id}" />
</form>

