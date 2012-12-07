<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="help.jsp"%>

<form id='ckEditForm' action='${urlAction}'>
    <div id="errors" style="color:red;"><%--Error messages inserted by JavaScript --%></div>
    <c:if test="${not empty positiveArg }">
        <input type='hidden' name='ispos' value='${positiveArg}'/>
    </c:if>
    <input type='hidden' name='idParent' value='${idParent}'/>
    <input type="hidden" name="idItem" value="${idItem}" />
    <label for='titleItem' style="padding: 0px 5px 0 0;">Titre</label>
    <input type='text' id='titleItem' name='title' style='width:330px;' value="${titleItem}"/>
    
    <textarea id='contentItem'  name='content' >${contentItem}</textarea>
    
    <div>
        <div style="float:right; display:inline-block;padding: 3px; margin: 5px;">
        	<div style="float:right;">
    		<c:choose>
		        <c:when test="${empty idItem}">
		        	<input type='submit' id='CkEditFormSubmit' value='Ajouter' />
		        </c:when>
		         <c:otherwise>
		         	<input type='submit' id='CkEditFormSubmit' value='Sauver' />
		         </c:otherwise>
		    </c:choose>
		    </div>
        	<div style="float:right; padding: 6px;">
   				<div id='CkEditFormAbort' class="divButton" style=''>
		   			Annuler 	
       			</div>
        	</div>
    	</div>
    </div>
</form>

