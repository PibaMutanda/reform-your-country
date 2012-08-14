<%@page import="org.zefer.html.doc.u"%>
<%@ page import="reformyourcountry.exception.UserAlreadyExistsException" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inscription</title>
</head>
<body>
     
      ${error}
      
      <form action="registersubmit" method="post">
      
            Pseudo <input type="text" name="userName"/><br><br>
            Votre mot de passe <input type="password" name="password"/><br><br>
            E-mail <input type="text" name="mail" /><br>
                   <input type="submit" value="m'inscrire" />
      </form>
</body>
</html>