<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
       
                <jsp:useBean id="now" class="java.util.Date" />
                <fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<h1>Editer un utilisateur</h1>

    <ryctag:form action="usereditsubmit" modelAttribute="user">
        <ryctag:input path="lastName" label="Nom" required="required"/>
        <ryctag:input path="firstName" label="Prénom"/>
        <ryctag:input path="userName" label="Pseudonyme"/>
        <tr>
            <td>Date de naissance</td>
            <td>
                <select name="birthDay" id="birthDay" class="input_pulldown">
                        <c:forEach var="i" begin="1" end="31" step="1" varStatus ="status">
                            <option value="${i}" <c:if test="${birthDay==i}">selected="selected"</c:if>>${i}</option>
                        </c:forEach> 
                </select>
                <select name="birthMonth" id="birthMonth" class="input_pulldown" >
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
                    <c:forEach var="i" begin="0" end="100" step="1" varStatus ="status">
                        <option value="${year-i}" <c:if test="${birthYear==(year-i)}">selected="selected"</c:if>>${year-i}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>       
        <tr><td><form:label path="gender">Genre</form:label></td>
           <td><form:radiobutton path="gender" value="MALE"/>MALE
           <form:radiobutton path="gender" value="FEMALE"/>FEMALE</td>
           <form:errors path="gender"  cssClass="error"/></tr>
        <ryctag:input path="mail" label="Mail"/>
        <ryctag:checkbox path="nlSubscriber" label="Newsletters"/>
        <form:hidden path="id" value="${id}"/> <%-- We need to add 'value=...' because of bug of Spring 3.1.2: the custom tag will render no value attribute if re redisplay the form after an validation error message --%>
        <tr><td><input type="submit" value="Sauver" /></td><td> <a href="user?id=${id}">Annuler</a></td></tr>
    </ryctag:form>
     <%--Script for the Birthday date picker--%>
            <script type="application/javascript">    
            $("#birthMonth").change(function () {
                var year = $('#birthDay').val();
                var month = $('#birthMonth').val();
                if ((year != 0) &&  (month!=0)) {
                    var lastday = 32 - new Date(year, month - 1, 32).getDate();
                    var selected_day = $('#birthDay').val();

                    // Change selected day if it is greater than the number of days in current month
                    if (selected_day > lastday) {
                        $('#birthDay  > option[value=' + selected_day + ']').attr('selected', false);
                        $('#birthDay  > option[value=' + lastday + ']').attr('selected', true);
                    }

                    // Remove possibly offending days
                    for (var i = lastday + 1; i < 32; i++) {
                        $('#birthDay  > option[value=' + i + ']').remove();  
                    }

                    // Add possibly missing days
                    for (var i = 29; i < lastday + 1; i++) {
                        if (!$('#birthDay  > option[value=' + i + ']').length) {
                            $('#birthDay').append($("<option></option>").attr("value",i).text(i));
                        } 
                    }
                }
            });
                </script>
</body>
</html>