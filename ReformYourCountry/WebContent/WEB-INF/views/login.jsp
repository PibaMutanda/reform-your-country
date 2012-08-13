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
      
       
    
       
      <form action="/login" method="post">
             Pseudo/Adresse e-mail <input type="text" name="identifier"><br><br>
             Votre mot de passe <input type="password" name="password"><br>
             Je oublié  mon<a href="">  mot de passe</a><br>
                                <input type="checkbox" name="keepLoggedIn"/>Je souhaite rester connecté<br> 
             <input type="submit"  value="Connexion"/>                   
      </form> 
       
</body>
</html>