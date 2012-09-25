<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form" %>   
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 
<head>
<title>Inscription</title>
</head>
<body>
    <ryctag:pageheadertitle title="Inscription"/>
    ${error}
   <ryctag:form action="registersubmit" modelAttribute="user">
    	<ryctag:input path="userName" label="pseudo"/>
    	<ryctag:password path="password" label="mot de passe"/>
    	<tr>
	    	<td><label for="mail">e-mail</label></td>
    		<td><form:input path="mail" type="mail" required="required"  pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"/></td>
    		<td><form:errors path="mail" cssClass="error" /></td>
    	</tr>
    	<tr>
    	<td COLSPAN="2" align="center">
    	<span class="ka_button small_button small_royalblue" >
    	<input type="submit" value="m'inscrire" />
    	</span>
    	</td>
    	</tr>
    </ryctag:form>

</body>