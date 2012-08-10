<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
       Connexion 
       <form action="/loginsubmit" method="post">
           Pseudo/Adresse é-mail <input type="text" name="identifier"/><br><br>
           Votre mot de passe <input type="password"  name="password" /><br><br>
           <input type="checkbox" name="keepLoggedIn" />Je souhaite rester connecté<br><br>
           <input type="submit" value="Connexion"/>
       </form>
</body>
</html>