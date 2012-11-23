<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="help" style="display:none;background-color:#FFFFCC; padding:10px; font-size:0.8em; ">
            <div style="width:100%; text-align: right;">
                <div style="font-weight: bold;" onclick="hideHelp();">
                    X
                </div>
            </div>
            ${helpContent}
</div>


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
    
    <c:choose>
        <c:when test="${empty idItem}">
        	<input type='submit' id='CkEditFormSubmit' value='Ajouter' style='padding: 3px; margin: 5px;'/>
        </c:when>
         <c:otherwise>
         	<input type='submit' id='CkEditFormSubmit' value='Sauver' style='padding: 3px; margin: 5px;'/>
         </c:otherwise>
    </c:choose>
    
</form>

