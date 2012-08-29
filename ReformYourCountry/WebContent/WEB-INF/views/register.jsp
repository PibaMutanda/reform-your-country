<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form" %>    
<head>
<title>Inscription</title>
</head>
<body>
    ${error}
    <form:form modelAttribute="user" action="registersubmit">
            <label for="userName">pseudo</label><form:input path="userName" required="required"/>
        <br />
            <label for="password">mot de passe</label> <form:password path="password" required="required"/>
        <br />
            <label for="mail">e-mail</label> <form:input path="mail" type="mail" required="required"/>
        <br />
        <input type="submit" value="m'inscrire" />
    </form:form>
</body>