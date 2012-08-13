<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
      <form:form modelAttribute="" method="post">
      
              Pseudo  <form:input path="userName"/>&nbsp;&nbsp;<form:errors path="userName"></form:errors><br><br>
              Votre mot de passe     <form:input path="password"/>&nbsp;&nbsp;<form:errors path="password"></form:errors><br><br>
              É-mail <form:input path="mail" />&nbsp;&nbsp;<form:errors path="mail"></form:errors> <br><br>
              <input type="submit" value="m'inscrire"/>
              
      </form:form>
</body>
</html>