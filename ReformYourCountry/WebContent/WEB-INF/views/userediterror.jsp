<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Erreur</h1>

<div class="edit-error" >
     <c:choose>
        <c:when test="${newUserName!=null}">
          Le pseudo '${newUserName}' est déjà utilisé<br/>
          <form action="useredit">
          <input type="hidden" name="id" value="${id}">
          	<input type="submit" value="modifier le pseudo">
          </form>
        </c:when>
        <c:when test="${newMail!=null}">
          L'adresse mail '${newMail}' est déjà utilisée<br/>
          <form action="useredit">
          <input type="hidden" name="id" value="${id}">
          	<input type="submit" value="modifier l'adresse mail">
          </form>
        </c:when>
        <c:otherwise>
            <a href="login">Connexion</a>
         </c:otherwise>
     </c:choose>
   
</div>



</body>
</html>