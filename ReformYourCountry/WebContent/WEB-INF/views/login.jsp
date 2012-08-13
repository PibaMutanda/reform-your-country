<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
      
       
       <form:form modelAttribute="loginUser" method="post">
       
             Pseudo/Adresse  é-mail <form:input path="identifier"/>&nbsp;&nbsp;<form:errors path="identifier"></form:errors><br><br>
             Votre mot de passe     <form:input path="password"/>&nbsp;&nbsp;<form:errors path="password"></form:errors><br><br>
             Je oublié mon<a href="">pseudo</a>ou mon<a href="">mot de passe</a><br>
                               <form:checkbox path="keepLoggedIn"/>Je souhaite rester connecté<br> 
          <input type="submit" value="Connexion"/>
          
       </form:form>
       
</body>
</html>