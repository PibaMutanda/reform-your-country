<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
       
       <form action="/loginsubmit" method="post">
           Identifier <input type="text" name="identifier"/><br><br>
           Password <input type="password"  name="password" /><br><br>
           keepLoggedIn <input type="checkbox" name="keepLoggedIn" /><br><br>
           <input type="submit" value="Connect"/>
       </form>
</body>
</html>