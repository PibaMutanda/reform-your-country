<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Editer un utilisateur</title>
</head>
<body>
<script src="js/int/birthday_picker.js" type = "text/javascript"></script> 


<ryctag:pageheadertitle title="${user.firstName} ${user.lastName}">
	<ryctag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="user/${user.userName}" />
	<ryctag:breadcrumbelement label="Edition" />
</ryctag:pageheadertitle>





    <ryctag:form action="user/editsubmit" modelAttribute="user">
        <ryctag:input path="lastName" label="Nom" />
        <ryctag:input path="firstName" label="Prénom"/>
        <ryctag:input path="userName" label="Pseudonyme" required="required"/>
        
        <tr> <%-- We do not use a date picker here, because for old dates, it's not practical --%>
            <td>Date de naissance</td>
            <td>
                <jsp:useBean id="now" class="java.util.Date" /><%-- Used by the birthday picker, "year" variable --%>
                <fmt:formatDate var="year" value="${now}" pattern="yyyy" /><%-- Used by the birthday picker, "year" variable --%>	
                <select name="birthDay" id="birthDay" class="input_pulldown">
                		<option value=null <c:if test="${birthDay==null}">selected="selected"</c:if>>Jour</option>
                        <c:forEach var="i" begin="1" end="31" step="1" varStatus ="status">
                            <option  value="${i}" <c:if test="${birthDay==i}">selected="selected"</c:if>>${i}</option>
                        </c:forEach> 
                </select>
                <select name="birthMonth" id="birthMonth" class="input_pulldown" >
                		<option value="null" <c:if test="${birthMonth==null}">selected="selected"</c:if>>Mois</option>
                        <option value="1" <c:if test="${birthMonth==0}">selected="selected"</c:if>>Janvier</option>
                        <option value="2" <c:if test="${birthMonth==1}">selected="selected"</c:if>>Fevrier</option>
                        <option value="3" <c:if test="${birthMonth==2}">selected="selected"</c:if>>Mars</option>
                        <option value="4" <c:if test="${birthMonth==3}">selected="selected"</c:if>>Avril</option>
                        <option value="5" <c:if test="${birthMonth==4}">selected="selected"</c:if>>Mai</option>
                        <option value="6" <c:if test="${birthMonth==5}">selected="selected"</c:if>>Juin</option>
                        <option value="7" <c:if test="${birthMonth==6}">selected="selected"</c:if>>Juillet</option>
                        <option value="8" <c:if test="${birthMonth==7}">selected="selected"</c:if>>Aout</option>
                        <option value="9" <c:if test="${birthMonth==8}">selected="selected"</c:if>>Septembre</option>
                        <option value="10" <c:if test="${birthMonth==9}">selected="selected"</c:if>>Octobre</option>
                        <option value="11" <c:if test="${birthMonth==10}">selected="selected"</c:if>>Novembre</option>
                        <option value="12" <c:if test="${birthMonth==11}">selected="selected"</c:if>>Décembre</option>
                </select>
                <select name="birthYear" id="birthYear" class="input_pulldown">
                	<option value=null <c:if test="${birthYear==null}">selected="selected"</c:if>>Année</option>
                    <c:forEach var="i" begin="0" end="100" step="1" varStatus ="status">
                        <option value="${year-i}" <c:if test="${birthYear==(year-i)}">selected="selected"</c:if>>${year-i}</option>
                    </c:forEach>
                </select>
            </td><td>${errorBirthDate}</td>
        </tr>       
        
        <tr><td><form:label path="gender">Genre</form:label></td>
           <td><form:radiobutton  path="gender" value="MALE"/>MALE
           <form:radiobutton   path="gender" value="FEMALE"/>FEMALE</td>
           <form:errors path="gender"  cssClass="error"/>
        </tr>
        
        <ryctag:input path="mail" label="Mail"/>
        <ryctag:checkbox path="nlSubscriber" label="Newsletters"/>
        
		<input type="hidden" name="id" value="${id}"/> <%-- We do not use form:hidden because user.id is sometimes null (fake user)--%>
		
        <tr><td><input type="submit" value="Sauver" /></td><td> <a href="user/${user.userName}">Annuler</a></td></tr>
    </ryctag:form> 

</body>
</html>