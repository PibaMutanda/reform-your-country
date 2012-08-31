<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<title>Login</title>
</head>
<body>
     ${error}
      <form action="loginsubmit" method="post">
             <label for="identifier">Pseudo/Adresse e-mail </label><input type="text" name="identifier" required="required"><br/>
             <ryc:conditionDisplay environment="PROD" >
                <label for="password">Votre mot de passe </label> <input type="password" name="password" required="required"><br/>
             </ryc:conditionDisplay>
             J'ai oublié  mon <a href="">mot de passe</a><br/>
             <input type="checkbox" name="keepLoggedIn"/><label for="keepLoggedIn">Je souhaite rester connecté</label><br/> 
             <input type="submit"  value="Connexion"/>                   
      </form> 
       
</body>
</html>