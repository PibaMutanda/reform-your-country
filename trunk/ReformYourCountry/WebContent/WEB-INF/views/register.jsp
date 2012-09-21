<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form" %>   
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 
<head>
<title>Inscription</title>
</head>
<body>
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
    	<td>
    	<span class="ka_button small_button small_royalblue">
    	<input type="submit" value="m'inscrire" />
    	</span>
    	</td>
    	</tr>
    	<a href="#" class="ka_button small_button small_royalblue" ><span>Small Button</span></a>
    </ryctag:form>

   <%--
     <form:form modelAttribute="user" action="registersubmit">
            <label for="userName">pseudo</label><form:input path="userName" required="required"/>
        <br />
            <label for="password">mot de passe</label> <form:password path="password" required="required"/>
        <br />
            <label for="mail">e-mail</label> <form:input path="mail" type="mail" required="required"/>
        <br />
         <input type="submit" value="m'inscrire" />
    </form:form>
    
    --%>
</body>