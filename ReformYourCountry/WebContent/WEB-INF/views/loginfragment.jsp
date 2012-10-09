<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ page import ="reformyourcountry.web.ContextUtil" %>


<!-- This fragment will be displayed in a jQuery dialog box. -->
    <label id ="errorMsg" style ="color:red;"></label>

	
		<label for="identifier">pseudo / adresse e-mail</label><br/>
        <input type="text" name="identifier" /><br/>
        <label for="password">mot de passe</label><br/>
		<c:choose>
			<c:when test="<%= !ContextUtil.devMode %>">				
				<input type="password" name="password" />
			</c:when>
  		    <c:otherwise>
			    <input type="password" name="password"  value="secret"/><br/>
			    Your are in dev mode: password is not verified (type any).<br/>
			</c:otherwise>
		</c:choose>
        J'ai <a href="">oublié mon mot de passe</a><br />
		<br />
		
		 
		

		<input id ="ryc" class="image-login" type="submit" value="me connecter"/>
	
